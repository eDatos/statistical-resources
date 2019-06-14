package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatabaseDatasourceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatabaseDatasourceResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class CreateDatabaseDatasourceActionHandler extends SecurityActionHandler<CreateDatabaseDatasourceAction, CreateDatabaseDatasourceResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public CreateDatabaseDatasourceActionHandler() {
        super(CreateDatabaseDatasourceAction.class);
    }

    @Override
    public CreateDatabaseDatasourceResult executeSecurityAction(CreateDatabaseDatasourceAction action) throws ActionException {
        try {
            statisticalResourcesServiceFacade.createDatabaseDatasourceInDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), action.getUrn(), action.getTablename());
            return new CreateDatabaseDatasourceResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
