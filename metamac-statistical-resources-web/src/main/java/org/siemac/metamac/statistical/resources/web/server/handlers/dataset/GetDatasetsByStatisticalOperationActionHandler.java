package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetsByStatisticalOperationActionHandler extends SecurityActionHandler<GetDatasetsByStatisticalOperationAction, GetDatasetsByStatisticalOperationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasetsByStatisticalOperationActionHandler() {
        super(GetDatasetsByStatisticalOperationAction.class);
    }

    @Override
    public GetDatasetsByStatisticalOperationResult executeSecurityAction(GetDatasetsByStatisticalOperationAction action) throws ActionException {
        try {

            MetamacCriteria criteria = new MetamacCriteria();

            // TODO set any criteria ?

            criteria.setPaginator(new MetamacCriteriaPaginator());
            criteria.getPaginator().setFirstResult(action.getFirstResult());
            criteria.getPaginator().setMaximumResultSize(action.getMaxResults());
            criteria.getPaginator().setCountTotalResults(true);

            MetamacCriteriaResult<DatasetDto> result = statisticalResourcesServiceFacade.findDatasetsByCondition(ServiceContextHolder.getCurrentServiceContext(), criteria);
            return new GetDatasetsByStatisticalOperationResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
