package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasourcesByDatasetPaginatedListActionHandler extends SecurityActionHandler<GetDatasourcesByDatasetPaginatedListAction, GetDatasourcesByDatasetPaginatedListResult> {
    
    
    public GetDatasourcesByDatasetPaginatedListActionHandler() {
        super(GetDatasourcesByDatasetPaginatedListAction.class);
    }
    
    @Override
    public GetDatasourcesByDatasetPaginatedListResult executeSecurityAction(GetDatasourcesByDatasetPaginatedListAction action) throws ActionException {
        try {
            MetamacCriteriaResult<DatasourceDto> criteriaResult = MockServices.findDatasources(action.getDatasetUrn(), action.getFirstResult(), action.getMaxResults());
            return new GetDatasourcesByDatasetPaginatedListResult(criteriaResult.getResults(), criteriaResult.getPaginatorResult().getFirstResult(),criteriaResult.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
