package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetQueryVersionActionHandler extends SecurityActionHandler<GetQueryVersionAction, GetQueryVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetQueryVersionActionHandler() {
        super(GetQueryVersionAction.class);
    }

    @Override
    public GetQueryVersionResult executeSecurityAction(GetQueryVersionAction action) throws ActionException {
        try {
            QueryVersionDto queryVersionDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getQueryUrn());
            return new GetQueryVersionResult(queryVersionDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
