package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.VersionQueryVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.query.VersionQueryVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class VersionQueryVersionActionHandler extends SecurityActionHandler<VersionQueryVersionAction, VersionQueryVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public VersionQueryVersionActionHandler() {
        super(VersionQueryVersionAction.class);
    }

    @Override
    public VersionQueryVersionResult executeSecurityAction(VersionQueryVersionAction action) throws ActionException {
        try {
            // TODO
            // QueryVersionDto queryDto = statisticalResourcesServiceFacade.versioningQueryVersion(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionUrn(),
            // action.getVersionType());
            // return new VersionQueryVersionResult(queryDto);
            return new VersionQueryVersionResult(statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionUrn()));
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
