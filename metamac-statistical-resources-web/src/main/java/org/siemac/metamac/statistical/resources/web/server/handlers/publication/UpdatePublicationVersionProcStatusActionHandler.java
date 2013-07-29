package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdatePublicationVersionProcStatusActionHandler extends SecurityActionHandler<UpdatePublicationVersionProcStatusAction, UpdatePublicationVersionProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdatePublicationVersionProcStatusActionHandler() {
        super(UpdatePublicationVersionProcStatusAction.class);
    }

    @Override
    public UpdatePublicationVersionProcStatusResult executeSecurityAction(UpdatePublicationVersionProcStatusAction action) throws ActionException {

        try {

            PublicationVersionDto publicationVersionDto = null;

            ProcStatusEnum nextProcStatus = action.getNextProcStatus();

            switch (nextProcStatus) {
                case PRODUCTION_VALIDATION: {
                    publicationVersionDto = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus());
                    break;
                }
                case DIFFUSION_VALIDATION: {
                    publicationVersionDto = statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus());
                    break;
                }
                case VALIDATION_REJECTED: {
                    publicationVersionDto = statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(),
                            action.getPublicationVersionToUpdateProcStatus());
                }
                case PUBLISHED: {
                    // TODO
                }
                default:
                    break;
            }

            return new UpdatePublicationVersionProcStatusResult(publicationVersionDto);

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
