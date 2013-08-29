package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsAttributeDtoAndDsdAttributeInstaceDto;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;

import com.arte.statistic.dataset.repository.dto.AttributeDto;

public class StatisticalResourcesDto2StatRepoDtoMapperTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private StatisticalResourcesDto2StatRepoDtoMapper dto2StatRepoDtoMapper = new StatisticalResourcesDto2StatRepoDtoMapperImpl();

    @Test
    public void testDsdAttributeInstaceDtoToAttributeDto() throws MetamacException {
        DsdAttributeInstanceDto dsdAttributeInstanceDto = StatisticalResourcesDtoMocks.mockDsdAttributeInstanceDtoWithEnumeratedValue();
        AttributeDto attributeDto = dto2StatRepoDtoMapper.dsdAttributeInstaceDtoToAttributeDto(dsdAttributeInstanceDto);
        assertEqualsAttributeDtoAndDsdAttributeInstaceDto(attributeDto, dsdAttributeInstanceDto);
    }
}
