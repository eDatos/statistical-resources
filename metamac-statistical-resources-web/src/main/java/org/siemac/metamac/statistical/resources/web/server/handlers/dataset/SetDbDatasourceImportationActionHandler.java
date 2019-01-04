package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SetDbDatasourceImportationAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SetDbDatasourceImportationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SetDbDatasourceImportationActionHandler extends SecurityActionHandler<SetDbDatasourceImportationAction, SetDbDatasourceImportationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SetDbDatasourceImportationActionHandler() {
        super(SetDbDatasourceImportationAction.class);
    }

    @Override
    public SetDbDatasourceImportationResult executeSecurityAction(SetDbDatasourceImportationAction action) throws ActionException {
        try {
            statisticalResourcesServiceFacade.importDbDatasourceInDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), action.getUrn(), action.getTablename());
            return new SetDbDatasourceImportationResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
