package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class LifeCycleResourceVersionForm extends GroupDynamicForm {

    public LifeCycleResourceVersionForm() {
        super(getConstants().formVersion());

        ViewTextItem versionLogic = new ViewTextItem(VersionableResourceDS.VERSION, getConstants().versionableStatisticalResourceVersionLogic());
        ViewTextItem versionRationaleTypes = new ViewTextItem(VersionableResourceDS.VERSION_RATIONALE_TYPES, getConstants().versionableStatisticalResourceVersionRationaleTypes());
        ViewMultiLanguageTextItem versionRationale = new ViewMultiLanguageTextItem(VersionableResourceDS.VERSION_RATIONALE, getConstants().versionableStatisticalResourceVersionRationale());
        ViewTextItem validFrom = new ViewTextItem(VersionableResourceDS.VALID_FROM, getConstants().versionableStatisticalResourceValidFrom());
        ViewTextItem validTo = new ViewTextItem(VersionableResourceDS.VALID_TO, getConstants().versionableStatisticalResourceValidTo());
        ViewTextItem nextVersion = new ViewTextItem(VersionableResourceDS.NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersion());
        ViewTextItem nextVersionDate = new ViewTextItem(VersionableResourceDS.DATE_NEXT_VERSION, getConstants().versionableStatisticalResourceNextVersionDate());

        setFields(versionLogic, versionRationaleTypes, versionRationale, validFrom, validTo, nextVersion, nextVersionDate);
    }

    public void setLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        setValue(VersionableResourceDS.VERSION, lifeCycleStatisticalResourceDto.getVersionLogic());
        setValue(VersionableResourceDS.VERSION_RATIONALE_TYPES, CommonUtils.getStatisticalResourceVersionRationaleTypeNames(lifeCycleStatisticalResourceDto.getVersionRationaleTypes()));
        setValue(VersionableResourceDS.VERSION_RATIONALE, RecordUtils.getInternationalStringRecord(lifeCycleStatisticalResourceDto.getVersionRationale()));
        setValue(VersionableResourceDS.VALID_FROM, lifeCycleStatisticalResourceDto.getValidFrom());
        setValue(VersionableResourceDS.VALID_TO, lifeCycleStatisticalResourceDto.getValidTo());
        setValue(VersionableResourceDS.NEXT_VERSION, CommonUtils.getStatisticalResourceNextVersionName(lifeCycleStatisticalResourceDto.getNextVersion()));
        setValue(VersionableResourceDS.DATE_NEXT_VERSION, lifeCycleStatisticalResourceDto.getNextVersionDate());
    }
}