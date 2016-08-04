package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAttributeInstanceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAttributeInstanceResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveDatasetAttributeInstanceActionHandler extends SecurityActionHandler<SaveDatasetAttributeInstanceAction, SaveDatasetAttributeInstanceResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveDatasetAttributeInstanceActionHandler() {
        super(SaveDatasetAttributeInstanceAction.class);
    }

    @Override
    public SaveDatasetAttributeInstanceResult executeSecurityAction(SaveDatasetAttributeInstanceAction action) throws ActionException {
        try {
            DsdAttributeInstanceDto savedAttributeInstace = null;
            if (action.getDsdAttributeInstanceDto().getUuid() == null) {
                savedAttributeInstace = statisticalResourcesServiceFacade.createAttributeInstance(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn(),
                        action.getDsdAttributeInstanceDto());
            } else {
                savedAttributeInstace = statisticalResourcesServiceFacade.updateAttributeInstance(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn(),
                        action.getDsdAttributeInstanceDto());
            }
            return new SaveDatasetAttributeInstanceResult(savedAttributeInstace);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
