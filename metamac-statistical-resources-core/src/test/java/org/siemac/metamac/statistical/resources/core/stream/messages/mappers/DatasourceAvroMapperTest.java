package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class DatasourceAvroMapperTest {

    @Mock
    private static DatasetVersionRepository datasetVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
    }

    @Test
    public void testDo2Avro() {
        DatasourceAvro expected = MappersMockUtils.mockDatasourceAvro();
        Datasource source = MappersMockUtils.mockDatasource(true);

        DatasourceAvro actual = DatasourceAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        Datasource expected = MappersMockUtils.mockDatasource();
        DatasourceAvro source = MappersMockUtils.mockDatasourceAvro();

        when(datasetVersionRepository.retrieveByUrn(any())).thenReturn(expected.getDatasetVersion());

        Datasource actual = DatasourceAvroMapper.avro2Do(source);

        assertEqualDatasourceDo(expected, actual);
    }

    protected void assertEqualDatasourceDo(Datasource expected, Datasource actual) throws MetamacException {
        assertThat(actual.getDateNextUpdate(), is(equalTo(expected.getDateNextUpdate())));
        assertThat(actual.getFilename(), is(equalTo(expected.getFilename())));
        assertEquals(actual.getIdentifiableStatisticalResource(), actual.getIdentifiableStatisticalResource());

        DatasetsAsserts.assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());
    }

}
