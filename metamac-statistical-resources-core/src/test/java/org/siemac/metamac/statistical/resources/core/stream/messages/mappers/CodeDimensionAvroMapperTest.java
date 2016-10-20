package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
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
        when(datasetVersionRepository.retrieveLastVersion(any())).thenReturn(MappersMockUtils.mockDatasetVersion());

        CodeDimension expected = MappersMockUtils.mockCodeDimension();
        CodeDimensionAvro source = MappersMockUtils.mockCodeDimensionAvro();

        CodeDimension actual = CodeDimensionAvroMapper.avro2Do(source);

        assertThat("getId()", actual.getId(), is(equalTo(expected.getId())));
        assertThat("getDsdComponentId()", actual.getDsdComponentId(), is(equalTo(expected.getDsdComponentId())));
        assertThat(actual.getIdentifier(), is(equalTo(expected.getIdentifier())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
        assertThat(actual.getVersion(), is(equalTo(expected.getVersion())));

        DatasetsAsserts.assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());

    }

}

