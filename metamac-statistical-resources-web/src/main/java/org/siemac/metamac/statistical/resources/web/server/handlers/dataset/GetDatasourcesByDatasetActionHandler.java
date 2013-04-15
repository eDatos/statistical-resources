package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasourcesByDatasetActionHandler extends SecurityActionHandler<GetDatasourcesByDatasetAction, GetDatasourcesByDatasetResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasourcesByDatasetActionHandler() {
        super(GetDatasourcesByDatasetAction.class);
    }

    // FIXME: Pagination
    @Override
    public GetDatasourcesByDatasetResult executeSecurityAction(GetDatasourcesByDatasetAction action) throws ActionException {
        try {
            List<DatasourceDto> datasources = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetUrn());
            return new GetDatasourcesByDatasetResult(datasources);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
