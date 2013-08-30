package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;

@Component(StatRepoDto2StatisticalResourcesDtoMapper.BEAN_ID)
public class StatRepoDto2StatisticalResourcesDtoMapperImpl implements StatRepoDto2StatisticalResourcesDtoMapper {

    @Override
    public DsdAttributeInstanceDto attributeDtoToDsdAttributeInstanceDto(AttributeDto source) throws MetamacException {
        DsdAttributeInstanceDto target = new DsdAttributeInstanceDto();
        target.setUuid(source.getUuid());
        target.setAttributeId(source.getAttributeId());
        // TODO
        // target.setValue()
        // target.setCodeDimensions()
        return target;
    }
}
