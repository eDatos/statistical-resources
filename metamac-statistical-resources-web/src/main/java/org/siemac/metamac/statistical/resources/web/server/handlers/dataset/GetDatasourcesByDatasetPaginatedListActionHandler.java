package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasourcesByDatasetPaginatedListActionHandler extends SecurityActionHandler<GetDatasourcesByDatasetPaginatedListAction, GetDatasourcesByDatasetPaginatedListResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;
    
    public GetDatasourcesByDatasetPaginatedListActionHandler() {
        super(GetDatasourcesByDatasetPaginatedListAction.class);
    }

    //FIXME: Pagination
    @Override
    public GetDatasourcesByDatasetPaginatedListResult executeSecurityAction(GetDatasourcesByDatasetPaginatedListAction action) throws ActionException {
        try {
            List<DatasourceDto> datasources = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetUrn());
            return new GetDatasourcesByDatasetPaginatedListResult(datasources, 0, datasources.size());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
