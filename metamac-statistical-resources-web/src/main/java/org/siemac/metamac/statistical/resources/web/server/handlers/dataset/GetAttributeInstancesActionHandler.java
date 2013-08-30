package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetAttributeInstancesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetAttributeInstancesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetAttributeInstancesActionHandler extends SecurityActionHandler<GetAttributeInstancesAction, GetAttributeInstancesResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetAttributeInstancesActionHandler() {
        super(GetAttributeInstancesAction.class);
    }

    @Override
    public GetAttributeInstancesResult executeSecurityAction(GetAttributeInstancesAction action) throws ActionException {
        try {
            List<DsdAttributeInstanceDto> instances = statisticalResourcesServiceFacade.retrieveAttributeInstances(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn(),
                    action.getAttributeId());
            return new GetAttributeInstancesResult(instances);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
