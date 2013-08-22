package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionMainCoveragesDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionMainCoveragesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionMainCoveragesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetVersionMainCoveragesActionHandler extends SecurityActionHandler<GetDatasetVersionMainCoveragesAction, GetDatasetVersionMainCoveragesResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasetVersionMainCoveragesActionHandler() {
        super(GetDatasetVersionMainCoveragesAction.class);
    }

    @Override
    public GetDatasetVersionMainCoveragesResult executeSecurityAction(GetDatasetVersionMainCoveragesAction action) throws ActionException {
        try {
            DatasetVersionMainCoveragesDto mainCoverages = statisticalResourcesServiceFacade.retrieveDatasetVersionMainCoverages(ServiceContextHolder.getCurrentServiceContext(),
                    action.getDatasetVersionUrn());
            return new GetDatasetVersionMainCoveragesResult(mainCoverages.getGeographicCoverage(), mainCoverages.getTemporalCoverage(), mainCoverages.getMeasureCoverage());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
