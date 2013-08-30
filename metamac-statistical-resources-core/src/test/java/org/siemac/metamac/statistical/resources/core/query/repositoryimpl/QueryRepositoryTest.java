package org.siemac.metamac.statistical.resources.core.query.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_SIMPLE_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class QueryRepositoryTest extends StatisticalResourcesBaseTest implements QueryRepositoryTestBase {

    @Autowired
    protected QueryRepository queryRepository;
    
    @Autowired
    protected QueryMockFactory queryMockFactory;

    @Override
    @Test
    @MetamacMock(QUERY_01_SIMPLE_NAME)
    public void testRetrieveByUrn() throws Exception {
        Query expected = queryMockFactory.retrieveMock(QUERY_01_SIMPLE_NAME);
        Query actual = queryRepository.retrieveByUrn(expected.getIdentifiableStatisticalResource().getUrn());
        assertEqualsQuery(expected, actual);
    }
    
    @Test
    public void testRetrieveByUrnNotFound() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, URN_NOT_EXISTS));
        queryRepository.retrieveByUrn(URN_NOT_EXISTS);
    }
}
