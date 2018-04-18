package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetMockFactory.MULTIDATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.Multidataset;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.MultidatasetsAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class MultidatasetRepositoryTest extends StatisticalResourcesBaseTest implements MultidatasetRepositoryTestBase {

    @Autowired
    protected MultidatasetRepository multidatasetRepository;

    @Override
    @Test
    @MetamacMock(MULTIDATASET_02_BASIC_WITH_GENERATED_VERSION_NAME)
    public void testRetrieveByUrn() throws Exception {
        Multidataset expected = multidatasetMockFactory.retrieveMock(MULTIDATASET_02_BASIC_WITH_GENERATED_VERSION_NAME);
        Multidataset actual = multidatasetRepository.retrieveByUrn(expected.getIdentifiableStatisticalResource().getUrn());
        MultidatasetsAsserts.assertEqualsMultidataset(expected, actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.MULTIDATASET_VERSION_NOT_FOUND, URN_NOT_EXISTS));
        multidatasetRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

}
