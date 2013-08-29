package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;

import com.arte.statistic.dataset.repository.dto.AttributeDto;

public interface StatRepoDto2StatisticalResourcesDtoMapper {

    public static final String BEAN_ID = "statRepoDto2StatisticalResourcesDtoMapper";

    public DsdAttributeInstanceDto attributeDtoToDsdAttributeInstanceDto(AttributeDto source) throws MetamacException;
}
