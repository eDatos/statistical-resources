package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
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
public class StatisticalResourcesServiceFacadeTest extends StatisticalResourcesBaseTest implements StatisticalResourcesServiceFacadeTestBase {

    @Autowired
    protected StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    protected QueryRepository queryRepository;
    
    @Autowired
    protected QueryMockFactory queryMockFactory;

    @Test
    public void testRetrieveQueryByUrn() throws Exception {
        Query expected = queryRepository.save(StatisticalResourcesDoMocks.mockQuery());
        QueryDto actual = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), expected.getNameableStatisticalResource().getUrn());
        StatisticalResourcesAsserts.assertEqualsQuery(expected, actual);
    }

    @Test
    public void testRetrieveQueries() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveQueries not implemented");
    }

    @Test
    public void testCreateQuery() throws Exception {
        // TODO Auto-generated method stub
        fail("testCreateQuery not implemented");
    }

    @Test
    public void testUpdateQuery() throws Exception {
        // TODO Auto-generated method stub
        fail("testUpdateQuery not implemented");
    }

    @Test
    public void testFindQueriesByCondition() throws Exception {
        // TODO Auto-generated method stub
        fail("testFindQueriesByCondition not implemented");
    }
}
