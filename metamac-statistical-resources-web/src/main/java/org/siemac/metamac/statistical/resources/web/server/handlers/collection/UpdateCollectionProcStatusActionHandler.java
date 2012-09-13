package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.UpdateCollectionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.UpdateCollectionProcStatusResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateCollectionProcStatusActionHandler extends SecurityActionHandler<UpdateCollectionProcStatusAction, UpdateCollectionProcStatusResult> {

    public UpdateCollectionProcStatusActionHandler() {
        super(UpdateCollectionProcStatusAction.class);
    }

    @Override
    public UpdateCollectionProcStatusResult executeSecurityAction(UpdateCollectionProcStatusAction action) throws ActionException {
        String urn = action.getUrn();
        StatisticalResourceProcStatusEnum procStatus = action.getNextProcStatus();
        try {
            CollectionDto collectionDto = null;
            if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
                collectionDto = MockServices.sendCollectionToProductionValidation(urn);
            } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                collectionDto = MockServices.sendCollectionToDiffusionValidation(urn);
            } else if (StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                StatisticalResourceProcStatusEnum currentProcStatus = action.getCurrentProcStatus();
                if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(currentProcStatus)) {
                    collectionDto = MockServices.rejectCollectionProductionValidation(urn);
                } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(currentProcStatus)) {
                    collectionDto = MockServices.rejectCollectionDiffusionValidation(urn);
                }
            } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.equals(procStatus)) {
                collectionDto = MockServices.sendCollectionToPendingPublication(urn);
            } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED.equals(procStatus)) {
                collectionDto = MockServices.programCollectionPublication(urn);
            } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.equals(procStatus)) {
                collectionDto = MockServices.cancelProgrammedCollectionPublication(urn);
            } else if (StatisticalResourceProcStatusEnum.PUBLISHED.equals(procStatus)) {
                collectionDto = MockServices.publishCollection(urn);
            } else if (StatisticalResourceProcStatusEnum.ARCHIVED.equals(procStatus)) {
                collectionDto = MockServices.archiveCollection(urn);
            }
            return new UpdateCollectionProcStatusResult(collectionDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
