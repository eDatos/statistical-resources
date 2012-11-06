package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_01;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_01_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_ORDERED_01;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_ORDERED_02;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_ORDERED_03;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_BASIC_ORDERED_03_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.query.criteria.QueryCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.query.criteria.QueryCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDtoMocks;
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
    protected QueryRepository                   queryRepository;

    @Autowired
    protected QueryMockFactory                  queryMockFactory;

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock(QUERY_BASIC_01_NAME)
    public void testRetrieveQueryByUrn() throws Exception {
        QueryDto actual = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), QUERY_BASIC_01.getNameableStatisticalResource().getUrn());
        StatisticalResourcesAsserts.assertEqualsQuery(QUERY_BASIC_01, actual);
    }

    @Test
    @MetamacMock({QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME, QUERY_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueries() throws Exception {
        List<Query> expected = queryMockFactory.getMocks(QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME, QUERY_BASIC_ORDERED_03_NAME);

        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueries(getServiceContextAdministrador());

        StatisticalResourcesAsserts.assertEqualsQueryDoAndDtoCollection(expected, actual);
    }

    @Test(expected = AssertionError.class)
    @MetamacMock({QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME, QUERY_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueriesFail() throws Exception {
        List<Query> expected = queryMockFactory.getMocks(QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME);

        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueries(getServiceContextAdministrador());

        StatisticalResourcesAsserts.assertEqualsQueryDoAndDtoCollection(expected, actual);
    }

    @Test
    public void testCreateQuery() throws Exception {
        QueryDto persistedQuery = statisticalResourcesServiceFacade.createQuery(getServiceContextAdministrador(), StatisticalResourcesDtoMocks.mockQueryDto());
        assertNotNull(persistedQuery);
        assertNotNull(persistedQuery.getUrn());
    }

    @Test
    @MetamacMock({QUERY_BASIC_01_NAME, QUERY_BASIC_ORDERED_01_NAME})
    public void testUpdateQuery() throws Exception {
        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), QUERY_BASIC_01.getNameableStatisticalResource().getUrn());
        expectedQuery.setTitle(StatisticalResourcesDoMocks.mockInternationalStringDto());

        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQuery(getServiceContextWithoutPrincipal(), expectedQuery);
        assertNotNull(actualQuery);
        StatisticalResourcesAsserts.assertEqualsInternationalStringDto(expectedQuery.getTitle(), actualQuery.getTitle());
    }

    @Test
    @MetamacMock({QUERY_BASIC_ORDERED_01_NAME, QUERY_BASIC_ORDERED_02_NAME, QUERY_BASIC_ORDERED_03_NAME})
    public void testFindQueriesByCondition() throws Exception {
        // Find all
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            // Order
            metamacCriteria.setOrdersBy(new ArrayList<MetamacCriteriaOrder>());
            MetamacCriteriaOrder order = new MetamacCriteriaOrder();
            order.setType(OrderTypeEnum.ASC);
            order.setPropertyName(QueryCriteriaOrderEnum.CODE.name());
            metamacCriteria.getOrdersBy().add(order);

            // Pagination
            metamacCriteria.setPaginator(new MetamacCriteriaPaginator());
            metamacCriteria.getPaginator().setFirstResult(0);
            metamacCriteria.getPaginator().setMaximumResultSize(Integer.MAX_VALUE);
            metamacCriteria.getPaginator().setCountTotalResults(Boolean.TRUE);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(3, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(3, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(QUERY_BASIC_ORDERED_02.getNameableStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(QUERY_BASIC_ORDERED_03.getNameableStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find code
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            // Order
            metamacCriteria.setOrdersBy(new ArrayList<MetamacCriteriaOrder>());
            MetamacCriteriaOrder order = new MetamacCriteriaOrder();
            order.setType(OrderTypeEnum.ASC);
            order.setPropertyName(QueryCriteriaOrderEnum.CODE.name());
            metamacCriteria.getOrdersBy().add(order);

            // Pagination
            metamacCriteria.setPaginator(new MetamacCriteriaPaginator());
            metamacCriteria.getPaginator().setFirstResult(0);
            metamacCriteria.getPaginator().setMaximumResultSize(Integer.MAX_VALUE);
            metamacCriteria.getPaginator().setCountTotalResults(Boolean.TRUE);

            // Restrictions
            String code = QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getCode();

            MetamacCriteriaPropertyRestriction propertyRestriction = new MetamacCriteriaPropertyRestriction();
            propertyRestriction.setPropertyName(QueryCriteriaPropertyEnum.CODE.name());
            propertyRestriction.setOperationType(OperationType.EQ);
            propertyRestriction.setStringValue(code);
            metamacCriteria.setRestriction(propertyRestriction);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            // Order
            metamacCriteria.setOrdersBy(new ArrayList<MetamacCriteriaOrder>());
            MetamacCriteriaOrder order = new MetamacCriteriaOrder();
            order.setType(OrderTypeEnum.ASC);
            order.setPropertyName(QueryCriteriaOrderEnum.CODE.name());
            metamacCriteria.getOrdersBy().add(order);

            // Pagination
            metamacCriteria.setPaginator(new MetamacCriteriaPaginator());
            metamacCriteria.getPaginator().setFirstResult(0);
            metamacCriteria.getPaginator().setMaximumResultSize(Integer.MAX_VALUE);
            metamacCriteria.getPaginator().setCountTotalResults(Boolean.TRUE);

            // Restrictions
            String urn = QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn();

            MetamacCriteriaPropertyRestriction propertyRestriction = new MetamacCriteriaPropertyRestriction();
            propertyRestriction.setPropertyName(QueryCriteriaPropertyEnum.URN.name());
            propertyRestriction.setOperationType(OperationType.EQ);
            propertyRestriction.setStringValue(urn);
            metamacCriteria.setRestriction(propertyRestriction);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find title
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            // Order
            metamacCriteria.setOrdersBy(new ArrayList<MetamacCriteriaOrder>());
            MetamacCriteriaOrder order = new MetamacCriteriaOrder();
            order.setType(OrderTypeEnum.ASC);
            order.setPropertyName(QueryCriteriaOrderEnum.CODE.name());
            metamacCriteria.getOrdersBy().add(order);

            // Pagination
            metamacCriteria.setPaginator(new MetamacCriteriaPaginator());
            metamacCriteria.getPaginator().setFirstResult(0);
            metamacCriteria.getPaginator().setMaximumResultSize(Integer.MAX_VALUE);
            metamacCriteria.getPaginator().setCountTotalResults(Boolean.TRUE);

            // Restrictions
            String titleQuery = QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getTitle().getLocalisedLabel("es");

            MetamacCriteriaPropertyRestriction propertyRestriction = new MetamacCriteriaPropertyRestriction();
            propertyRestriction.setPropertyName(QueryCriteriaPropertyEnum.TITLE.name());
            propertyRestriction.setOperationType(OperationType.EQ);
            propertyRestriction.setStringValue(titleQuery);
            metamacCriteria.setRestriction(propertyRestriction);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(QUERY_BASIC_ORDERED_01.getNameableStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Test
    public void testCreateDatasource() throws Exception {
        fail("not implemented");
    }

    @Test
    public void testUpdateDatasource() throws Exception {
        fail("not implemented");
    }

    @Test
    public void testRetrieveDatasource() throws Exception {
        fail("not implemented");
    }

    @Test
    public void testDeleteDatasource() throws Exception {
        fail("not implemented");
    }

    @Test
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        fail("not implemented");
    }
}
