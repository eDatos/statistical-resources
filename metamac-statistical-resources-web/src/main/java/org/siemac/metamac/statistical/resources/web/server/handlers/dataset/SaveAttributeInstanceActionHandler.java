package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveAttributeInstanceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveAttributeInstanceResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveAttributeInstanceActionHandler extends SecurityActionHandler<SaveAttributeInstanceAction, SaveAttributeInstanceResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveAttributeInstanceActionHandler() {
        super(SaveAttributeInstanceAction.class);
    }

    @Override
    public SaveAttributeInstanceResult executeSecurityAction(SaveAttributeInstanceAction action) throws ActionException {
        try {
            DsdAttributeInstanceDto savedAttributeInstace = null;
            if (action.getDsdAttributeInstanceDto().getUuid() == null) {
                // Create
                savedAttributeInstace = statisticalResourcesServiceFacade.createAttributeInstance(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn(),
                        action.getDsdAttributeInstanceDto());
            } else {
                // Save
                // TODO
            }
            return new SaveAttributeInstanceResult(savedAttributeInstace);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
