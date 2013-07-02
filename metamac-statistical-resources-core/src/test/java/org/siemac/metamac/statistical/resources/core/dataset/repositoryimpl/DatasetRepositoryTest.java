package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDataset;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetRepositoryTest extends StatisticalResourcesBaseTest implements DatasetRepositoryTestBase {

    @Autowired
    private DatasetRepository datasetRepository;
    
    @Autowired
    private DatasetMockFactory datasetMockFactory; 

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
}
