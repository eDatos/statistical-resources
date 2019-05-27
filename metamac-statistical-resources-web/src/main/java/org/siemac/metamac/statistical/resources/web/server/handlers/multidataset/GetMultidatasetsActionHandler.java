package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebCriteriaUtils;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetsAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetMultidatasetsActionHandler extends SecurityActionHandler<GetMultidatasetsAction, GetMultidatasetsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetMultidatasetsActionHandler() {
        super(GetMultidatasetsAction.class);
    }

    @Override
    public GetMultidatasetsResult executeSecurityAction(GetMultidatasetsAction action) throws ActionException {
        try {
            MetamacCriteriaResult<RelatedResourceDto> result = statisticalResourcesServiceFacade.findMultidatasetsByCondition(ServiceContextHolder.getCurrentServiceContext(),
                    getMetamacCriteria(action));
            return new GetMultidatasetsResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    private MetamacCriteria getMetamacCriteria(GetMultidatasetsAction action) {
        MetamacCriteria criteria = new MetamacCriteria();
        criteria.setRestriction(getRestriction(action));
        criteria.setPaginator(getPaginator(action));
        return criteria;
    }

    private MetamacCriteriaPaginator getPaginator(GetMultidatasetsAction action) {
        MetamacCriteriaPaginator paginator = new MetamacCriteriaPaginator();
        paginator.setFirstResult(action.getFirstResult());
        paginator.setMaximumResultSize(action.getMaxResults());
        paginator.setCountTotalResults(Boolean.TRUE);
        return paginator;
    }

    private MetamacCriteriaConjunctionRestriction getRestriction(GetMultidatasetsAction action) {
        MetamacCriteriaConjunctionRestriction restriction = new MetamacCriteriaConjunctionRestriction();
        restriction.getRestrictions().add(MetamacWebCriteriaUtils.buildMetamacCriteriaFromWebcriteria(action.getCriteria()));
        return restriction;
    }

}
