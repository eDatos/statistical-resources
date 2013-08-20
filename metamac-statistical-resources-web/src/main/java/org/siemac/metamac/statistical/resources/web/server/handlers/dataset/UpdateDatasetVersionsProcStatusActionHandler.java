package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionsProcStatusResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateDatasetVersionsProcStatusActionHandler extends UpdateResourceProcStatusBaseActionHandler<UpdateDatasetVersionsProcStatusAction, UpdateDatasetVersionsProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateDatasetVersionsProcStatusActionHandler() {
        super(UpdateDatasetVersionsProcStatusAction.class);
    }

    @Override
    public UpdateDatasetVersionsProcStatusResult executeSecurityAction(UpdateDatasetVersionsProcStatusAction action) throws ActionException {

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
                        statisticalResourcesServiceFacade.sendDatasetVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto);
                        break;

                    case PUBLISH:
                        // TODO
                        break;

                    case VERSION:
                        statisticalResourcesServiceFacade.versioningDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto, action.getVersionType());
                        break;

                    default:
                        break;
                }

            } catch (MetamacException e) {
                addExceptionsItemToMetamacException(lifeCycleAction, datasetVersionDto, metamacException, e);
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {
            return new UpdateDatasetVersionsProcStatusResult();
        } else {
            throw WebExceptionUtils.createMetamacWebException(metamacException);
        }
    }
}
