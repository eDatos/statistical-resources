package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.*;

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
    @MetamacMock(QUERY_BASIC_01_NAME)
    public void testRetrieveQueryByUrn() throws MetamacException {
        Query actual = queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), QUERY_BASIC_01.getNameableStatisticalResource().getUrn());
        StatisticalResourcesAsserts.assertEqualsQuery(QUERY_BASIC_01, actual);
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

    @Test
    @MetamacMock({QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME, QUERY_BASIC_ORDERED_03_NAME})
    public void testFindQueriesByCondition() throws Exception {
        // Find all
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).orderBy(QueryProperties.nameableStatisticalResource().code()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(3, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(QUERY_BASIC_ORDERED_02.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(QUERY_BASIC_ORDERED_03.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }
        
        // Find code
        {
            String code = QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getCode();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().code()).eq(code).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }
        
        // Find URN
        {
            String urn = QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().urn()).eq(urn).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }

        // Find title
        {
            String titleQuery = QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getTitle().getLocalisedLabel("es");
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().title().texts().label()).eq(titleQuery).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }
    }

    @Test
    public void testCreateQuery() throws Exception {
        Query queryPersisted = queryService.createQuery(getServiceContextWithoutPrincipal(), QUERY_BASIC_01);
        StatisticalResourcesAsserts.assertEqualsQuery(QUERY_BASIC_01, queryPersisted);
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
    @MetamacMock(QUERY_BASIC_01_NAME)
    public void testUpdateQuery() throws Exception {
        Query query = QUERY_BASIC_01;
        query.getNameableStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());
        
        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        StatisticalResourcesAsserts.assertEqualsQuery(query, updatedQuery);
    }

    @Test
    @MetamacMock({QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME, QUERY_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueries() throws Exception {
        List<Query> expected = queryMockFactory.getMocks(QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME, QUERY_BASIC_ORDERED_03_NAME);
        
        List<Query> actual = queryService.retrieveQueries(getServiceContextWithoutPrincipal());
        
        StatisticalResourcesAsserts.assertEqualsQueryCollection(expected, actual);
    }
}
