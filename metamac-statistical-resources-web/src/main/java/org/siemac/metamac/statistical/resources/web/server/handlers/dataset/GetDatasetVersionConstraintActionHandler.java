package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionConstraintAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionConstraintResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetVersionConstraintActionHandler extends SecurityActionHandler<GetDatasetVersionConstraintAction, GetDatasetVersionConstraintResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasetVersionConstraintActionHandler() {
        super(GetDatasetVersionConstraintAction.class);
    }

    @Override
    public GetDatasetVersionConstraintResult executeSecurityAction(GetDatasetVersionConstraintAction action) throws ActionException {

        try {
            List<ExternalItemDto> constraints = statisticalResourcesServiceFacade.findContentConstraintsForArtefact(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn());
            if (!constraints.isEmpty()) {
                // In METAMAC, only one constraint can be attached to a datasetVersion
                String urn = constraints.get(0).getUrn();
                ContentConstraintDto contentConstraint = statisticalResourcesServiceFacade.retrieveContentConstraintByUrn(ServiceContextHolder.getCurrentServiceContext(), urn);
                return new GetDatasetVersionConstraintResult.Builder().contentConstraint(contentConstraint).build();
            }

            return new GetDatasetVersionConstraintResult.Builder().build();

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
