package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsDate;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsDay;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsInternationalStringDto;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsChapter;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsCube;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationStructure;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuerySelection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersionDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_01_BASIC_NAME;
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
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_16_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_38_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME;
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
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_21_FOR_QUERY_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
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
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.enums.DatasetCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.enums.DatasetCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.publication.criteria.enums.PublicationCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.publication.criteria.enums.PublicationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItemRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItemRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.DsRepositoryMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.SrmMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml",
                                    "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class StatisticalResourcesServiceFacadeTest extends StatisticalResourcesBaseTest implements StatisticalResourcesServiceFacadeTestBase {

    
    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private QueryVersionMockFactory           queryVersionMockFactory;

    @Autowired
    private QueryMockFactory                  queryMockFactory;

    @Autowired
    private DatasourceMockFactory             datasourceMockFactory;

    @Autowired
    private StatisticOfficialityMockFactory   statisticOfficialityMockFactory;

    @Autowired
    private DatasetVersionMockFactory         datasetVersionMockFactory;

    @Autowired
    private PublicationVersionMockFactory     publicationVersionMockFactory;

    @Autowired
    private ChapterMockFactory                chapterMockFactory;

    @Autowired
    private CubeMockFactory                   cubeMockFactory;

    @Autowired
    private DatasetMockFactory                datasetMockFactory;

    @Autowired
    private PublicationMockFactory            publicationMockFactory;

    @Autowired
    private QuerySelectionItemRepository      querySelectionItemRepository;

    @Autowired
    private CodeItemRepository                codeItemRepository;

    @Autowired
    private SrmRestInternalService            srmRestInternalService;

    @Autowired
    private DatasetRepositoriesServiceFacade  statisticsDatasetRepositoriesServiceFacade;

    @Before
    public void onBeforeTest() throws Exception {
        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());

        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);
    }

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testRetrieveQueryVersionByUrn() throws Exception {
        QueryDto actual = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEqualsQueryVersion(queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME), actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestQueryVersion() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME);
        QueryDto actual = statisticalResourcesServiceFacade.retrieveLatestQueryVersion(getServiceContextAdministrador(), queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestPublishedQueryVersion() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_21_FOR_QUERY_03_NAME);
        QueryDto actual = statisticalResourcesServiceFacade.retrieveLatestPublishedQueryVersion(getServiceContextAdministrador(), queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME, QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME,
            QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testRetrieveQueriesVersions() throws Exception {
        List<QueryVersion> expected = queryVersionMockFactory.retrieveMocks(QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME,
                QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME, QUERY_VERSION_01_WITH_SELECTION_NAME);
        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueriesVersions(getServiceContextAdministrador());

        assertEqualsQueryVersionDoAndDtoCollection(expected, actual);
    }

    @Test(expected = AssertionError.class)
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueriesErrorDifferentResponse() throws Exception {
        List<QueryVersion> expected = queryVersionMockFactory.retrieveMocks(QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME);
        List<QueryDto> actual = statisticalResourcesServiceFacade.retrieveQueriesVersions(getServiceContextAdministrador());

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
    public void testUpdateQueryVersion() throws Exception {
        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        expectedQuery.setTitle(StatisticalResourcesDoMocks.mockInternationalStringDto());

        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), expectedQuery);
        assertNotNull(actualQuery);
        assertEqualsInternationalStringDto(expectedQuery.getTitle(), actualQuery.getTitle());
    }

    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testUpdateQueryVersionSelection() throws Exception {
        int querySelectionItemsBefore = querySelectionItemRepository.findAll().size();
        int codeItemsBefore = codeItemRepository.findAll().size();

        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());

        expectedQuery.getSelection().remove("SEX");
        expectedQuery.getSelection().put("DIM1", Arrays.asList(
                new CodeItemDto("A","A"),
                new CodeItemDto("B","B"),
                new CodeItemDto("C","C")));
        expectedQuery.getSelection().put("DIM2", Arrays.asList(
                new CodeItemDto("D","D"),
                new CodeItemDto("E","E")));

        // Service operation
        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), expectedQuery);

        // Checks
        int querySelectionItemsAfter = querySelectionItemRepository.findAll().size();
        int codeItemsAfter = codeItemRepository.findAll().size();

        assertEquals(querySelectionItemsBefore - 1 + 2, querySelectionItemsAfter);
        assertEquals(codeItemsBefore - 1 + 5, codeItemsAfter);
        assertNotNull(actualQuery);
        assertEqualsQuerySelection(expectedQuery.getSelection(), actualQuery.getSelection());
    }

    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testUpdateQueryVersionDontUpdateStatus() throws Exception {
        QueryDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        QueryStatusEnum expectedStatus = expectedQuery.getStatus();
        expectedQuery.setStatus(QueryStatusEnum.PENDING_REVIEW);
        assertTrue(!expectedStatus.equals(expectedQuery.getStatus()));

        QueryDto actualQuery = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), expectedQuery);
        assertEquals(expectedStatus, actualQuery.getStatus());
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME, QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryVersionAsDiscontinued() throws Exception {
        // Retrieve Dto
        QueryDto mockDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn());

        // Test
        QueryDto queryDto = statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), mockDto);
        assertEquals(QueryStatusEnum.DISCONTINUED, queryDto.getStatus());
        assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn(), queryDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testDeleteQueryVersion() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME).getLifeCycleStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn));

        statisticalResourcesServiceFacade.deleteQueryVersion(getServiceContextAdministrador(), urn);
        statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testFindQueriesVersionsByCondition() throws Exception {
        // Find all
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(3, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(3, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_03_BASIC_ORDERED_02_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_04_BASIC_ORDERED_03_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find code
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String code = queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getCode();

            setCriteriaStringPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.CODE, OperationType.EQ, code);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn();

            setCriteriaStringPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.URN, OperationType.EQ, urn);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find title
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, QueryCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String titleQuery = queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");

            setCriteriaStringPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.TITLE, OperationType.EQ, titleQuery);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }
    }

    @Test
    @MetamacMock({QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_07_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME, QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testFindQueriesVersionsByConditionUsingStatus() throws Exception {

        // ACTIVE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.STATUS, OperationType.EQ, QueryStatusEnum.ACTIVE);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(2, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(2, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);
        }

        // DISCONTINUED
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.STATUS, OperationType.EQ, QueryStatusEnum.DISCONTINUED);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryDto);
        }

        // PENDING_REVIEW
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, QueryCriteriaPropertyEnum.STATUS, OperationType.EQ, QueryStatusEnum.PENDING_REVIEW);

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

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

            MetamacCriteriaResult<QueryDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

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
    public void testRetrieveDatasetVersionByUrn() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DatasetDto dataset = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersion(datasetVersion, dataset);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestDatasetVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        DatasetDto actual = statisticalResourcesServiceFacade.retrieveLatestDatasetVersion(getServiceContextAdministrador(), datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestPublishedDatasetVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        DatasetDto actual = statisticalResourcesServiceFacade.retrieveLatestPublishedDatasetVersion(getServiceContextAdministrador(), datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
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
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();

        DatasetDto newDatasetDto = statisticalResourcesServiceFacade.createDataset(getServiceContextAdministrador(), datasetDto, statisticalOperation);
        assertNotNull(newDatasetDto);
        assertNotNull(newDatasetDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        datasetDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(datasetDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME})
    public void testUpdateDatasetVersionWithLanguages() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);
        datasetDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(datasetDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionChangeCodeNotAllowed() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        datasetDto.setCode("CHANGED");

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(datasetVersion.getSiemacMetadataStatisticalResource().getCode(), updatedDataset.getCode());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionChangeStatisticalOperationNotAllowed() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String originalStatisticalOperationCode = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();
        datasetDto.setStatisticalOperation(statisticalOperation);

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(originalStatisticalOperationCode, updatedDataset.getStatisticalOperation().getCode());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionIgnoreDateNextVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DateTime originalDateNextVersion = datasetVersion.getSiemacMetadataStatisticalResource().getNextVersionDate();

        {
            DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            datasetDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetDto.setNextVersion(NextVersionTypeEnum.NO_UPDATES);

            DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(originalDateNextVersion, updatedDataset.getNextVersionDate());
        }
        {
            DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            datasetDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetDto.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);

            DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(originalDateNextVersion, updatedDataset.getNextVersionDate());
        }
        {
            DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            datasetDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetDto.setNextVersion(NextVersionTypeEnum.SCHEDULED_UPDATE);

            DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(new DateTime(datasetDto.getNextVersionDate()), updatedDataset.getNextVersionDate());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionNotAllowedMetadata() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        String oldCreator = datasetDto.getCreatedBy();

        datasetDto.setCreatedBy("My user");

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(oldCreator, updatedDataset.getCreatedBy());
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testDeleteDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        assertNotNull(statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
        statisticalResourcesServiceFacade.deleteDatasetVersion(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, datasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
        statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME})
    public void testFindDatasetsVersionsByCondition() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        DatasetVersion dsOper2Code1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_000001_NAME);
        DatasetVersion dsOper2Code2 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_11_OPER_0002_CODE_000002_NAME);

        // Find All
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
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
    public void testFindDatasetsVersionsByConditionByCode() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // Find CODE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.CODE, OperationType.EQ, dsOper1Code3.getSiemacMetadataStatisticalResource().getCode());

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME})
    public void testFindDatasetsVersionsByConditionByUrn() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.URN, OperationType.EQ, dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn());

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
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
    public void testFindDatasetsVersionsByConditionByProcStatus() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        DatasetVersion dsOper2Code1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_000001_NAME);
        DatasetVersion dsOper2Code2 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_11_OPER_0002_CODE_000002_NAME);
        DatasetVersion dsOper2Code3ProdVal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME);

        // Find PROC STATUS
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.DRAFT);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
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
            setCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.PRODUCTION_VALIDATION);

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(dsOper2Code3ProdVal.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME,
            DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME, DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME, DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME})
    public void testFindDatasetsVersionsByConditionByStatisticalOperationUrn() throws Exception {
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

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
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

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
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

            MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(0, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetDto> results = pagedResults.getResults();
            assertEquals(0, results.size());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME,
            DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME, DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME, DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME})
    public void testFindDatasetsVersionsByConditionOrderByStatisticalOperationUrn() throws Exception {
        DatasetVersion datasetOper2Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME);
        DatasetVersion datasetOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // FIND CODE
        String code = "000003";

        MetamacCriteria metamacCriteria = new MetamacCriteria();
        addOrderToCriteria(metamacCriteria, DatasetCriteriaOrderEnum.STATISTICAL_OPERATION_URN, OrderTypeEnum.ASC);
        setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
        setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.CODE, OperationType.ILIKE, code);

        MetamacCriteriaResult<DatasetDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<DatasetDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEquals(datasetOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        assertEquals(datasetOper2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        DatasetDto newVersion = statisticalResourcesServiceFacade.versioningDatasetVersion(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn(),
                VersionTypeEnum.MINOR);
        assertNotNull(newVersion);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendDatasetVersionToProductionValidation() throws Exception {
        mockDsdAndatasetRepositoryForProductionValidation();

        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(ProcStatusEnum.PRODUCTION_VALIDATION, updatedDataset.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedDataset.getProductionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedDataset.getProductionValidationDate()));
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendDatasetVersionToDiffusionValidation() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetDto datasetDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        DatasetDto updatedDataset = statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(), datasetDto);
        assertNotNull(updatedDataset);
        assertEquals(ProcStatusEnum.DIFFUSION_VALIDATION, updatedDataset.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedDataset.getDiffusionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedDataset.getDiffusionValidationDate()));
    }

    @Override
    public void testRetrieveCoverageForDatasetVersionDimension() throws Exception {
        fail("testRetrieveCoverageForDatasetVersionDimension not implemented");
    }
    
    @Override
    public void testRetrieveDatasetVersionDimensionsIds() throws Exception {
        fail("testRetrieveCoverageForDatasetVersionDimension not implemented");
    }
    
    @Override
    @Test
    public void testCreatePublication() throws Exception {
        PublicationDto publicationDto = StatisticalResourcesDtoMocks.mockPublicationDto();
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();

        PublicationDto newPublicationDto = statisticalResourcesServiceFacade.createPublication(getServiceContextAdministrador(), publicationDto, statisticalOperation);
        assertNotNull(newPublicationDto);
        assertNotNull(newPublicationDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testUpdatePublicationVersion() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);
        publicationDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        PublicationDto updatedDataset = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(publicationDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreChangeCode() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);

        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        publicationDto.setCode("CHANGED_CODE");

        PublicationDto updatedPublication = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedPublication);
        assertEquals(publicationVersion.getSiemacMetadataStatisticalResource().getCode(), updatedPublication.getCode());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreChangeStatisticalOperation() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        String originalStatisticalOperationCode = publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();
        publicationDto.setStatisticalOperation(statisticalOperation);

        PublicationDto updatedPublication = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedPublication);
        assertEquals(originalStatisticalOperationCode, updatedPublication.getStatisticalOperation().getCode());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreDateNextVersionIfItsNotAllowed() throws Exception {
        // DATE_NEXT_VERSION can only be modified if dateNextVersionType is SCHEDULED_UPDATE

        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        DateTime originalDateNextVersion = publicationVersion.getSiemacMetadataStatisticalResource().getNextVersionDate();

        {
            PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                    .getSiemacMetadataStatisticalResource().getUrn());
            publicationDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationDto.setNextVersion(NextVersionTypeEnum.NO_UPDATES);

            PublicationDto updatedPublicationDto = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDto);
            assertNotNull(updatedPublicationDto);
            assertEqualsDate(originalDateNextVersion, updatedPublicationDto.getNextVersionDate());
        }
        {
            PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                    .getSiemacMetadataStatisticalResource().getUrn());
            publicationDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationDto.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);

            PublicationDto updatedPublicationDto = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDto);
            assertNotNull(updatedPublicationDto);
            assertEqualsDate(originalDateNextVersion, updatedPublicationDto.getNextVersionDate());
        }
        {
            PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                    .getSiemacMetadataStatisticalResource().getUrn());
            publicationDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationDto.setNextVersion(NextVersionTypeEnum.SCHEDULED_UPDATE);

            PublicationDto updatedPublicationDto = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDto);
            assertNotNull(updatedPublicationDto);
            assertEqualsDate(new DateTime(publicationDto.getNextVersionDate()), updatedPublicationDto.getNextVersionDate());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreChangeCreatorMetadata() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);
        String originalCreator = publicationDto.getCreatedBy();

        publicationDto.setCreatedBy("My user");

        PublicationDto updatedDataset = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedDataset);
        assertEquals(originalCreator, updatedDataset.getCreatedBy());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testDeletePublicationVersion() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        statisticalResourcesServiceFacade.deletePublicationVersion(getServiceContextAdministrador(), publicationVersionUrn);

        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, publicationVersionUrn));
        statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationVersionByCondition() throws Exception {
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

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
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
    public void testFindPublicationsVersionByConditionByCode() throws Exception {
        PublicationVersion publicationVersionOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);

        // Find CODE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OperationType.EQ, publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource()
                    .getCode());

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationVersionByConditionByUrn() throws Exception {
        PublicationVersion publicationVersionOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.URN, OperationType.EQ, publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getUrn());

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationVersionOperation1Code3.getSiemacMetadataStatisticalResource().getCode(), results.get(0).getCode());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testFindPublicationVersionByConditionByProcStatus() throws Exception {
        PublicationVersion publicationVersionDraft = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_12_DRAFT_NAME);
        PublicationVersion publicationVersionProductionValidation = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME);
        PublicationVersion publicationVersionValidationRejected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME);

        // Find PROC STATUS
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.PROC_STATUS, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setDisjunctionCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
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
            setCriteriaEnumPropertyRestriction(metamacCriteria, DatasetCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.PRODUCTION_VALIDATION);

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(publicationVersionProductionValidation.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationsVersionByConditionByStatisticalOperationUrn() throws Exception {
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

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
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

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
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

            MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(0, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationDto> results = pagedResults.getResults();
            assertEquals(0, results.size());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationVersionByConditionOrderByStatisticalOperationUrn() throws Exception {
        PublicationVersion publicationOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);
        PublicationVersion publicationOperation2Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME);

        // FIND CODE
        String code = "000003";

        MetamacCriteria metamacCriteria = new MetamacCriteria();
        addOrderToCriteria(metamacCriteria, PublicationCriteriaOrderEnum.STATISTICAL_OPERATION_URN, OrderTypeEnum.ASC);
        setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
        setCriteriaStringPropertyRestriction(metamacCriteria, PublicationCriteriaPropertyEnum.CODE, OperationType.ILIKE, code);

        MetamacCriteriaResult<PublicationDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<PublicationDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEquals(publicationOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        assertEquals(publicationOperation2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testRetrievePublicationVersionByUrn() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        assertEqualsPublicationVersion(publicationVersion, publicationDto);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLatestPublicationVersion() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);
        PublicationDto actual = statisticalResourcesServiceFacade.retrieveLatestPublicationVersion(getServiceContextAdministrador(), publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLatestPublishedPublicationVersion() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        PublicationDto actual = statisticalResourcesServiceFacade.retrieveLatestPublishedPublicationVersion(getServiceContextAdministrador(), publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
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
    @MetamacMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendPublicationVersionToProductionValidation() throws Exception {
        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);

        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);

        PublicationDto updatedDataset = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedDataset);
        assertEquals(ProcStatusEnum.PRODUCTION_VALIDATION, updatedDataset.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedDataset.getProductionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedDataset.getProductionValidationDate()));
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendPublicationVersionToDiffusionValidation() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);

        PublicationDto updatedPublication = statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedPublication);
        assertEquals(ProcStatusEnum.DIFFUSION_VALIDATION, updatedPublication.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedPublication.getDiffusionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedPublication.getDiffusionValidationDate()));
    }
    

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_38_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME)
    public void testSendPublicationVersionToValidationRejected() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_38_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME).getSiemacMetadataStatisticalResource().getUrn();
        PublicationDto publicationDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);

        PublicationDto updatedPublication = statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(getServiceContextAdministrador(), publicationDto);
        assertNotNull(updatedPublication);
        assertEquals(ProcStatusEnum.VALIDATION_REJECTED, updatedPublication.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedPublication.getRejectValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedPublication.getRejectValidationDate()));
        
        assertNotNull(updatedPublication.getProductionValidationUser());
        assertNotNull(updatedPublication.getProductionValidationDate());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_16_PUBLISHED_NAME)
    public void testVersioningPublicationVersion() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME);
        PublicationDto newVersion = statisticalResourcesServiceFacade.versioningPublicationVersion(getServiceContextAdministrador(),
                publicationVersion.getSiemacMetadataStatisticalResource().getUrn(), VersionTypeEnum.MINOR);
        assertNotNull(newVersion);
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testRetrievePublicationVersionStructure() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME);
        PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(getServiceContextAdministrador(), publicationVersion
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsPublicationStructure(publicationVersion, publicationStructureDto);
    }

    // ------------------------------------------------------------
    // CHAPTER
    // ------------------------------------------------------------

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testCreateChapter() throws Exception {
        String publicationUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME).getSiemacMetadataStatisticalResource().getUrn();
        ChapterDto expected = StatisticalResourcesDtoMocks.mockChapterDto();
        ChapterDto actual = statisticalResourcesServiceFacade.createChapter(getServiceContextAdministrador(), publicationUrn, expected);
        assertNotNull(actual);
        assertNotNull(actual.getUrn());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testUpdateChapter() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME);
        ChapterDto expected = statisticalResourcesServiceFacade.retrieveChapter(getServiceContextAdministrador(), publicationVersion.getChildrenFirstLevel().get(0).getChapter()
                .getNameableStatisticalResource().getUrn());
        expected.setTitle(StatisticalResourcesNotPersistedDoMocks.mockInternationalStringDto());
        ChapterDto actual = statisticalResourcesServiceFacade.updateChapter(getServiceContextAdministrador(), expected);
        assertEqualsInternationalStringDto(expected.getTitle(), actual.getTitle());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testUpdateChapterLocation() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME);
        String chapterUrn = publicationVersion.getChildrenFirstLevel().get(0).getChapter().getNameableStatisticalResource().getUrn();
        String parentChapterUrn = publicationVersion.getChildrenFirstLevel().get(1).getChapter().getNameableStatisticalResource().getUrn();
        ChapterDto chapterDto = statisticalResourcesServiceFacade.updateChapterLocation(getServiceContextAdministrador(), chapterUrn, parentChapterUrn, Long.valueOf(1));
        assertEquals(Long.valueOf(1), chapterDto.getOrderInLevel());
        assertEquals(parentChapterUrn, chapterDto.getParentChapterUrn());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testRetrieveChapter() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME);
        Chapter expected = publicationVersion.getChildrenFirstLevel().get(0).getChapter();
        ChapterDto actual = statisticalResourcesServiceFacade.retrieveChapter(getServiceContextAdministrador(), expected.getNameableStatisticalResource().getUrn());
        assertEqualsChapter(expected, actual);
    }

    @Override
    @Test
    @MetamacMock(CHAPTER_01_BASIC_NAME)
    public void testDeleteChapter() throws Exception {
        String urn = chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND, urn));

        statisticalResourcesServiceFacade.deleteChapter(getServiceContextAdministrador(), urn);
        statisticalResourcesServiceFacade.retrieveChapter(getServiceContextAdministrador(), urn);
    }

    // ------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME, DATASET_01_BASIC_NAME})
    public void testCreateCube() throws Exception {
        String publicationUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_01_BASIC_NAME).getIdentifiableStatisticalResource().getUrn();
        CubeDto expected = StatisticalResourcesDtoMocks.mockDatasetCubeDto(datasetUrn);
        CubeDto actual = statisticalResourcesServiceFacade.createCube(getServiceContextAdministrador(), publicationUrn, expected);
        assertNotNull(actual);
        assertNotNull(actual.getUrn());
    }

    @Override
    @Test
    @MetamacMock(CUBE_01_BASIC_NAME)
    public void testUpdateCube() throws Exception {
        String cubeUrn = cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        CubeDto expected = statisticalResourcesServiceFacade.retrieveCube(getServiceContextAdministrador(), cubeUrn);
        expected.setTitle(StatisticalResourcesNotPersistedDoMocks.mockInternationalStringDto());
        CubeDto actual = statisticalResourcesServiceFacade.updateCube(getServiceContextAdministrador(), expected);
        assertEqualsInternationalStringDto(expected.getTitle(), actual.getTitle());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testUpdateCubeLocation() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME);
        String cubeUrn = publicationVersion.getChildrenFirstLevel().get(3).getCube().getNameableStatisticalResource().getUrn();
        String parentChapterUrn = publicationVersion.getChildrenFirstLevel().get(2).getChapter().getNameableStatisticalResource().getUrn();
        CubeDto cubeDto = statisticalResourcesServiceFacade.updateCubeLocation(getServiceContextAdministrador(), cubeUrn, parentChapterUrn, Long.valueOf(1));
        assertEquals(Long.valueOf(1), cubeDto.getOrderInLevel());
        assertEquals(parentChapterUrn, cubeDto.getParentChapterUrn());
    }

    @Override
    @Test
    @MetamacMock({CUBE_01_BASIC_NAME, CUBE_02_BASIC_NAME})
    public void testRetrieveCube() throws Exception {
        Cube expected = cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME);
        CubeDto actual = statisticalResourcesServiceFacade.retrieveCube(getServiceContextAdministrador(), expected.getNameableStatisticalResource().getUrn());
        assertEqualsCube(expected, actual);
    }

    @Override
    @Test
    @MetamacMock(CUBE_01_BASIC_NAME)
    public void testDeleteCube() throws Exception {
        String urn = cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.CUBE_NOT_FOUND, urn));

        statisticalResourcesServiceFacade.deleteCube(getServiceContextAdministrador(), urn);
        statisticalResourcesServiceFacade.retrieveCube(getServiceContextAdministrador(), urn);
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
    
    private void mockDsdAndatasetRepositoryForProductionValidation() throws Exception {
        List<ConditionObservationDto> dimensionsCodes = new ArrayList<ConditionObservationDto>();
        
        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("GEO_DIM", "code-01", "code-02", "code-03"));
        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("TIME_PERIOD", "2010", "2011", "2012"));
        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("MEAS_DIM", "concept-01", "concept-02", "concept-03"));
        Mockito.when(statisticsDatasetRepositoriesServiceFacade.findCodeDimensions(Mockito.anyString())).thenReturn(dimensionsCodes);
        
        DatasetRepositoryDto datasetRepoDto = DsRepositoryMockUtils.mockDatasetRepository("dsrepo-01", "GEO_DIM", "TIME_PERIOD", "MEAS_DIM");
        Mockito.when(statisticsDatasetRepositoriesServiceFacade.retrieveDatasetRepository(Mockito.anyString())).thenReturn(datasetRepoDto);

        //Mock codelist and concept Scheme
        
        Codelist codelist = SrmMockUtils.buildCodelistWithCodes("codelist-01", "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=TEST:codelist-01(1.0)", StatisticalResourcesDoMocks.DEFAULT_DATA_LOCALE, 3);
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn(codelist.getUrn())).thenReturn(codelist);
        
        ConceptScheme conceptScheme = SrmMockUtils.buildConceptSchemeWithConcepts("csch-01", "urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=TEST:cshm-01(1.0)", StatisticalResourcesDoMocks.DEFAULT_DATA_LOCALE, 3);
        Mockito.when(srmRestInternalService.retrieveConceptSchemeByUrn(conceptScheme.getUrn())).thenReturn(conceptScheme);
        
        //Create a datastructure with dimensions marked as measure temporal and spatial
        
        DataStructure dsd = SrmMockUtils.mockDsdWithGeoTimeAndMeasureDimensions("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", "GEO_DIM", "TIME_PERIOD", "MEAS_DIM", conceptScheme, codelist);
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(dsd);
    }
        
}
