package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;

import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceDto;

public interface StatRepoDto2StatisticalResourcesDtoMapper {

    public static final String BEAN_ID = "statRepoDto2StatisticalResourcesDtoMapper";

    public DsdAttributeInstanceDto attributeDtoToDsdAttributeInstanceDto(DsdAttribute attribute, AttributeInstanceDto source) throws MetamacException;
    public List<DsdAttributeInstanceDto> attributeDtosToDsdAttributeInstanceDtos(DsdAttribute attribute, List<AttributeInstanceDto> sources) throws MetamacException;
}
