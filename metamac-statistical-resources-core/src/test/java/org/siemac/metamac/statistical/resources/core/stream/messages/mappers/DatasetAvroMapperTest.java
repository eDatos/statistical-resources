package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.DatasetAvro2DoMapper;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;

public class DatasetAvroMapperTest {

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
    public void testDo2Avro() {
        DatasetAvro expected = MappersMockUtils.mockDatasetAvro(3);
        Dataset source = MappersMockUtils.mockDataset();

        DatasetAvro actual = DatasetDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        Dataset expected = MappersMockUtils.mockDataset();
        when(datasetVersionRepository.retrieveByUrn(startsWith(MappersMockUtils.EXPECTED_URN))).thenReturn(expected.getVersions().get(0)).thenReturn(expected.getVersions().get(1))
                .thenReturn(expected.getVersions().get(2));
        when(datasetRepository.retrieveByUrn(any())).thenReturn(MappersMockUtils.mockDataset());

        DatasetAvro source = MappersMockUtils.mockDatasetAvro(3);

        Dataset actual = DatasetAvro2DoMapper.avro2Do(source);

        assertEqualDimensionRepresentationMapping(expected, actual);
        assertEqualVersionList(expected, actual);
        assertThat(actual.getIdentifiableStatisticalResource().getStatisticalOperation(), is(not(nullValue())));
    }

    protected void assertEqualVersionList(Dataset expected, Dataset actual) throws MetamacException {
        DatasetsAsserts.assertEqualsDatasetVersionCollection(expected.getVersions(), actual.getVersions());
    }

    protected void assertEqualDimensionRepresentationMapping(Dataset expected, Dataset actual) throws MetamacException {
        int i = 0;
        for (DimensionRepresentationMapping expectedDimension : expected.getDimensionRepresentationMappings()) {
            DatasetsAsserts.assertEqualsDimensionRepresentationMapping(expectedDimension, actual.getDimensionRepresentationMappings().get(i));
            i++;
        }
    }

}
