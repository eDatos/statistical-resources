package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetMultidatasetVersionActionHandler extends SecurityActionHandler<GetMultidatasetVersionAction, GetMultidatasetVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetMultidatasetVersionActionHandler() {
        super(GetMultidatasetVersionAction.class);
    }

    @Override
    public GetMultidatasetVersionResult executeSecurityAction(GetMultidatasetVersionAction action) throws ActionException {
        try {
            MultidatasetVersionDto multidatasetVersionDto = statisticalResourcesServiceFacade.retrieveMultidatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getUrn());
            return new GetMultidatasetVersionResult(multidatasetVersionDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
