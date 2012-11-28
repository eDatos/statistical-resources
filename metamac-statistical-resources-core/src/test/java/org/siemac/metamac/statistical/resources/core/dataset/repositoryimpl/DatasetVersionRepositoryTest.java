package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetVersionRepositoryTest extends StatisticalResourcesBaseTest implements DatasetVersionRepositoryTestBase {

    @Autowired
    protected DatasetVersionRepository datasetVersionRepository;
    
    @Autowired
    protected DatasetVersionMockFactory datasetVersionMockFactory;
    
    @Autowired
    protected DatasetMockFactory datasetMockFactory;
    

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByUrn(datasetVersionMockFactory.DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersion(datasetVersionMockFactory.DATASET_VERSION_01_BASIC, actual);
    }
    
    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        try {
            datasetVersionRepository.retrieveByUrn(URN_NOT_EXISTS);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(datasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS.getId());
        assertEqualsDatasetVersion(datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION, actual);
    }

    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveByVersion() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByVersion(datasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS.getId(), datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEqualsDatasetVersion(datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION, actual);
    }
}
