package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
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

        StatisticalResourceProcStatusEnum procStatus = action.getNextProcStatus();
        try {
            PublicationDto publicationDto = null;
            if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
                publicationDto = MockServices.sendCollectionToProductionValidation(urn);
            } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                publicationDto = MockServices.sendCollectionToDiffusionValidation(urn);
            } else if (StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                StatisticalResourceProcStatusEnum currentProcStatus = action.getCurrentProcStatus();
                if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(currentProcStatus)) {
                    publicationDto = MockServices.rejectCollectionProductionValidation(urn);
                } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(currentProcStatus)) {
                    publicationDto = MockServices.rejectCollectionDiffusionValidation(urn);
                }
            } else if (StatisticalResourceProcStatusEnum.PUBLISHED.equals(procStatus)) {
                publicationDto = MockServices.publishCollection(urn);
            }
            return new UpdatePublicationProcStatusResult(publicationDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
