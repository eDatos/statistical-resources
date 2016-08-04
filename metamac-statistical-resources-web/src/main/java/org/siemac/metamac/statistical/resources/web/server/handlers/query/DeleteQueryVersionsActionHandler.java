package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueryVersionsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteQueryVersionsActionHandler extends SecurityActionHandler<DeleteQueryVersionsAction, DeleteQueryVersionsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteQueryVersionsActionHandler() {
        super(DeleteQueryVersionsAction.class);
    }

    @Override
    public DeleteQueryVersionsResult executeSecurityAction(DeleteQueryVersionsAction action) throws ActionException {
        try {
            for (String urn : action.getUrns()) {
                statisticalResourcesServiceFacade.deleteQueryVersion(ServiceContextHolder.getCurrentServiceContext(), urn);
            }
            return new DeleteQueryVersionsResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
