package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
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

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByUrn(DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersion(DATASET_VERSION_01_BASIC, actual);
    }

    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS.getId());
        assertEqualsDatasetVersion(DATASET_VERSION_04_ASSOCIATED_WITH_DATASET_03_AND_LAST_VERSION, actual);
    }

    @Test
    public void testRetrieveByVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testFindByVersion not implemented");
    }
}
