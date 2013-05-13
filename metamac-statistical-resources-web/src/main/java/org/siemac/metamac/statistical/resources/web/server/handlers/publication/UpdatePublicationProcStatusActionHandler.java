package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdatePublicationProcStatusActionHandler extends SecurityActionHandler<UpdatePublicationProcStatusAction, UpdatePublicationProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdatePublicationProcStatusActionHandler() {
        super(UpdatePublicationProcStatusAction.class);
    }

    @Override
    public UpdatePublicationProcStatusResult executeSecurityAction(UpdatePublicationProcStatusAction action) throws ActionException {

        // FIXME: invoke core

        String urn = action.getUrn();

        ProcStatusEnum procStatus = action.getNextProcStatus();
        try {
            PublicationDto publicationDto = null;
            if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
                publicationDto = MockServices.sendCollectionToProductionValidation(urn);
            } else if (ProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                publicationDto = MockServices.sendCollectionToDiffusionValidation(urn);
            } else if (ProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                ProcStatusEnum currentProcStatus = action.getCurrentProcStatus();
                if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(currentProcStatus)) {
                    publicationDto = MockServices.rejectCollectionProductionValidation(urn);
                } else if (ProcStatusEnum.DIFFUSION_VALIDATION.equals(currentProcStatus)) {
                    publicationDto = MockServices.rejectCollectionDiffusionValidation(urn);
                }
            } else if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                publicationDto = MockServices.publishCollection(urn);
            }
            return new UpdatePublicationProcStatusResult(publicationDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
