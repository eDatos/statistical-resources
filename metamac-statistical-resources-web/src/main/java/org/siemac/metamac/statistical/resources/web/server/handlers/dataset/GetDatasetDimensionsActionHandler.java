package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetDatasetDimensionsActionHandler extends SecurityActionHandler<GetDatasetDimensionsAction, GetDatasetDimensionsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private SrmRestInternalFacade             srmRestInternalFacade;

    public GetDatasetDimensionsActionHandler() {
        super(GetDatasetDimensionsAction.class);
    }

    @Override
    public GetDatasetDimensionsResult executeSecurityAction(GetDatasetDimensionsAction action) throws com.gwtplatform.dispatch.shared.ActionException {
        try {
            DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getUrn());
            List<DsdDimensionDto> dimensions = srmRestInternalFacade.retrieveDsdDimensions(datasetVersionDto.getRelatedDsd().getUrn());
            return new GetDatasetDimensionsResult(datasetVersionDto, dimensions);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    };
}
