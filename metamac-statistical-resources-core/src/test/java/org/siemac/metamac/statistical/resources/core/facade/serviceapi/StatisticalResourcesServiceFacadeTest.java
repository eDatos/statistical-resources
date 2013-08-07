package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsDate;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsDay;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsInternationalStringDto;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDataset;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsChapter;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsCube;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublication;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersionStructure;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuerySelection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersionDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_09_OPER_0001_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_10_OPER_0002_CODE_000001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME;
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
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_SIMPLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME;
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
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_15_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_21_FOR_QUERY_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_02_BASIC_NAME;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
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
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
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
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
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
    @MetamacMock({QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testFindQueriesByCondition() throws Exception {
        QueryVersion latestQueryVersionQuery02 = queryMockFactory.retrieveMock(QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME).getVersions().get(0);
        QueryVersion latestQueryVersionQuery03 = queryVersionMockFactory.retrieveMock(QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME);

        MetamacCriteria metamacCriteria = new MetamacCriteria();

        MetamacCriteriaResult<RelatedResourceDto> pagedResults = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<RelatedResourceDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEqualsQuery(latestQueryVersionQuery02, results.get(0));
        assertEqualsQuery(latestQueryVersionQuery03, results.get(1));
    }

    @Test
    @MetamacMock({QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testFindQueriesByConditionTitle() throws Exception {
        QueryVersion latestQueryVersionQuery03 = queryVersionMockFactory.retrieveMock(QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME);

        // Restrictions
        MetamacCriteria metamacCriteria = new MetamacCriteria();
        String title = latestQueryVersionQuery03.getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");
        setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.TITLE, OperationType.LIKE, title);

        MetamacCriteriaResult<RelatedResourceDto> pagedResults = statisticalResourcesServiceFacade.findQueriesByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<RelatedResourceDto> results = pagedResults.getResults();
        assertEquals(1, results.size());

        assertEqualsQuery(latestQueryVersionQuery03, results.get(0));
    }

    // ------------------------------------------------------------------------
    // QUERIES VERSIONS
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testRetrieveQueryVersionByUrn() throws Exception {
        QueryVersionDto actual = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEqualsQueryVersion(queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME), actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestQueryVersion() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME);
        QueryVersionDto actual = statisticalResourcesServiceFacade.retrieveLatestQueryVersion(getServiceContextAdministrador(), queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestPublishedQueryVersion() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_21_FOR_QUERY_03_NAME);
        QueryVersionDto actual = statisticalResourcesServiceFacade.retrieveLatestPublishedQueryVersion(getServiceContextAdministrador(), queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME, QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME,
            QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testRetrieveQueriesVersions() throws Exception {
        List<QueryVersion> expected = queryVersionMockFactory.retrieveMocks(QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME,
                QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME, QUERY_VERSION_01_WITH_SELECTION_NAME);
        List<QueryVersionDto> actual = statisticalResourcesServiceFacade.retrieveQueriesVersions(getServiceContextAdministrador());

        assertEqualsQueryVersionDoAndDtoCollection(expected, actual);
    }

    @Test(expected = AssertionError.class)
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueriesErrorDifferentResponse() throws Exception {
        List<QueryVersion> expected = queryVersionMockFactory.retrieveMocks(QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME);
        List<QueryVersionDto> actual = statisticalResourcesServiceFacade.retrieveQueriesVersions(getServiceContextAdministrador());

        assertEqualsQueryVersionDoAndDtoCollection(expected, actual);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQuery() throws Exception {
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();

        QueryVersionDto persistedQuery = statisticalResourcesServiceFacade.createQuery(getServiceContextAdministrador(),
                StatisticalResourcesDtoMocks.mockQueryVersionDto(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME)), statisticalOperation);
        assertNotNull(persistedQuery);
        assertNotNull(persistedQuery.getUrn());
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testUpdateQueryVersion() throws Exception {
        QueryVersionDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        expectedQuery.setTitle(StatisticalResourcesDoMocks.mockInternationalStringDto());

        QueryVersionDto actualQuery = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), expectedQuery);
        assertNotNull(actualQuery);
        assertEqualsInternationalStringDto(expectedQuery.getTitle(), actualQuery.getTitle());
    }

    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testUpdateQueryVersionSelection() throws Exception {
        int querySelectionItemsBefore = querySelectionItemRepository.findAll().size();
        int codeItemsBefore = codeItemRepository.findAll().size();

        QueryVersionDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());

        expectedQuery.getSelection().remove("SEX");
        expectedQuery.getSelection().put("DIM1", Arrays.asList(new CodeItemDto("A", "A"), new CodeItemDto("B", "B"), new CodeItemDto("C", "C")));
        expectedQuery.getSelection().put("DIM2", Arrays.asList(new CodeItemDto("D", "D"), new CodeItemDto("E", "E")));

        // Service operation
        QueryVersionDto actualQuery = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), expectedQuery);

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
        QueryVersionDto expectedQuery = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        QueryStatusEnum expectedStatus = expectedQuery.getStatus();
        expectedQuery.setStatus(QueryStatusEnum.PENDING_REVIEW);
        assertTrue(!expectedStatus.equals(expectedQuery.getStatus()));

        QueryVersionDto actualQuery = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), expectedQuery);
        assertEquals(expectedStatus, actualQuery.getStatus());
    }

    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testUpdateQueryVersionIgnoreChangeMaintainer() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME);
        String originalMaintainerCode = queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCode();

        QueryVersionDto queryDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryVersion.getLifeCycleStatisticalResource().getUrn());
        ExternalItemDto maintainer = StatisticalResourcesDtoMocks.mockAgencyExternalItemDto();
        queryDto.setMaintainer(maintainer);

        QueryVersionDto updatedQuery = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDto);
        assertNotNull(updatedQuery);
        assertEquals(originalMaintainerCode, updatedQuery.getMaintainer().getCode());
    }

    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testUpdateQueryVersionIgnoreChangeStatisticalOperation() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME);
        String originalStatisticalOperationCode = queryVersion.getLifeCycleStatisticalResource().getStatisticalOperation().getCode();

        QueryVersionDto queryVersionDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryVersion.getLifeCycleStatisticalResource().getUrn());
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();
        queryVersionDto.setStatisticalOperation(statisticalOperation);

        QueryVersionDto updatedQueryVersion = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDto);
        assertNotNull(updatedQueryVersion);
        assertEquals(originalStatisticalOperationCode, updatedQueryVersion.getStatisticalOperation().getCode());
    }

    @Test
    @MetamacMock({QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME, QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME})
    public void testUpdateQueryVersionIgnoreChangeCodeForNonPublishedQueryVersion() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME);
        QueryVersionDto queryVersionDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryVersion.getLifeCycleStatisticalResource().getUrn());

        queryVersion.getLifeCycleStatisticalResource().setCode("newCode");
        QueryVersionDto updatedQueryVersion = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDto);
        assertEquals(queryVersionDto.getCode(), updatedQueryVersion.getCode());
    }

    @Test
    @MetamacMock({QUERY_VERSION_15_PUBLISHED_NAME})
    public void testUpdateQueryVersionIgnoreChangeCodeForPublishedQueryVersion() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_15_PUBLISHED_NAME);
        QueryVersionDto queryVersionDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryVersion.getLifeCycleStatisticalResource().getUrn());

        queryVersion.getLifeCycleStatisticalResource().setCode("newCode");
        QueryVersionDto updatedQueryVersion = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDto);
        assertEquals(queryVersionDto.getCode(), updatedQueryVersion.getCode());
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME, QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryVersionAsDiscontinued() throws Exception {
        // Retrieve Dto
        QueryVersionDto mockDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn());

        // Test
        QueryVersionDto queryDto = statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), mockDto);
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(3, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(3, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_03_BASIC_ORDERED_02_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_04_BASIC_ORDERED_03_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find code
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String code = queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getCode();

            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.CODE, OperationType.EQ, code);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find URN
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn();

            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.URN, OperationType.EQ, urn);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }

        // Find title
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            // Restrictions
            String titleQuery = queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");

            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.TITLE, OperationType.EQ, titleQuery);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);

            int i = 0;
            assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getResults().get(i++).getUrn());
        }
    }

    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testFindQueriesVersionsByConditionWithMetamacCriteriaDontThrowError() throws Exception {
        statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), null);
    }

    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testFindQueriesVersionsByConditionCheckLastUpdatedIsDefaultOrder() throws Exception {
        String urnQueryVersion02 = queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource().getUrn();
        String urnQueryVersion03 = queryVersionMockFactory.retrieveMock(QUERY_VERSION_03_BASIC_ORDERED_02_NAME).getLifeCycleStatisticalResource().getUrn();
        String urnQueryVersion04 = queryVersionMockFactory.retrieveMock(QUERY_VERSION_04_BASIC_ORDERED_03_NAME).getLifeCycleStatisticalResource().getUrn();

        MetamacCriteria metamacCriteria = new MetamacCriteria();

        MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

        // Validate
        assertEquals(3, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
        assertEquals(3, queriesPagedResult.getResults().size());
        assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);

        int i = 0;
        assertEquals(urnQueryVersion02, queriesPagedResult.getResults().get(i++).getUrn());
        assertEquals(urnQueryVersion03, queriesPagedResult.getResults().get(i++).getUrn());
        assertEquals(urnQueryVersion04, queriesPagedResult.getResults().get(i++).getUrn());

        // Update queryVersion 02
        QueryVersionDto queryVersionDto03 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urnQueryVersion03);
        statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDto03);

        // Search again and validate
        queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

        // Validate
        assertEquals(3, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
        assertEquals(3, queriesPagedResult.getResults().size());
        assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);

        i = 0;
        assertEquals(urnQueryVersion02, queriesPagedResult.getResults().get(i++).getUrn());
        assertEquals(urnQueryVersion04, queriesPagedResult.getResults().get(i++).getUrn());
        assertEquals(urnQueryVersion03, queriesPagedResult.getResults().get(i++).getUrn());
    }

    @Test
    @MetamacMock({QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_07_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME, QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testFindQueriesVersionsByConditionUsingStatus() throws Exception {

        // ACTIVE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.QUERY_STATUS, OperationType.EQ, QueryStatusEnum.ACTIVE);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(2, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(2, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);
        }

        // DISCONTINUED
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.QUERY_STATUS, OperationType.EQ, QueryStatusEnum.DISCONTINUED);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);
        }

        // PENDING_REVIEW
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            setCriteriaEnumPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.QUERY_STATUS, OperationType.EQ, QueryStatusEnum.PENDING_REVIEW);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(1, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(1, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);
        }

        // PENDING_REVIEW or DISCONTINUED
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();

            MetamacCriteriaDisjunctionRestriction disjunction = new MetamacCriteriaDisjunctionRestriction();
            disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.QUERY_STATUS.name(), QueryStatusEnum.PENDING_REVIEW, OperationType.EQ));
            disjunction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.QUERY_STATUS.name(), QueryStatusEnum.DISCONTINUED, OperationType.EQ));
            metamacCriteria.setRestriction(disjunction);

            MetamacCriteriaResult<QueryVersionDto> queriesPagedResult = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

            // Validate
            assertEquals(2, queriesPagedResult.getPaginatorResult().getTotalResults().intValue());
            assertEquals(2, queriesPagedResult.getResults().size());
            assertTrue(queriesPagedResult.getResults().get(0) instanceof QueryVersionDto);
        }
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    @Test
    @Ignore
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    //Datasources can not longer be created from facade
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
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME})
    public void testFindDatasetsByCondition() throws Exception {
        DatasetVersion latestDatasetVersionDataset03 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        DatasetVersion latestDatasetVersionDataset04 = datasetMockFactory.retrieveMock(DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME).getVersions().get(0);

        MetamacCriteria metamacCriteria = new MetamacCriteria();

        MetamacCriteriaResult<RelatedResourceDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<RelatedResourceDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEqualsDataset(latestDatasetVersionDataset03, results.get(0));
        assertEqualsDataset(latestDatasetVersionDataset04, results.get(1));
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME})
    public void testFindDatasetsByConditionTitle() throws Exception {
        DatasetVersion latestDatasetVersionDataset03 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);

        // Restrictions
        MetamacCriteria metamacCriteria = new MetamacCriteria();
        String title = latestDatasetVersionDataset03.getSiemacMetadataStatisticalResource().getTitle().getLocalisedLabel("es");
        setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.TITLE, OperationType.LIKE, title);

        MetamacCriteriaResult<RelatedResourceDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<RelatedResourceDto> results = pagedResults.getResults();
        assertEquals(1, results.size());

        assertEqualsDataset(latestDatasetVersionDataset03, results.get(0));
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testRetrieveDatasetVersionByUrn() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DatasetVersionDto dataset = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersion(datasetVersion, dataset);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestDatasetVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        DatasetVersionDto actual = statisticalResourcesServiceFacade.retrieveLatestDatasetVersion(getServiceContextAdministrador(), datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestPublishedDatasetVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        DatasetVersionDto actual = statisticalResourcesServiceFacade.retrieveLatestPublishedDatasetVersion(getServiceContextAdministrador(), datasetUrn);
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
            List<DatasetVersionDto> datasets = statisticalResourcesServiceFacade.retrieveDatasetVersions(getServiceContextAdministrador(), datasetVersionLast.getSiemacMetadataStatisticalResource()
                    .getUrn());
            assertNotNull(datasets);
            assertEquals(2, datasets.size());
            assertEqualsDatasetVersion(datasetVersionFirst, datasets.get(0));
            assertEqualsDatasetVersion(datasetVersionLast, datasets.get(1));
        }
        {
            List<DatasetVersionDto> datasets = statisticalResourcesServiceFacade.retrieveDatasetVersions(getServiceContextAdministrador(), datasetVersionFirst.getSiemacMetadataStatisticalResource()
                    .getUrn());
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
        DatasetVersionDto datasetVersionDto = StatisticalResourcesDtoMocks.mockDatasetVersionDto(officiality);
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();

        DatasetVersionDto newDatasetVersionDto = statisticalResourcesServiceFacade.createDataset(getServiceContextAdministrador(), datasetVersionDto, statisticalOperation);
        assertNotNull(newDatasetVersionDto);
        assertNotNull(newDatasetVersionDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        datasetVersionDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(datasetVersionDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME})
    public void testUpdateDatasetVersionWithLanguages() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);
        datasetVersionDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
        assertNotNull(updatedDataset);
        assertEqualsInternationalStringDto(datasetVersionDto.getTitle(), updatedDataset.getTitle());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionChangeCodeNotAllowed() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        datasetVersionDto.setCode("CHANGED");

        DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
        assertNotNull(updatedDataset);
        assertEquals(datasetVersion.getSiemacMetadataStatisticalResource().getCode(), updatedDataset.getCode());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionChangeStatisticalOperationNotAllowed() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String originalStatisticalOperationCode = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();
        datasetVersionDto.setStatisticalOperation(statisticalOperation);

        DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
        assertNotNull(updatedDataset);
        assertEquals(originalStatisticalOperationCode, updatedDataset.getStatisticalOperation().getCode());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionChangeMaintainerNotAllowed() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String originalMaintainerCode = datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCode();

        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        ExternalItemDto maintainer = StatisticalResourcesDtoMocks.mockAgencyExternalItemDto();
        datasetVersionDto.setMaintainer(maintainer);

        DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
        assertNotNull(updatedDataset);
        assertEquals(originalMaintainerCode, updatedDataset.getMaintainer().getCode());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionIgnoreDateNextVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DateTime originalDateNextVersion = datasetVersion.getSiemacMetadataStatisticalResource().getNextVersionDate();

        {
            DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                    .getUrn());
            datasetVersionDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetVersionDto.setNextVersion(NextVersionTypeEnum.NO_UPDATES);

            DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(originalDateNextVersion, updatedDataset.getNextVersionDate());
        }
        {
            DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                    .getUrn());
            datasetVersionDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetVersionDto.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);

            DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(originalDateNextVersion, updatedDataset.getNextVersionDate());
        }
        {
            DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                    .getUrn());
            datasetVersionDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            datasetVersionDto.setNextVersion(NextVersionTypeEnum.SCHEDULED_UPDATE);

            DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
            assertNotNull(updatedDataset);
            assertEqualsDate(new DateTime(datasetVersionDto.getNextVersionDate()), updatedDataset.getNextVersionDate());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetVersionNotAllowedMetadata() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn());
        String oldCreator = datasetVersionDto.getCreatedBy();

        datasetVersionDto.setCreatedBy("My user");

        DatasetVersionDto updatedDataset = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDto);
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(dsOper2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(dsOper2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME})
    public void testFindDatasetsVersionsByConditionCheckLastUpdatedIsDefaultOrder() throws Exception {
        String dsOper1Code3Urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME).getLifeCycleStatisticalResource().getUrn();
        String dsOper2Code1Urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_000001_NAME).getLifeCycleStatisticalResource().getUrn();
        String dsOper2Code2Urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_11_OPER_0002_CODE_000002_NAME).getLifeCycleStatisticalResource().getUrn();

        MetamacCriteria metamacCriteria = new MetamacCriteria();

        MetamacCriteriaResult<DatasetVersionDto> datasetsPagedResult = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

        // Validate
        assertEquals(3, datasetsPagedResult.getPaginatorResult().getTotalResults().intValue());
        assertEquals(3, datasetsPagedResult.getResults().size());
        assertTrue(datasetsPagedResult.getResults().get(0) instanceof DatasetVersionDto);

        int i = 0;
        assertEquals(dsOper1Code3Urn, datasetsPagedResult.getResults().get(i++).getUrn());
        assertEquals(dsOper2Code1Urn, datasetsPagedResult.getResults().get(i++).getUrn());
        assertEquals(dsOper2Code2Urn, datasetsPagedResult.getResults().get(i++).getUrn());

        // Update queryVersion 02
        DatasetVersionDto dsOper2Code1 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), dsOper2Code1Urn);
        statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), dsOper2Code1);

        // Search again and validate
        datasetsPagedResult = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);

        // Validate
        assertEquals(3, datasetsPagedResult.getPaginatorResult().getTotalResults().intValue());
        assertEquals(3, datasetsPagedResult.getResults().size());
        assertTrue(datasetsPagedResult.getResults().get(0) instanceof DatasetVersionDto);

        i = 0;
        assertEquals(dsOper1Code3Urn, datasetsPagedResult.getResults().get(i++).getUrn());
        assertEquals(dsOper2Code2Urn, datasetsPagedResult.getResults().get(i++).getUrn());
        assertEquals(dsOper2Code1Urn, datasetsPagedResult.getResults().get(i++).getUrn());
    }

    @Test
    @MetamacMock({DATASET_VERSION_09_OPER_0001_CODE_000003_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_11_OPER_0002_CODE_000002_NAME})
    public void testFindDatasetsVersionsByConditionByCode() throws Exception {
        DatasetVersion dsOper1Code3 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);

        // Find CODE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.CODE, OperationType.EQ, dsOper1Code3.getSiemacMetadataStatisticalResource().getCode());

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.URN, OperationType.EQ, dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn());

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaEnumPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.DRAFT);

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(dsOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(dsOper2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(dsOper2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaEnumPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.PRODUCTION_VALIDATION);

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(4, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
            assertEquals(4, results.size());

            assertEquals(datasetOper2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(datasetOper2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(datasetOper2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
            assertEquals(datasetOper2CodeMax.getSiemacMetadataStatisticalResource().getUrn(), results.get(3).getUrn());
        }
        {
            String statisticalOperationUrn = StatisticalResourcesDoMocks.mockStatisticalOperationUrn(DatasetVersionMockFactory.OPERATION_01_CODE);

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
            assertEquals(1, results.size());

            assertEquals(datasetOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        }

        {
            String statisticalOperationUrn = URN_NOT_EXISTS;

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(0, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<DatasetVersionDto> results = pagedResults.getResults();
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
        addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.STATISTICAL_OPERATION_URN, OrderTypeEnum.ASC);
        setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
        setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.CODE, OperationType.ILIKE, code);

        MetamacCriteriaResult<DatasetVersionDto> pagedResults = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<DatasetVersionDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEquals(datasetOper1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        assertEquals(datasetOper2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        DatasetVersionDto newVersion = statisticalResourcesServiceFacade.versioningDatasetVersion(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn(),
                VersionTypeEnum.MINOR);
        assertNotNull(newVersion);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendDatasetVersionToProductionValidation() throws Exception {
        mockDsdAndatasetRepositoryForProductionValidation();

        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        DatasetVersionDto updatedDatasetVersion = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(), datasetVersionDto);
        assertNotNull(updatedDatasetVersion);
        assertEquals(ProcStatusEnum.PRODUCTION_VALIDATION, updatedDatasetVersion.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedDatasetVersion.getProductionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedDatasetVersion.getProductionValidationDate()));
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendDatasetVersionToDiffusionValidation() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        DatasetVersionDto updatedDatasetVersion = statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(), datasetVersionDto);
        assertNotNull(updatedDatasetVersion);
        assertEquals(ProcStatusEnum.DIFFUSION_VALIDATION, updatedDatasetVersion.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedDatasetVersion.getDiffusionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedDatasetVersion.getDiffusionValidationDate()));
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
    @MetamacMock({STATISTIC_OFFICIALITY_01_BASIC_NAME, STATISTIC_OFFICIALITY_02_BASIC_NAME})
    public void testFindStatisticOfficialities() throws Exception {
        List<StatisticOfficialityDto> officiality = statisticalResourcesServiceFacade.findStatisticOfficialities(getServiceContextAdministrador());
        assertEquals(2, officiality.size());
    }
    
    @Override
    @Test
    @MetamacMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME)
    public void testImportDatasourcesInDatasetVersion() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersionDto datasetVersionDto = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);
        
        URL url = new URL("file", null, "myfile.px");
        
        statisticalResourcesServiceFacade.importDatasourcesInDatasetVersion(getServiceContextAdministrador(), datasetVersionDto, Arrays.asList(url));
    }
    
    @Override
    public void testImportDatasourcesInStatisticalOperation() throws Exception {
        //See testImportDatasourcesInStatisticalOperation in DatasetServiceTest
    }

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testFindPublicationsByCondition() throws Exception {
        PublicationVersion latestPublicationVersionPublication02 = publicationMockFactory.retrieveMock(PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME).getVersions().get(0);
        PublicationVersion latestPublicationVersionPublication03 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);

        MetamacCriteria metamacCriteria = new MetamacCriteria();

        MetamacCriteriaResult<RelatedResourceDto> pagedResults = statisticalResourcesServiceFacade.findPublicationsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<RelatedResourceDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEqualsPublication(latestPublicationVersionPublication02, results.get(0));
        assertEqualsPublication(latestPublicationVersionPublication03, results.get(1));
    }

    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testFindPublicationsByConditionTitle() throws Exception {
        PublicationVersion latestPublicationVersionPublication03 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);

        // Restrictions
        MetamacCriteria metamacCriteria = new MetamacCriteria();
        String title = latestPublicationVersionPublication03.getSiemacMetadataStatisticalResource().getTitle().getLocalisedLabel("es");
        setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.TITLE, OperationType.LIKE, title);

        MetamacCriteriaResult<RelatedResourceDto> pagedResults = statisticalResourcesServiceFacade.findPublicationsByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<RelatedResourceDto> results = pagedResults.getResults();
        assertEquals(1, results.size());

        assertEqualsPublication(latestPublicationVersionPublication03, results.get(0));
    }

    // ------------------------------------------------------------------------
    // PUBLICATIONS VERSIONS
    // ------------------------------------------------------------------------

    @Override
    @Test
    public void testCreatePublication() throws Exception {
        PublicationVersionDto publicationVersionDto = StatisticalResourcesDtoMocks.mockPublicationVersionDto();
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();

        PublicationVersionDto newPublicationVersionDto = statisticalResourcesServiceFacade.createPublication(getServiceContextAdministrador(), publicationVersionDto, statisticalOperation);
        assertNotNull(newPublicationVersionDto);
        assertNotNull(newPublicationVersionDto.getUrn());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testUpdatePublicationVersion() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);
        publicationVersionDto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "Mi titulo"));

        PublicationVersionDto updatedPublicationVersion = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
        assertNotNull(updatedPublicationVersion);
        assertEqualsInternationalStringDto(publicationVersionDto.getTitle(), updatedPublicationVersion.getTitle());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreChangeCode() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);

        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                .getSiemacMetadataStatisticalResource().getUrn());
        publicationVersionDto.setCode("CHANGED_CODE");

        PublicationVersionDto updatedPublicationVersion = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
        assertNotNull(updatedPublicationVersion);
        assertEquals(publicationVersion.getSiemacMetadataStatisticalResource().getCode(), updatedPublicationVersion.getCode());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreChangeStatisticalOperation() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        String originalStatisticalOperationCode = publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                .getSiemacMetadataStatisticalResource().getUrn());
        ExternalItemDto statisticalOperation = StatisticalResourcesDtoMocks.mockStatisticalOperationExternalItemDto();
        publicationVersionDto.setStatisticalOperation(statisticalOperation);

        PublicationVersionDto updatedPublicationVersion = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
        assertNotNull(updatedPublicationVersion);
        assertEquals(originalStatisticalOperationCode, updatedPublicationVersion.getStatisticalOperation().getCode());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreChangeMaintainer() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        String originalMaintainerCode = publicationVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCode();

        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                .getSiemacMetadataStatisticalResource().getUrn());
        ExternalItemDto maintainer = StatisticalResourcesDtoMocks.mockAgencyExternalItemDto();
        publicationVersionDto.setMaintainer(maintainer);

        PublicationVersionDto updatedPublicationVersion = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
        assertNotNull(updatedPublicationVersion);
        assertEquals(originalMaintainerCode, updatedPublicationVersion.getMaintainer().getCode());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreDateNextVersionIfItsNotAllowed() throws Exception {
        // DATE_NEXT_VERSION can only be modified if dateNextVersionType is SCHEDULED_UPDATE

        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        DateTime originalDateNextVersion = publicationVersion.getSiemacMetadataStatisticalResource().getNextVersionDate();

        {
            PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                    .getSiemacMetadataStatisticalResource().getUrn());
            publicationVersionDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationVersionDto.setNextVersion(NextVersionTypeEnum.NO_UPDATES);

            PublicationVersionDto updatedPublicationVersionDto = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
            assertNotNull(updatedPublicationVersionDto);
            assertEqualsDate(originalDateNextVersion, updatedPublicationVersionDto.getNextVersionDate());
        }
        {
            PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                    .getSiemacMetadataStatisticalResource().getUrn());
            publicationVersionDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationVersionDto.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);

            PublicationVersionDto updatedPublicationVersionDto = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
            assertNotNull(updatedPublicationVersionDto);
            assertEqualsDate(originalDateNextVersion, updatedPublicationVersionDto.getNextVersionDate());
        }
        {
            PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                    .getSiemacMetadataStatisticalResource().getUrn());
            publicationVersionDto.setNextVersionDate(new DateTime().plusDays(1).toDate());
            publicationVersionDto.setNextVersion(NextVersionTypeEnum.SCHEDULED_UPDATE);

            PublicationVersionDto updatedPublicationVersionDto = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
            assertNotNull(updatedPublicationVersionDto);
            assertEqualsDate(new DateTime(publicationVersionDto.getNextVersionDate()), updatedPublicationVersionDto.getNextVersionDate());
        }
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testUpdatePublicationVersionIgnoreChangeCreatorMetadata() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);
        String originalCreator = publicationVersionDto.getCreatedBy();

        publicationVersionDto.setCreatedBy("My user");

        PublicationVersionDto updatedDataset = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDto);
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(6, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
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
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME})
    public void testFindPublicationsVersionsByConditionCheckLastUpdatedIsDefaultOrder() throws Exception {
        String publicationVersionOperation1Code1Urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME).getSiemacMetadataStatisticalResource()
                .getUrn();
        String publicationVersionOperation1Code2Urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME).getSiemacMetadataStatisticalResource()
                .getUrn();
        String publicationVersionOperation1Code3Urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME).getSiemacMetadataStatisticalResource()
                .getUrn();

        MetamacCriteria metamacCriteria = new MetamacCriteria();

        MetamacCriteriaResult<PublicationVersionDto> publicationsPagedResult = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);

        // Validate
        assertEquals(3, publicationsPagedResult.getPaginatorResult().getTotalResults().intValue());
        assertEquals(3, publicationsPagedResult.getResults().size());
        assertTrue(publicationsPagedResult.getResults().get(0) instanceof PublicationVersionDto);

        int i = 0;
        assertEquals(publicationVersionOperation1Code1Urn, publicationsPagedResult.getResults().get(i++).getUrn());
        assertEquals(publicationVersionOperation1Code2Urn, publicationsPagedResult.getResults().get(i++).getUrn());
        assertEquals(publicationVersionOperation1Code3Urn, publicationsPagedResult.getResults().get(i++).getUrn());

        // Update
        PublicationVersionDto publicationVersionOperation1Code2 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionOperation1Code2Urn);
        statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionOperation1Code2);

        // Search again and validate
        publicationsPagedResult = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);

        // Validate
        assertEquals(3, publicationsPagedResult.getPaginatorResult().getTotalResults().intValue());
        assertEquals(3, publicationsPagedResult.getResults().size());
        assertTrue(publicationsPagedResult.getResults().get(0) instanceof PublicationVersionDto);

        i = 0;
        assertEquals(publicationVersionOperation1Code1Urn, publicationsPagedResult.getResults().get(i++).getUrn());
        assertEquals(publicationVersionOperation1Code3Urn, publicationsPagedResult.getResults().get(i++).getUrn());
        assertEquals(publicationVersionOperation1Code2Urn, publicationsPagedResult.getResults().get(i++).getUrn());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME})
    public void testFindPublicationsVersionByConditionByCode() throws Exception {
        PublicationVersion publicationVersionOperation1Code3 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);

        // Find CODE
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OperationType.EQ, publicationVersionOperation1Code3
                    .getSiemacMetadataStatisticalResource().getCode());

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.URN, OperationType.EQ, publicationVersionOperation1Code3
                    .getSiemacMetadataStatisticalResource().getUrn());

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.PROC_STATUS, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setDisjunctionCriteriaEnumPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.DRAFT,
                    ProcStatusEnum.VALIDATION_REJECTED);

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
            assertEquals(2, results.size());

            assertEquals(publicationVersionDraft.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationVersionValidationRejected.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
        }
        {
            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaEnumPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.PROC_STATUS, OperationType.EQ, ProcStatusEnum.PRODUCTION_VALIDATION);

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(1, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
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
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(publicationOperation1Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationOperation1Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(publicationOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }
        {
            String statisticalOperationUrn = StatisticalResourcesDoMocks.mockStatisticalOperationUrn(DatasetVersionMockFactory.OPERATION_02_CODE);

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(3, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
            assertEquals(3, results.size());

            assertEquals(publicationOperation2Code1.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
            assertEquals(publicationOperation2Code2.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
            assertEquals(publicationOperation2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(2).getUrn());
        }

        {
            String statisticalOperationUrn = URN_NOT_EXISTS;

            MetamacCriteria metamacCriteria = new MetamacCriteria();
            addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.CODE, OrderTypeEnum.ASC);
            setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
            setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.STATISTICAL_OPERATION_URN, OperationType.EQ, statisticalOperationUrn);

            MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
            assertEquals(0, pagedResults.getPaginatorResult().getTotalResults().intValue());
            List<PublicationVersionDto> results = pagedResults.getResults();
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
        addOrderToCriteria(metamacCriteria, StatisticalResourcesCriteriaOrderEnum.STATISTICAL_OPERATION_URN, OrderTypeEnum.ASC);
        setCriteriaPaginator(metamacCriteria, 0, Integer.MAX_VALUE, Boolean.TRUE);
        setCriteriaStringPropertyRestriction(metamacCriteria, StatisticalResourcesCriteriaPropertyEnum.CODE, OperationType.ILIKE, code);

        MetamacCriteriaResult<PublicationVersionDto> pagedResults = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), metamacCriteria);
        assertEquals(2, pagedResults.getPaginatorResult().getTotalResults().intValue());
        List<PublicationVersionDto> results = pagedResults.getResults();
        assertEquals(2, results.size());

        assertEquals(publicationOperation1Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(0).getUrn());
        assertEquals(publicationOperation2Code3.getSiemacMetadataStatisticalResource().getUrn(), results.get(1).getUrn());
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testRetrievePublicationVersionByUrn() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersion
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsPublicationVersion(publicationVersion, publicationVersionDto);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLatestPublicationVersion() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);
        PublicationVersionDto actual = statisticalResourcesServiceFacade.retrieveLatestPublicationVersion(getServiceContextAdministrador(), publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLatestPublishedPublicationVersion() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        PublicationVersionDto actual = statisticalResourcesServiceFacade.retrieveLatestPublishedPublicationVersion(getServiceContextAdministrador(), publicationUrn);
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
            List<PublicationVersionDto> publicationsVersions = statisticalResourcesServiceFacade.retrievePublicationVersions(getServiceContextAdministrador(), publicationVersionLast
                    .getSiemacMetadataStatisticalResource().getUrn());
            assertNotNull(publicationsVersions);
            assertEquals(2, publicationsVersions.size());
            assertEqualsPublicationVersion(publicationVersionFirst, publicationsVersions.get(0));
            assertEqualsPublicationVersion(publicationVersionLast, publicationsVersions.get(1));
        }
        {
            List<PublicationVersionDto> publicationsVersions = statisticalResourcesServiceFacade.retrievePublicationVersions(getServiceContextAdministrador(), publicationVersionFirst
                    .getSiemacMetadataStatisticalResource().getUrn());
            assertNotNull(publicationsVersions);
            assertEquals(2, publicationsVersions.size());
            assertEqualsPublicationVersion(publicationVersionFirst, publicationsVersions.get(0));
            assertEqualsPublicationVersion(publicationVersionLast, publicationsVersions.get(1));
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
        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);

        PublicationVersionDto updatedPublicationVersion = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(), publicationVersionDto);
        assertNotNull(updatedPublicationVersion);
        assertEquals(ProcStatusEnum.PRODUCTION_VALIDATION, updatedPublicationVersion.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedPublicationVersion.getProductionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedPublicationVersion.getProductionValidationDate()));
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendPublicationVersionToDiffusionValidation() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
                .getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);

        PublicationVersionDto updatedPublicationVersion = statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(), publicationVersionDto);
        assertNotNull(updatedPublicationVersion);
        assertEquals(ProcStatusEnum.DIFFUSION_VALIDATION, updatedPublicationVersion.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedPublicationVersion.getDiffusionValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedPublicationVersion.getDiffusionValidationDate()));
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_38_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME)
    public void testSendPublicationVersionToValidationRejected() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_38_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME)
                .getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersionDto publicationVersionDto = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionUrn);

        PublicationVersionDto updatedPublicationVersion = statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(getServiceContextAdministrador(), publicationVersionDto);
        assertNotNull(updatedPublicationVersion);
        assertEquals(ProcStatusEnum.VALIDATION_REJECTED, updatedPublicationVersion.getProcStatus());
        assertEquals(getServiceContextAdministrador().getUserId(), updatedPublicationVersion.getRejectValidationUser());
        assertEqualsDay(new DateTime().toDateTime(), new DateTime(updatedPublicationVersion.getRejectValidationDate()));

        assertNotNull(updatedPublicationVersion.getProductionValidationUser());
        assertNotNull(updatedPublicationVersion.getProductionValidationDate());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_16_PUBLISHED_NAME)
    public void testVersioningPublicationVersion() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME);
        PublicationVersionDto newVersion = statisticalResourcesServiceFacade.versioningPublicationVersion(getServiceContextAdministrador(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), VersionTypeEnum.MINOR);
        assertNotNull(newVersion);
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testRetrievePublicationVersionStructure() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME);
        PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(getServiceContextAdministrador(), publicationVersion
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsPublicationVersionStructure(publicationVersion, publicationStructureDto);
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
        String chapterUrn = chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND, chapterUrn));

        statisticalResourcesServiceFacade.deleteChapter(getServiceContextAdministrador(), chapterUrn);
        statisticalResourcesServiceFacade.retrieveChapter(getServiceContextAdministrador(), chapterUrn);
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

    @Test
    @MetamacMock({CUBE_01_BASIC_NAME, DATASET_01_BASIC_NAME})
    public void testUpdateCubeDataset() throws Exception {
        String cubeUrn = cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        CubeDto expected = statisticalResourcesServiceFacade.retrieveCube(getServiceContextAdministrador(), cubeUrn);
        expected.setDatasetUrn(datasetMockFactory.retrieveMock(DATASET_01_BASIC_NAME).getIdentifiableStatisticalResource().getUrn());
        expected.setQueryUrn(null);
        CubeDto actual = statisticalResourcesServiceFacade.updateCube(getServiceContextAdministrador(), expected);
        assertEqualsInternationalStringDto(expected.getTitle(), actual.getTitle());
    }

    @Test
    @MetamacMock({CUBE_01_BASIC_NAME, QUERY_01_SIMPLE_NAME})
    public void testUpdateCubeQuery() throws Exception {
        String cubeUrn = cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        CubeDto expected = statisticalResourcesServiceFacade.retrieveCube(getServiceContextAdministrador(), cubeUrn);
        expected.setQueryUrn(queryMockFactory.retrieveMock(QUERY_01_SIMPLE_NAME).getIdentifiableStatisticalResource().getUrn());
        expected.setDatasetUrn(null);
        CubeDto actual = statisticalResourcesServiceFacade.updateCube(getServiceContextAdministrador(), expected);
        assertEqualsInternationalStringDto(expected.getTitle(), actual.getTitle());
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

        // Mock codelist and concept Scheme

        CodelistReferenceType codelistReference = SrmMockUtils.buildCodelistRef("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=TEST:codelist-01(1.0)");
        Codes codes = SrmMockUtils.buildCodes(3);
        Mockito.when(srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistReference.getURN())).thenReturn(codes);

        ConceptSchemeReferenceType conceptSchemeReference = SrmMockUtils.buildConceptSchemeRef("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=TEST:cshm-01(1.0)"); 
        Concepts concepts = SrmMockUtils.buildConcepts(3);
        Mockito.when(srmRestInternalService.retrieveConceptsOfConceptSchemeEfficiently(conceptSchemeReference.getURN())).thenReturn(concepts);

        // Create a datastructure with dimensions marked as measure temporal and spatial

        DataStructure dsd = SrmMockUtils.mockDsdWithGeoTimeAndMeasureDimensions("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", "GEO_DIM", "TIME_PERIOD", "MEAS_DIM", conceptSchemeReference, codelistReference);
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(dsd);
    }
}
