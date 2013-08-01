package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.SearchVersionRationaleTypeItem;
import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class LifeCycleResourceVersionEditionForm extends GroupDynamicForm {

    protected ProcStatusEnum procStatus;

    public LifeCycleResourceVersionEditionForm() {
        super(getConstants().formVersion());

        ViewTextItem versionLogic = new ViewTextItem(VersionableResourceDS.VERSION, getConstants().versionableStatisticalResourceVersionLogic());

        SearchVersionRationaleTypeItem versionRationaleTypeItem = new SearchVersionRationaleTypeItem(VersionableResourceDS.VERSION_RATIONALE_TYPES, getConstants()
                .versionableStatisticalResourceVersionRationaleTypes());
        versionRationaleTypeItem.setSourceVersionRationaleTypeDtos(CommonUtils.getVersionRationaleTypeValues());

        MultilanguageRichTextEditorItem versionRationale = new MultilanguageRichTextEditorItem(VersionableResourceDS.VERSION_RATIONALE, getConstants().versionableStatisticalResourceVersionRationale());
        versionRationale.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                if (CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus)) {
                    // TODO It is required only if the versionRationaleType == MINOR_ERRATA
                }
                return true;
            }
        });

        ViewTextItem validFrom = new ViewTextItem(VersionableResourceDS.VALID_FROM, getConstants().versionableStatisticalResourceValidFrom());

        ViewTextItem validTo = new ViewTextItem(VersionableResourceDS.VALID_TO, getConstants().versionableStatisticalResourceValidTo());

        final CustomSelectItem nextVersion = new CustomSelectItem(VersionableResourceDS.NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersion());
        nextVersion.setValueMap(CommonUtils.getStatisticalResourceNextVersionHashMap());
        nextVersion.addChangedHandler(FormItemUtils.getMarkForRedrawChangedHandler(this));
        nextVersion.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? !StringUtils.isBlank(nextVersion.getValueAsString()) : true;
            }
        });

        CustomDateItem nextVersionDate = new CustomDateItem(VersionableResourceDS.DATE_NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersionDate());
        nextVersionDate.setShowIfCondition(getNextVersionDateFormItemIfFunction());

        setFields(versionLogic, versionRationaleTypeItem, versionRationale, validFrom, validTo, nextVersion, nextVersionDate);
    }

    public void setLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        this.procStatus = lifeCycleStatisticalResourceDto.getProcStatus();

        setValue(VersionableResourceDS.VERSION, lifeCycleStatisticalResourceDto.getVersionLogic());

        ((SearchVersionRationaleTypeItem) getItem(VersionableResourceDS.VERSION_RATIONALE_TYPES)).setVersionRationaleTypes(lifeCycleStatisticalResourceDto.getVersionRationaleTypes());

        setValue(VersionableResourceDS.VERSION_RATIONALE, RecordUtils.getInternationalStringRecord(lifeCycleStatisticalResourceDto.getVersionRationale()));
        setValue(VersionableResourceDS.VALID_FROM, lifeCycleStatisticalResourceDto.getValidFrom());
        setValue(VersionableResourceDS.VALID_TO, lifeCycleStatisticalResourceDto.getValidTo());
        setValue(VersionableResourceDS.NEXT_VERSION, lifeCycleStatisticalResourceDto.getNextVersion() != null ? lifeCycleStatisticalResourceDto.getNextVersion().name() : null);
        setValue(VersionableResourceDS.DATE_NEXT_VERSION, lifeCycleStatisticalResourceDto.getNextVersionDate());

        markForRedraw();
    }

    public LifeCycleStatisticalResourceDto getLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {

        lifeCycleStatisticalResourceDto.getVersionRationaleTypes().clear();
        lifeCycleStatisticalResourceDto.getVersionRationaleTypes().addAll(
                ((SearchVersionRationaleTypeItem) getItem(VersionableResourceDS.VERSION_RATIONALE_TYPES)).getSelectedVersionRationaleTypeDtos());

        lifeCycleStatisticalResourceDto.setVersionRationale((InternationalStringDto) getValue(VersionableResourceDS.VERSION_RATIONALE));;
        lifeCycleStatisticalResourceDto.setNextVersion(!StringUtils.isBlank(getValueAsString(VersionableResourceDS.NEXT_VERSION)) ? NextVersionTypeEnum
                .valueOf(getValueAsString(VersionableResourceDS.NEXT_VERSION)) : null);
        lifeCycleStatisticalResourceDto.setNextVersionDate(((CustomDateItem) getItem(VersionableResourceDS.DATE_NEXT_VERSION)).getValueAsDate());
        return lifeCycleStatisticalResourceDto;
    }

    private FormItemIfFunction getNextVersionDateFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Show item if the next version is SCHEDULED_UPDATE
                String nextVersionValue = form.getValueAsString(VersionableResourceDS.NEXT_VERSION);
                return StringUtils.equals(NextVersionTypeEnum.SCHEDULED_UPDATE.toString(), nextVersionValue);
            }
        };
    }
}
