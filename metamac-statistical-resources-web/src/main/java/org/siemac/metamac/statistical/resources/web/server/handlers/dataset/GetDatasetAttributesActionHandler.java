package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetDatasetAttributesActionHandler extends SecurityActionHandler<GetDatasetAttributesAction, GetDatasetAttributesResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private SrmRestInternalFacade             srmRestInternalFacade;

    public GetDatasetAttributesActionHandler() {
        super(GetDatasetAttributesAction.class);
    }

    @Override
    public GetDatasetAttributesResult executeSecurityAction(GetDatasetAttributesAction action) throws com.gwtplatform.dispatch.shared.ActionException {
        try {
            DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getUrn());
            List<DsdAttributeDto> attributes = srmRestInternalFacade.retrieveDsdAttributes(datasetVersionDto.getRelatedDsd().getUrn());
            return new GetDatasetAttributesResult(attributes);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    };
}
