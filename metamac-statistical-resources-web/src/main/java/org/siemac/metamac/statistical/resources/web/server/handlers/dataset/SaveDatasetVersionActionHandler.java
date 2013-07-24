package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveDatasetVersionActionHandler extends SecurityActionHandler<SaveDatasetVersionAction, SaveDatasetVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    StatisticalOperationsRestInternalFacade   statisticalOperationsRestInternalFacade;

    public SaveDatasetVersionActionHandler() {
        super(SaveDatasetVersionAction.class);
    }

    @Override
    public SaveDatasetVersionResult executeSecurityAction(SaveDatasetVersionAction action) throws ActionException {
        try {
            DatasetVersionDto savedDataset = null;
            if (action.getDatasetVersion().getUuid() == null) {
                ExternalItemDto statisticalOperation = statisticalOperationsRestInternalFacade.retrieveOperation(action.getStatisticalOperationCode());
                savedDataset = statisticalResourcesServiceFacade.createDataset(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersion(), statisticalOperation);
            } else {
                savedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersion());
            }
            return new SaveDatasetVersionResult(savedDataset);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
