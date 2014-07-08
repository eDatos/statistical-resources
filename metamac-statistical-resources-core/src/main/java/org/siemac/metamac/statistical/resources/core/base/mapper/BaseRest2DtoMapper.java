package org.siemac.metamac.statistical.resources.core.base.mapper;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MaintainableArtefact;
import org.siemac.metamac.statistical.resources.core.dto.constraint.MaintainableArtefactDto;

public interface BaseRest2DtoMapper {

    public void maintainableArtefactRestToDto(MaintainableArtefact source, MaintainableArtefactDto target) throws MetamacException;
    public ExternalItemDto externalItemToExternalItemDto(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal source, ExternalItemDto target) throws MetamacException;
}
