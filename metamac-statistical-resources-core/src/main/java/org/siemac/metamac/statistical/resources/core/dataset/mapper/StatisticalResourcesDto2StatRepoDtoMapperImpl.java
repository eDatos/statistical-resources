package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;

@Component(StatisticalResourcesDto2StatRepoDtoMapper.BEAN_ID)
public class StatisticalResourcesDto2StatRepoDtoMapperImpl implements StatisticalResourcesDto2StatRepoDtoMapper {

    @Override
    public AttributeDto dsdAttributeInstaceDtoToAttributeDto(DsdAttributeInstanceDto source) throws MetamacException {
        if (source == null) {
            return null;
        }
        AttributeDto attributeDto = new AttributeDto();
        // TODO
        return attributeDto;
    }
}
