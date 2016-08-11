package org.siemac.metamac.statistical.resources.core.constraint.mapper;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionReference;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintBasicDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;

public interface ConstraintRest2DtoMapper {

    public ContentConstraintDto toConstraintDto(ContentConstraint source) throws MetamacException;
    public ContentConstraintBasicDto toConstraintBasicDto(ContentConstraint source) throws MetamacException;

    public RegionValueDto toRegionDto(RegionReference source) throws MetamacException;

    public ExternalItemDto externalItemConstraintToExternalItemDto(ResourceInternal source) throws MetamacException;
    public List<ExternalItemDto> externalItemConstraintToExternalItemDto(List<ResourceInternal> sources) throws MetamacException;
}
