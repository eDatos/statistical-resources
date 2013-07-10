package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsCodeItemDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_05_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.utils.DsRepositoryMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.SrmMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class StatisticalResourcesOptimisticLockingTest extends StatisticalResourcesBaseTest implements StatisticalResourcesServiceFacadeTestBase {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private QueryVersionMockFactory           queryMockFactory;

    @Autowired
    private DatasourceMockFactory             datasourceMockFactory;

    @Autowired
    private DatasetVersionMockFactory         datasetVersionMockFactory;

    @Autowired
    private PublicationVersionMockFactory     publicationVersionMockFactory;

    @Autowired
    private CubeMockFactory                   cubeMockFactory;

    @Autowired
    private ChapterMockFactory                chapterMockFactory;

    @Autowired
    private SrmRestInternalService            srmRestInternalService;
    
    @Autowired
    private DatasetRepositoriesServiceFacade  statisticsDatasetRepositoriesServiceFacade;

    @Before
    public void onBeforeTest() {
        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());

        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);
    }

    // ------------------------------------------------------------
    // QUERY
    // ------------------------------------------------------------

    @Test
    @Override
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testUpdateQueryVersion() throws Exception {
        // Retrieve query - session 1
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession01.getOptimisticLockingVersion());
        queryDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve query - session 2
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession02.getOptimisticLockingVersion());
        queryDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update query - session 1 --> OK
        QueryDto queryDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @SuppressWarnings("serial")
    @Test
    @MetamacMock({QUERY_VERSION_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateSelectionInQuery() throws Exception {
        // Retrieve query - session 1
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession01.getOptimisticLockingVersion());
        Map<String, List<CodeItemDto>> selection = new HashMap<String, List<CodeItemDto>>() {

            {
                put("DIM_SESSION1", Arrays.asList(
                                new CodeItemDto("A", "A"),
                                new CodeItemDto("B", "B")));
            }
        };
        queryDtoSession01.setSelection(selection);

        // Retrieve query - session 2
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession02.getOptimisticLockingVersion());
        selection = new HashMap<String, List<CodeItemDto>>() {

            {
                put("DIM_SESSION2", Arrays.asList(
                        new CodeItemDto("C", "C"),
                        new CodeItemDto("D", "D")));
            }
        };
        queryDtoSession02.setSelection(selection);

        // Update query - session 1 --> OK
        QueryDto queryDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession01);
        assertEquals(1, queryDtoSession01.getSelection().size());
        assertEqualsCodeItemDtoCollection(
                Arrays.asList(
                        new CodeItemDto("A", "A"),
                        new CodeItemDto("B", "B"))
                ,queryDtoSession1AfterUpdate01.getSelection().get("DIM_SESSION1"));
        assertTrue(queryDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock({QUERY_VERSION_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetInQuery() throws Exception {
        // Retrieve query - session 1
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession01.getOptimisticLockingVersion());

        DatasetVersion datasetVersion06 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME);
        queryDtoSession01.setRelatedDatasetVersion(StatisticalResourcesDtoMocks.mockPersistedRelatedResourceDatasetVersionDto(datasetVersion06));

        // Retrieve query - session 2
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession02.getOptimisticLockingVersion());
        DatasetVersion datasetVersion01 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        queryDtoSession02.setRelatedDatasetVersion(StatisticalResourcesDtoMocks.mockPersistedRelatedResourceDatasetVersionDto(datasetVersion01));

        // Update query - session 1 --> OK
        QueryDto queryDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession01);
        assertEquals(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME).getSiemacMetadataStatisticalResource().getUrn(), queryDtoSession1AfterUpdate01
                .getRelatedDatasetVersion().getUrn());
        assertTrue(queryDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    public void testMarkQueryVersionAsDiscontinued() throws Exception {
        // no optimistic locking in this operation
        // Instead we have:
        // - testUpdateQueryAndMarkQueryAsDiscontinued
        // - testMarkQueryAsDiscontinuedAndMarkQueryAsDiscontinued
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryVersionAsDiscontinuedAndUpdate() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Retrieve query --> session 02
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);
        queryDtoSession02.setTitle(new StatisticalResourcesDtoMocks().mockInternationalStringDto());

        // Mark as discontinued --> session 01 --> OK
        QueryDto queryDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query --> session 02 --> fail - optimistic locking
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryVersionAsDiscontinuedAndMarkQueryAsDiscontinued() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Retrieve query --> session 02
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Mark as discontinued --> session 01 --> OK
        QueryDto queryDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Mark as discontinued --> session 02 --> fail
        try {
            statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testUpdateQueryVersionAndMarkQueryAsDiscontinued() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);
        queryDtoSession01.setTitle(new StatisticalResourcesDtoMocks().mockInternationalStringDto());

        // Retrieve query --> session 02
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Update query --> session 01 --> OK
        QueryDto queryDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Mark as discontinued --> session 02 --> fail
        try {
            statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    // ------------------------------------------------------------
    // DATASOURCE
    // ------------------------------------------------------------

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME})
    @Override
    public void testUpdateDatasource() throws Exception {
        // Retrieve datasource - session 1
        DatasourceDto datasourceDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME)
                .getIdentifiableStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasourceDtoSession01.getOptimisticLockingVersion());
        datasourceDtoSession01.setCode("newCode" + StatisticalResourcesDtoMocks.mockString(5));

        // Retrieve datasource - session 2
        DatasourceDto datasourceDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasourceByUrn(getServiceContextAdministrador(), datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME)
                .getIdentifiableStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasourceDtoSession02.getOptimisticLockingVersion());
        datasourceDtoSession02.setCode("newCode" + StatisticalResourcesDtoMocks.mockString(5));

        // Update datasource - session 1 --> OK
        DatasourceDto datasourceDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateDatasource(getServiceContextAdministrador(), datasourceDtoSession01);
        assertTrue(datasourceDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasourceDtoSession01.getOptimisticLockingVersion());

        // Update datasource - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateDatasource(getServiceContextAdministrador(), datasourceDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update datasource - session 1 --> OK
        datasourceDtoSession1AfterUpdate01.setCode("newCode" + StatisticalResourcesDtoMocks.mockString(5));
        DatasourceDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasource(getServiceContextAdministrador(), datasourceDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasourceDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    // ------------------------------------------------------------
    // PUBLICATION
    // ------------------------------------------------------------

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_01_BASIC_NAME)
    public void testUpdatePublicationVersion() throws Exception {
        // Retrieve publication - session 1
        PublicationDto publicationDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession01.getOptimisticLockingVersion());
        publicationDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve publication - session 2
        PublicationDto publicationDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession02.getOptimisticLockingVersion());
        publicationDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update publication - session 1 --> OK
        PublicationDto publicationDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDtoSession01);
        assertTrue(publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationDtoSession01.getOptimisticLockingVersion());

        // Update publication - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationDto publicationDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDtoSession1AfterUpdate01);
        assertTrue(publicationDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }


    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendPublicationVersionToProductionValidation() throws Exception {
        // Retrieve publication - session 1
        PublicationDto publicationDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationDto publicationDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        PublicationDto publicationDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(), publicationDtoSession01);
        assertTrue(publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(), publicationDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationDto publicationDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDtoSession1AfterUpdate01);
        assertTrue(publicationDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendPublicationVersionToDiffusionValidation() throws Exception {
        // Retrieve publication - session 1
        PublicationDto publicationDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationDto publicationDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        PublicationDto publicationDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(), publicationDtoSession01);
        assertTrue(publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(), publicationDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationDto publicationDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationDtoSession1AfterUpdate01);
        assertTrue(publicationDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion());

    }
    
    // ------------------------------------------------------------
    // DATASET
    // ------------------------------------------------------------

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_01_BASIC_NAME)
    public void testUpdateDatasetVersion() throws Exception {
        // Retrieve dataset - session 1
        DatasetDto datasetDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession01.getOptimisticLockingVersion());
        datasetDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve dataset - session 2
        DatasetDto datasetDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession02.getOptimisticLockingVersion());
        datasetDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update dataset - session 1 --> OK
        DatasetDto datasetDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDtoSession01);
        assertTrue(datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetDtoSession01.getOptimisticLockingVersion());

        // Update dataset - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetDto datasetDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDtoSession1AfterUpdate01);
        assertTrue(datasetDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendDatasetVersionToProductionValidation() throws Exception {
        mockDsdAndatasetRepositoryForProductionValidation();
        
        // Retrieve dataset - session 1
        DatasetDto datasetDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetDto datasetDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        DatasetDto datasetDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(), datasetDtoSession01);
        assertTrue(datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(), datasetDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetDto datasetDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDtoSession1AfterUpdate01);
        assertTrue(datasetDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendDatasetVersionToDiffusionValidation() throws Exception {
        // Retrieve dataset - session 1
        DatasetDto datasetDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetDto datasetDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        DatasetDto datasetDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(), datasetDtoSession01);
        assertTrue(datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(), datasetDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetDto datasetDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetDtoSession1AfterUpdate01);
        assertTrue(datasetDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion());

    }
    
    @Override
    public void testRetrieveCoverageForDatasetVersionDimension() throws Exception {
        fail("testRetrieveCoverageForDatasetVersionDimension not implemented");   
    }
    
    @Override
    public void testRetrieveDatasetVersionDimensionsIds() throws Exception {
        fail("testRetrieveDatasetVersionDimensionsIds not implemented");
    }

    // ------------------------------------------------------------
    // CUBE
    // ------------------------------------------------------------

    @Override
    @Test
    @MetamacMock(CUBE_01_BASIC_NAME)
    public void testUpdateCube() throws Exception {
        // Retrieve cube - session 1
        CubeDto cubeDtoSession01 = statisticalResourcesServiceFacade.retrieveCube(getServiceContextAdministrador(), cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME).getNameableStatisticalResource()
                .getUrn());
        assertEquals(Long.valueOf(0), cubeDtoSession01.getOptimisticLockingVersion());
        cubeDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve cube - session 2
        CubeDto cubeDtoSession02 = statisticalResourcesServiceFacade.retrieveCube(getServiceContextAdministrador(), cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME).getNameableStatisticalResource()
                .getUrn());
        assertEquals(Long.valueOf(0), cubeDtoSession02.getOptimisticLockingVersion());
        cubeDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update cube - session 1 --> OK
        CubeDto cubeDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateCube(getServiceContextAdministrador(), cubeDtoSession01);
        assertTrue(cubeDtoSession1AfterUpdate01.getOptimisticLockingVersion() > cubeDtoSession01.getOptimisticLockingVersion());

        // Update cube - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateCube(getServiceContextAdministrador(), cubeDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update cube - session 1 --> OK
        cubeDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        CubeDto cubeDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateCube(getServiceContextAdministrador(), cubeDtoSession1AfterUpdate01);
        assertTrue(cubeDtoSession1AfterUpdate02.getOptimisticLockingVersion() > cubeDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    // ------------------------------------------------------------
    // CHAPTER
    // ------------------------------------------------------------

    @Override
    @Test
    @MetamacMock(CHAPTER_01_BASIC_NAME)
    public void testUpdateChapter() throws Exception {
        // Retrieve chapter - session 1
        ChapterDto chapterDtoSession01 = statisticalResourcesServiceFacade.retrieveChapter(getServiceContextAdministrador(), chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME)
                .getNameableStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), chapterDtoSession01.getOptimisticLockingVersion());
        chapterDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve chapter - session 2
        ChapterDto chapterDtoSession02 = statisticalResourcesServiceFacade.retrieveChapter(getServiceContextAdministrador(), chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME)
                .getNameableStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), chapterDtoSession02.getOptimisticLockingVersion());
        chapterDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update chapter - session 1 --> OK
        ChapterDto chapterDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateChapter(getServiceContextAdministrador(), chapterDtoSession01);
        assertTrue(chapterDtoSession1AfterUpdate01.getOptimisticLockingVersion() > chapterDtoSession01.getOptimisticLockingVersion());

        // Update chapter - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateChapter(getServiceContextAdministrador(), chapterDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update chapter - session 1 --> OK
        chapterDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        ChapterDto chapterDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateChapter(getServiceContextAdministrador(), chapterDtoSession1AfterUpdate01);
        assertTrue(chapterDtoSession1AfterUpdate02.getOptimisticLockingVersion() > chapterDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    // ------------------------------------------------------------
    // NO OPTIMISTIC LOCKING OPERATIONS
    // ------------------------------------------------------------

    @Override
    public void testRetrieveQueryVersionByUrn() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveQueriesVersions() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreateQuery() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindQueriesVersionsByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreateDatasource() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveDatasourceByUrn() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeleteDatasource() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreateDataset() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeleteDatasetVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindDatasetsVersionsByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveDatasetVersionByUrn() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveDatasetVersions() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testVersioningDatasetVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeleteQueryVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreatePublication() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeletePublicationVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindPublicationVersionByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrievePublicationVersionByUrn() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrievePublicationVersions() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testVersioningPublicationVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreateCube() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveCube() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeleteCube() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testUpdateCubeLocation() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testUpdateChapterLocation() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreateChapter() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveChapter() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeleteChapter() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrievePublicationVersionStructure() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveLatestDatasetVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveLatestPublishedDatasetVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveLatestPublicationVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveLatestPublishedPublicationVersion() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveLatestQueryVersion() throws Exception {
     // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveLatestPublishedQueryVersion() throws Exception {
        /// no optimistic locking in this operation
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
        
        Codelist codelist = SrmMockUtils.buildCodelistWithCodes("codelist-01", "urn:uuid:codelist-01", StatisticalResourcesDoMocks.DEFAULT_DATA_LOCALE, 3);
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn(codelist.getUrn())).thenReturn(codelist);
        
        ConceptScheme conceptScheme = SrmMockUtils.buildConceptSchemeWithConcepts("csch-01", "urn:uuid:cshm-01", StatisticalResourcesDoMocks.DEFAULT_DATA_LOCALE, 3);
        Mockito.when(srmRestInternalService.retrieveConceptSchemeByUrn(conceptScheme.getUrn())).thenReturn(conceptScheme);
        
        //Create a datastructure with dimensions marked as measure temporal and spatial
        
        DataStructure dsd = SrmMockUtils.mockDsdWithGeoTimeAndMeasureDimensions("urn:uuid:dsd-urn", "GEO_DIM", "TIME_PERIOD", "MEAS_DIM", conceptScheme, codelist);
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(dsd);
    }
}
