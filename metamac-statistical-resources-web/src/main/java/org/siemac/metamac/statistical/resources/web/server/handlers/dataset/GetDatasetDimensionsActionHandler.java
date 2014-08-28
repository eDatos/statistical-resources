package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetDatasetDimensionsActionHandler extends SecurityActionHandler<GetDatasetDimensionsAction, GetDatasetDimensionsResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetDatasetDimensionsActionHandler() {
        super(GetDatasetDimensionsAction.class);
    }

    @Override
    public GetDatasetDimensionsResult executeSecurityAction(GetDatasetDimensionsAction action) throws com.gwtplatform.dispatch.shared.ActionException {
        List<DsdDimensionDto> dimensions = srmRestInternalFacade.retrieveDsdDimensions(action.getDsdUrn());
        return new GetDatasetDimensionsResult(dimensions);
    }
}
