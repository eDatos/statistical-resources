package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueBasicDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetConstraintAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetConstraintResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetConstraintActionHandler extends SecurityActionHandler<GetDatasetConstraintAction, GetDatasetConstraintResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetDatasetConstraintActionHandler() {
        super(GetDatasetConstraintAction.class);
    }

    @Override
    public GetDatasetConstraintResult executeSecurityAction(GetDatasetConstraintAction action) throws ActionException {
        try {

            DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn());

            List<ExternalItemDto> constraints = statisticalResourcesServiceFacade.findContentConstraintsForArtefact(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn());
            if (!constraints.isEmpty()) {
                // In METAMAC, only one constraint can be attached to a datasetVersion
                String urn = constraints.get(0).getUrn();
                ContentConstraintDto contentConstraint = statisticalResourcesServiceFacade.retrieveContentConstraintByUrn(ServiceContextHolder.getCurrentServiceContext(), urn);

                // In METAMAC, a constraint only have one region
                RegionValueDto region = null;
                if (!contentConstraint.getRegions().isEmpty()) {
                    RegionValueBasicDto regionValueBasicDto = contentConstraint.getRegions().get(0);
                    region = statisticalResourcesServiceFacade.retrieveRegionForContentConstraint(ServiceContextHolder.getCurrentServiceContext(), contentConstraint.getUrn(),
                            regionValueBasicDto.getCode());
                }
                return new GetDatasetConstraintResult.Builder(datasetVersionDto).contentConstraint(contentConstraint).region(region).build();
            }

            return new GetDatasetConstraintResult.Builder(datasetVersionDto).build();

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
