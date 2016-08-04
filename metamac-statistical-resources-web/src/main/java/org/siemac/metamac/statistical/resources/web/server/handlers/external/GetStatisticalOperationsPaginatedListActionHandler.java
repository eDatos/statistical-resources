package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
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
        ExternalItemsResult result = statisticalOperationsRestInternalFacade.findOperations(action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetStatisticalOperationsPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }

}
