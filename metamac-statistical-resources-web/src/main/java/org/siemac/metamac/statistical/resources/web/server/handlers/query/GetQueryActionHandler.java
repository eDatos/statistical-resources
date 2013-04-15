package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetQueryActionHandler extends SecurityActionHandler<GetQueryAction, GetQueryResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetQueryActionHandler() {
        super(GetQueryAction.class);
    }

    @Override
    public GetQueryResult executeSecurityAction(GetQueryAction action) throws ActionException {
        try {
            QueryDto queryDto = statisticalResourcesServiceFacade.retrieveQueryByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getQueryUrn());
            return new GetQueryResult(queryDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
