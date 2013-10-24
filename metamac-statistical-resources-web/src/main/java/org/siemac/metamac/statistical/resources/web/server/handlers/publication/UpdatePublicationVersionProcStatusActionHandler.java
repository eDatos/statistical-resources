package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdatePublicationVersionProcStatusActionHandler extends UpdateResourceProcStatusBaseActionHandler<UpdatePublicationVersionProcStatusAction, UpdatePublicationVersionProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdatePublicationVersionProcStatusActionHandler() {
        super(UpdatePublicationVersionProcStatusAction.class);
    }

    @Override
    public UpdatePublicationVersionProcStatusResult executeSecurityAction(UpdatePublicationVersionProcStatusAction action) throws ActionException {

        LifeCycleActionEnum lifeCycleAction = action.getLifeCycleAction();

        PublicationVersionDto publicationVersionDto = null;

        try {

            switch (lifeCycleAction) {
                case SEND_TO_PRODUCTION_VALIDATION:
                    publicationVersionDto = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus());
                    break;

                case SEND_TO_DIFFUSION_VALIDATION:
                    publicationVersionDto = statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus());
                    break;

                case REJECT_VALIDATION:
                    publicationVersionDto = statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus());
                    break;

                case PUBLISH:
                    if (action.getValidFrom() != null) {
                        publicationVersionDto = statisticalResourcesServiceFacade.programPublicationPublicationVersion(ServiceContextHolder.getCurrentServiceContext(),
                                action.getPublicationVersionToUpdateProcStatus(), action.getValidFrom());
                    } else {
                        publicationVersionDto = statisticalResourcesServiceFacade.publishPublicationVersion(ServiceContextHolder.getCurrentServiceContext(),
                                action.getPublicationVersionToUpdateProcStatus());
                    }
                    break;
                case CANCEL_PROGRAMMED_PUBLICATION:
                    publicationVersionDto = statisticalResourcesServiceFacade.cancelPublicationPublicationVersion(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus());
                    break;

                case VERSION:
                    publicationVersionDto = statisticalResourcesServiceFacade.versioningPublicationVersion(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus(), action.getVersionType());
                    break;

                default:
                    break;
            }

            return new UpdatePublicationVersionProcStatusResult(publicationVersionDto);

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
