package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetVersionsOfMultidatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetVersionsOfMultidatasetResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetVersionsOfMultidatasetActionHandler extends SecurityActionHandler<GetVersionsOfMultidatasetAction, GetVersionsOfMultidatasetResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetVersionsOfMultidatasetActionHandler() {
        super(GetVersionsOfMultidatasetAction.class);
    }

    @Override
    public GetVersionsOfMultidatasetResult executeSecurityAction(GetVersionsOfMultidatasetAction action) throws ActionException {
        try {
            List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos = statisticalResourcesServiceFacade.retrieveMultidatasetVersions(ServiceContextHolder.getCurrentServiceContext(),
                    action.getMultidatasetVersionUrn());
            return new GetVersionsOfMultidatasetResult(multidatasetVersionBaseDtos);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
