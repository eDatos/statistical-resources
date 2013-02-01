package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsInternationalStringDto;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion01Basic;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion03ForDataset03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion04LastVersionForDataset03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.getDatasource01Basic;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_04_BASIC_ORDERED_03_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery01Basic;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery02BasicOrdered01;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery03BasicOrdered02;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery04BasicOrdered03;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryDoAndDtoCollection;

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
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDtoMocks;
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

    @Autowired
    protected DatasourceMockFactory             datasourceMockFactory;

    @Autowired
    protected DatasetVersionMockFactory         datasetVersionMockFactory;

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({QUERY_01_BASIC_NAME, QUERY_02_BASIC_ORDERED_01_NAME})
    public void testRetrieveQueryByUrn() throws Exception {
        QueryDto actual = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), getQuery01Basic().getLifeCycleStatisticalResource().getUrn());
        assertEqualsQuery(getQuery01Basic(), actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueries() throws Exception {
        List<Query> expected = queryMockFactory.getMocks(QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME);

        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueries(getServiceContextAdministrador());

        assertEqualsQueryDoAndDtoCollection(expected, actual);
    }

    @Test(expected = AssertionError.class)
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueriesErrorDifferentResponse() throws Exception {
        List<Query> expected = queryMockFactory.getMocks(QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME);

        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueries(getServiceContextAdministrador());

        assertEqualsQueryDoAndDtoCollection(expected, actual);
    }

    @Override
    @Test
    public void testCreateQuery() throws Exception {
        QueryDto persistedQuery = statisticalResourcesServiceFacade.createQuery(getServiceContextAdministrador(), StatisticalResourcesDtoMocks.mockQueryDto());
        assertNotNull(persistedQuery);
        assertNotNull(persistedQuery.getUrn());
    }

    @Override
    @Test
    @MetamacMock({QUERY_01_BASIC_NAME, QUERY_02_BASIC_ORDERED_01_NAME})
    public void testUpdateQuery() throws Exception {
        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), getQuery01Basic().getLifeCycleStatisticalResource().getUrn());
        expectedQuery.setTitle(StatisticalResourcesDoMocks.mockInternationalStringDto());

        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), expectedQuery);
        assertNotNull(actualQuery);
        assertEqualsInternationalStringDto(expectedQuery.getTitle(), actualQuery.getTitle());
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
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
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(getQuery03BasicOrdered02().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(getQuery04BasicOrdered03().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
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
            String code = getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getCode();

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
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
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
            String urn = getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn();

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
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
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
            String titleQuery = getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");

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
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testCreateDatasource() throws Exception {
        DatasourceDto persistedDatasource = statisticalResourcesServiceFacade.createDatasource(getServiceContextAdministrador(), getDatasetVersion01Basic().getSiemacMetadataStatisticalResource()
                .getUrn(), StatisticalResourcesDtoMocks.mockDatasourceDto());
        assertNotNull(persistedDatasource);
        assertNotNull(persistedDatasource.getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testUpdateDatasource() throws Exception {
        DatasourceDto expectedDatasource = statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), getDatasource01Basic().getIdentifiableStatisticalResource()
                .getUrn());
        expectedDatasource.setCode("newCode" + StatisticalResourcesDtoMocks.mockString(5));

        DatasourceDto actualDatasource = statisticalResourcesServiceFacade.updateDatasource(getServiceContextAdministrador(), expectedDatasource);
        assertNotNull(actualDatasource);
        assertEquals(expectedDatasource.getCode(), actualDatasource.getCode());
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testRetrieveDatasourceByUrn() throws Exception {
        DatasourceDto actual = statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), getDatasource01Basic().getIdentifiableStatisticalResource().getUrn());
        assertEqualsDatasource(getDatasource01Basic(), actual);
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testDeleteDatasource() throws Exception {
        String datasourceUrn = getDatasource01Basic().getIdentifiableStatisticalResource().getUrn();
        statisticalResourcesServiceFacade.deleteDatasource(getServiceContextAdministrador(), datasourceUrn);

        try {
            statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), datasourceUrn);
            fail("datasource deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASOURCE_NOT_FOUND, 1, new String[]{datasourceUrn}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = getDatasetVersion03ForDataset03().getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = getDatasetVersion03ForDataset03().getDatasources();

            List<DatasourceDto> actual = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
            assertEqualsDatasourceDoAndDtoCollection(expected, actual);
        }

        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = getDatasetVersion04LastVersionForDataset03().getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = getDatasetVersion04LastVersionForDataset03().getDatasources();

            List<DatasourceDto> actual = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
            assertEqualsDatasourceDoAndDtoCollection(expected, actual);
        }
    }

    @Test(expected = AssertionError.class)
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasourcesByDatasetVersionErrorDifferentResponse() throws Exception {
        String datasetVersionUrn = getDatasetVersion03ForDataset03().getSiemacMetadataStatisticalResource().getUrn();
        List<Datasource> expected = getDatasetVersion03ForDataset03().getDatasources();
        expected.remove(0);

        List<DatasourceDto> actual = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
        assertEqualsDatasourceDoAndDtoCollection(expected, actual);
    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    @Test
    @Override
    public void testCreateDataset() throws Exception {
        fail("not implemented");
        // TODO Auto-generated method stub

    }

    @Test
    @Override
    public void testUpdateDataset() throws Exception {
        fail("not implemented");
        // TODO Auto-generated method stub

    }

    @Test
    @Override
    public void testDeleteDataset() throws Exception {
        fail("not implemented");
        // TODO Auto-generated method stub

    }

    @Test
    @Override
    public void testFindDatasetsByCondition() throws Exception {
        fail("not implemented");
        // TODO Auto-generated method stub

    }

    @Test
    @Override
    public void testRetrieveDatasetByUrn() throws Exception {
        fail("not implemented");
        // TODO Auto-generated method stub

    }

    @Test
    @Override
    public void testRetrieveDatasetVersions() throws Exception {
        fail("not implemented");
        // TODO Auto-generated method stub

    }

    @Test
    @Override
    public void testVersioningDataset() throws Exception {
        fail("not implemented");
        // TODO Auto-generated method stub

    }
}
