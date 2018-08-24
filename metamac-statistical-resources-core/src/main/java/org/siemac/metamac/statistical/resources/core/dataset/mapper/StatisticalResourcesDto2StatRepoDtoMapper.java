package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;

import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceDto;

public interface StatisticalResourcesDto2StatRepoDtoMapper {

    public static final String BEAN_ID = "statisticalResourcesDto2StatRepoDtoMapper";

    public AttributeInstanceDto dsdAttributeInstanceDtoToAttributeInstanceDto(DsdAttributeInstanceDto source) throws MetamacException;
}
