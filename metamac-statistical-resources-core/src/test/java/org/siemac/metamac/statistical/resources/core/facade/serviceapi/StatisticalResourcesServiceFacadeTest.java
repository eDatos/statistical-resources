package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsDate;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsDay;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsInternationalStringDto;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersionDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_09_OPER_0001_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_10_OPER_0002_CODE_000001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_11_OPER_0002_CODE_000002_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_12_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_16_PUBLICATION_FAILED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_17_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_04_BASIC_ORDERED_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_06_BASIC_ACTIVE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_07_BASIC_ACTIVE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_08_BASIC_DISCONTINUED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_11_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.enums.DatasetCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.enums.DatasetCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.criteria.enums.PublicationCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.publication.criteria.enums.PublicationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItemRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItemRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
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
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private QueryVersionMockFactory                  queryMockFactory;

    @Autowired
    private DatasourceMockFactory             datasourceMockFactory;

    @Autowired
    private StatisticOfficialityMockFactory   statisticOfficialityMockFactory;

    @Autowired
    private DatasetVersionMockFactory         datasetVersionMockFactory;

    @Autowired
    private PublicationVersionMockFactory     publicationVersionMockFactory;

    @Autowired
    private QuerySelectionItemRepository      querySelectionItemRepository;

    @Autowired
    private CodeItemRepository                codeItemRepository;

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testRetrieveQueryByUrn() throws Exception {
        QueryDto actual = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEqualsQueryVersion(queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME), actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME, QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME, QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testRetrieveQueries() throws Exception {
        List<QueryVersion> expected = queryMockFactory.retrieveMocks(QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME, QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME,
                QUERY_VERSION_01_WITH_SELECTION_NAME);
        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueries(getServiceContextAdministrador());

        assertEqualsQueryVersionDoAndDtoCollection(expected, actual);
    }

    @Test(expected = AssertionError.class)
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueriesErrorDifferentResponse() throws Exception {
        List<QueryVersion> expected = queryMockFactory.retrieveMocks(QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME);
        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueries(getServiceContextAdministrador());

        assertEqualsQueryVersionDoAndDtoCollection(expected, actual);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQuery() throws Exception {
        QueryDto persistedQuery = statisticalResourcesServiceFacade.createQuery(getServiceContextAdministrador(),
                StatisticalResourcesDtoMocks.mockQueryDto(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME)));
        assertNotNull(persistedQuery);
        assertNotNull(persistedQuery.getUrn());
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testUpdateQuery() throws Exception {
        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        expectedQuery.setTitle(StatisticalResourcesDoMocks.mockInternationalStringDto());

        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), expectedQuery);
        assertNotNull(actualQuery);
        assertEqualsInternationalStringDto(expectedQuery.getTitle(), actualQuery.getTitle());
    }

    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testUpdateQuerySelection() throws Exception {
        int querySelectionItemsBefore = querySelectionItemRepository.findAll().size();
        int codeItemsBefore = codeItemRepository.findAll().size();

        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());

        expectedQuery.getSelection().remove("SEX");
        expectedQuery.getSelection().put("DIM1", new HashSet<String>(Arrays.asList("A", "B", "C")));
        expectedQuery.getSelection().put("DIM2", new HashSet<String>(Arrays.asList("D", "E")));

        // Service operation
        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), expectedQuery);

        // Checks
        int querySelectionItemsAfter = querySelectionItemRepository.findAll().size();
        int codeItemsAfter = codeItemRepository.findAll().size();

        assertEquals(querySelectionItemsBefore - 1 + 2, querySelectionItemsAfter);
        assertEquals(codeItemsBefore - 1 + 5, codeItemsAfter);
        assertNotNull(actualQuery);
        assertTrue(actualQuery.getSelection().get("DIM1").contains("A"));
        assertTrue(actualQuery.getSelection().get("DIM1").contains("B"));
        assertTrue(actualQuery.getSelection().get("DIM1").contains("C"));
        assertTrue(actualQuery.getSelection().get("DIM2").contains("D"));
        assertTrue(actualQuery.getSelection().get("DIM2").contains("E"));
    }

    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testUpdateQueryDontUpdateStatus() throws Exception {
        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        QueryStatusEnum expectedStatus = expectedQuery.getStatus();
        expectedQuery.setStatus(QueryStatusEnum.PENDING_REVIEW);
        assertTrue(!expectedStatus.equals(expectedQuery.getStatus()));

        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), expectedQuery);
        assertEquals(expectedStatus, actualQuery.getStatus());
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME, QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryAsDiscontinued() throws Exception {
        // Retrieve Dto
        QueryDto mockDto = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME)
                .getLifeCycleStatisticalResource().getUrn());

        // Test
        QueryDto queryDto = statisticalResourcesServiceFacade.markQueryAsDiscontinued(getServiceContextAdministrador(), mockDto);
        assertEquals(QueryStatusEnum.DISCONTINUED, queryDto.getStatus());
        assertEquals(queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn(), queryDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testDeleteQuery() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME).getLifeCycleStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn));

        statisticalResourcesServiceFacade.deleteQuery(getServiceContextAdministrador(), urn);
        statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), urn);

    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testFindQueriesByCondition() throws Exception {
        // Find all
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(3, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(3, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(queryMockFactory.retrieveMock(QUERY_VERSION_03_BASIC_ORDERED_02_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(queryMockFactory.retrieveMock(QUERY_VERSION_04_BASIC_ORDERED_03_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find code
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String code = queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getCode();

            setCriteriaStringPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.CODE, OperationType.EQ, code);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String urn = queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn();

            setCriteriaStringPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.URN, OperationType.EQ, urn);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find title
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String titleQuery = queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");

            setCriteriaStringPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.TITLE, OperationType.EQ, titleQuery);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }
    }

    @Test
    @MetamacMock({QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_07_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME, QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testFindQueriesByConditionUsingStatus() throws Exception {

        // ACTIVE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.STATUS, OperationType.EQ, QueryStatusEnum.ACTIVE);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(2, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(2, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);
        }

        // DISCONTINUED
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.STATUS, OperationType.EQ, QueryStatusEnum.DISCONTINUED);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);
        }

        // PENDING_REVIEW
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.STATUS, OperationType.EQ, QueryStatusEnum.PENDING_REVIEW);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);
        }

        // PENDING_REVIEW or DISCONTINUED
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
            disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(QueryCriteriaPropertyEnum.STATUS.name(), QueryStatusEnum.PENDING_REVIEW, OperationType.EQ));
            disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(QueryCriteriaPropertyEnum.STATUS.name(), QueryStatusEnum.DISCONTINUED, OperationType.EQ));
            metamacCriteria.setRestriction(disjunction);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(2, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(2, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);
        }
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testCreateDatasource() throws Exception {
        DatasourceDto persistedDatasource = statisticalResourcesServiceFacade.createDatasource(getServiceContextAdministrador(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME)
                .getSiemacMetadataStatisticalResource().getUrn(), StatisticalResourcesDtoMocks.mockDatasourceDto());
        assertNotNull(persistedDatasource);
        assertNotNull(persistedDatasource.getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testUpdateDatasource() throws Exception {
        DatasourceDto updatedDatasource = statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME)
                .getIdentifiableStatisticalResource().getUrn());
        String oldCode = updatedDatasource.getCode();
        updatedDatasource.setCode("newCode" + StatisticalResourcesDtoMocks.mockString(5));

        DatasourceDto actualDatasource = statisticalResourcesServiceFacade.updateDatasource(getServiceContextAdministrador(), updatedDatasource);
        assertNotNull(actualDatasource);
        assertEquals(oldCode, actualDatasource.getCode());
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testRetrieveDatasourceByUrn() throws Exception {
        DatasourceDto actual = statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME)
                .getIdentifiableStatisticalResource().getUrn());
        assertEqualsDatasource(datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME), actual);
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testDeleteDatasource() throws Exception {
        String datasourceUrn = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME).getIdentifiableStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASOURCE_NOT_FOUND, datasourceUrn));

        statisticalResourcesServiceFacade.deleteDatasource(getServiceContextAdministrador(), datasourceUrn);
        statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), datasourceUrn);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getDatasources();

            List<DatasourceDto> actual = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
            assertEqualsDatasourceDoAndDtoCollection(expected, actual);
        }

        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getDatasources();

            List<DatasourceDto> actual = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
            assertEqualsDatasourceDoAndDtoCollection(expected, actual);
        }
    }

    @Test(expected = AssertionError.class)
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasourcesByDatasetVersionErrorDifferentResponse() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        List<Datasource> expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getDatasources();
        expected.remove(0);

        List<DatasourceDto> actual = statisticalResourcesServiceFacade.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
        assertEqualsDatasourceDoAndDtoCollection(expected, actual);
    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testRetrieveDatasetByUrn() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetDto dataset = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        assertEqualsDatasetVersion(datasetVersion, dataset);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasetVersions() throws Exception {
        DatasetVersion datasetVersionFirst = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        DatasetVersion datasetVersionLast = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        // Version in urn does not care
        {
            List<DatasetDto> datasets = statisticalResourcesServiceFacade.retrieveDatasetVersions(getServiceContextAdministrador(), datasetVersionLast.getSiemacMetadataStatisticalResource().getUrn());
            assertNotNull(datasets);
            assertEquals(2, datasets.size());
            assertEqualsDatasetVersion(datasetVersionFirst, datasets.get(0));
            assertEqualsDatasetVersion(datasetVersionLast, datasets.get(1));
        }
        {
            List<DatasetDto> datasets = statisticalResourcesServiceFacade
                    .retrieveDatasetVersions(getServiceContextAdministrador(), datasetVersionFirst.getSiemacMetadataStatisticalResource().getUrn());
            assertNotNull(datasets);
            assertEquals(2, datasets.size());
            assertEqualsDatasetVersion(datasetVersionFirst, datasets.get(0));
            assertEqualsDatasetVersion(datasetVersionLast, datasets.get(1));
        }
    }

    @Override
    @Test
    @MetamacMock(STATISTIC_OFFICIALITY_01_BASIC_NAME)
    public void testCreateDataset() throws Exception {
        StatisticOfficiality officiality = statisticOfficialityMockFactory.retrieveMock(STATISTIC_OFFICIALITY_01_BASIC_NAME);
        DatasetDto datasetDto = StatisticalResourcesDtoMocks.mockDatasetDto(officiality);
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationItem();

        DatasetDto newDatasetDto = statisticalResourcesServiceFacade.createDataset(getServiceContextAdministrador(), datasetDto, statisticalOperation);
        assertNotNull(newDatasetDto);
        assertNotNull(newDatasetDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDataset() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        datasetDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(datasetDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME})
    public void testUpdateDatasetWithLanguages() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionUrn);
        datasetDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(datasetDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetChangeCodeNotAllowed() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        datasetDto.setCode("CHANGED");

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(datasetVersion.getSiemacMetadataStatisticalResource().getCode(), updatedDataset.getCode());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetChangeStatisticalOperationNotAllowed() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String originalStatisticalOperationCode = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationItem();
        datasetDto.setStatisticalOperation(statisticalOperation);

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(originalStatisticalOperationCode, updatedDataset.getStatisticalOperation().getCode());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetIgnoreDateNextVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DateTime originalDateNextVersion = datasetVersion.getSiemacMetadataStatisticalResource().getNextVersionDate();

        {
            DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            datasetDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetDto.setNextVersion(StatisticalResourceNextVersionEnum.NO_UPDATES);

            DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(originalDateNextVersion, updatedDataset.getNextVersionDate());
        }
        {
            DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            datasetDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetDto.setNextVersion(StatisticalResourceNextVersionEnum.NON_SCHEDULED_UPDATE);

            DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(originalDateNextVersion, updatedDataset.getNextVersionDate());
        }
        {
            DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            datasetDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetDto.setNextVersion(StatisticalResourceNextVersionEnum.SCHEDULED_UPDATE);

            DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(new DateTime(datasetDto.getNextVersionDate()), updatedDataset.getNextVersionDate());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetNotAllowedMetadata() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        String oldCreator = datasetDto.getCreatedBy();

        datasetDto.setCreatedBy("My user");

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(oldCreator, updatedDataset.getCreatedBy());
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testDeleteDataset() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        assertNotNull(statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
        statisticalResourcesServiceFacade.deleteDataset(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, datasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
        statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME})
    public void testFindDatasetsByCondition() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        DatasetVersion dsOper2Code1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_000001_NAME);
        DatasetVersion dsOper2Code2 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_11_OPER_0002_CODE_000002_NAME);

        // Find All
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(dsOper2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(dsOper2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME})
    public void testFindDatasetsByConditionByCode() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // Find CODE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.CODE, OperationType.EQ, dsOper1Code3.getSiemacMetadataStatisticalResource().getCode());

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME})
    public void testFindDatasetsByConditionByUrn() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.URN, OperationType.EQ, dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn());

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getCode(), results.get(0).getCode());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME,
            DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME})
    public void testFindDatasetsByConditionByProcStatus() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        DatasetVersion dsOper2Code1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_000001_NAME);
        DatasetVersion dsOper2Code2 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_11_OPER_0002_CODE_000002_NAME);
        DatasetVersion dsOper2Code3ProdVal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME);

        // Find PROC STATUS
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, StatisticalResourceProcStatusEnum.DRAFT);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(dsOper2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(dsOper2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(dsOper2Code3ProdVal.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME,
            DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME, DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME, DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME})
    public void testFindDatasetsByConditionByStatisticalOperationUrn() throws Exception {
        DatasetVersion datasetOper2Code1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_000001_NAME);
        DatasetVersion datasetOper2Code2 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_11_OPER_0002_CODE_000002_NAME);
        DatasetVersion datasetOper2CodeMax = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME);
        DatasetVersion datasetOper2Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME);
        DatasetVersion datasetOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // Find STATISTICAL_OPERATION
        {
            String statisticalOperationUrn = StatisticalResourcesDoMocks.mockStatisticalOperationUrn(DatasetVersionMockFactory.OPERATION_02_CODE);

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(4, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(4, results.size());

            assertEquals(datasetOper2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(datasetOper2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(datasetOper2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
            assertEquals(datasetOper2CodeMax.getSiemacMetadataStatisticalResource().getUrn(), results.get(3).getUrn());
        }
        {
            String statisticalOperationUrn = StatisticalResourcesDoMocks.mockStatisticalOperationUrn(DatasetVersionMockFactory.OPERATION_01_CODE);

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(datasetOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }

        {
            String statisticalOperationUrn = URN_NOT_EXISTS;

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(0, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(0, results.size());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME,
            DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME, DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME, DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME})
    public void testFindDatasetsByConditionOrderByStatisticalOperationUrn() throws Exception {
        DatasetVersion datasetOper2Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME);
        DatasetVersion datasetOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // FIND CODE
        String code = "000003";

        MetamacCriteria metamacCriteria = new MetamacCriteria();
        addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.STATISTICAL_OPERATION_URN, OrderTypeEnum.ASC);
        setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
        setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.CODE, OperationType.ILIKE, code);

        MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<DatasetDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEquals(datasetOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        assertEquals(datasetOper2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDataset() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        DatasetDto newVersion = statisticalResourcesServiceFacade.versioningDataset(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn(),
                VersionTypeEnum.MINOR);
        assertNotNull(newVersion);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendToProductionValidation() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.sendToProductionValidation(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, updatedDataset.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedDataset.getProductionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedDataset.getProductionValidationDate()));
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendToDiffusionValidation() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.sendToDiffusionValidation(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION, updatedDataset.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedDataset.getDiffusionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedDataset.getDiffusionValidationDate()));
    }

    @Override
    @Test
    public void testCreatePublication() throws Exception {
        PublicationDto publicationDto = StatisticalResourcesDtoMocks.mockPublicationDto();
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationItem();

        PublicationDto newPublicationDto = statisticalResourcesServiceFacade.createPublication(getServiceContextAdministrador(), publicationDto, statisticalOperation);
        assertNotNull(newPublicationDto);
        assertNotNull(newPublicationDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testUpdatePublication() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersionUrn);
        publicationDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        PublicationDto updatedDataset = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(publicationDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationIgnoreChangeCode() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);

        PublicationDto publicationDto = statisticalResourcesServiceFacade
                .retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        publicationDto.setCode("CHANGED_CODE");

        PublicationDto updatedPublication = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedPublication);
        assertEquals(publicationVersion.getSiemacMetadataStatisticalResource().getCode(), updatedPublication.getCode());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationIgnoreChangeStatisticalOperation() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        String originalStatisticalOperationCode = publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        PublicationDto publicationDto = statisticalResourcesServiceFacade
                .retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationItem();
        publicationDto.setStatisticalOperation(statisticalOperation);

        PublicationDto updatedPublication = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedPublication);
        assertEquals(originalStatisticalOperationCode, updatedPublication.getStatisticalOperation().getCode());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationIgnoreDateNextVersionIfItsNotAllowed() throws Exception {
        // DATE_NEXT_VERSION can only be modified if dateNextVersionType is SCHEDULED_UPDATE

        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        DateTime originalDateNextVersion = publicationVersion.getSiemacMetadataStatisticalResource().getNextVersionDate();

        {
            PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource()
                    .getUrn());
            publicationDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationDto.setNextVersion(StatisticalResourceNextVersionEnum.NO_UPDATES);

            PublicationDto updatedPublicationDto = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDto);
            assertNotNull(updatedPublicationDto);
            assertEqualsDate(originalDateNextVersion, updatedPublicationDto.getNextVersionDate());
        }
        {
            PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource()
                    .getUrn());
            publicationDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationDto.setNextVersion(StatisticalResourceNextVersionEnum.NON_SCHEDULED_UPDATE);

            PublicationDto updatedPublicationDto = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDto);
            assertNotNull(updatedPublicationDto);
            assertEqualsDate(originalDateNextVersion, updatedPublicationDto.getNextVersionDate());
        }
        {
            PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource()
                    .getUrn());
            publicationDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationDto.setNextVersion(StatisticalResourceNextVersionEnum.SCHEDULED_UPDATE);

            PublicationDto updatedPublicationDto = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDto);
            assertNotNull(updatedPublicationDto);
            assertEqualsDate(new DateTime(publicationDto.getNextVersionDate()), updatedPublicationDto.getNextVersionDate());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationIgnoreChangeCreatorMetadata() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersionUrn);
        String originalCreator = publicationDto.getCreatedBy();

        publicationDto.setCreatedBy("My user");

        PublicationDto updatedDataset = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedDataset);
        assertEquals(originalCreator, updatedDataset.getCreatedBy());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testDeletePublication() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        statisticalResourcesServiceFacade.deletePublication(getServiceContextAdministrador(), publicationVersionUrn);

        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, publicationVersionUrn));
        statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersionUrn);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationByCondition() throws Exception {
        PublicationVersion publicationVersionOperation1Code1 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME);
        PublicationVersion publicationVersionOperation1Code2 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME);
        PublicationVersion publicationVersionOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);
        PublicationVersion publicationVersionOperation2Code1 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME);
        PublicationVersion publicationVersionOperation2Code2 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME);
        PublicationVersion publicationVersionOperation2Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME);

        // Find All
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(6, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(6, results.size());

            assertEquals(publicationVersionOperation1Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationVersionOperation1Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
            assertEquals(publicationVersionOperation2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(3).getUrn());
            assertEquals(publicationVersionOperation2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(4).getUrn());
            assertEquals(publicationVersionOperation2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(5).getUrn());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationsByConditionByCode() throws Exception {
        PublicationVersion publicationVersionOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);

        // Find CODE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OperationType.EQ, publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource()
                    .getCode());

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationByConditionByUrn() throws Exception {
        PublicationVersion publicationVersionOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.URN, OperationType.EQ, publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getUrn());

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getCode(), results.get(0).getCode());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLICATION_FAILED_NAME, PUBLICATION_VERSION_17_PUBLISHED_NAME})
    public void testFindPublicationByConditionByProcStatus() throws Exception {
        PublicationVersion publicationVersionDraft = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_12_DRAFT_NAME);
        PublicationVersion publicationVersionProductionValidation = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME);
        PublicationVersion publicationVersionValidationRejected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME);

        // Find PROC STATUS
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.PROC_STATUS, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setDisjunctionCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, StatisticalResourceProcStatusEnum.DRAFT,
                    StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(2, results.size());

            assertEquals(publicationVersionDraft.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationVersionValidationRejected.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
        }
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(publicationVersionProductionValidation.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationsByConditionByStatisticalOperationUrn() throws Exception {
        PublicationVersion publicationOperation1Code1 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME);
        PublicationVersion publicationOperation1Code2 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME);
        PublicationVersion publicationOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);
        PublicationVersion publicationOperation2Code1 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME);
        PublicationVersion publicationOperation2Code2 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME);
        PublicationVersion publicationOperation2Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME);

        // Find STATISTICAL_OPERATION
        {
            String statisticalOperationUrn = StatisticalResourcesDoMocks.mockStatisticalOperationUrn(DatasetVersionMockFactory.OPERATION_01_CODE);

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, PublicationCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(publicationOperation1Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationOperation1Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(publicationOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }
        {
            String statisticalOperationUrn = StatisticalResourcesDoMocks.mockStatisticalOperationUrn(DatasetVersionMockFactory.OPERATION_02_CODE);

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, PublicationCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(publicationOperation2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationOperation2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(publicationOperation2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }

        {
            String statisticalOperationUrn = URN_NOT_EXISTS;

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, PublicationCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(0, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(0, results.size());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationByConditionOrderByStatisticalOperationUrn() throws Exception {
        PublicationVersion publicationOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);
        PublicationVersion publicationOperation2Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME);

        // FIND CODE
        String code = "000003";

        MetamacCriteria metamacCriteria = new MetamacCriteria();
        addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.STATISTICAL_OPERATION_URN, OrderTypeEnum.ASC);
        setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
        setCriteriaStringPropertyRestriction(metamacCriteria, PublicationCriteriaPropertyEnum.CODE, OperationType.ILIKE, code);

        MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<PublicationDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEquals(publicationOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        assertEquals(publicationOperation2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testRetrievePublicationByUrn() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        PublicationDto publicationDto = statisticalResourcesServiceFacade
                .retrievePublicationByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsPublicationVersion(publicationVersion, publicationDto);
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME)
    public void testRetrievePublicationVersions() throws Exception {
        PublicationVersion publicationVersionFirst = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        PublicationVersion publicationVersionLast = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);
        // Version in urn does not care
        {
            List<PublicationDto> datasets = statisticalResourcesServiceFacade.retrievePublicationVersions(getServiceContextAdministrador(), publicationVersionLast
                    .getSiemacMetadataStatisticalResource().getUrn());
            assertNotNull(datasets);
            assertEquals(2, datasets.size());
            assertEqualsPublicationVersion(publicationVersionFirst, datasets.get(0));
            assertEqualsPublicationVersion(publicationVersionLast, datasets.get(1));
        }
        {
            List<PublicationDto> datasets = statisticalResourcesServiceFacade.retrievePublicationVersions(getServiceContextAdministrador(), publicationVersionFirst
                    .getSiemacMetadataStatisticalResource().getUrn());
            assertNotNull(datasets);
            assertEquals(2, datasets.size());
            assertEqualsPublicationVersion(publicationVersionFirst, datasets.get(0));
            assertEqualsPublicationVersion(publicationVersionLast, datasets.get(1));
        }

    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_17_PUBLISHED_NAME)
    public void testVersioningPublication() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_17_PUBLISHED_NAME);
        PublicationDto newVersion = statisticalResourcesServiceFacade.versioningPublication(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn(),
                VersionTypeEnum.MINOR);
        assertNotNull(newVersion);
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    // TODO: Este metodo debe ser eliminado cuando la jerarquia este bien hecho
    // TODO: Este test no es unitario pero no importa porque es provisional
    public void testFindDatasetVersionForPublicationVersion() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetVersionUrn01 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetVersionUrn02 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_02_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetVersionUrn03 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();

        statisticalResourcesServiceFacade.addDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn01);
        statisticalResourcesServiceFacade.addDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn02);
        statisticalResourcesServiceFacade.addDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn03);

        int datasetVersions = statisticalResourcesServiceFacade.findDatasetVersionForPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn).size();

        assertEquals(3, datasetVersions);
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    // TODO: Este metodo debe ser eliminado cuando la jerarquia este bien hecho
    public void testAddDatasetVersionToPublicationVersion() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        int datasetVersionsBefore = statisticalResourcesServiceFacade.findDatasetVersionForPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn).size();

        statisticalResourcesServiceFacade.addDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn);

        int datasetVersionsAfter = statisticalResourcesServiceFacade.findDatasetVersionForPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn).size();
        assertEquals(datasetVersionsBefore + 1, datasetVersionsAfter);
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    // TODO: Este metodo debe ser eliminado cuando la jerarquia este bien hecho
    // TODO: Este test no es unitario pero no importa porque es provisional
    public void testRemoveDatasetVersionToPublicationVersion() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetVersionUrn01 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetVersionUrn02 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_02_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetVersionUrn03 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();

        statisticalResourcesServiceFacade.addDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn01);
        statisticalResourcesServiceFacade.addDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn02);
        statisticalResourcesServiceFacade.addDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn03);

        int datasetVersionsBefore = statisticalResourcesServiceFacade.findDatasetVersionForPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn).size();

        statisticalResourcesServiceFacade.removeDatasetVersionToPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn, datasetVersionUrn02);

        int datasetVersionsAfter = statisticalResourcesServiceFacade.findDatasetVersionForPublicationVersion(getServiceContextAdministrador(), publicationVersionUrn).size();
        assertEquals(datasetVersionsBefore - 1, datasetVersionsAfter);
    }

    // ------------------------------------------------------------
    // CRITERIA UTILS
    // ------------------------------------------------------------
    @SuppressWarnings("rawtypes")
    private void addOrderToCriteria(MetamacCriteria metamacCriteria, Enum property, OrderTypeEnum orderType) {
        if (metamacCriteria.getOrdersBy() == null) {
            metamacCriteria.setOrdersBy(new ArrayList<MetamacCriteriaOrder>());
        }
        MetamacCriteriaOrder order = new MetamacCriteriaOrder();
        order.setType(orderType);
        order.setPropertyName(property.name());
        metamacCriteria.getOrdersBy().add(order);
    }

    private void setCriteriaPaginator(MetamacCriteria metamacCriteria, int firstResult, int maxResultSize, Boolean countTotalResults) {
        metamacCriteria.setPaginator(new MetamacCriteriaPaginator());
        metamacCriteria.getPaginator().setFirstResult(firstResult);
        metamacCriteria.getPaginator().setMaximumResultSize(maxResultSize);
        metamacCriteria.getPaginator().setCountTotalResults(countTotalResults);
    }

    @SuppressWarnings("rawtypes")
    private void setCriteriaEnumPropertyRestriction(MetamacCriteria metamacCriteria, Enum property, OperationType operationType, Enum enumValue) {
        MetamacCriteriaPropertyRestriction propertyRestriction = new MetamacCriteriaPropertyRestriction();
        propertyRestriction.setPropertyName(property.name());
        propertyRestriction.setOperationType(operationType);
        propertyRestriction.setEnumValue(enumValue);
        metamacCriteria.setRestriction(propertyRestriction);
    }

    @SuppressWarnings("rawtypes")
    private void setDisjunctionCriteriaEnumPropertyRestriction(MetamacCriteria metamacCriteria, Enum property, OperationType operationType, Enum enumValue01, Enum enumValue02) {
        MetamacCriteriaDisjunctionRestriction disjunctionRestriction = new MetamacCriteriaDisjunctionRestriction();
        disjunctionRestriction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(property.name(), enumValue01, OperationType.EQ));
        disjunctionRestriction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(property.name(), enumValue02, OperationType.EQ));
        metamacCriteria.setRestriction(disjunctionRestriction);
    }

    @SuppressWarnings("rawtypes")
    private void setCriteriaStringPropertyRestriction(MetamacCriteria metamacCriteria, Enum property, OperationType operationType, String stringValue) {
        MetamacCriteriaPropertyRestriction propertyRestriction = new MetamacCriteriaPropertyRestriction();
        propertyRestriction.setPropertyName(property.name());
        propertyRestriction.setOperationType(operationType);
        propertyRestriction.setStringValue(stringValue);
        metamacCriteria.setRestriction(propertyRestriction);
    }
}
