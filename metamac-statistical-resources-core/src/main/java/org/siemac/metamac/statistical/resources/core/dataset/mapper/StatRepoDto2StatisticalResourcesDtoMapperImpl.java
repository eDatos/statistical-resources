package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;

@Component(StatRepoDto2StatisticalResourcesDtoMapper.BEAN_ID)
public class StatRepoDto2StatisticalResourcesDtoMapperImpl implements StatRepoDto2StatisticalResourcesDtoMapper {

    @Override
    public DsdAttributeInstanceDto attributeDtoToDsdAttributeInstanceDto(AttributeDto source) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }
}
