package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusResult.Builder;
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

        List<PublicationVersionDto> publicationVersionsToUpdateProcStatus = action.getPublicationVersionsToUpdateProcStatus();
        ProcStatusEnum nextProcStatus = action.getNextProcStatus();

        MetamacException metamacException = new MetamacException();

        for (PublicationVersionDto publicationVersionDto : publicationVersionsToUpdateProcStatus) {
            try {

                switch (nextProcStatus) {
                    case PRODUCTION_VALIDATION:
                        statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        break;

                    case DIFFUSION_VALIDATION:
                        statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        break;

                    case VALIDATION_REJECTED:
                        statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(), publicationVersionDto);
                        break;

                    case PUBLISHED:
                        // TODO
                        break;

                    default:
                        break;
                }

            } catch (MetamacException e) {
                if (publicationVersionsToUpdateProcStatus.size() == 1) {
                    // If there was only one resource, throw the exception
                    throw WebExceptionUtils.createMetamacWebException(e);
                } else {
                    // If there were more than one resource, the messages should be shown in a tree structure
                    addExceptionsItemToMetamacException(nextProcStatus, publicationVersionDto, metamacException, e);
                }
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {

            // If there were no exceptions...

            Builder builder = new UpdatePublicationVersionProcStatusResult.Builder();

            if (publicationVersionsToUpdateProcStatus.size() == 1) {
                try {
                    PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(ServiceContextHolder.getCurrentServiceContext(),
                            publicationVersionsToUpdateProcStatus.get(0).getUrn());
                    builder.publicationVersionDto(publicationVersionDto);
                } catch (MetamacException e) {
                    throw WebExceptionUtils.createMetamacWebException(e);
                }
            }

            return builder.build();

        } else {

            // Throw the captured exceptions
            throw WebExceptionUtils.createMetamacWebException(metamacException);
        }
    }
}
