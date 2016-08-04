package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.SearchVersionRationaleTypeItem;
import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;

public class LifeCycleResourceVersionForm extends GroupDynamicForm {

    public LifeCycleResourceVersionForm() {
        super(getConstants().formVersion());

        ViewTextItem versionLogic = new ViewTextItem(VersionableResourceDS.VERSION, getConstants().versionableStatisticalResourceVersionLogic());
        SearchVersionRationaleTypeItem versionRationaleTypeItem = new SearchVersionRationaleTypeItem(VersionableResourceDS.VERSION_RATIONALE_TYPES, getConstants()
                .versionableStatisticalResourceVersionRationaleTypes(), false);
        ViewMultiLanguageTextItem versionRationale = new ViewMultiLanguageTextItem(VersionableResourceDS.VERSION_RATIONALE, getConstants().versionableStatisticalResourceVersionRationale());
        ViewTextItem validFrom = new ViewTextItem(VersionableResourceDS.VALID_FROM, getConstants().versionableStatisticalResourceValidFrom());
        ViewTextItem validTo = new ViewTextItem(VersionableResourceDS.VALID_TO, getConstants().versionableStatisticalResourceValidTo());
        ViewTextItem nextVersion = new ViewTextItem(VersionableResourceDS.NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersion());
        ViewTextItem nextVersionView = new ViewTextItem(VersionableResourceDS.NEXT_VERSION_VIEW, getConstants().versionableStatisticalResourceNextVersion());
        nextVersionView.setVisible(false);
        ViewTextItem nextVersionDate = new ViewTextItem(VersionableResourceDS.DATE_NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersionDate());
        nextVersionDate.setShowIfCondition(getNextVersionDateFormItemIfFunction());

        setFields(versionLogic, versionRationaleTypeItem, versionRationale, validFrom, validTo, nextVersion, nextVersionDate);
    }

    public void setLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        setValue(VersionableResourceDS.VERSION, lifeCycleStatisticalResourceDto.getVersionLogic());
        ((SearchVersionRationaleTypeItem) getItem(VersionableResourceDS.VERSION_RATIONALE_TYPES)).setVersionRationaleTypes(lifeCycleStatisticalResourceDto.getVersionRationaleTypes());
        setValue(VersionableResourceDS.VERSION_RATIONALE, lifeCycleStatisticalResourceDto.getVersionRationale());
        setValue(VersionableResourceDS.VALID_FROM, DateUtils.getFormattedDateTime(lifeCycleStatisticalResourceDto.getValidFrom()));
        setValue(VersionableResourceDS.VALID_TO, DateUtils.getFormattedDateTime(lifeCycleStatisticalResourceDto.getValidTo()));
        setValue(VersionableResourceDS.NEXT_VERSION, CommonUtils.getStatisticalResourceNextVersionName(lifeCycleStatisticalResourceDto.getNextVersion()));
        setValue(VersionableResourceDS.NEXT_VERSION_VIEW, lifeCycleStatisticalResourceDto.getNextVersion() == null ? StringUtils.EMPTY : lifeCycleStatisticalResourceDto.getNextVersion().name());
        setValue(VersionableResourceDS.DATE_NEXT_VERSION, lifeCycleStatisticalResourceDto.getNextVersionDate());
        markForRedraw();
    }

    protected FormItemIfFunction getNextVersionDateFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return isNextVersionScheduledUpdate();
            }
        };
    }

    protected boolean isNextVersionScheduledUpdate() {
        String nextVersionValue = getValueAsString(VersionableResourceDS.NEXT_VERSION_VIEW);
        return StringUtils.equals(NextVersionTypeEnum.SCHEDULED_UPDATE.name(), nextVersionValue);
    }
}
