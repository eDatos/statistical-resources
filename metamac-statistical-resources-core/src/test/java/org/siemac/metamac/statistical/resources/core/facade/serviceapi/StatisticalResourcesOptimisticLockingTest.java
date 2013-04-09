package org.siemac.metamac.statistical.resources.core.facade.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_05_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
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

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class StatisticalResourcesOptimisticLockingTest extends StatisticalResourcesBaseTest implements StatisticalResourcesServiceFacadeTestBase {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private QueryVersionMockFactory                  queryMockFactory;

    @Autowired
    private DatasourceMockFactory             datasourceMockFactory;

    @Autowired
    private DatasetVersionMockFactory         datasetVersionMockFactory;

    @Autowired
    private PublicationVersionMockFactory     publicationVersionMockFactory;

    @Test
    @Override
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testUpdateQuery() throws Exception {
        // Retrieve query - session 1
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession01.getOptimisticLockingVersion());
        queryDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve query - session 2
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession02.getOptimisticLockingVersion());
        queryDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update query - session 1 --> OK
        QueryDto queryDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Test
    @MetamacMock({QUERY_VERSION_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateDatasetInQuery() throws Exception {
        // Retrieve query - session 1
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession01.getOptimisticLockingVersion());
        queryDtoSession01.setDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME).getSiemacMetadataStatisticalResource().getUrn());

        // Retrieve query - session 2
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession02.getOptimisticLockingVersion());
        queryDtoSession02.setDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());

        // Update query - session 1 --> OK
        QueryDto queryDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession01);
        assertEquals(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME).getSiemacMetadataStatisticalResource().getUrn(), queryDtoSession1AfterUpdate01.getDatasetVersion());
        assertTrue(queryDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @SuppressWarnings("serial")
    @Test
    @MetamacMock({QUERY_VERSION_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testUpdateSelectionInQuery() throws Exception {
        // Retrieve query - session 1
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession01.getOptimisticLockingVersion());
        Map<String, Set<String>> selection = new HashMap<String, Set<String>>() {

            {
                put("DIM_SESSION1", new HashSet<String>(Arrays.asList("A", "B")));
            }
        };
        queryDtoSession01.setSelection(selection);

        // Retrieve query - session 2
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME)
                .getLifeCycleStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), queryDtoSession02.getOptimisticLockingVersion());
        selection = new HashMap<String, Set<String>>() {

            {
                put("DIM_SESSION2", new HashSet<String>(Arrays.asList("C", "D")));
            }
        };
        queryDtoSession02.setSelection(selection);

        // Update query - session 1 --> OK
        QueryDto queryDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession01);
        assertEquals(1, queryDtoSession01.getSelection().size());
        assertEquals(new HashSet<String>(Arrays.asList("A", "B")), queryDtoSession1AfterUpdate01.getSelection().get("DIM_SESSION1"));
        assertTrue(queryDtoSession1AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update query - session 1 --> OK
        queryDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        QueryDto queryDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession1AfterUpdate01);
        assertTrue(queryDtoSession1AfterUpdate02.getOptimisticLockingVersion() > queryDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

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

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryAsDiscontinuedAndUpdate() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), urn);

        // Retrieve query --> session 02
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), urn);
        queryDtoSession02.setTitle(new StatisticalResourcesDtoMocks().mockInternationalStringDto());

        // Mark as discontinued --> session 01 --> OK
        QueryDto queryDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.markQueryAsDiscontinued(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Update query --> session 02 --> fail - optimistic locking
        try {
            statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testMarkQueryAsDiscontinuedAndMarkQueryAsDiscontinued() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), urn);

        // Retrieve query --> session 02
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), urn);

        // Mark as discontinued --> session 01 --> OK
        QueryDto queryDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.markQueryAsDiscontinued(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Mark as discontinued --> session 02 --> fail
        try {
            statisticalResourcesServiceFacade.markQueryAsDiscontinued(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testUpdateQueryAndMarkQueryAsDiscontinued() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn();

        // Retrieve query --> session 01
        QueryDto queryDtoSession01 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), urn);
        queryDtoSession01.setTitle(new StatisticalResourcesDtoMocks().mockInternationalStringDto());

        // Retrieve query --> session 02
        QueryDto queryDtoSession02 = statisticalResourcesServiceFacade.retrieveQueryByUrn(getServiceContextAdministrador(), urn);

        // Update query --> session 01 --> OK
        QueryDto queryDtoSession01AfterUpdate01 = statisticalResourcesServiceFacade.updateQuery(getServiceContextAdministrador(), queryDtoSession01);
        assertTrue(queryDtoSession01AfterUpdate01.getOptimisticLockingVersion() > queryDtoSession01.getOptimisticLockingVersion());

        // Mark as discontinued --> session 02 --> fail
        try {
            statisticalResourcesServiceFacade.markQueryAsDiscontinued(getServiceContextAdministrador(), queryDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_01_BASIC_NAME)
    public void testUpdatePublication() throws Exception {
        // Retrieve publication - session 1
        PublicationDto publicationDtoSession01 = statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession01.getOptimisticLockingVersion());
        publicationDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve publication - session 2
        PublicationDto publicationDtoSession02 = statisticalResourcesServiceFacade.retrievePublicationByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), publicationDtoSession02.getOptimisticLockingVersion());
        publicationDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update publication - session 1 --> OK
        PublicationDto publicationDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDtoSession01);
        assertTrue(publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion() > publicationDtoSession01.getOptimisticLockingVersion());

        // Update publication - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update publication - session 1 --> OK
        publicationDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        PublicationDto publicationDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updatePublication(getServiceContextAdministrador(), publicationDtoSession1AfterUpdate01);
        assertTrue(publicationDtoSession1AfterUpdate02.getOptimisticLockingVersion() > publicationDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_01_BASIC_NAME)
    public void testUpdateDataset() throws Exception {
        // Retrieve dataset - session 1
        DatasetDto datasetDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME)
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession01.getOptimisticLockingVersion());
        datasetDtoSession01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Retrieve dataset - session 2
        DatasetDto datasetDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME)
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession02.getOptimisticLockingVersion());
        datasetDtoSession02.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());

        // Update dataset - session 1 --> OK
        DatasetDto datasetDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDtoSession01);
        assertTrue(datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetDtoSession01.getOptimisticLockingVersion());

        // Update dataset - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetDto datasetDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDtoSession1AfterUpdate01);
        assertTrue(datasetDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendToProductionValidation() throws Exception {
        // Retrieve dataset - session 1
        DatasetDto datasetDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetDto datasetDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        DatasetDto datasetDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendToProductionValidation(getServiceContextAdministrador(), datasetDtoSession01);
        assertTrue(datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendToProductionValidation(getServiceContextAdministrador(), datasetDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetDto datasetDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDtoSession1AfterUpdate01);
        assertTrue(datasetDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendToDiffusionValidation() throws Exception {
     // Retrieve dataset - session 1
        DatasetDto datasetDtoSession01 = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession01.getOptimisticLockingVersion());

        // Retrieve dataset - session 2
        DatasetDto datasetDtoSession02 = statisticalResourcesServiceFacade.retrieveDatasetByUrn(getServiceContextAdministrador(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
                .getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(Long.valueOf(0), datasetDtoSession02.getOptimisticLockingVersion());

        // Send to production validation - session 1 --> OK
        DatasetDto datasetDtoSession1AfterUpdate01 = statisticalResourcesServiceFacade.sendToDiffusionValidation(getServiceContextAdministrador(), datasetDtoSession01);
        assertTrue(datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion() > datasetDtoSession01.getOptimisticLockingVersion());

        // Send to production validation - session 2 --> FAIL
        try {
            statisticalResourcesServiceFacade.sendToDiffusionValidation(getServiceContextAdministrador(), datasetDtoSession02);
            fail("optimistic locking");
        } catch (MetamacException e) {
            assertEqualsMetamacExceptionItem(ServiceExceptionType.OPTIMISTIC_LOCKING, 0, null, e.getExceptionItems().get(0));
        }

        // Update dataset - session 1 --> OK
        datasetDtoSession1AfterUpdate01.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        DatasetDto datasetDtoSession1AfterUpdate02 = statisticalResourcesServiceFacade.updateDataset(getServiceContextAdministrador(), datasetDtoSession1AfterUpdate01);
        assertTrue(datasetDtoSession1AfterUpdate02.getOptimisticLockingVersion() > datasetDtoSession1AfterUpdate01.getOptimisticLockingVersion());

    }

    @Override
    @Test
    public void testMarkQueryAsDiscontinued() throws Exception {
        // no optimistic locking in this operation
        // Instead we have:
        // - testUpdateQueryAndMarkQueryAsDiscontinued
        // - testMarkQueryAsDiscontinuedAndMarkQueryAsDiscontinued
    }

    @Override
    public void testRetrieveQueryByUrn() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveQueries() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreateQuery() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindQueriesByCondition() throws Exception {
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
    public void testDeleteDataset() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindDatasetsByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveDatasetByUrn() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrieveDatasetVersions() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testVersioningDataset() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeleteQuery() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testCreatePublication() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testDeletePublication() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindPublicationByCondition() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrievePublicationByUrn() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testRetrievePublicationVersions() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testVersioningPublication() throws Exception {
        // no optimistic locking in this operation
    }

    @Override
    public void testFindDatasetVersionForPublicationVersion() throws Exception {
        // TODO Este metodo debe ser eliminado cuando la jerarquia este bien hecha

    }

    @Override
    public void testAddDatasetVersionToPublicationVersion() throws Exception {
        // TODO Este metodo debe ser eliminado cuando la jerarquia este bien hecha

    }

    @Override
    public void testRemoveDatasetVersionToPublicationVersion() throws Exception {
        // TODO Este metodo debe ser eliminado cuando la jerarquia este bien hecha

    }
}
