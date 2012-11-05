package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
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
    @MetamacMock(DATASET_VERSION_BASIC_01_NAME)
    public void testRetrieveByUrn() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByUrn(DATASET_VERSION_BASIC_01.getSiemacMetadataStatisticalResource().getUrn());
        StatisticalResourcesAsserts.assertEqualsDatasetVersion(DATASET_VERSION_BASIC_01, actual);
    }

    @Test
    public void testRetrieveLastVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveLastVersion not implemented");
    }

    @Test
    public void testRetrieveByVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testFindByVersion not implemented");
    }
}
