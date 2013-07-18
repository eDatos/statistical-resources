package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryAction;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveQueryActionHandler extends SecurityActionHandler<SaveQueryAction, SaveQueryResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveQueryActionHandler() {
        super(SaveQueryAction.class);
    }

    @Override
    public SaveQueryResult executeSecurityAction(SaveQueryAction action) throws ActionException {
        try {
            QueryDto savedQuery = null;
            if (action.getQuery().getId() == null) {
                savedQuery = statisticalResourcesServiceFacade.createQuery(ServiceContextHolder.getCurrentServiceContext(), action.getQuery());
            } else {
                savedQuery = statisticalResourcesServiceFacade.updateQueryVersion(ServiceContextHolder.getCurrentServiceContext(), action.getQuery());
            }
            return new SaveQueryResult(savedQuery);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
