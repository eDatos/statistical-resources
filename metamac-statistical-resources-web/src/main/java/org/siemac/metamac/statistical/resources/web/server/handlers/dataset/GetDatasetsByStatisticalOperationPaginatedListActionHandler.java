package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetsByStatisticalOperationPaginatedListActionHandler
        extends
            SecurityActionHandler<GetDatasetsByStatisticalOperationPaginatedListAction, GetDatasetsByStatisticalOperationPaginatedListResult> {

    public GetDatasetsByStatisticalOperationPaginatedListActionHandler() {
        super(GetDatasetsByStatisticalOperationPaginatedListAction.class);
    }

    @Override
    public GetDatasetsByStatisticalOperationPaginatedListResult executeSecurityAction(GetDatasetsByStatisticalOperationPaginatedListAction action) throws ActionException {
        try {
            int firstResult = 0;
            int totalResults = 0;
            List<DatasetDto> datasetsDtos = MockServices.findDatasets(action.getOperationUrn(), action.getFirstResult(), action.getMaxResults());
            return new GetDatasetsByStatisticalOperationPaginatedListResult(datasetsDtos, firstResult, totalResults);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
