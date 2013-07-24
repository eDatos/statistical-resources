package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetVersionsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteDatasetVersionsActionHandler extends SecurityActionHandler<DeleteDatasetVersionsAction, DeleteDatasetVersionsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteDatasetVersionsActionHandler() {
        super(DeleteDatasetVersionsAction.class);
    }

    @Override
    public DeleteDatasetVersionsResult executeSecurityAction(DeleteDatasetVersionsAction action) throws ActionException {
        try {
            for (String urn : action.getUrns()) {
                statisticalResourcesServiceFacade.deleteDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), urn);
            }
        } catch (MetamacException e) {
            WebExceptionUtils.createMetamacWebException(e);
        }
        return new DeleteDatasetVersionsResult();
    }
}
