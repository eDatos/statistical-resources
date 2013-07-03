package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetVersionRepositoryTest extends StatisticalResourcesBaseTest implements DatasetVersionRepositoryTestBase {

    @Autowired
    private DatasetVersionRepository  datasetVersionRepository;

    @Autowired
    private DatasetVersionMockFactory datasetVersionMockFactory;

    @Autowired
    private DatasetMockFactory        datasetMockFactory;

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByUrn(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME), actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, URN_NOT_EXISTS));
        
        datasetVersionRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }
    
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME})
    public void testRetrieveLastVersionWithAllVersionsPublished() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }
    
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastVersionWithLatestVersionNoVisible() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }
    
    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersionWithAllVersionsPublished() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }
    
    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_VERSION_15_DRAFT_NOT_READY_NAME})
    public void testRetrieveLastPublishedVersionWithoutVersionsPublished() throws Exception {
        String datasetUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME).getDataset().getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = null;
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }
    
    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_VERSION_15_DRAFT_NOT_READY_NAME, DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastPublishedVersionWithLatestVersionNoVisible() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }
    
    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testIsLastVersion() throws Exception {
        DatasetVersion notLastDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        DatasetVersion lastDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        
        assertTrue(datasetVersionRepository.isLastVersion(lastDatasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
        assertFalse(datasetVersionRepository.isLastVersion(notLastDatasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
    }

    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveByVersion() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByVersion(datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getId(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME), actual);
    }
}
