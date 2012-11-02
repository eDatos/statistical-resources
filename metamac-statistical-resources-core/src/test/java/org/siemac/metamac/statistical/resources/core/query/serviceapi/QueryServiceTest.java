package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.NotTransactional;
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
public class QueryServiceTest extends StatisticalResourcesBaseTest implements QueryServiceTestBase {

    @Autowired
    protected QueryService queryService;

    @Autowired
    protected QueryRepository             queryRepository;
    
    @Autowired
    protected QueryMockFactory queryMockFactory;

    @Test
    @MetamacMock(QueryMockFactory.QueryBasic01)
    public void testRetrieveQueryByUrn() throws MetamacException {
        Query expected = queryMockFactory.getMock(QueryMockFactory.QueryBasic01);
        Query actual = queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), expected.getNameableStatisticalResource().getUrn());
        StatisticalResourcesAsserts.assertEqualsQuery(expected, actual);
    }

    @Test
    public void testRetrieveQueryByUrnParameterRequired() throws MetamacException {
        try {
            queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), EMPTY);
            fail("parameter required");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            StatisticalResourcesAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionParameters.URN}, e.getExceptionItems().get(0));
        }
    }

    @MetamacMock({QueryMockFactory.QueryBasicOrdered01, QueryMockFactory.QueryBasicOrdered02, QueryMockFactory.QueryBasicOrdered03})
    @Test
    public void testFindQueryByCondition() throws Exception {
        Query query01 = queryMockFactory.getMock(QueryMockFactory.QueryBasicOrdered01);
        Query query02 = queryMockFactory.getMock(QueryMockFactory.QueryBasicOrdered02);
        Query query03 = queryMockFactory.getMock(QueryMockFactory.QueryBasicOrdered03);

        // Find all
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).orderBy(QueryProperties.nameableStatisticalResource().code()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(3, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(query01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(query02.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(query03.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }
        
        // Find code
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().code()).eq(query01.getNameableStatisticalResource().getCode()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(query01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }
        
        // Find URN
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().urn()).eq(query01.getNameableStatisticalResource().getUrn()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(query01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }

        // Find title
        {
            String titleQuery = query01.getNameableStatisticalResource().getTitle().getLocalisedLabel("es");
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().title().texts().label()).eq(titleQuery).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(query01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }
    }

    @Test
    public void testCreateQuery() throws Exception {
        Query query = queryMockFactory.getMock(QueryMockFactory.QueryBasic01);
        Query queryPersisted = queryService.createQuery(getServiceContextWithoutPrincipal(), query);
        StatisticalResourcesAsserts.assertEqualsQuery(query, queryPersisted);
    }

    @Test
    public void testCreateQueryErrorNameableResourceRequired() throws Exception {
        Query query = QueryMockFactory.createQueryWithNameableNull();
        try {
            queryService.createQuery(getServiceContextWithoutPrincipal(), query);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            StatisticalResourcesAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionParameters.NAMEABLE_RESOURCE}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock(QueryMockFactory.QueryBasic01)
    public void testUpdateQuery() throws Exception {
        Query query = queryMockFactory.getMock(QueryMockFactory.QueryBasic01);
        
        query.getNameableStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());
        
        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        StatisticalResourcesAsserts.assertEqualsQuery(query, updatedQuery);
    }

    @Test
    public void testRetrieveQueries() throws Exception {
       
        

        fail("not implemented");
    }
    

    @Test
    public void testFindQueriesByCondition() throws Exception {
        fail("not implemented");
        
    }
}
