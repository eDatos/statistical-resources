package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.*;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetRepositoryTest extends StatisticalResourcesBaseTest 
    implements DatasetRepositoryTestBase {
    @Autowired
    protected DatasetRepository datasetRepository;
    
    @Autowired
    private DatasetVersionMockFactory datasetVersionMockFactory;

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_08_VALID_CODE_0002_NAME, DATASET_VERSION_07_VALID_CODE_0001_NAME})
    public void testFindLastDatasetCode() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_08_VALID_CODE_0002_NAME);
        String code = datasetRepository.findLastDatasetCode(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn());
        assertNotNull(code);
        assertEquals("0002", code);
    }
    
    @Test
    @MetamacMock({DATASET_VERSION_08_VALID_CODE_0002_NAME, DATASET_VERSION_07_VALID_CODE_0001_NAME, DATASET_VERSION_10_OPER_0002_CODE_0001_NAME, 
        DATASET_VERSION_09_OPER_0001_CODE_0003_NAME})
    public void testFindLastDatasetCodeMultiOperation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_0003_NAME);
        String code = datasetRepository.findLastDatasetCode(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn());
        assertNotNull(code);
        assertEquals("0003", code);
        
        datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_0001_NAME);
        code = datasetRepository.findLastDatasetCode(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn());
        assertNotNull(code);
        assertEquals("0001", code);
        
        code = datasetRepository.findLastDatasetCode(CODE_NOT_EXISTS);
        assertNull(code);
    }
}
