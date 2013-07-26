package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetPublicationVersionsActionHandler extends SecurityActionHandler<GetPublicationVersionsAction, GetPublicationVersionsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetPublicationVersionsActionHandler() {
        super(GetPublicationVersionsAction.class);
    }

    @Override
    public GetPublicationVersionsResult executeSecurityAction(GetPublicationVersionsAction action) throws ActionException {

        MetamacCriteria criteria = new MetamacCriteria();

        // Order
        criteria.getOrdersBy().add(MetamacWebCriteriaUtils.buildMetamacCriteriaLastUpdatedOrder());

        // Criteria
        MetamacCriteriaConjunctionRestriction restriction = new MetamacCriteriaConjunctionRestriction();
        restriction.getRestrictions().add(MetamacWebCriteriaUtils.buildMetamacCriteriaFromWebcriteria(action.getCriteria()));
        criteria.setRestriction(restriction);

        // Pagination
        criteria.setPaginator(new MetamacCriteriaPaginator());
        criteria.getPaginator().setFirstResult(action.getFirstResult());
        criteria.getPaginator().setMaximumResultSize(action.getMaxResults());
        criteria.getPaginator().setCountTotalResults(true);

        try {
            MetamacCriteriaResult<PublicationVersionDto> result = statisticalResourcesServiceFacade.findPublicationVersionByCondition(ServiceContextHolder.getCurrentServiceContext(), criteria);
            return new GetPublicationVersionsResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
