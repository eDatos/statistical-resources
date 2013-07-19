package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetProcStatusResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateDatasetProcStatusActionHandler extends SecurityActionHandler<UpdateDatasetProcStatusAction, UpdateDatasetProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateDatasetProcStatusActionHandler() {
        super(UpdateDatasetProcStatusAction.class);
    }

    @Override
    public UpdateDatasetProcStatusResult executeSecurityAction(UpdateDatasetProcStatusAction action) throws ActionException {
        DatasetVersionDto datasetDto = action.getDatasetVersionDto();
        try {
            switch (action.getNextProcStatus()) {
                case PRODUCTION_VALIDATION:
                    datasetDto = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetDto);
                    break;
                case DIFFUSION_VALIDATION:
                    datasetDto = statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetDto);
                    break;
                default:
                    // TODO: handle more cases
                    break;
            }
            /*
             * if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
             * datasetDto = MockServices.sendDatasetToProductionValidation(urn);
             * } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
             * datasetDto = MockServices.sendDatasetToDiffusionValidation(urn);
             * } else if (StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
             * StatisticalResourceProcStatusEnum currentProcStatus = action.getCurrentProcStatus();
             * if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(currentProcStatus)) {
             * datasetDto = MockServices.rejectDatasetProductionValidation(urn);
             * } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(currentProcStatus)) {
             * datasetDto = MockServices.rejectDatasetDiffusionValidation(urn);
             * }
             * // FIXME: the checkings bellow need the date of pending publication
             * // } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED.equals(procStatus)) {
             * // datasetDto = MockServices.programDatasetPublication(urn);
             * // } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.equals(procStatus)) {
             * // datasetDto = MockServices.cancelProgrammedDatasetPublication(urn);
             * } else if (StatisticalResourceProcStatusEnum.PUBLISHED.equals(procStatus)) {
             * datasetDto = MockServices.publishDataset(urn);
             * }
             */

            return new UpdateDatasetProcStatusResult(datasetDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
