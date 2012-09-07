package org.siemac.metamac.statistical.resources.web.server.handlers.operation;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetStatisticalOperationsPaginatedListActionHandler extends SecurityActionHandler<GetStatisticalOperationsPaginatedListAction, GetStatisticalOperationsPaginatedListResult> {

    @Autowired
    StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    public GetStatisticalOperationsPaginatedListActionHandler() {
        super(GetStatisticalOperationsPaginatedListAction.class);
    }

    @Override
    public GetStatisticalOperationsPaginatedListResult executeSecurityAction(GetStatisticalOperationsPaginatedListAction action) throws ActionException {
        int firstResult = 0;
        int totalResults = 0;
        List<ExternalItemDto> externalItemDtos = statisticalOperationsRestInternalFacade.findOperations(action.getFirstResult(), action.getMaxResults(), action.getOperation());
        return new GetStatisticalOperationsPaginatedListResult(externalItemDtos, firstResult, totalResults);
    }

}
