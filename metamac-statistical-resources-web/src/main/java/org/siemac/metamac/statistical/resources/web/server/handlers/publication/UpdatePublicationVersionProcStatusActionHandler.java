package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusResult.Builder;
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

            PublicationVersionDto publicationVersionResult = null;

            ProcStatusEnum nextProcStatus = action.getNextProcStatus();

            switch (nextProcStatus) {
                case PRODUCTION_VALIDATION: {
                    for (PublicationVersionDto publicationVersionDto : action.getPublicationVersionsToUpdateProcStatus()) {
                        publicationVersionResult = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(),
                                publicationVersionDto);
                    }
                    break;
                }
                case DIFFUSION_VALIDATION: {
                    for (PublicationVersionDto publicationVersionDto : action.getPublicationVersionsToUpdateProcStatus()) {
                        publicationVersionResult = statisticalResourcesServiceFacade
                                .sendPublicationVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                    }
                    break;
                }
                case VALIDATION_REJECTED: {
                    for (PublicationVersionDto publicationVersionDto : action.getPublicationVersionsToUpdateProcStatus()) {
                        publicationVersionResult = statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                    }
                }
                case PUBLISHED: {
                    // TODO
                }
                default:
                    break;
            }

            Builder builder = new UpdatePublicationVersionProcStatusResult.Builder();
            builder.publicationVersionDto(publicationVersionResult);
            return builder.build();

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
