package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasourceListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasourceListResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteDatasourceListActionHandler extends SecurityActionHandler<DeleteDatasourceListAction, DeleteDatasourceListResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteDatasourceListActionHandler() {
        super(DeleteDatasourceListAction.class);
    }

    @Override
    public DeleteDatasourceListResult executeSecurityAction(DeleteDatasourceListAction action) throws ActionException {
        try {
            for (String urn : action.getUrns()) {
                statisticalResourcesServiceFacade.deleteDatasource(ServiceContextHolder.getCurrentServiceContext(), urn);
            }
        } catch (MetamacException e) {
            WebExceptionUtils.createMetamacWebException(e);
        }
        return new DeleteDatasourceListResult();
    }
}
