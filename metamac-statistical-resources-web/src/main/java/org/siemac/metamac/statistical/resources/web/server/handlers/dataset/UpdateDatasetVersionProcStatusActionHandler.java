package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusResult.Builder;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateDatasetVersionProcStatusActionHandler extends UpdateResourceProcStatusBaseActionHandler<UpdateDatasetVersionProcStatusAction, UpdateDatasetVersionProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateDatasetVersionProcStatusActionHandler() {
        super(UpdateDatasetVersionProcStatusAction.class);
    }

    @Override
    public UpdateDatasetVersionProcStatusResult executeSecurityAction(UpdateDatasetVersionProcStatusAction action) throws ActionException {

        List<DatasetVersionDto> datasetVersionsToUpdateProcStatus = action.getDatasetVersionsToUpdateProcStatus();
        LifeCycleActionEnum lifeCycleAction = action.getLifeCycleAction();

        MetamacException metamacException = new MetamacException();

        for (DatasetVersionDto datasetVersionDto : datasetVersionsToUpdateProcStatus) {
            try {

                switch (lifeCycleAction) {
                    case SEND_TO_PRODUCTION_VALIDATION:
                        statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto);
                        break;

                    case SEND_TO_DIFFUSION_VALIDATION:
                        statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto);
                        break;

                    case REJECT_VALIDATION:
                        // TODO
                        break;

                    case PUBLISH:
                        // TODO
                        break;

                    case VERSION:
                        // TODO
                        break;

                    default:
                        break;
                }

            } catch (MetamacException e) {
                if (datasetVersionsToUpdateProcStatus.size() == 1) {
                    // If there was only one resource, throw the exception
                    throw WebExceptionUtils.createMetamacWebException(e);
                } else {
                    // If there were more than one resource, the messages should be shown in a tree structure
                    addExceptionsItemToMetamacException(lifeCycleAction, datasetVersionDto, metamacException, e);
                }
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {

            // If there were no exceptions...

            Builder builder = new UpdateDatasetVersionProcStatusResult.Builder();

            if (datasetVersionsToUpdateProcStatus.size() == 1) {
                try {
                    DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(),
                            datasetVersionsToUpdateProcStatus.get(0).getUrn());
                    builder.datasetVersionDto(datasetVersionDto);
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
