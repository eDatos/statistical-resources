package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;


public class DimensionRepresentationMappingAvroMapperTest {

    @Mock
    private static DatasetRepository datasetRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetRepository(datasetRepository);
    }

    @Test
    public void testDo2Avro() {
        DimensionRepresentationMappingAvro expected = MappersMockUtils.mockDimensionRepresentationMappingAvro();
        DimensionRepresentationMapping source = MappersMockUtils.mockDimensionRepresentationMapping();

        DimensionRepresentationMappingAvro actual = DimensionRepresentationMappingAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        when(datasetRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(MappersMockUtils.mockDataset());

        DimensionRepresentationMapping expected = MappersMockUtils.mockDimensionRepresentationMapping();
        DimensionRepresentationMappingAvro source = MappersMockUtils.mockDimensionRepresentationMappingAvro();

        DimensionRepresentationMapping actual = DimensionRepresentationMappingAvroMapper.avro2Do(source);

        assertThat(actual.getDatasourceFilename(), is(equalTo(expected.getDatasourceFilename())));
        assertThat(actual.getMapping(), is(equalTo(expected.getMapping())));
        DatasetsAsserts.assertEqualsDataset(expected.getDataset(), actual.getDataset());
    }

}

