package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsAttributeDtoAndDsdAttributeInstaceDto;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;

public class StatisticalResourcesDto2StatRepoDtoMapperTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private final StatisticalResourcesDto2StatRepoDtoMapper dto2StatRepoDtoMapper = new StatisticalResourcesDto2StatRepoDtoMapperImpl();

    @Test
    public void testDsdAttributeInstaceDtoToAttributeDtoWithEnumeratedValue() throws MetamacException {
        DsdAttributeInstanceDto dsdAttributeInstanceDto = StatisticalResourcesDtoMocks.mockDsdAttributeInstanceDtoWithEnumeratedValue();
        AttributeInstanceDto attributeInstanceDto = dto2StatRepoDtoMapper.dsdAttributeInstanceDtoToAttributeInstanceDto(dsdAttributeInstanceDto);
        assertEqualsAttributeDtoAndDsdAttributeInstaceDto(attributeInstanceDto, dsdAttributeInstanceDto);
    }

    @Test
    public void testDsdAttributeInstaceDtoToAttributeDtoWithNonNumeratedValue() throws MetamacException {
        DsdAttributeInstanceDto dsdAttributeInstanceDto = StatisticalResourcesDtoMocks.mockDsdAttributeInstanceDtoWithNonNumeratedValue();
        AttributeInstanceDto attributeInstanceDto = dto2StatRepoDtoMapper.dsdAttributeInstanceDtoToAttributeInstanceDto(dsdAttributeInstanceDto);
        assertEqualsAttributeDtoAndDsdAttributeInstaceDto(attributeInstanceDto, dsdAttributeInstanceDto);
    }
}
