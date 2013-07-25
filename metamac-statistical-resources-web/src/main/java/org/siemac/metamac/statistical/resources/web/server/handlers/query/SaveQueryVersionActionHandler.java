package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveQueryVersionActionHandler extends SecurityActionHandler<SaveQueryVersionAction, SaveQueryVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveQueryVersionActionHandler() {
        super(SaveQueryVersionAction.class);
    }

    @Override
    public SaveQueryVersionResult executeSecurityAction(SaveQueryVersionAction action) throws ActionException {
        try {
            QueryVersionDto savedQueryVersion = null;
            if (action.getQueryVersionDto().getId() == null) {
                // FIXME specify the statistical operation!!!!!
                savedQueryVersion = statisticalResourcesServiceFacade.createQuery(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionDto(), null);
            } else {
                savedQueryVersion = statisticalResourcesServiceFacade.updateQueryVersion(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionDto());
            }
            return new SaveQueryVersionResult(savedQueryVersion);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
