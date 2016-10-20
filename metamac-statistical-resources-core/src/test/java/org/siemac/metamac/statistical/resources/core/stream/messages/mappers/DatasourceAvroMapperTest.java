package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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


public class DatasourceAvroMapperTest {

    @Mock
    private static DatasetVersionRepository datasetVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        DatasourceAvroMapper.setDatasetVersionRepository(datasetVersionRepository);
    }

    @Test
    public void testDo2Avro() {
        DatasourceAvro expected = MappersMockUtils.mockDatasourceAvro();
        Datasource source = MappersMockUtils.mockDatasource();

        DatasourceAvro actual = DatasourceAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        when(datasetVersionRepository.retrieveLastVersion(any())).thenReturn(MappersMockUtils.mockDatasetVersion());

        Datasource expected = MappersMockUtils.mockDatasource();
        DatasourceAvro source = MappersMockUtils.mockDatasourceAvro();

        Datasource actual = DatasourceAvroMapper.avro2Do(source);

        assertEqualDatasourceDo(expected, actual);
    }

    protected void assertEqualDatasourceDo(Datasource expected, Datasource actual) throws MetamacException {
        assertThat(actual.getDateNextUpdate(), is(equalTo(expected.getDateNextUpdate())));
        assertThat(actual.getFilename(), is(equalTo(expected.getFilename())));
        assertEquals(actual.getIdentifiableStatisticalResource(), actual.getIdentifiableStatisticalResource());
        assertThat(actual.getVersion(), is(equalTo(expected.getVersion())));

        DatasetsAsserts.assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());
    }







}
