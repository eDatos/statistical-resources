package org.siemac.metamac.statistical.resources.core.base.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MaintainableArtefact;
import org.siemac.metamac.statistical.resources.core.dto.constraint.MaintainableArtefactDto;

public interface BaseDto2RestMapper {

    public void maintainableArtefactDtoToRest(MaintainableArtefactDto source, MaintainableArtefact target, String metadataName) throws MetamacException;
}
