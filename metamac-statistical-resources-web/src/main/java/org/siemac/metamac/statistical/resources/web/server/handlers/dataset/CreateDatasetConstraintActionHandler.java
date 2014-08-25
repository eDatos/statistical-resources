package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.enume.constraint.domain.RegionValueTypeEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetConstraintAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetConstraintResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class CreateDatasetConstraintActionHandler extends SecurityActionHandler<CreateDatasetConstraintAction, CreateDatasetConstraintResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public CreateDatasetConstraintActionHandler() {
        super(CreateDatasetConstraintAction.class);
    }

    @Override
    public CreateDatasetConstraintResult executeSecurityAction(CreateDatasetConstraintAction action) throws ActionException {

        try {

            ExternalItemDto datasetVersion = new ExternalItemDto();
            datasetVersion.setType(TypeExternalArtefactsEnum.CONTENT_CONSTRAINTS);
            datasetVersion.setUrn(action.getDatasetVersionUrn());

            ContentConstraintDto contentConstraintDto = new ContentConstraintDto();
            contentConstraintDto.setConstraintAttachment(datasetVersion);
            contentConstraintDto.setAgencyID(action.getMaintainer() != null ? action.getMaintainer().getCode() : null);

            ContentConstraintDto createdConstraint = statisticalResourcesServiceFacade.createContentConstraint(ServiceContextHolder.getCurrentServiceContext(), contentConstraintDto);

            RegionValueDto regionValueDto = new RegionValueDto();
            regionValueDto.setContentConstraintUrn(createdConstraint.getUrn());
            regionValueDto.setRegionValueTypeEnum(RegionValueTypeEnum.CUBE);

            RegionValueDto createdRegion = statisticalResourcesServiceFacade
                    .saveRegionForContentConstraint(ServiceContextHolder.getCurrentServiceContext(), createdConstraint.getUrn(), regionValueDto);

            createdConstraint = statisticalResourcesServiceFacade.retrieveContentConstraintByUrn(ServiceContextHolder.getCurrentServiceContext(), createdConstraint.getUrn());

            return new CreateDatasetConstraintResult(createdConstraint, createdRegion);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
