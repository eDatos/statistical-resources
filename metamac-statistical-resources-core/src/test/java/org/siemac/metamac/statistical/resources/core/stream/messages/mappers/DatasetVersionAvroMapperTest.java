package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.DatasetVersionAvro2DoMapper;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;

public class DatasetVersionAvroMapperTest {

    @Mock
    private static DatasetVersionRepository datasetVersionRepository;

    @Mock
    private static DatasetRepository datasetRepository;

    @Mock
    private static ConfigurationService configurationService;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
        AvroMapperUtils.setDatasetRepository(datasetRepository);
        AvroMapperUtils.setConfiguratinService(configurationService);
        try {
            when(configurationService.retrieveStatisticalResourcesInternalApiUrlBase()).thenReturn(MappersMockUtils.EXPECTED_API_BASE);
        } catch (MetamacException e) {
        }
    }

    @Test
    public void testDo2Avro() throws MetamacException {
        DatasetVersionAvro expected = MappersMockUtils.mockDatasetVersionAvro();
        DatasetVersion source = MappersMockUtils.mockDatasetVersion();

        DatasetVersionAvro actual = DatasetVersionDo2AvroMapper.do2Avro(source);

        assertEqualDatasetVersionAvro(expected, actual);
    }

    protected void assertEqualDatasetVersionAvro(DatasetVersionAvro expected, DatasetVersionAvro actual) {
        assertThat(actual.getBibliographicCitation(), is(equalTo(expected.getBibliographicCitation())));
        assertThat(actual.getDateEnd(), is(equalTo(expected.getDateEnd())));
        assertThat(actual.getDateNextUpdate(), is(equalTo(expected.getDateNextUpdate())));
        assertThat(actual.getDateStart(), is(equalTo(expected.getDateStart())));
        assertThat(actual.getRelatedDsd(), is(equalTo(expected.getRelatedDsd())));
        assertThat(actual.getStatisticOfficiality(), is(equalTo(expected.getStatisticOfficiality())));
        assertThat(actual.getUserModifiedDateNextUpdate(), is(equalTo(expected.getUserModifiedDateNextUpdate())));
        assertThat(actual.getDataset(), is(equalTo(expected.getDataset())));
        assertThat(actual.getFormatExtentDimensions(), is(equalTo(expected.getFormatExtentDimensions())));
        assertThat(actual.getSiemacMetadataStatisticalResource(), is(equalTo(expected.getSiemacMetadataStatisticalResource())));
        assertThat(actual.getUpdateFrequency(), is(equalTo(expected.getUpdateFrequency())));
        assertThat(actual.getTemporalCoverage(), is(equalTo(expected.getTemporalCoverage())));
        assertThat(actual.getDatasources(), is(equalTo(expected.getDatasources())));
        assertThat(actual.getMeasureCoverage(), is(equalTo(expected.getMeasureCoverage())));
        assertThat(actual.getGeographicGranularities(), is(equalTo(expected.getGeographicGranularities())));
        assertThat(actual.getStatisticalUnit(), is(equalTo(expected.getStatisticalUnit())));
        assertThat(actual.getTemporalGranularities(), is(equalTo(expected.getTemporalGranularities())));
        assertThat(actual.getGeographicCoverage(), is(equalTo(expected.getGeographicCoverage())));
        assertThat(actual.getCategorisations(), is(equalTo(expected.getCategorisations())));
        assertThat(actual.getAttributesCoverage(), is(equalTo(expected.getAttributesCoverage())));
        assertThat(actual.getDimensionsCoverage(), is(equalTo(expected.getDimensionsCoverage())));
    }

    @Test
    public void testAvro2Do() throws MetamacException {

        DatasetVersion expected = MappersMockUtils.mockDatasetVersion();
        DatasetVersionAvro source = MappersMockUtils.mockDatasetVersionAvro();

        when(datasetRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(expected.getDataset());
        when(datasetVersionRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(expected);

        DatasetVersion actual = DatasetVersionAvro2DoMapper.avro2Do(source);

        DatasetsAsserts.assertEqualsDatasetVersion(expected, actual);
    }

}
