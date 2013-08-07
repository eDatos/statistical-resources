package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusResult.Builder;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateDatasetVersionProcStatusActionHandler extends SecurityActionHandler<UpdateDatasetVersionProcStatusAction, UpdateDatasetVersionProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateDatasetVersionProcStatusActionHandler() {
        super(UpdateDatasetVersionProcStatusAction.class);
    }

    @Override
    public UpdateDatasetVersionProcStatusResult executeSecurityAction(UpdateDatasetVersionProcStatusAction action) throws ActionException {

        try {

            DatasetVersionDto datasetVersionResult = null;

            ProcStatusEnum nextProcStatus = action.getNextProcStatus();

            switch (nextProcStatus) {
                case PRODUCTION_VALIDATION: {
                    for (DatasetVersionDto datasetVersionDto : action.getDatasetVersionsToUpdateProcStatus()) {
                        datasetVersionResult = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto);
                    }
                    break;
                }
                case DIFFUSION_VALIDATION: {
                    for (DatasetVersionDto datasetVersionDto : action.getDatasetVersionsToUpdateProcStatus()) {
                        datasetVersionResult = statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto);
                    }
                    break;
                }
                case VALIDATION_REJECTED: {
                    // TODO
                }
                case PUBLISHED: {
                    // TODO
                }
                default:
                    break;
            }

            Builder builder = new UpdateDatasetVersionProcStatusResult.Builder();

            if (action.getDatasetVersionsToUpdateProcStatus().size() == 1) {
                // TODO Remove this retrieve: this is here because the DTO that is returned by the CORE is not updated (its optimisticLocking value is not updated)
                DatasetVersionDto updatedDatasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action
                        .getDatasetVersionsToUpdateProcStatus().get(0).getUrn());
                builder.datasetVersionDto(updatedDatasetVersionDto);
            }

            return builder.build();

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
