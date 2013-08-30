package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;

@Component(StatRepoDto2StatisticalResourcesDtoMapper.BEAN_ID)
public class StatRepoDto2StatisticalResourcesDtoMapperImpl implements StatRepoDto2StatisticalResourcesDtoMapper {

    @Override
    public DsdAttributeInstanceDto attributeDtoToDsdAttributeInstanceDto(AttributeDto source) throws MetamacException {
        DsdAttributeInstanceDto target = new DsdAttributeInstanceDto();
        target.setAttributeId(source.getAttributeId());
        // TODO
        // target.setValue()
        // target.setCodeDimensions()
        return target;
    }

    @Override
    public List<DsdAttributeInstanceDto> attributeDtosToDsdAttributeInstanceDtos(List<AttributeDto> sources) throws MetamacException {
        List<DsdAttributeInstanceDto> targets = new ArrayList<DsdAttributeInstanceDto>(sources.size());
        for (AttributeDto source : sources) {
            targets.add(attributeDtoToDsdAttributeInstanceDto(source));
        }
        return targets;
    }
}
