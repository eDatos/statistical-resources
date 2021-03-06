package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDataset;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetRepositoryTest extends StatisticalResourcesBaseTest implements DatasetRepositoryTestBase {

    @Autowired
    private DatasetRepository datasetRepository;

    @Override
    @Test
    @MetamacMock(DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME)
    public void testRetrieveByUrn() throws Exception {
        Dataset expected = datasetMockFactory.retrieveMock(DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME);
        Dataset actual = datasetRepository.retrieveByUrn(expected.getIdentifiableStatisticalResource().getUrn());
        assertEqualsDataset(expected, actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, URN_NOT_EXISTS));
        datasetRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock(DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME)
    public void testFindDatasetUrnLinkedToDatasourceSourceName() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME);
        checkDatasetDatasourcesFiles(dataset);
    }

    @Test
    @MetamacMock(DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME)
    public void testFindDatasetsUrnsLinkedToDatasourceNotFound() throws Exception {
        String filename = StatisticalResourcesPersistedDoMocks.mockString(10);
        String urn = datasetRepository.findDatasetUrnLinkedToDatasourceSourceName(filename);
        assertNull(urn);
    }

    @Test
    public void testFindDatasetsUrnsLinkedToDatasourceNoDatasets() throws Exception {
        String filename = StatisticalResourcesPersistedDoMocks.mockString(10);
        String urn = datasetRepository.findDatasetUrnLinkedToDatasourceSourceName(filename);
        assertNull(urn);
    }

    @Test
    @MetamacMock(DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME)
    public void testFindDatasetsUrnsLinkedToDatasourceFileMultipleDatasources() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME);
        checkDatasetDatasourcesFiles(dataset);
    }

    @Test
    @MetamacMock(DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE_NAME)
    public void testFindDatasetsUrnsLinkedToDatasourceFileWithUnderscore() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE_NAME);
        checkDatasetDatasourcesFiles(dataset);

        // filename is datasource_underscore.px_(date), we'll try to hack looking for "datasource" is the part before first underscore
        String urn = datasetRepository.findDatasetUrnLinkedToDatasourceSourceName("datasource");
        assertNull(urn);
    }

    @Test
    @MetamacMock({DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME, DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME})
    public void testFindDatasetsUrnsLinkedToDatasourceFileMultipleDatasets() throws Exception {
        Dataset dataset07 = datasetMockFactory.retrieveMock(DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME);
        checkDatasetDatasourcesFiles(dataset07);

        Dataset dataset08 = datasetMockFactory.retrieveMock(DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME);
        checkDatasetDatasourcesFiles(dataset08);

    }

    private void checkDatasetDatasourcesFiles(Dataset dataset) {
        List<String> sourceNames = getDatasourcesLinkedSourceNameInDataset(dataset);
        for (String sourceName : sourceNames) {
            String urn = datasetRepository.findDatasetUrnLinkedToDatasourceSourceName(sourceName);
            assertEquals(dataset.getIdentifiableStatisticalResource().getUrn(), urn);
        }
    }

    private List<String> getDatasourcesLinkedSourceNameInDataset(Dataset dataset) {
        List<String> result = new ArrayList<String>();
        for (DatasetVersion version : dataset.getVersions()) {
            for (Datasource datasource : version.getDatasources()) {
                result.add(datasource.getSourceName());
            }
        }
        return result;
    }
}
