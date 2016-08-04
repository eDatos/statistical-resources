package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetStatisticalOperationActionHandler extends SecurityActionHandler<GetStatisticalOperationAction, GetStatisticalOperationResult> {

    @Autowired
    StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    public GetStatisticalOperationActionHandler() {
        super(GetStatisticalOperationAction.class);
    }

    @Override
    public GetStatisticalOperationResult executeSecurityAction(GetStatisticalOperationAction action) throws ActionException {
        String code = UrnUtils.removePrefix(action.getUrn());
        ExternalItemDto externalItemDto = statisticalOperationsRestInternalFacade.retrieveOperation(code);
        return new GetStatisticalOperationResult(externalItemDto);
    }
}
