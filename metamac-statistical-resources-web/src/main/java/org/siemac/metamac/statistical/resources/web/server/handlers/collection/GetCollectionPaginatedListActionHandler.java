package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetCollectionPaginatedListActionHandler extends SecurityActionHandler<GetCollectionPaginatedListAction, GetCollectionPaginatedListResult> {

    public GetCollectionPaginatedListActionHandler() {
        super(GetCollectionPaginatedListAction.class);
    }

    @Override
    public GetCollectionPaginatedListResult executeSecurityAction(GetCollectionPaginatedListAction action) throws ActionException {
        // MetamacCriteria criteria = new MetamacCriteria();
        // // Order
        // MetamacCriteriaOrder order = new MetamacCriteriaOrder();
        // order.setType(OrderTypeEnum.DESC);
        // order.setPropertyName(CollectionMetamacCriteriaOrderEnum.LAST_UPDATED.name());
        // List<MetamacCriteriaOrder> criteriaOrders = new ArrayList<MetamacCriteriaOrder>();
        // criteriaOrders.add(order);
        // criteria.setOrdersBy(criteriaOrders);
        // // Only find last versions
        // MetamacCriteriaPropertyRestriction propertyRestriction = new MetamacCriteriaPropertyRestriction(CollectionMetamacCriteriaPropertyEnum.IS_LAST_VERSION.name(), Boolean.TRUE,
        // OperationType.EQ);
        // criteria.setRestriction(propertyRestriction);
        // // Pagination
        // criteria.setPaginator(new MetamacCriteriaPaginator());
        // criteria.getPaginator().setFirstResult(action.getFirstResult());
        // criteria.getPaginator().setMaximumResultSize(action.getMaxResults());
        // criteria.getPaginator().setCountTotalResults(true);
        
        try {
            int firstResult = 0;
            int totalResults = 0;
            List<CollectionDto> collectionsDtos = MockServices.findCollections(action.getOperationUrn(), action.getFirstResult(), action.getMaxResults());
            return new GetCollectionPaginatedListResult(collectionsDtos, firstResult, totalResults);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
