package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56_NAME;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.DataMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/task-mockito.xml",
        "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetServiceDatasourceManagementTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetService                          datasetService;

    @Autowired
    private DatasetVersionMockFactory               datasetVersionMockFactory;

    @Autowired
    private DatasourceMockFactory                   datasourceMockFactory;

    @Autowired
    private DatasetRepositoriesServiceFacade        datasetRepositoriesServiceFacade;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Before
    public void setUp() throws MetamacException {
        Mockito.reset(datasetRepositoriesServiceFacade);
        TaskMockUtils.mockAllTaskInProgressForDatasetVersion(false);
    }

    @After
    public void after() {
        Mockito.validateMockitoUsage();
    }

    // ------------------------------------------------------------------------
    // CREATE
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testCreateDatasource() throws Exception {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        Datasource actual = datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);
        expected.setDatasetVersion(expectedDatasetVersion);

        // SET EXPECTED METADATA

        DataMockUtils.fillDatasetVersionWithCalculatedMetadataFromData(expectedDatasetVersion);

        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME)
    public void testCreateDatasourceDraft() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();
        testCanAddDatasourceInCurrentProcStatus(DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME)
    public void testCreateDatasourceProductionValidation() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();
        testCanNotAddDatasourceInCurrentProcStatus(DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME);
    }
    @Test
    @MetamacMock(DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME)
    public void testCreateDatasourceDiffusionValidation() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();
        testCanNotAddDatasourceInCurrentProcStatus(DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME);
    }
    @Test
    @MetamacMock(DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME)
    public void testCreateDatasourceValidationRejected() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();
        testCanAddDatasourceInCurrentProcStatus(DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME);
    }
    @Test
    @MetamacMock(DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME)
    public void testCreateDatasourcePublished() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();
        testCanNotAddDatasourceInCurrentProcStatus(DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME);
    }

    private void testCanNotAddDatasourceInCurrentProcStatus(String datasetVersionMockName) throws MetamacException {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_CANT_ALTER_DATASOURCES, datasetVersionUrn));

        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);
        Assert.fail("Should have thrown Exception and should not created datasource");
    }

    private void testCanAddDatasourceInCurrentProcStatus(String datasetVersionMockName) throws MetamacException {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();

        Datasource actual = datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);
        assertNotNull(actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME})
    public void testUpdateDateNextUpdateOnCreateDatasourceFromPxWithDateNextUpdateBuiltAutomatically() throws Exception {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        DateTime newNextUpdate = new DateTime().plusWeeks(1);
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        expected.setDateNextUpdate(newNextUpdate);

        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);

        DatasetVersion actual = datasetService.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        BaseAsserts.assertEqualsDate(newNextUpdate, actual.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME})
    public void testUpdateDateNextUpdateOnCreateDatasourceFromPxWithDateNextUpdateBuiltAutomaticallyNewDateIsAfter() throws Exception {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        DateTime oldNextUpdate = expectedDatasetVersion.getDateNextUpdate();
        DateTime newNextUpdate = new DateTime().plusYears(1);
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        expected.setDateNextUpdate(newNextUpdate);

        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);

        DatasetVersion actual = datasetService.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        BaseAsserts.assertEqualsDate(oldNextUpdate, actual.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH_NAME})
    public void testUpdateDateNextUpdateOnCreateDatasourceFromPxWithUserDateNextUpdate() throws Exception {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH_NAME);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        DateTime oldNextUpdate = expectedDatasetVersion.getDateNextUpdate();
        DateTime nextUpdate = new DateTime().plusWeeks(1);
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        expected.setDateNextUpdate(nextUpdate);

        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);

        DatasetVersion actual = datasetService.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);

        // SET EXPECTED METADATA
        BaseAsserts.assertEqualsDate(oldNextUpdate, actual.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasourceErrorIdentifiableResourceRequired() throws Exception {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASOURCE__IDENTIFIABLE_STATISTICAL_RESOURCE));

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceWithIdentifiableAndDatasetVersionNull(expectedDatasetVersion);
        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasoureErrorDatasetVersionMustBeEmpty() throws Exception {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_UNEXPECTED, ServiceExceptionParameters.DATASOURCE__DATASET_VERSION));

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasource(expectedDatasetVersion);
        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);
    }

    // ------------------------------------------------------------------------
    // UPDATE
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testUpdateDatasource() throws Exception {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);

        expected.getIdentifiableStatisticalResource().setLastUpdatedBy("user");

        Datasource actual = datasetService.updateDatasource(getServiceContextWithoutPrincipal(), expected);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME)
    public void testUpdateDatasourceDraft() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanUpdateDatasourceInCurrentProcStatus(DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME)
    public void testUpdateDatasourceNotProductionValidation() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();
        testCanNotUpdateDatasourceInCurrentProcStatus(DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME)
    public void testUpdateDatasourceDiffusionValidtaion() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanNotUpdateDatasourceInCurrentProcStatus(DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME)
    public void testUpdateDatasourceValidationRejected() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanUpdateDatasourceInCurrentProcStatus(DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME);
        testCanNotUpdateDatasourceInCurrentProcStatus(DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME)
    public void testUpdateDatasourcePublished() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanNotUpdateDatasourceInCurrentProcStatus(DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME);
    }

    private void testCanNotUpdateDatasourceInCurrentProcStatus(String datasetVersionMockName) throws MetamacException {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        Datasource expected = expectedDatasetVersion.getDatasources().get(0);

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_CANT_ALTER_DATASOURCES, datasetVersionUrn));

        datasetService.updateDatasource(getServiceContextWithoutPrincipal(), expected);
        Assert.fail("Should have thrown Exception and should not updated datasource");
    }

    private void testCanUpdateDatasourceInCurrentProcStatus(String datasetVersionMockName) throws MetamacException {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);

        Datasource expected = expectedDatasetVersion.getDatasources().get(0);

        Datasource actual = datasetService.updateDatasource(getServiceContextWithoutPrincipal(), expected);
        assertNotNull(actual);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testUpdateDatasourceImportationTaskInProgress() throws Exception {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);
        DatasetVersion expectedDatasetVersion = expected.getDatasetVersion();
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        TaskMockUtils.mockTaskInProgressForDatasetVersion(datasetVersionUrn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, datasetVersionUrn));

        expected.getIdentifiableStatisticalResource().setLastUpdatedBy("user");
        datasetService.updateDatasource(getServiceContextWithoutPrincipal(), expected);
    }

    // ------------------------------------------------------------------------
    // RETRIEVE
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testRetrieveDatasourceByUrn() throws Exception {
        Datasource actual = datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME)
                .getIdentifiableStatisticalResource().getUrn());
        assertEqualsDatasource(datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME), actual);
    }

    @Test
    public void testRetrieveDatasourceByUrnParameterRequired() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionSingleParameters.URN));

        datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), EMPTY);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testRetrieveDatasourceByUrnNotExists() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASOURCE_NOT_FOUND, URN_NOT_EXISTS));

        datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock({DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME, QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56_NAME, QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56_NAME})
    public void testDeleteDatasourceInDatasetVersionWithQueries() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME);

        Datasource expected = datasetVersion.getDatasources().get(0);

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        String datasourceUrn = expected.getIdentifiableStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASOURCE_IN_DATASET_VERSION_WITH_QUERIES_DELETE_ERROR, datasourceUrn));

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasource() throws Exception {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        String datasourceUrn = expected.getIdentifiableStatisticalResource().getUrn();

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASOURCE_NOT_FOUND, datasourceUrn));
        datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), datasourceUrn);
    }

    @Test
    @MetamacMock(DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME)
    public void testDeleteDatasourceDraft() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanDeleteDatasourceInCurrentProcStatus(DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME)
    public void testDeleteDatasourceProductionValidation() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanNotDeleteDatasourceInCurrentProcStatus(DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME)
    public void testDeleteDatasourceDiffusionValidation() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanNotDeleteDatasourceInCurrentProcStatus(DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME)
    public void testDeleteDatasourceValidationRejected() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanDeleteDatasourceInCurrentProcStatus(DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock(DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME)
    public void testDeleteDatasourcePublished() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        testCanNotDeleteDatasourceInCurrentProcStatus(DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasourceErrorDeletingData() throws Exception {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        String datasourceUrn = expected.getIdentifiableStatisticalResource().getUrn();

        Mockito.doThrow(ApplicationException.class).when(datasetRepositoriesServiceFacade)
                .deleteObservationsByAttributeValue(Mockito.anyString(), Mockito.anyInt(), Mockito.any(AttributeBasicDto.class));

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASOURCE_DATA_DELETE_ERROR, expected.getIdentifiableStatisticalResource().getCode()));

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasourceImportationInProgress() throws Exception {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);
        DatasetVersion expectedDatasetVersion = expected.getDatasetVersion();
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        TaskMockUtils.mockTaskInProgressForDatasetVersion(datasetVersionUrn, true);

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        String datasourceUrn = expected.getIdentifiableStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, datasetVersionUrn));

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasourceUpdateDatasetVersionDataMetadata() throws Exception {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);
        DatasetVersion expectedDatasetVersion = expected.getDatasetVersion();
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        String datasourceUrn = expected.getIdentifiableStatisticalResource().getUrn();

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);

        DatasetVersion actualDatasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasetVersionUrn);

        DataMockUtils.fillDatasetVersionWithCalculatedMetadataFromData(expectedDatasetVersion);

        assertEqualsDatasetVersion(expectedDatasetVersion, actualDatasetVersion);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasourceErrorNotExists() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASOURCE_NOT_FOUND, URN_NOT_EXISTS));

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getDatasources();

            List<Datasource> actual = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn);
            assertEquals(expected.size(), actual.size());
            assertEqualsDatasourceCollection(expected, actual);
        }

        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getDatasources();

            List<Datasource> actual = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn);
            assertEquals(expected.size(), actual.size());
            assertEqualsDatasourceCollection(expected, actual);
        }
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testUpdateLastUpdateDateOnDatasourceDeletion() throws Exception {
        Datasource datasource = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);
        DatasetVersion expectedDatasetVersion = datasource.getDatasetVersion();
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        String datasourceUrn = datasource.getIdentifiableStatisticalResource().getUrn();

        DateTime oldLastUpdate = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate();

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);

        // retrieve dataset version, lastupdate must have been changed
        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasetVersionUrn);
        assertNotNull(datasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate());
        assertFalse(datasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate().equals(oldLastUpdate));
    }

    private void testCanNotDeleteDatasourceInCurrentProcStatus(String datasetVersionMockName) throws MetamacException {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        Datasource expected = expectedDatasetVersion.getDatasources().get(0);

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_CANT_ALTER_DATASOURCES, datasetVersionUrn));

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), expected.getIdentifiableStatisticalResource().getUrn());

        fail("Should have thrown Exception and should not deleted datasource");
    }

    private void testCanDeleteDatasourceInCurrentProcStatus(String datasetVersionMockName) throws MetamacException {
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);

        Datasource expected = expectedDatasetVersion.getDatasources().get(0);

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), expected.getIdentifiableStatisticalResource().getUrn());
    }

}
