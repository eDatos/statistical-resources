package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueriesAction;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueriesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteQueriesActionHandler extends SecurityActionHandler<DeleteQueriesAction, DeleteQueriesResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteQueriesActionHandler() {
        super(DeleteQueriesAction.class);
    }

    @Override
    public DeleteQueriesResult executeSecurityAction(DeleteQueriesAction action) throws ActionException {
        try {
            for (String urn : action.getUrns()) {
                statisticalResourcesServiceFacade.deleteQuery(ServiceContextHolder.getCurrentServiceContext(), urn);
            }
            return new DeleteQueriesResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
