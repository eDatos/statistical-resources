package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionsProcStatusResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdatePublicationVersionsProcStatusActionHandler extends UpdateResourceProcStatusBaseActionHandler<UpdatePublicationVersionsProcStatusAction, UpdatePublicationVersionsProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdatePublicationVersionsProcStatusActionHandler() {
        super(UpdatePublicationVersionsProcStatusAction.class);
    }

    @Override
    public UpdatePublicationVersionsProcStatusResult executeSecurityAction(UpdatePublicationVersionsProcStatusAction action) throws ActionException {

        List<PublicationVersionBaseDto> publicationVersionsToUpdateProcStatus = action.getPublicationVersionsToUpdateProcStatus();
        LifeCycleActionEnum lifeCycleAction = action.getLifeCycleAction();

        MetamacException metamacException = new MetamacException();

        for (PublicationVersionBaseDto publicationVersionDto : publicationVersionsToUpdateProcStatus) {
            try {

                switch (lifeCycleAction) {
                    case SEND_TO_PRODUCTION_VALIDATION:
                        statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        break;

                    case SEND_TO_DIFFUSION_VALIDATION:
                        statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        break;

                    case REJECT_VALIDATION:
                        statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        break;

                    case PUBLISH:
                        if (action.getValidFrom() != null) {
                            statisticalResourcesServiceFacade.programPublicationPublicationVersion(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto, action.getValidFrom());
                        } else {
                            statisticalResourcesServiceFacade.publishPublicationVersion(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        }
                        break;
                    case CANCEL_PROGRAMMED_PUBLICATION:
                        statisticalResourcesServiceFacade.cancelPublicationPublicationVersion(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        break;
                    case VERSION:
                        statisticalResourcesServiceFacade.versioningPublicationVersion(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto, action.getVersionType());
                        break;

                    default:
                        break;
                }

            } catch (MetamacException e) {
                addExceptionsItemToMetamacException(lifeCycleAction, publicationVersionDto, metamacException, e);
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {
            return new UpdatePublicationVersionsProcStatusResult();
        } else {
            throw WebExceptionUtils.createMetamacWebException(metamacException);
        }
    }
}
