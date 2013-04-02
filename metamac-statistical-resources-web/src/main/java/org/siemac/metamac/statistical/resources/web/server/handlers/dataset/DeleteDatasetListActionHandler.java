package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetListResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteDatasetListActionHandler extends SecurityActionHandler<DeleteDatasetListAction, DeleteDatasetListResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteDatasetListActionHandler() {
        super(DeleteDatasetListAction.class);
    }

    @Override
    public DeleteDatasetListResult executeSecurityAction(DeleteDatasetListAction action) throws ActionException {
        try {
            for (String urn : action.getUrns()) {
                statisticalResourcesServiceFacade.deleteDataset(ServiceContextHolder.getCurrentServiceContext(), urn);
            }
        } catch (MetamacException e) {
            WebExceptionUtils.createMetamacWebException(e);
        }
        return new DeleteDatasetListResult();
    }
}
