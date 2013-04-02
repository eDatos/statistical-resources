package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.RelatedResourceUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class LifeCycleResourceLifeCycleForm extends GroupDynamicForm {

    public LifeCycleResourceLifeCycleForm() {
        super(getConstants().formLifeCycle());

        ViewTextItem procStatus = new ViewTextItem(LifeCycleResourceDS.PROC_STATUS, getConstants().lifeCycleStatisticalResourceProcStatus());
        ViewTextItem creationDate = new ViewTextItem(LifeCycleResourceDS.CREATION_DATE, getConstants().lifeCycleStatisticalResourceCreationDate());
        ViewTextItem creationUser = new ViewTextItem(LifeCycleResourceDS.CREATION_USER, getConstants().lifeCycleStatisticalResourceCreationUser());
        ViewTextItem productionValidationDate = new ViewTextItem(LifeCycleResourceDS.PRODUCTION_VALIDATION_DATE, getConstants().lifeCycleStatisticalResourceProductionValidationDate());
        ViewTextItem productionValidationUser = new ViewTextItem(LifeCycleResourceDS.PRODUCTION_VALIDATION_USER, getConstants().lifeCycleStatisticalResourceProductionValidationUser());
        ViewTextItem diffusionValidationDate = new ViewTextItem(LifeCycleResourceDS.DIFFUSION_VALIDATION_DATE, getConstants().lifeCycleStatisticalResourceDiffusionValidationDate());
        ViewTextItem diffusionValidationUser = new ViewTextItem(LifeCycleResourceDS.DIFFUSION_VALIDATION_USER, getConstants().lifeCycleStatisticalResourceDiffusionValidationUser());
        ViewTextItem rejectValidationDate = new ViewTextItem(LifeCycleResourceDS.REJECT_VALIDATION_DATE, getConstants().lifeCycleStatisticalResourceRejectValidationDate());
        ViewTextItem rejectValidationUser = new ViewTextItem(LifeCycleResourceDS.REJECT_VALIDATION_USER, getConstants().lifeCycleStatisticalResourceRejectValidationUser());
        ViewTextItem internalPublicationDate = new ViewTextItem(LifeCycleResourceDS.INTERNAL_PUBLICATION_DATE, getConstants().lifeCycleStatisticalResourceInternalPublicationDate());
        ViewTextItem internalPublicationUser = new ViewTextItem(LifeCycleResourceDS.INTERNAL_PUBLICATION_USER, getConstants().lifeCycleStatisticalResourceInternalPublicationUser());
        ViewTextItem externalPublicationDate = new ViewTextItem(LifeCycleResourceDS.EXTERNAL_PUBLICATION_DATE, getConstants().lifeCycleStatisticalResourceExternalPublicationDate());
        ViewTextItem externalPublicationUser = new ViewTextItem(LifeCycleResourceDS.EXTERNAL_PUBLICATION_USER, getConstants().lifeCycleStatisticalResourceExternalPublicationUser());
        ViewTextItem isExternalPublicationFailed = new ViewTextItem(LifeCycleResourceDS.IS_EXTERNAL_PUBLICATION_FAILED, getConstants().lifeCycleStatisticalResourceExternalPublicationFailed());
        ViewTextItem isExternalPublicationFailedDate = new ViewTextItem(LifeCycleResourceDS.EXTERNAL_PUBLICATION_FAILED_DATE, getConstants()
                .lifeCycleStatisticalResourceExternalPublicationFailedDate());
        ViewTextItem replacesVersion = new ViewTextItem(LifeCycleResourceDS.REPLACES_VERSION, getConstants().lifeCycleStatisticalResourceReplacesVersion());
        ViewTextItem isReplacedByVersion = new ViewTextItem(LifeCycleResourceDS.IS_REPLACED_BY_VERSION, getConstants().lifeCycleStatisticalResourceIsReplacedByVersion());

        addFields(procStatus, creationDate, creationUser, productionValidationDate, productionValidationUser, diffusionValidationDate, diffusionValidationUser, rejectValidationDate,
                rejectValidationUser, internalPublicationDate, internalPublicationUser, internalPublicationUser, externalPublicationDate, externalPublicationUser, isExternalPublicationFailed,
                isExternalPublicationFailedDate, replacesVersion, isReplacedByVersion);
    }

    public void setLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        setValue(LifeCycleResourceDS.PROC_STATUS, CommonUtils.getProcStatusName(lifeCycleStatisticalResourceDto));
        setValue(LifeCycleResourceDS.CREATION_DATE, lifeCycleStatisticalResourceDto.getCreationDate());
        setValue(LifeCycleResourceDS.CREATION_USER, lifeCycleStatisticalResourceDto.getCreationUser());
        setValue(LifeCycleResourceDS.PRODUCTION_VALIDATION_DATE, lifeCycleStatisticalResourceDto.getProductionValidationDate());
        setValue(LifeCycleResourceDS.PRODUCTION_VALIDATION_USER, lifeCycleStatisticalResourceDto.getProductionValidationUser());
        setValue(LifeCycleResourceDS.DIFFUSION_VALIDATION_DATE, lifeCycleStatisticalResourceDto.getDiffusionValidationDate());
        setValue(LifeCycleResourceDS.DIFFUSION_VALIDATION_USER, lifeCycleStatisticalResourceDto.getDiffusionValidationUser());
        setValue(LifeCycleResourceDS.REJECT_VALIDATION_DATE, lifeCycleStatisticalResourceDto.getRejectValidationDate());
        setValue(LifeCycleResourceDS.REJECT_VALIDATION_USER, lifeCycleStatisticalResourceDto.getRejectValidationUser());
        setValue(LifeCycleResourceDS.INTERNAL_PUBLICATION_DATE, lifeCycleStatisticalResourceDto.getInternalPublicationDate());
        setValue(LifeCycleResourceDS.INTERNAL_PUBLICATION_USER, lifeCycleStatisticalResourceDto.getInternalPublicationUser());
        setValue(LifeCycleResourceDS.EXTERNAL_PUBLICATION_DATE, lifeCycleStatisticalResourceDto.getExternalPublicationDate());
        setValue(LifeCycleResourceDS.EXTERNAL_PUBLICATION_USER, lifeCycleStatisticalResourceDto.getExternalPublicationUser());
        setValue(LifeCycleResourceDS.IS_EXTERNAL_PUBLICATION_FAILED, CommonWebUtils.getBooleanValueAsString(lifeCycleStatisticalResourceDto.getExternalPublicationFailed()));
        setValue(LifeCycleResourceDS.EXTERNAL_PUBLICATION_FAILED_DATE, lifeCycleStatisticalResourceDto.getExternalPublicationFailedDate());
        setValue(LifeCycleResourceDS.REPLACES_VERSION, RelatedResourceUtils.getRelatedResourceName(lifeCycleStatisticalResourceDto.getReplacesVersion()));
        setValue(LifeCycleResourceDS.IS_REPLACED_BY_VERSION, RelatedResourceUtils.getRelatedResourceName(lifeCycleStatisticalResourceDto.getIsReplacedByVersion()));
    }
}
