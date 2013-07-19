package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveDatasourceActionHandler extends SecurityActionHandler<SaveDatasourceAction, SaveDatasourceResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveDatasourceActionHandler() {
        super(SaveDatasourceAction.class);
    }

    @Override
    public SaveDatasourceResult executeSecurityAction(SaveDatasourceAction action) throws ActionException {
        try {
            DatasourceDto datasource = null;
            if (action.getDatasource().getUrn() != null) {
                datasource = statisticalResourcesServiceFacade.updateDatasource(ServiceContextHolder.getCurrentServiceContext(), action.getDatasource());
            } else {
                datasource = statisticalResourcesServiceFacade.createDatasource(ServiceContextHolder.getCurrentServiceContext(), action.getUrnDatasetVersion(), action.getDatasource());
            }
            return new SaveDatasourceResult(datasource);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

    }
}
