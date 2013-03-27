package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationsResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetPublicationsActionHandler extends SecurityActionHandler<GetPublicationsAction, GetPublicationsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetPublicationsActionHandler() {
        super(GetPublicationsAction.class);
    }

    @Override
    public GetPublicationsResult executeSecurityAction(GetPublicationsAction action) throws ActionException {
        // FIXME: invoke core
        // MetamacCriteria criteria = new MetamacCriteria();
        // Order
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
            List<PublicationDto> collectionsDtos = MockServices.findCollections(action.getOperationUrn(), action.getFirstResult(), action.getMaxResults());
            return new GetPublicationsResult(collectionsDtos, firstResult, totalResults);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
