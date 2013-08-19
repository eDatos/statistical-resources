package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetVersionsOfDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetVersionsOfDatasetResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetVersionsOfDatasetActionHandler extends SecurityActionHandler<GetVersionsOfDatasetAction, GetVersionsOfDatasetResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetVersionsOfDatasetActionHandler() {
        super(GetVersionsOfDatasetAction.class);
    }

    @Override
    public GetVersionsOfDatasetResult executeSecurityAction(GetVersionsOfDatasetAction action) throws ActionException {
        try {
            List<DatasetVersionDto> datasetVersionDtos = statisticalResourcesServiceFacade.retrieveDatasetVersions(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn());
            return new GetVersionsOfDatasetResult(datasetVersionDtos);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
