package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;

public class DimensionRepresentationMappingAvroMapperTest {

    @Mock
    private static DatasetRepository datasetRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.datasetRepository = datasetRepository;
    }

    @Test
    public void testDo2Avro() {
        DimensionRepresentationMappingAvro expected = MappersMockUtils.mockDimensionRepresentationMappingAvro();
        DimensionRepresentationMapping source = MappersMockUtils.mockDimensionRepresentationMapping();

        DimensionRepresentationMappingAvro actual = DimensionRepresentationMappingDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
