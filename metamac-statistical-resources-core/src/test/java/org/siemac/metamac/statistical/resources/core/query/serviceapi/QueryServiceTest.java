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

    private static final String           QUERY_1_CODE = "query01";
    private static final String           QUERY_1_URN  = "urn:mock:query01";
    private static final String           QUERY_2_CODE = "query02";
    private static final String           QUERY_2_URN  = "urn:mock:query02";
    private static final String           QUERY_3_CODE = "query03";
    private static final String           QUERY_3_URN  = "urn:mock:query03";

    @Autowired
    protected QueryService queryService;

    @Autowired
    protected QueryRepository             queryRepository;

    @Test
    public void testRetrieveQueryByUrn() throws MetamacException {
        Query expected = queryRepository.save(StatisticalResourcesDoMocks.mockQuery());
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

    @Test
    public void testFindQueryByCondition() throws Exception {
        Query query01 = StatisticalResourcesDoMocks.mockQuery();
        query01.getNameableStatisticalResource().setCode(QUERY_1_CODE);
        query01.getNameableStatisticalResource().setUrn(QUERY_1_URN);
        queryRepository.save(query01);

        Query query02 = StatisticalResourcesDoMocks.mockQuery();
        query02.getNameableStatisticalResource().setCode(QUERY_2_CODE);
        query02.getNameableStatisticalResource().setUrn(QUERY_2_URN);
        queryRepository.save(query02);

        Query query03 = StatisticalResourcesDoMocks.mockQuery();
        query03.getNameableStatisticalResource().setCode(QUERY_3_CODE);
        query03.getNameableStatisticalResource().setUrn(QUERY_3_URN);
        queryRepository.save(query03);

        // Find all
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).orderBy(QueryProperties.nameableStatisticalResource().code())
                    .orderBy(QueryProperties.nameableStatisticalResource().urn()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);
            
            // Validate
            assertEquals(3, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(QUERY_1_URN, queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(QUERY_2_URN, queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(QUERY_3_URN, queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }

        // Find title
        {
            // TODO
        }

        // Find code
        {
            // TODO
        }

        // Find URN
        {
            // TODO
        }
    }

    @Test
    public void testCreateQuery() throws Exception {
        Query query = StatisticalResourcesDoMocks.mockQuery();
        Query queryPersisted = queryService.createQuery(getServiceContextWithoutPrincipal(), query);
        StatisticalResourcesAsserts.assertEqualsQuery(query, queryPersisted);
    }

    @Test
    public void testCreateQueryErrorNameableResourceRequired() throws Exception {
        Query query = StatisticalResourcesDoMocks.mockQuery();
        query.setNameableStatisticalResource(null);
        try {
            queryService.createQuery(getServiceContextWithoutPrincipal(), query);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            StatisticalResourcesAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionParameters.NAMEABLE_RESOURCE}, e.getExceptionItems().get(0));
        }
    }

    @Test
    public void testUpdateQuery() throws Exception {
        Query query = StatisticalResourcesDoMocks.mockQuery();
        query = queryService.createQuery(getServiceContextWithoutPrincipal(), query);

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
