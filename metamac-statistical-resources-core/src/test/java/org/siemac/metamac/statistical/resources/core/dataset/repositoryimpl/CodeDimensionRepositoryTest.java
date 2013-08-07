package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimensionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
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
public class CodeDimensionRepositoryTest extends StatisticalResourcesBaseTest implements CodeDimensionRepositoryTestBase {
    
    @Autowired
    protected CodeDimensionRepository codeDimensionRepository;
    
    @Autowired
    private DatasetVersionMockFactory datasetVersionMockFactory;

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testFindCodesForDatasetVersionByDimensionId() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);
        {
            List<CodeDimension> codeDimensions = codeDimensionRepository.findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), "dim-none");
            Assert.assertEquals(0, codeDimensions.size());
        }
        
    }
    
}
