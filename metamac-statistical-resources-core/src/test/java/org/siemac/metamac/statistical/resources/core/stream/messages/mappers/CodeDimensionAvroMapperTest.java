package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class CodeDimensionAvroMapperTest {

    @Mock
    private static DatasetVersionRepository datasetVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
    }

    @Test
    public void testDo2Avro() {
        CodeDimensionAvro expected = MappersMockUtils.mockCodeDimensionAvro();
        CodeDimension source = MappersMockUtils.mockCodeDimension();

        CodeDimensionAvro actual = CodeDimensionAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        CodeDimension expected = MappersMockUtils.mockCodeDimension();
        CodeDimensionAvro source = MappersMockUtils.mockCodeDimensionAvro();

        when(datasetVersionRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(expected.getDatasetVersion());

        CodeDimension actual = CodeDimensionAvroMapper.avro2Do(source);

        assertThat("getId()", actual.getId(), is(equalTo(expected.getId())));
        assertThat("getDsdComponentId()", actual.getDsdComponentId(), is(equalTo(expected.getDsdComponentId())));
        assertThat(actual.getIdentifier(), is(equalTo(expected.getIdentifier())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));

        DatasetsAsserts.assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());

    }

}

