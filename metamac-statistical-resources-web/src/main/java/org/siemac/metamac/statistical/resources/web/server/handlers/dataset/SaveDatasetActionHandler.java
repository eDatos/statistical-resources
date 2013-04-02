package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
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
    StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;
    
    public SaveDatasetActionHandler() {
        super(SaveDatasetAction.class);
    }

    @Override
    public SaveDatasetResult executeSecurityAction(SaveDatasetAction action) throws ActionException {
        try {
            DatasetDto savedDataset = null;
            if (action.getDataset().getUuid() == null) {
                ExternalItemDto statisticalOperation = statisticalOperationsRestInternalFacade.retrieveOperation(action.getStatisticalOperationCode());            
                savedDataset = statisticalResourcesServiceFacade.createDataset(ServiceContextHolder.getCurrentServiceContext(), action.getDataset(), statisticalOperation);
            } else {
                savedDataset = statisticalResourcesServiceFacade.updateDataset(ServiceContextHolder.getCurrentServiceContext(), action.getDataset());
            }
            return new SaveDatasetResult(savedDataset);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
    
    
}
