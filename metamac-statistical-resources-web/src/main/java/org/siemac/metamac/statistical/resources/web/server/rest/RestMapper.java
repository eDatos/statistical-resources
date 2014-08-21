package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface RestMapper {

    public List<DsdDimensionDto> buildDsdDimensionDtosFromDsdDimensions(List<DsdDimension> dsdDimensions) throws MetamacWebException;
    public DsdDimensionDto buildDsdDimemsionDtoFromDsdDimension(DsdDimension dsdDimension) throws MetamacWebException;
    public List<DsdAttributeDto> buildDsdAttributeDtosFromDsdAttributes(List<DsdAttribute> dsdAttributes, Map<String, List<String>> dsdGgroupDimensions) throws MetamacWebException;
    public DsdAttributeDto buildDsdAttributeDtoFromDsdAttribute(DsdAttribute dsdAttribute, Map<String, List<String>> dsdGgroupDimensions) throws MetamacWebException;
}
