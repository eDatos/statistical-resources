package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.GetVersionsOfQueryAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetVersionsOfQueryResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetVersionsOfQueryActionHandler extends SecurityActionHandler<GetVersionsOfQueryAction, GetVersionsOfQueryResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetVersionsOfQueryActionHandler() {
        super(GetVersionsOfQueryAction.class);
    }

    @Override
    public GetVersionsOfQueryResult executeSecurityAction(GetVersionsOfQueryAction action) throws ActionException {
        try {
            List<QueryVersionBaseDto> queryVersionBaseDtos = statisticalResourcesServiceFacade.retrieveQueriesVersions(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionUrn());
            return new GetVersionsOfQueryResult(queryVersionBaseDtos);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
