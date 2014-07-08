package org.siemac.metamac.statistical.resources.core.constraint.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Region;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionReference;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;

public interface ConstraintDto2RestMapper {

    public ContentConstraint constraintDtoTo(ContentConstraintDto source) throws MetamacException;
    public Region toRegion(RegionValueDto source) throws MetamacException;
    public RegionReference toRegionReference(RegionValueDto source) throws MetamacException;
}
