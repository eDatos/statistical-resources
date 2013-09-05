package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;

public interface StatRepoDto2StatisticalResourcesDtoMapper {

    public static final String BEAN_ID = "statRepoDto2StatisticalResourcesDtoMapper";

    public DsdAttributeInstanceDto attributeDtoToDsdAttributeInstanceDto(AttributeInstanceDto source) throws MetamacException;
    public List<DsdAttributeInstanceDto> attributeDtosToDsdAttributeInstanceDtos(List<AttributeInstanceDto> sources) throws MetamacException;
}
