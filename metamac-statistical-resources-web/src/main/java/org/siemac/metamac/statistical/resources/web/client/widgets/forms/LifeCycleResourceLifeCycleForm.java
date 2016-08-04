package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourceValue;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceLinkItem;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class LifeCycleResourceLifeCycleForm extends NavigationEnabledDynamicForm {

    private BaseUiHandlers uiHandlers;

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
        ViewTextItem publicationDate = new ViewTextItem(LifeCycleResourceDS.PUBLICATION_DATE, getConstants().lifeCycleStatisticalResourcePublicationDate());
        ViewTextItem publicationUser = new ViewTextItem(LifeCycleResourceDS.PUBLICATION_USER, getConstants().lifeCycleStatisticalResourcePublicationUser());
        RelatedResourceLinkItem replacesVersion = new RelatedResourceLinkItem(LifeCycleResourceDS.REPLACES_VERSION, getConstants().lifeCycleStatisticalResourceReplacesVersion(),
                getCustomLinkItemNavigationClickHandler());
        RelatedResourceLinkItem isReplacedByVersion = new RelatedResourceLinkItem(LifeCycleResourceDS.IS_REPLACED_BY_VERSION, getConstants().lifeCycleStatisticalResourceIsReplacedByVersion(),
                getCustomLinkItemNavigationClickHandler());

        addFields(procStatus, creationDate, creationUser, productionValidationDate, productionValidationUser, diffusionValidationDate, diffusionValidationUser, rejectValidationDate,
                rejectValidationUser, publicationDate, publicationUser, replacesVersion, isReplacedByVersion);
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
        setValue(LifeCycleResourceDS.PUBLICATION_DATE, lifeCycleStatisticalResourceDto.getPublicationDate());
        setValue(LifeCycleResourceDS.PUBLICATION_USER, lifeCycleStatisticalResourceDto.getPublicationUser());
        setRelatedResourceValue(getItem(LifeCycleResourceDS.REPLACES_VERSION), lifeCycleStatisticalResourceDto.getReplacesVersion());
        setRelatedResourceValue(getItem(LifeCycleResourceDS.IS_REPLACED_BY_VERSION), lifeCycleStatisticalResourceDto.getIsReplacedByVersion());
    }

    public void setUiHandlers(BaseUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
