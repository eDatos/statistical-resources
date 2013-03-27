package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.statistical.resources.web.shared.collection.UpdatePublicationProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.UpdatePublicationProcStatusResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdatePublicationProcStatusActionHandler extends SecurityActionHandler<UpdatePublicationProcStatusAction, UpdatePublicationProcStatusResult> {

    public UpdatePublicationProcStatusActionHandler() {
        super(UpdatePublicationProcStatusAction.class);
    }

    @Override
    public UpdatePublicationProcStatusResult executeSecurityAction(UpdatePublicationProcStatusAction action) throws ActionException {
        // String urn = action.getUrn();
        // StatisticalResourceProcStatusEnum procStatus = action.getNextProcStatus();
        // try {
        // PublicationDto collectionDto = null;
        // if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
        // collectionDto = MockServices.sendPublicationToProductionValidation(urn);
        // } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
        // collectionDto = MockServices.sendPublicationToDiffusionValidation(urn);
        // } else if (StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
        // StatisticalResourceProcStatusEnum currentProcStatus = action.getCurrentProcStatus();
        // if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(currentProcStatus)) {
        // collectionDto = MockServices.rejectPublicationProductionValidation(urn);
        // } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(currentProcStatus)) {
        // collectionDto = MockServices.rejectPublicationDiffusionValidation(urn);
        // }
        // } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.equals(procStatus)) {
        // collectionDto = MockServices.sendPublicationToPendingPublication(urn);
        // } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED.equals(procStatus)) {
        // collectionDto = MockServices.programPublicationPublication(urn);
        // } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.equals(procStatus)) {
        // collectionDto = MockServices.cancelProgrammedPublicationPublication(urn);
        // } else if (StatisticalResourceProcStatusEnum.PUBLISHED.equals(procStatus)) {
        // collectionDto = MockServices.publishPublication(urn);
        // } else if (StatisticalResourceProcStatusEnum.ARCHIVED.equals(procStatus)) {
        // collectionDto = MockServices.archivePublication(urn);
        // }
        // return new UpdateCollectionProcStatusResult(collectionDto);
        // } catch (MetamacException e) {
        // throw WebExceptionUtils.createMetamacWebException(e);
        // }

        // FIXME: invoke core
        return new UpdatePublicationProcStatusResult(null);
    }

}
