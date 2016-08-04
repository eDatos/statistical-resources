package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetCategorisationsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetCategorisationsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetCategorisationsActionHandler extends SecurityActionHandler<GetDatasetCategorisationsAction, GetDatasetCategorisationsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasetCategorisationsActionHandler() {
        super(GetDatasetCategorisationsAction.class);
    }

    @Override
    public GetDatasetCategorisationsResult executeSecurityAction(GetDatasetCategorisationsAction action) throws ActionException {
        try {
            List<CategorisationDto> categorisations = statisticalResourcesServiceFacade.retrieveCategorisationsByDatasetVersion(ServiceContextHolder.getCurrentServiceContext(),
                    action.getDatasetVersionUrn());
            return new GetDatasetCategorisationsResult(categorisations);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
