package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;

@Component(StatRepoDto2StatisticalResourcesDtoMapper.BEAN_ID)
public class StatRepoDto2StatisticalResourcesDtoMapperImpl implements StatRepoDto2StatisticalResourcesDtoMapper {

    @Override
    public DsdAttributeInstanceDto attributeDtoToDsdAttributeInstanceDto(AttributeInstanceDto source) throws MetamacException {
        DsdAttributeInstanceDto target = new DsdAttributeInstanceDto();
        target.setAttributeId(source.getAttributeId());
        // TODO attributeDtoToDsdAttributeInstanceDto
        // target.setValue()
        // target.setCodeDimensions()
        return target;
    }

    @Override
    public List<DsdAttributeInstanceDto> attributeDtosToDsdAttributeInstanceDtos(List<AttributeInstanceDto> sources) throws MetamacException {
        List<DsdAttributeInstanceDto> targets = new ArrayList<DsdAttributeInstanceDto>(sources.size());
        for (AttributeInstanceDto source : sources) {
            targets.add(attributeDtoToDsdAttributeInstanceDto(source));
        }
        return targets;
    }
}
