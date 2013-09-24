package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.METHOD_NOT_IMPLEMENT_IN_THIS_VERSION;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsCodeItemDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_30_V1_PUBLISHED_FOR_PUBLICATION_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_39_PUBLISHED_WITH_NO_ROOT_MAINTAINER_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_05_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_11_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_15_PUBLISHED_NAME;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureComponents;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.utils.DataMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/rest-services-mockito.xml",
        "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class StatisticalResourcesOptimisticLockingTest extends StatisticalResourcesBaseTest implements StatisticalResourcesServiceFacadeTestBase {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private SrmRestInternalService            srmRestInternalService;

    @Autowired
    private DatasetRepositoriesServiceFacade  datasetRepositoriesServiceFacade;

    @Before
    public void onBeforeTest() throws Exception {
        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponents());

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
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());
        queryVersionDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve query - session 2
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());
        queryVersionDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update query - session 1 --> OK
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @SuppressWarnings("serial")
    @Test
    @MetamacMock({QUERY_VERSION_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateSelectionInQuery() throws Exception {
        // Retrieve query - session 1
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());
        Map<String, List<CodeItemDto>> selection = new HashMap<String, List<CodeItemDto>>() {

            {
                put("DIM01", Arrays.asList(new CodeItemDto("CODE01", "CODE01")));
                put("DIM02", Arrays.asList(new CodeItemDto("CODE01", "CODE01")));
            }
        };
        queryVersionDtoSession01.setSelection(selection);

        // Retrieve query - session 2
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());
        selection = new HashMap<String, List<CodeItemDto>>() {

            {
                put("DIM01", Arrays.asList(new CodeItemDto("CODE02", "CODE02")));
                put("DIM02", Arrays.asList(new CodeItemDto("CODE02", "CODE02")));
            }
        };
        queryVersionDtoSession02.setSelection(selection);

        // Update query - session 1 --> OK
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertEquals(2, queryVersionDtoSession01.getSelection().size());
        assertEqualsCodeItemDtoCollection(Arrays.asList(new CodeItemDto("CODE01", "CODE01")), queryVersionDtoSession1AfterUpdate01.getSelection().get("DIM01"));
        assertEqualsCodeItemDtoCollection(Arrays.asList(new CodeItemDto("CODE01", "CODE01")), queryVersionDtoSession1AfterUpdate01.getSelection().get("DIM02"));
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock({QUERY_VERSION_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetInQuery() throws Exception {
        // Retrieve query - session 1
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        DatasetVersion datasetVersion06 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME);
        queryVersionDtoSession01.setRelatedDatasetVersion(StatisticalResourcesDtoMocks.mockPersistedRelatedResourceDatasetVersionDto(datasetVersion06));
        // update selection because of dataset change
        queryVersionDtoSession01.getSelection().clear();
        queryVersionDtoSession01.getSelection().put("DIM_01", Arrays.asList(new CodeItemDto("CODE_01", "CODE_01")));
        queryVersionDtoSession01.getSelection().put("DIM_02", Arrays.asList(new CodeItemDto("CODE_11", "CODE_11")));

        // Retrieve query - session 2
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());
        DatasetVersion datasetVersion01 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        queryVersionDtoSession02.setRelatedDatasetVersion(StatisticalResourcesDtoMocks.mockPersistedRelatedResourceDatasetVersionDto(datasetVersion01));

        // Update query - session 1 --> OK
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertEquals(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME).getSiemacMetadataStatisticalResource().getUrn(), queryVersionDtoSession1AfterUpdate01
                .getRelatedDatasetVersion().getUrn());
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
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
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Retrieve query --> session 02
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);
        queryVersionDtoSession02.setTitle(new StatisticalResourcesDtoMocks().mockInternationalStringDto());

        // Mark as discontinued --> session 01 --> OK
        QueryVersionDto queryVersionDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Update query --> session 02 --> fail - optimistic locking
        try {
            statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryVersionAsDiscontinuedAndMarkQueryAsDiscontinued() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Retrieve query --> session 02
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Mark as discontinued --> session 01 --> OK
        QueryVersionDto queryVersionDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Mark as discontinued --> session 02 --> fail
        try {
            statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testUpdateQueryVersionAndMarkQueryAsDiscontinued() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);
        queryVersionDtoSession01.setTitle(new StatisticalResourcesDtoMocks().mockInternationalStringDto());

        // Retrieve query --> session 02
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(), urn);

        // Update query --> session 01 --> OK
        QueryVersionDto queryVersionDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Mark as discontinued --> session 02 --> fail
        try {
            statisticalResourcesServiceFacade.markQueryVersionAsDiscontinued(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(QUERY_VERSION_11_DRAFT_NAME)
    public void testSendQueryVersionToProductionValidation() throws Exception {
        // Retrieve query - session 1
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(QUERY_VERSION_11_DRAFT_NAME)
    public void testSendQueryVersionToProductionValidationWithDtoBasic() throws Exception {
        List<QueryVersionBaseDto> queries = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, queries.size());

        // Retrieve query - session 1
        QueryVersionBaseDto queryVersionDtoSession01 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionBaseDto queryVersionDtoSession02 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        QueryVersionBaseDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock(QUERY_VERSION_11_DRAFT_NAME)
    public void testSendQueryVersionToProductionValidationAndThenUpdate() throws Exception {
        // Retrieve query
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());
        assertEquals(Long.valueOf(2), queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());

        // Update query
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
        assertEquals(Long.valueOf(3), queryVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME)
    public void testSendQueryVersionToDiffusionValidation() throws Exception {
        // Retrieve query - session 1
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME)
    public void testSendQueryVersionToDiffusionValidationWithDtoBasic() throws Exception {
        List<QueryVersionBaseDto> queries = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, queries.size());

        // Retrieve query - session 1
        QueryVersionBaseDto queryVersionDtoSession01 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionBaseDto queryVersionDtoSession02 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to diffusion validation - session 1 --> OK
        QueryVersionBaseDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to diffusion validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME)
    public void testSendQueryVersionToValidationRejected() throws Exception {
        // Retrieve query - session 1
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendQueryVersionToValidationRejected(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendQueryVersionToValidationRejected(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME)
    public void testSendQueryVersionToValidationRejectedWithDtoBasic() throws Exception {
        List<QueryVersionBaseDto> queries = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, queries.size());

        // Retrieve query - session 1
        QueryVersionBaseDto queryVersionDtoSession01 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionBaseDto queryVersionDtoSession02 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        QueryVersionBaseDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendQueryVersionToValidationRejected(getServiceContextAdministrador(), queryVersionDtoSession01);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendQueryVersionToValidationRejected(getServiceContextAdministrador(), queryVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(QUERY_VERSION_15_PUBLISHED_NAME)
    public void testVersioningQueryVersion() throws Exception {
        // It's MetamacException because facade interceptor transform all Exceptions in MetamacException
        thrown.expect(MetamacException.class);
        thrown.expectMessage(METHOD_NOT_IMPLEMENT_IN_THIS_VERSION);

        // Retrieve query - session 1
        QueryVersionDto queryVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_15_PUBLISHED_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionDto queryVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(getServiceContextAdministrador(),
                queryVersionMockFactory.retrieveMock(QUERY_VERSION_15_PUBLISHED_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        QueryVersionDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.versioningQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession01,
                VersionTypeEnum.MAJOR);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.versioningQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession02, VersionTypeEnum.MAJOR);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryVersionDto queryVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession1AfterUpdate01);
        assertTrue(queryVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(QUERY_VERSION_15_PUBLISHED_NAME)
    public void testSendQueryVersionToVersioningWithDtoBasic() throws Exception {
        // It's MetamacException because facade interceptor transform all Exceptions in MetamacException
        thrown.expect(MetamacException.class);
        thrown.expectMessage(METHOD_NOT_IMPLEMENT_IN_THIS_VERSION);

        List<QueryVersionBaseDto> queries = statisticalResourcesServiceFacade.findQueriesVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, queries.size());

        // Retrieve query - session 1
        QueryVersionBaseDto queryVersionDtoSession01 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve query - session 2
        QueryVersionBaseDto queryVersionDtoSession02 = queries.get(0);
        assertEquals(Long.valueOf(0), queryVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        QueryVersionBaseDto queryVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.versioningQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession01,
                VersionTypeEnum.MAJOR);
        assertTrue(queryVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.versioningQueryVersion(getServiceContextAdministrador(), queryVersionDtoSession02, VersionTypeEnum.MAJOR);
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
        PublicationVersionDto publicationVersionDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());
        publicationVersionDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve publication - session 2
        PublicationVersionDto publicationVersionDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());
        publicationVersionDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update publication - session 1 --> OK
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Update publication - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(), publicationVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession1AfterUpdate01);
        assertTrue(publicationVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendPublicationVersionToProductionValidation() throws Exception {
        // Retrieve publication - session 1
        PublicationVersionDto publicationVersionDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionDto publicationVersionDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(),
                publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(), publicationVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession1AfterUpdate01);
        assertTrue(publicationVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendPublicationVersionToProductionValidationAndThenUpdate() throws Exception {
        // Retrieve publication
        PublicationVersionDto publicationVersionDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(),
                publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());
        assertEquals(Long.valueOf(2), publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());

        // Update publication
        publicationVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession1AfterUpdate01);
        assertTrue(publicationVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
        assertEquals(Long.valueOf(3), publicationVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_33_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendPublicationVersionToProductionValidationWithDtoBasic() throws Exception {
        List<PublicationVersionBaseDto> publications = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, publications.size());

        // Retrieve publication - session 1
        PublicationVersionBaseDto publicationVersionDtoSession01 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionBaseDto publicationVersionDtoSession02 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        PublicationVersionBaseDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(),
                publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToProductionValidation(getServiceContextAdministrador(), publicationVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendPublicationVersionToDiffusionValidation() throws Exception {
        // Retrieve publication - session 1
        PublicationVersionDto publicationVersionDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionDto publicationVersionDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(),
                publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(), publicationVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession1AfterUpdate01);
        assertTrue(publicationVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendPublicationVersionToDiffusionValidationWithDtoBasic() throws Exception {
        List<PublicationVersionBaseDto> publications = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, publications.size());

        // Retrieve publication - session 1
        PublicationVersionBaseDto publicationVersionDtoSession01 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionBaseDto publicationVersionDtoSession02 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Send to diffusion validation - session 1 --> OK
        PublicationVersionBaseDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(),
                publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to diffusion validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToDiffusionValidation(getServiceContextAdministrador(), publicationVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendPublicationVersionToValidationRejected() throws Exception {
        // Retrieve publication - session 1
        PublicationVersionDto publicationVersionDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionDto publicationVersionDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(getServiceContextAdministrador(),
                publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(getServiceContextAdministrador(), publicationVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession1AfterUpdate01);
        assertTrue(publicationVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_37_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendPublicationVersionToValidationRejectedWithDtoBasic() throws Exception {
        List<PublicationVersionBaseDto> publications = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, publications.size());

        // Retrieve publication - session 1
        PublicationVersionBaseDto publicationVersionDtoSession01 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionBaseDto publicationVersionDtoSession02 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        PublicationVersionBaseDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(getServiceContextAdministrador(),
                publicationVersionDtoSession01);
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendPublicationVersionToValidationRejected(getServiceContextAdministrador(), publicationVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME)
    public void testVersioningPublicationVersion() throws Exception {
        // Retrieve publication - session 1
        PublicationVersionDto publicationVersionDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionDto publicationVersionDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(), publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Versioning - session 1 --> OK
        PublicationVersionDto publicationVersionDtoSession1NewVersion = statisticalResourcesServiceFacade.versioningPublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession01, VersionTypeEnum.MAJOR);

        PublicationVersionDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME).getSiemacMetadataStatisticalResource().getUrn());

        assertEquals(Long.valueOf(0), publicationVersionDtoSession1NewVersion.getOptimisticLockingVersion());
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Versioning - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.versioningPublicationVersion(getServiceContextAdministrador(), publicationVersionDtoSession02, VersionTypeEnum.MAJOR);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationVersionDto publicationVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession1NewVersion);
        assertTrue(publicationVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationVersionDtoSession1NewVersion.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_39_PUBLISHED_WITH_NO_ROOT_MAINTAINER_NAME)
    public void testSendPublicationVersionToVersioningWithDtoBasic() throws Exception {
        List<PublicationVersionBaseDto> publications = statisticalResourcesServiceFacade.findPublicationVersionByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, publications.size());

        // Retrieve publication - session 1
        PublicationVersionBaseDto publicationVersionDtoSession01 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve publication - session 2
        PublicationVersionBaseDto publicationVersionDtoSession02 = publications.get(0);
        assertEquals(Long.valueOf(0), publicationVersionDtoSession02.getOptimisticLockingVersion());

        // Send to versioning - session 1 --> OK
        PublicationVersionBaseDto publicationVersionDtoSession1NewVersion = statisticalResourcesServiceFacade.versioningPublicationVersion(getServiceContextAdministrador(),
                publicationVersionDtoSession01, VersionTypeEnum.MAJOR);

        PublicationVersionDto publicationVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_39_PUBLISHED_WITH_NO_ROOT_MAINTAINER_NAME).getSiemacMetadataStatisticalResource().getUrn());

        assertEquals(Long.valueOf(0), publicationVersionDtoSession1NewVersion.getOptimisticLockingVersion());
        assertTrue(publicationVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationVersionDtoSession01.getOptimisticLockingVersion());

        // Send to versioning - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.versioningPublicationVersion(getServiceContextAdministrador(), publicationVersionDtoSession02, VersionTypeEnum.MAJOR);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    // ------------------------------------------------------------
    // DATASET VERSIONS
    // ------------------------------------------------------------

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_01_BASIC_NAME)
    public void testUpdateDatasetVersion() throws Exception {
        // Retrieve dataset - session 1
        DatasetVersionDto datasetVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());
        datasetVersionDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve dataset - session 2
        DatasetVersionDto datasetVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());
        datasetVersionDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update dataset - session 1 --> OK
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Update dataset - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession1AfterUpdate01);
        assertTrue(datasetVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendDatasetVersionToProductionValidation() throws Exception {
        mockDsdAndDataRepositorySimpleDimensions();

        // Retrieve dataset - session 1
        DatasetVersionDto datasetVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionDto datasetVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(),
                datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(), datasetVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession1AfterUpdate01);
        assertTrue(datasetVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendDatasetVersionToProductionValidationWithDtoBasic() throws Exception {
        List<DatasetVersionBaseDto> datasets = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, datasets.size());

        // Retrieve dataset - session 1
        DatasetVersionBaseDto datasetVersionDtoSession01 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionBaseDto datasetVersionDtoSession02 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        DatasetVersionBaseDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(),
                datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(), datasetVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendDatasetVersionToProductionValidationAndThenUpdate() throws Exception {
        mockDsdAndDataRepositorySimpleDimensions();

        // Retrieve dataset
        DatasetVersionDto datasetVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToProductionValidation(getServiceContextAdministrador(),
                datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Update dataset
        datasetVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession1AfterUpdate01);
        assertTrue(datasetVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendDatasetVersionToDiffusionValidation() throws Exception {
        // Retrieve dataset - session 1
        DatasetVersionDto datasetVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionDto datasetVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(),
                datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(), datasetVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession1AfterUpdate01);
        assertTrue(datasetVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendDatasetVersionToDiffusionValidationWithDtoBasic() throws Exception {
        List<DatasetVersionBaseDto> datasets = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, datasets.size());

        // Retrieve dataset - session 1
        DatasetVersionBaseDto datasetVersionDtoSession01 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionBaseDto datasetVersionDtoSession02 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Send to diffusion validation - session 1 --> OK
        DatasetVersionBaseDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(),
                datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to diffusion validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToDiffusionValidation(getServiceContextAdministrador(), datasetVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendDatasetVersionToValidationRejected() throws Exception {
        // Retrieve dataset - session 1
        DatasetVersionDto datasetVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionDto datasetVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToValidationRejected(getServiceContextAdministrador(),
                datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToValidationRejected(getServiceContextAdministrador(), datasetVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession1AfterUpdate01);
        assertTrue(datasetVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendDatasetVersionToValidationRejectedWithDtoBasic() throws Exception {
        List<DatasetVersionBaseDto> datasets = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, datasets.size());

        // Retrieve dataset - session 1
        DatasetVersionBaseDto datasetVersionDtoSession01 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionBaseDto datasetVersionDtoSession02 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Send to validation rejected - session 1 --> OK
        DatasetVersionBaseDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendDatasetVersionToValidationRejected(getServiceContextAdministrador(),
                datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to validation rejected - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendDatasetVersionToValidationRejected(getServiceContextAdministrador(), datasetVersionDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersion() throws Exception {
        // Retrieve dataset - session 1
        DatasetVersionDto datasetVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionDto datasetVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(),
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Versioning - session 1 --> OK
        DatasetVersionDto datasetVersionDtoSession1NewResource = statisticalResourcesServiceFacade.versioningDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession01,
                VersionTypeEnum.MAJOR);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession1NewResource.getOptimisticLockingVersion());

        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionMockFactory
                .retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME).getSiemacMetadataStatisticalResource().getUrn());

        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Versioning - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.versioningDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession02, VersionTypeEnum.MAJOR);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetVersionDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession1NewResource);
        assertTrue(datasetVersionDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetVersionDtoSession1NewResource.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock(DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER_NAME)
    public void testSendDatasetVersionToVersioningWithDtoBasic() throws Exception {
        List<DatasetVersionBaseDto> datasets = statisticalResourcesServiceFacade.findDatasetsVersionsByCondition(getServiceContextAdministrador(), null).getResults();
        assertEquals(1, datasets.size());

        // Retrieve dataset - session 1
        DatasetVersionBaseDto datasetVersionDtoSession01 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionBaseDto datasetVersionDtoSession02 = datasets.get(0);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // Send to versioning - session 1 --> OK
        DatasetVersionBaseDto datasetVersionDtoSession1NewVersion = statisticalResourcesServiceFacade.versioningDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession01,
                VersionTypeEnum.MAJOR);

        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionMockFactory
                .retrieveMock(DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER_NAME).getSiemacMetadataStatisticalResource().getUrn());

        assertEquals(Long.valueOf(0), datasetVersionDtoSession1NewVersion.getOptimisticLockingVersion());
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Send to versioning - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.versioningDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession02, VersionTypeEnum.MAJOR);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME)
    public void testImportDatasourcesInDatasetVersion() throws Exception {
        // Retrieve dataset - session 1
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersionDto datasetVersionDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetVersionDto datasetVersionDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);
        assertEquals(Long.valueOf(0), datasetVersionDtoSession02.getOptimisticLockingVersion());

        // save - session 1 --> OK
        DatasetVersionDto datasetVersionDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession01);
        assertTrue(datasetVersionDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetVersionDtoSession01.getOptimisticLockingVersion());

        // import - session 2 --> FAIL
        try {
            List<URL> urls = Arrays.asList(new URL("file", null, "prueba.px"));
            statisticalResourcesServiceFacade.importDatasourcesInDatasetVersion(getServiceContextAdministrador(), datasetVersionDtoSession02, urls);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

    }

    // ------------------------------------------------------------
    // ATTRIBUTES
    // ------------------------------------------------------------

    @Override
    public void testCreateAttributeInstance() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveAttributeInstances() throws Exception {
        // no optimistic locking in this operation
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
    public void testRetrieveDatasetVersionMainCoverages() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testImportDatasourcesInStatisticalOperation() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindStatisticOfficialities() throws Exception {
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
        // no optimistic locking in this operation
    }

    @Override
    public void testFindDatasetsByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindQueriesByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindPublicationsByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveCoverageForDatasetVersionDimension() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFilterCoverageForDatasetVersionDimension() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveDatasetVersionDimensionsIds() throws Exception {
        // no optimistic locking in this operation
    }

    private void mockDsdAndDataRepositorySimpleDimensions() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions(datasetRepositoriesServiceFacade, srmRestInternalService);
    }
}
