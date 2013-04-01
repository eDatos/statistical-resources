package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class LifeCycleResourceVersionEditionForm extends GroupDynamicForm {

    public LifeCycleResourceVersionEditionForm() {
        super(getConstants().formVersion());

        ViewTextItem versionLogic = new ViewTextItem(VersionableResourceDS.VERSION, getConstants().versionableStatisticalResourceVersionLogic());

        // TODO may be editable
        ViewTextItem versionRationaleTypes = new ViewTextItem(VersionableResourceDS.VERSION_RATIONALE_TYPES, getConstants().versionableStatisticalResourceVersionRationaleTypes());

        MultiLanguageTextItem versionRationale = new MultiLanguageTextItem(VersionableResourceDS.VERSION_RATIONALE, getConstants().versionableStatisticalResourceVersionRationale());

        ViewTextItem validFrom = new ViewTextItem(VersionableResourceDS.VALID_FROM, getConstants().versionableStatisticalResourceValidFrom());

        ViewTextItem validTo = new ViewTextItem(VersionableResourceDS.VALID_TO, getConstants().versionableStatisticalResourceValidTo());

        CustomSelectItem nextVersion = new CustomSelectItem(VersionableResourceDS.NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersion());
        nextVersion.setValueMap(CommonUtils.getStatisticalResourceNextVersionHashMap());

        CustomDateItem nextVersionDate = new CustomDateItem(VersionableResourceDS.DATE_NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersionDate());

        setFields(versionLogic, versionRationaleTypes, versionRationale, validFrom, validTo, nextVersion, nextVersionDate);
    }

    public void setLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        setValue(VersionableResourceDS.VERSION, lifeCycleStatisticalResourceDto.getVersionLogic());
        setValue(VersionableResourceDS.VERSION_RATIONALE_TYPES, CommonUtils.getStatisticalResourceVersionRationaleTypeNames(lifeCycleStatisticalResourceDto.getVersionRationaleTypes())); // TODO may be
                                                                                                                                                                                          // editable
        setValue(VersionableResourceDS.VERSION_RATIONALE, RecordUtils.getInternationalStringRecord(lifeCycleStatisticalResourceDto.getVersionRationale()));
        setValue(VersionableResourceDS.VALID_FROM, lifeCycleStatisticalResourceDto.getValidFrom());
        setValue(VersionableResourceDS.VALID_TO, lifeCycleStatisticalResourceDto.getValidTo());
        setValue(VersionableResourceDS.NEXT_VERSION, CommonUtils.getStatisticalResourceNextVersionName(lifeCycleStatisticalResourceDto.getNextVersion()));
        setValue(VersionableResourceDS.DATE_NEXT_VERSION, lifeCycleStatisticalResourceDto.getNextVersionDate());
    }

    public LifeCycleStatisticalResourceDto getLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        // TODO Version rationale types
        lifeCycleStatisticalResourceDto.setVersionRationale((InternationalStringDto) getValue(VersionableResourceDS.VERSION_RATIONALE));;
        lifeCycleStatisticalResourceDto.setNextVersion(!StringUtils.isBlank(getValueAsString(VersionableResourceDS.NEXT_VERSION)) ? StatisticalResourceNextVersionEnum
                .valueOf(getValueAsString(VersionableResourceDS.NEXT_VERSION)) : null);
        lifeCycleStatisticalResourceDto.setNextVersionDate(((CustomDateItem) getItem(VersionableResourceDS.DATE_NEXT_VERSION)).getValueAsDate());
        return lifeCycleStatisticalResourceDto;
    }
}
