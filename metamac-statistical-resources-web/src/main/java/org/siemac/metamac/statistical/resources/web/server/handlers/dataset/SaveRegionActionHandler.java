package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveRegionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveRegionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveRegionActionHandler extends SecurityActionHandler<SaveRegionAction, SaveRegionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveRegionActionHandler() {
        super(SaveRegionAction.class);
    }

    @Override
    public SaveRegionResult executeSecurityAction(SaveRegionAction action) throws ActionException {
        try {
            RegionValueDto createdRegion = statisticalResourcesServiceFacade.saveRegionForContentConstraint(ServiceContextHolder.getCurrentServiceContext(), action.getContentConstraintUrn(),
                    action.getRegionToSave());
            return new SaveRegionResult(createdRegion);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
