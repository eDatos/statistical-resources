package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetAttributeInstancesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetAttributeInstancesResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteDatasetAttributeInstancesActionHandler extends SecurityActionHandler<DeleteDatasetAttributeInstancesAction, DeleteDatasetAttributeInstancesResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteDatasetAttributeInstancesActionHandler() {
        super(DeleteDatasetAttributeInstancesAction.class);
    }

    @Override
    public DeleteDatasetAttributeInstancesResult executeSecurityAction(DeleteDatasetAttributeInstancesAction action) throws ActionException {
        // TODO Auto-generated method stub
        return new DeleteDatasetAttributeInstancesResult();
    }
}
