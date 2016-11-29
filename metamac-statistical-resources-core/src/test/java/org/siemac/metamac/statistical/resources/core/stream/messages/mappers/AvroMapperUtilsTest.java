package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;

public class AvroMapperUtilsTest {

    @Mock
    DatasetRepository datasetRepository;
    @Mock
    DatasetVersionRepository datasetVersionRepository;
    @Mock
    ConfigurationService configurationService;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetRepository(datasetRepository);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
        AvroMapperUtils.setConfiguratinService(configurationService);

        try {
            when(configurationService.retrieveStatisticalResourcesInternalApiUrlBase()).thenReturn(MappersMockUtils.EXPECTED_API_BASE);
        } catch (MetamacException e) {
        }
    }

    @Test
    public void testAvro2DoCallsTheCorrectClass() throws MetamacException {
        IdentifiableStatisticalResource expected = MappersMockUtils.mockIdentifiableStatisticalResource();
        IdentifiableStatisticalResourceAvro source = MappersMockUtils.mockIdentifiableStatisticalResourceAvro();
        IdentifiableStatisticalResource actual = (IdentifiableStatisticalResource) AvroMapperUtils.avro2Do(source);

        BaseAsserts.assertEqualsIdentifiableStatisticalResource(actual, expected);
    }

    @Test
    public void testAvro2DoCallsTheCorrectClassWithDatasetVersion() throws MetamacException {
        DatasetVersion expected = MappersMockUtils.mockDatasetVersion();
        DatasetVersionAvro source = MappersMockUtils.mockDatasetVersionAvro();

        when(datasetVersionRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(expected);
        when(datasetRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(expected.getDataset());

        DatasetVersion actual = (DatasetVersion) AvroMapperUtils.avro2Do(source);

        DatasetsAsserts.assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    public void testAvro2DoFailsOnNotFoundClass() {
        SpecificRecord record = mockSpecificRecordInterface();

        try {
            AvroMapperUtils.avro2Do(record);
            fail("The test should not get to here");
        } catch (MetamacException e) {
            assertTrue("Method must launch a MetamacException if something fails", true);
        }
    }

    protected SpecificRecord mockSpecificRecordInterface() {
        SpecificRecord record = new SpecificRecord() {

            @Override
            public Schema getSchema() {
                return null;
            }

            @Override
            public void put(int i, Object v) {
            }

            @Override
            public Object get(int i) {
                return null;
            }
        };
        return record;
    }

}
