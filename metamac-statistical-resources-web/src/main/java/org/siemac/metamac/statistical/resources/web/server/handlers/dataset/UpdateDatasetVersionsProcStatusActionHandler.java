package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
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

        List<DatasetVersionBaseDto> datasetVersionsToUpdateProcStatus = action.getDatasetVersionsToUpdateProcStatus();
        LifeCycleActionEnum lifeCycleAction = action.getLifeCycleAction();

        MetamacException metamacException = new MetamacException();

        for (DatasetVersionBaseDto datasetVersionBaseDto : datasetVersionsToUpdateProcStatus) {
            try {

                switch (lifeCycleAction) {
                    case SEND_TO_PRODUCTION_VALIDATION:
                        statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionBaseDto);
                        break;

                    case SEND_TO_DIFFUSION_VALIDATION:
                        statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionBaseDto);
                        break;

                    case REJECT_VALIDATION:
                        statisticalResourcesServiceFacade.sendDatasetVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(), datasetVersionBaseDto);
                        break;

                    case PUBLISH:
                        if (action.getValidFrom() != null) {
                            statisticalResourcesServiceFacade.programPublicationDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), datasetVersionBaseDto, action.getValidFrom());
                        } else {
                            statisticalResourcesServiceFacade.publishDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), datasetVersionBaseDto);
                        }
                        break;
                    case CANCEL_PROGRAMMED_PUBLICATION:
                        statisticalResourcesServiceFacade.cancelPublicationDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), datasetVersionBaseDto);
                        break;
                    case VERSION:
                        statisticalResourcesServiceFacade.versioningDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), datasetVersionBaseDto, action.getVersionType());
                        break;
                    default:
                        break;
                }

            } catch (MetamacException e) {
                addExceptionsItemToMetamacException(lifeCycleAction, datasetVersionBaseDto, metamacException, e);
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {
            return new UpdateDatasetVersionsProcStatusResult();
        } else {
            throw WebExceptionUtils.createMetamacWebException(metamacException);
        }
    }
}
