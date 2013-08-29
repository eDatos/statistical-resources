package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;

import com.arte.statistic.dataset.repository.dto.AttributeDto;

public interface StatisticalResourcesDto2StatRepoDtoMapper {

    public static final String BEAN_ID = "statisticalResourcesDto2StatRepoDtoMapper";

    public AttributeDto dsdAttributeInstaceDtoToAttributeDto(DsdAttributeInstanceDto source) throws MetamacException;
}
