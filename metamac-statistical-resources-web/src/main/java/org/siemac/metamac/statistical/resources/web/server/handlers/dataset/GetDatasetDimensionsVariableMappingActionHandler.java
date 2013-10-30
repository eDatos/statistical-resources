package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsVariableMappingAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsVariableMappingResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetDimensionsVariableMappingActionHandler extends SecurityActionHandler<GetDatasetDimensionsVariableMappingAction, GetDatasetDimensionsVariableMappingResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private SrmRestInternalFacade             srmRestInternalFacade;

    public GetDatasetDimensionsVariableMappingActionHandler() {
        super(GetDatasetDimensionsVariableMappingAction.class);
    }

    @Override
    public GetDatasetDimensionsVariableMappingResult executeSecurityAction(GetDatasetDimensionsVariableMappingAction action) throws ActionException {
        try {
            DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn());
            return new GetDatasetDimensionsVariableMappingResult(srmRestInternalFacade.findMappeableDimensionsInDsdWithVariables(datasetVersionDto.getRelatedDsd().getUrn()));
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
