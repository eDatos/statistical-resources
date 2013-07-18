package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationInstancesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationInstancesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetStatisticalOperationInstancesPaginatedListActionHandler
        extends
            SecurityActionHandler<GetStatisticalOperationInstancesPaginatedListAction, GetStatisticalOperationInstancesPaginatedListResult> {

    @Autowired
    private StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    public GetStatisticalOperationInstancesPaginatedListActionHandler() {
        super(GetStatisticalOperationInstancesPaginatedListAction.class);
    }

    @Override
    public GetStatisticalOperationInstancesPaginatedListResult executeSecurityAction(GetStatisticalOperationInstancesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = statisticalOperationsRestInternalFacade.findOperationInstances(action.getOperationCode(), action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetStatisticalOperationInstancesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
