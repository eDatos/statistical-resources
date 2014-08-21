package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsIdsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsIdsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetDatasetDimensionsIdsActionHandler extends SecurityActionHandler<GetDatasetDimensionsIdsAction, GetDatasetDimensionsIdsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasetDimensionsIdsActionHandler() {
        super(GetDatasetDimensionsIdsAction.class);
    }

    @Override
    public GetDatasetDimensionsIdsResult executeSecurityAction(GetDatasetDimensionsIdsAction action) throws com.gwtplatform.dispatch.shared.ActionException {
        try {
            List<String> dimensionsIds = statisticalResourcesServiceFacade.retrieveDatasetVersionDimensionsIds(ServiceContextHolder.getCurrentServiceContext(), action.getUrn());
            return new GetDatasetDimensionsIdsResult(dimensionsIds);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    };
}
