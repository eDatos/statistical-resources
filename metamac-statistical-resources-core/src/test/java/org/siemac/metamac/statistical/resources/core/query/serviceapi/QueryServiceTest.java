package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_04_BASIC_ORDERED_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryCollection;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesNotPersistedDoMocks;
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
    protected QueryService                            queryService;

    @Autowired
    protected QueryRepository                         queryRepository;

    @Autowired
    protected QueryMockFactory                        queryMockFactory;

    @Autowired
    protected StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Override
    @Test
    @MetamacMock(QUERY_01_BASIC_NAME)
    public void testRetrieveQueryByUrn() throws MetamacException {
        Query actual = queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), queryMockFactory.QUERY_01_BASIC.getNameableStatisticalResource().getUrn());
        assertEqualsQuery(queryMockFactory.QUERY_01_BASIC, actual);
    }

    @Test
    public void testRetrieveQueryByUrnParameterRequired() throws MetamacException {
        try {
            queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), EMPTY);
            fail("parameter required");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionSingleParameters.URN}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
    public void testFindQueriesByCondition() throws Exception {
        // Find all
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).orderBy(QueryProperties.nameableStatisticalResource().code()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(3, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(queryMockFactory.QUERY_02_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(queryMockFactory.QUERY_03_BASIC_ORDERED_02.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
            assertEquals(queryMockFactory.QUERY_04_BASIC_ORDERED_03.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }

        // Find code
        {
            String code = queryMockFactory.QUERY_02_BASIC_ORDERED_01.getNameableStatisticalResource().getCode();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().code()).eq(code).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(queryMockFactory.QUERY_02_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }

        // Find URN
        {
            String urn = queryMockFactory.QUERY_02_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().urn()).eq(urn).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(queryMockFactory.QUERY_02_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }

        // Find title
        {
            String titleQuery = queryMockFactory.QUERY_02_BASIC_ORDERED_01.getNameableStatisticalResource().getTitle().getLocalisedLabel("es");
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().title().texts().label())
                    .eq(titleQuery).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(queryMockFactory.QUERY_02_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getNameableStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    public void testCreateQuery() throws Exception {
        Query expected = statisticalResourcesNotPersistedDoMocks.mockQuery();
        Query actual = queryService.createQuery(getServiceContextWithoutPrincipal(), expected);
        assertEqualsQuery(expected, actual);
    }

    @Test
    public void testCreateQueryErrorNameableResourceRequired() throws Exception {
        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithNameableNull();
        try {
            queryService.createQuery(getServiceContextWithoutPrincipal(), query);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            BaseAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, 1, new String[]{ServiceExceptionParameters.QUERY__NAMEABLE_STATISTICAL_RESOURCE}, e
                    .getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(QUERY_01_BASIC_NAME)
    public void testUpdateQuery() throws Exception {
        Query query = queryMockFactory.QUERY_01_BASIC;
        query.getNameableStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        assertEqualsQuery(query, updatedQuery);
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueries() throws Exception {
        List<Query> expected = queryMockFactory.getMocks(QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME);

        List<Query> actual = queryService.retrieveQueries(getServiceContextWithoutPrincipal());

        assertEqualsQueryCollection(expected, actual);
    }
}
