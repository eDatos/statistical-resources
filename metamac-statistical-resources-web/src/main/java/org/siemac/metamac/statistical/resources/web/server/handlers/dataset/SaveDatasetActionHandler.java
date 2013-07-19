package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveDatasetActionHandler extends SecurityActionHandler<SaveDatasetAction, SaveDatasetResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    StatisticalOperationsRestInternalFacade   statisticalOperationsRestInternalFacade;

    public SaveDatasetActionHandler() {
        super(SaveDatasetAction.class);
    }

    @Override
    public SaveDatasetResult executeSecurityAction(SaveDatasetAction action) throws ActionException {
        try {
            DatasetVersionDto savedDataset = null;
            if (action.getDatasetVersion().getUuid() == null) {
                ExternalItemDto statisticalOperation = statisticalOperationsRestInternalFacade.retrieveOperation(action.getStatisticalOperationCode());
                savedDataset = statisticalResourcesServiceFacade.createDataset(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersion(), statisticalOperation);
            } else {
                savedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersion());
            }
            return new SaveDatasetResult(savedDataset);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
