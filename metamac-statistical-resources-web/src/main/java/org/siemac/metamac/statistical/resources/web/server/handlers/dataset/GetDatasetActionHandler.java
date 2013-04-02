package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetActionHandler extends SecurityActionHandler<GetDatasetAction, GetDatasetResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasetActionHandler() {
        super(GetDatasetAction.class);
    }

    @Override
    public GetDatasetResult executeSecurityAction(GetDatasetAction action) throws ActionException {
        try {
            DatasetDto dataset = statisticalResourcesServiceFacade.retrieveDatasetByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetUrn());
            return new GetDatasetResult(dataset);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
