package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersionCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersionNotChecksDataset;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_09_OPER_0001_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_30_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockCodeDimensionsWithIdentifiers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.lang3.BooleanUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorResult;
import org.siemac.metamac.statistical.resources.core.utils.DataMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
public class DatasetServiceTest extends StatisticalResourcesBaseTest implements DatasetServiceTestBase {

    @Autowired
    private DatasetService                          datasetService;

    @Autowired
    private DatasetVersionMockFactory               datasetVersionMockFactory;

    @Autowired
    private DatasourceMockFactory                   datasourceMockFactory;

    @Autowired
    private DatasetMockFactory                      datasetMockFactory;

    @Autowired
    private QueryVersionMockFactory                 queryMockFactory;

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
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testUpdateLastUpdateDateOnDatasourceCreation() throws Exception {
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String datasetVersionUrn = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DateTime oldLastUpdate = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionUrn, expected);

        // retrieve dataset version, lastupdate must have been changed
        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasetVersionUrn);
        assertNotNull(datasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate());
        assertFalse(datasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate().equals(oldLastUpdate));
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

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock(DATASET_01_BASIC_NAME)
    public void testFindDatasetsByCondition() throws Exception {
        Dataset result = datasetMockFactory.retrieveMock(DATASET_01_BASIC_NAME);

        // Find by code
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Dataset.class).withProperty(DatasetProperties.identifiableStatisticalResource().code())
                .eq(result.getIdentifiableStatisticalResource().getCode()).build();

        PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
        PagedResult<Dataset> datasetsPagedResult = datasetService.findDatasetsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
        assertEquals(1, datasetsPagedResult.getTotalRows());
        assertEquals(result.getIdentifiableStatisticalResource().getUrn(), datasetsPagedResult.getValues().get(0).getIdentifiableStatisticalResource().getUrn());
    }

    @Test
    @MetamacMock({DATASET_01_BASIC_NAME, DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testFindDatasetsByConditionWithoutConditions() throws Exception {
        // Find by code
        PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
        PagedResult<Dataset> datasetsPagedResult = datasetService.findDatasetsByCondition(getServiceContextWithoutPrincipal(), null, pagingParameter);
        assertEquals(3, datasetsPagedResult.getTotalRows());
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    @Override
    @Test
    public void testCreateDatasetVersion() throws Exception {
        DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        String operationCode = actual.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEquals(operationCode + "_000001", actual.getSiemacMetadataStatisticalResource().getCode());
        assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 1, "001.000"), actual.getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersionNotChecksDataset(expected, actual);
        assertNotNull(actual.getSiemacMetadataStatisticalResource().getCreatedDate());
        assertNotNull(actual.getSiemacMetadataStatisticalResource().getCreatedBy());
        assertTrue(actual.getSiemacMetadataStatisticalResource().getCreatedDate().equals(actual.getSiemacMetadataStatisticalResource().getCreationDate()));
        assertTrue(actual.getSiemacMetadataStatisticalResource().getCreatedBy().equals(actual.getSiemacMetadataStatisticalResource().getCreationUser()));
    }

    @Test
    @MetamacMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME)
    public void testCreateDatasetVersionSameOperationExistingDataset() throws Exception {
        DatasetVersion datasetVersionOper1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        String operationCode = datasetVersionOper1.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
        DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();

        DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEquals(operationCode + "_000004", actual.getSiemacMetadataStatisticalResource().getCode());
        assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 4, "001.000"), actual.getSiemacMetadataStatisticalResource().getUrn());

        assertEqualsDatasetVersionNotChecksDataset(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME)
    public void testCreateDatasetVersionSameOperationExistingDatasetConsecutiveCode() throws Exception {
        DatasetVersion datasetVersionOper1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        String operationCode = datasetVersionOper1.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        {
            ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
            DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
            DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
            assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
            assertEquals(operationCode + "_000004", actual.getSiemacMetadataStatisticalResource().getCode());
            assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 4, "001.000"), actual.getSiemacMetadataStatisticalResource()
                    .getUrn());
            assertEqualsDatasetVersionNotChecksDataset(expected, actual);
        }
        {
            ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
            DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
            DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
            assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
            assertEquals(operationCode + "_000005", actual.getSiemacMetadataStatisticalResource().getCode());
            assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 5, "001.000"), actual.getSiemacMetadataStatisticalResource()
                    .getUrn());
            assertEqualsDatasetVersionNotChecksDataset(expected, actual);
        }
    }

    @Test
    @MetamacMock(DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME)
    public void testCreateDatasetVersionMaxCode() throws Exception {
        DatasetVersion datasetVersionOper1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME);
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_MAX_REACHED_IN_OPERATION, datasetVersionOper1.getSiemacMetadataStatisticalResource().getStatisticalOperation()
                .getUrn()));

        String operationCode = datasetVersionOper1.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
        datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), statisticalResourcesNotPersistedDoMocks.mockDatasetVersion(), statisticalOperation);
    }

    @Test
    public void testCreateDatasetVersionErrorParameterDatasetRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION));
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), null, statisticalOperation);
    }

    @Test
    public void testCreateDatasetVersionErrorParameterStatisticalOperationRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.STATISTICAL_OPERATION));

        DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, null);
    }

    @Test
    public void testCreateDatasetVersionErrorMetadataSiemacStatisticalResourceRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE));

        DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersionWithNullableSiemacStatisticalResource();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
    }

    @Override
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testUpdateDatasetVersion() throws Exception {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        DatasetVersion actual = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME, QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56_NAME, QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56_NAME})
    public void testUpdateDatasetVersionChangeDsdWithQueriesError() throws Exception {
        DatasetVersion updatedDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME);
        updatedDataset.setRelatedDsdChanged(true);
        updatedDataset.setRelatedDsd(generateOtherDsdVersion(updatedDataset.getRelatedDsd()));

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_CANT_CHANGE_DSD_SOME_QUERIES_EXIST, updatedDataset.getSiemacMetadataStatisticalResource().getUrn()));
        datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), updatedDataset);
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testUpdateDatasetVersionImportationTaskInProgress() throws Exception {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        String datasetVersionUrn = expected.getSiemacMetadataStatisticalResource().getUrn();

        TaskMockUtils.mockTaskInProgressForDatasetVersion(datasetVersionUrn, true);

        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, datasetVersionUrn));

        datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), expected);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testUpdateDatasetVersionErrorFinal() throws Exception {
        DatasetVersion finalDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        String datasetVersionUrn = finalDataset.getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, datasetVersionUrn, "DRAFT, VALIDATION_REJECTED, PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"));

        datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), finalDataset);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testUpdateDatasetVersionErrorIncorrectCode() throws Exception {
        DatasetVersion dataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);

        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__CODE));

        dataset.getSiemacMetadataStatisticalResource().setCode("@12345");
        datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);
    }

    @Test
    @MetamacMock({DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_NAME})
    public void testUpdateDatasetVersionChangeDsdClearDataRelatedMetadata() throws Exception {
        DatasetVersion dataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_NAME);

        ExternalItem newDsd = StatisticalResourcesDoMocks.mockDsdExternalItem();
        dataset.setRelatedDsd(newDsd);

        DateTime oldLastUpdate = dataset.getSiemacMetadataStatisticalResource().getLastUpdate();

        DatasetVersion updatedDataset = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);

        assertEquals(newDsd.getUrn(), updatedDataset.getRelatedDsd().getUrn());
        assertEquals(0, updatedDataset.getDatasources().size());
        assertEquals(0, updatedDataset.getCoverages().size());
        assertEquals(0, updatedDataset.getGeographicCoverage().size());
        assertEquals(0, updatedDataset.getTemporalCoverage().size());
        assertEquals(0, updatedDataset.getMeasureCoverage().size());
        assertNull(updatedDataset.getDateStart());
        assertNull(updatedDataset.getDateEnd());
        assertNotNull(updatedDataset.getSiemacMetadataStatisticalResource().getLastUpdate());
        assertTrue(updatedDataset.getSiemacMetadataStatisticalResource().getLastUpdate().isAfter(oldLastUpdate));
        assertNull(updatedDataset.getFormatExtentDimensions());
        assertNull(updatedDataset.getFormatExtentObservations());
        assertTrue(BooleanUtils.isNotTrue(updatedDataset.getUserModifiedDateNextUpdate()));
        assertNull(updatedDataset.getDateNextUpdate());
        assertNull(updatedDataset.getDatasetRepositoryId());
    }

    @Test
    @MetamacMock({DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE_NAME})
    public void testUpdateDatasetVersionChangeDsdClearDataRelatedMetadataKeepUserDateNextUpdate() throws Exception {
        DatasetVersion dataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE_NAME);

        ExternalItem newDsd = StatisticalResourcesDoMocks.mockDsdExternalItem();
        dataset.setRelatedDsd(newDsd);

        DateTime oldLastUpdate = dataset.getSiemacMetadataStatisticalResource().getLastUpdate();
        DateTime oldNextDateUpdate = dataset.getDateNextUpdate();

        DatasetVersion updatedDataset = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);

        assertEquals(newDsd.getUrn(), updatedDataset.getRelatedDsd().getUrn());
        assertEquals(0, updatedDataset.getDatasources().size());
        assertEquals(0, updatedDataset.getCoverages().size());
        assertEquals(0, updatedDataset.getGeographicCoverage().size());
        assertEquals(0, updatedDataset.getTemporalCoverage().size());
        assertEquals(0, updatedDataset.getMeasureCoverage().size());
        assertNull(updatedDataset.getDateStart());
        assertNull(updatedDataset.getDateEnd());
        assertNotNull(updatedDataset.getSiemacMetadataStatisticalResource().getLastUpdate());
        assertTrue(updatedDataset.getSiemacMetadataStatisticalResource().getLastUpdate().isAfter(oldLastUpdate));
        assertNull(updatedDataset.getFormatExtentDimensions());
        assertNull(updatedDataset.getFormatExtentObservations());
        assertTrue(BooleanUtils.isTrue(updatedDataset.getUserModifiedDateNextUpdate()));
        BaseAsserts.assertEqualsDate(oldNextDateUpdate, updatedDataset.getDateNextUpdate());
        assertNull(updatedDataset.getDatasetRepositoryId());
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasetVersionByUrn() throws Exception {
        String urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersion actual = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME), actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestDatasetVersionByDatasetUrn() throws Exception {
        String urn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion actual = datasetService.retrieveLatestDatasetVersionByDatasetUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME), actual);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestDatasetVersionByDatasetUrnErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_URN));
        datasetService.retrieveLatestDatasetVersionByDatasetUrn(getServiceContextWithoutPrincipal(), null);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestPublishedDatasetVersionByDatasetUrn() throws Exception {
        String urn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion actual = datasetService.retrieveLatestPublishedDatasetVersionByDatasetUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME), actual);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLatestPublishedDatasetVersionByDatasetUrnErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_URN));
        datasetService.retrieveLatestPublishedDatasetVersionByDatasetUrn(getServiceContextWithoutPrincipal(), null);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasetVersionByUrnV2() throws Exception {
        String urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersion actual = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME), actual);
    }

    @Test
    public void testRetrieveDatasetVersionByUrnErrorParameterRequiered() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION_URN));

        datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), null);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasetVersionByUrnErrorNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, URN_NOT_EXISTS));

        datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_01_BASIC_NAME, DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testRetrieveDatasetVersions() throws Exception {
        String urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        List<DatasetVersion> actual = datasetService.retrieveDatasetVersions(getServiceContextWithoutPrincipal(), urn);

        assertEquals(2, actual.size());
        assertEqualsDatasetVersionCollection(datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getVersions(), actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_01_BASIC_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testFindDatasetVersionsByCondition() throws Exception {
        // Find by version number
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().versionLogic())
                .eq("002.000").orderBy(DatasetVersionProperties.siemacMetadataStatisticalResource().id()).ascending().build();

        PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
        PagedResult<DatasetVersion> datasetVersionPagedResult = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
        assertEquals(1, datasetVersionPagedResult.getTotalRows());
        assertEquals(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(), datasetVersionPagedResult
                .getValues().get(0).getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_01_BASIC_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testDeleteDatasetVersion() throws Exception {
        String urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, urn));

        // Delete dataset version
        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urn);
        datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_01_BASIC_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testDeleteDatasetVersionImportationTaskInProgress() throws Exception {
        String urn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();
        TaskMockUtils.mockTaskInProgressForDatasetVersion(urn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, urn));

        // Delete dataset version
        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testDeleteDatasetVersionWithTwoVersions() throws Exception {
        String urnV1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String urnV2 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, urnV2));

        // Delete dataset version
        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urnV2);

        // Validation
        DatasetVersion datasetVersionV1 = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urnV1);
        assertNull(datasetVersionV1.getSiemacMetadataStatisticalResource().getIsReplacedBy());

        datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urnV2);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testDeleteDatasetVersionErrorNotExists() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, URN_NOT_EXISTS));

        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testDeleteDatasetVersionErrorNoLastVersion() throws Exception {
        String urnV1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urnV1, "DRAFT, VALIDATION_REJECTED"));

        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urnV1);
    }

    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME})
    @Rollback(true)
    public void testDeleteDatasetVersionErrorQueryRelated() throws Exception {
        thrown.expect(PersistenceException.class);

        String urnDatasetVersion = queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();
        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urnDatasetVersion);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DatasetVersion datasetNewVersion = datasetService.versioningDatasetVersion(getServiceContextWithoutPrincipal(), urn, VersionTypeEnum.MINOR);

        assertNotNull(datasetNewVersion);
        assertFalse(datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic().equals(datasetNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        checkNewDatasetVersionCreated(datasetVersion, datasetNewVersion);
    }

    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersionImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        TaskMockUtils.mockTaskInProgressForDatasetVersion(urn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, urn));

        datasetService.versioningDatasetVersion(getServiceContextWithoutPrincipal(), urn, VersionTypeEnum.MINOR);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME)
    public void testImportDatasourcesInDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        List<URL> urls = Arrays.asList(new File("prueba.px").toURI().toURL());
        datasetService.importDatasourcesInDatasetVersion(getServiceContextWithoutPrincipal(), urn, urls);
    }

    @Test
    @MetamacMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME)
    public void testImportDatasourcesInDatasetVersionImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        TaskMockUtils.mockTaskInProgressForDatasetVersion(urn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, urn));

        List<URL> urls = Arrays.asList(new File("prueba.px").toURI().toURL());
        datasetService.importDatasourcesInDatasetVersion(getServiceContextWithoutPrincipal(), urn, urls);
    }

    @Test
    @MetamacMock({DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME, DATASET_VERSION_30_WITH_DATASOURCE_NAME})
    public void testImportDatasourcesInDatasetVersionFileAlreadyAssignedInOtherDataset() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.INVALID_FILE_FOR_DATASET_VERSION, "datasource_06.px", urn))));

        List<URL> urls = Arrays.asList(new File("datasource_06.px").toURI().toURL());
        datasetService.importDatasourcesInDatasetVersion(getServiceContextWithoutPrincipal(), urn, urls);
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME, DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME})
    public void testImportDatasourcesInStatisticalOperation() throws Exception {
        DatasetVersion datasetVersion37 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME);
        String fileForDatasetVersion37 = datasetVersion37.getDatasources().get(0).getFilename();
        DatasetVersion datasetVersion38 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME);
        String fileForDatasetVersion38 = datasetVersion38.getDatasources().get(0).getFilename();

        String statisticalOperationUrn = datasetVersion37.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn();

        List<URL> urls = Arrays.asList(buildURLForFile(fileForDatasetVersion37), buildURLForFile(fileForDatasetVersion38));
        datasetService.importDatasourcesInStatisticalOperation(getServiceContextWithoutPrincipal(), statisticalOperationUrn, urls);
    }

    @Test
    @MetamacMock({DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME, DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME})
    public void testImportDatasourcesInStatisticalOperationImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion37 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME);
        String fileForDatasetVersion37 = datasetVersion37.getDatasources().get(0).getFilename();
        DatasetVersion datasetVersion38 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME);
        String fileForDatasetVersion38 = datasetVersion38.getDatasources().get(0).getFilename();

        TaskMockUtils.mockTaskInProgressForDatasetVersion(datasetVersion37.getSiemacMetadataStatisticalResource().getUrn(), true);

        MetamacException expectedException = new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_ERROR, datasetVersion37.getSiemacMetadataStatisticalResource().getUrn());
        expectedException
                .getExceptionItems()
                .get(0)
                .setExceptionItems(
                        Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, datasetVersion37.getSiemacMetadataStatisticalResource().getUrn())));

        expectedMetamacException(expectedException);

        String statisticalOperationUrn = datasetVersion37.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn();

        List<URL> urls = Arrays.asList(buildURLForFile(fileForDatasetVersion37), buildURLForFile(fileForDatasetVersion38));
        datasetService.importDatasourcesInStatisticalOperation(getServiceContextWithoutPrincipal(), statisticalOperationUrn, urls);
    }

    @Test
    @MetamacMock({DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME, DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME})
    public void testImportDatasourcesInStatisticalOperationNotLinkedFile() throws Exception {
        DatasetVersion datasetVersion37 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME);
        DatasetVersion datasetVersion38 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME);
        String fileForDatasetVersion38 = datasetVersion38.getDatasources().get(0).getFilename();

        String statisticalOperationUrn = datasetVersion37.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.FILE_NOT_LINKED_TO_ANY_DATASET_IN_STATISTICAL_OPERATION, "not_exist.px", statisticalOperationUrn));

        List<URL> urls = Arrays.asList(buildURLForFile("not_exist.px"), buildURLForFile(fileForDatasetVersion38));
        datasetService.importDatasourcesInStatisticalOperation(getServiceContextWithoutPrincipal(), statisticalOperationUrn, urls);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME)
    public void testProccessDatasetFileImportationResult() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME).getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        FileDescriptorResult fileDescriptor = new FileDescriptorResult();
        fileDescriptor.setDatasourceId("file.csv_" + new Date().toString());
        fileDescriptor.setFileName("file.csv");
        fileDescriptor.setDatasetFileFormatEnum(DatasetFileFormatEnum.CSV);

        List<FileDescriptorResult> fileDescriptors = new ArrayList<FileDescriptorResult>();
        fileDescriptors.add(fileDescriptor);

        datasetService.proccessDatasetFileImportationResult(getServiceContextAdministrador(), datasetVersionUrn, fileDescriptors);

        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextAdministrador(), datasetVersionUrn);
        assertNotNull(datasetVersion.getDatasetRepositoryId());

        List<Datasource> datasources = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
        assertEquals(1, datasources.size());
        assertEquals(fileDescriptor.getDatasourceId(), datasources.get(0).getIdentifiableStatisticalResource().getCode());
        assertEquals(fileDescriptor.getFileName(), datasources.get(0).getFilename());
        assertNull(datasources.get(0).getDateNextUpdate());
    }

    @Test
    @MetamacMock(DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME)
    public void testProccessDatasetFileImportationResultPxDatasource() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME).getSiemacMetadataStatisticalResource().getUrn();

        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        FileDescriptorResult fileDescriptor = new FileDescriptorResult();
        fileDescriptor.setDatasourceId("file.px_" + new Date().toString());
        fileDescriptor.setFileName("file.px");
        fileDescriptor.setDatasetFileFormatEnum(DatasetFileFormatEnum.PX);
        fileDescriptor.setNextUpdate(new Date());

        List<FileDescriptorResult> fileDescriptors = new ArrayList<FileDescriptorResult>();
        fileDescriptors.add(fileDescriptor);

        datasetService.proccessDatasetFileImportationResult(getServiceContextAdministrador(), datasetVersionUrn, fileDescriptors);

        List<Datasource> datasources = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextAdministrador(), datasetVersionUrn);
        assertEquals(1, datasources.size());
        assertEquals(fileDescriptor.getDatasourceId(), datasources.get(0).getIdentifiableStatisticalResource().getCode());
        BaseAsserts.assertEqualsDate(datasources.get(0).getDateNextUpdate(), fileDescriptor.getNextUpdate());
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testRetrieveCoverageForDatasetVersionDimension() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        {
            List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(getServiceContextAdministrador(), datasetVersionUrn, "dim-none");
            Assert.assertEquals(0, codeDimensions.size());
        }
        {
            List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(getServiceContextAdministrador(), datasetVersionUrn, "dim1");
            DatasetsAsserts.assertEqualsCodeDimensionsCollection(mockCodeDimensionsWithIdentifiers(datasetVersion, "dim1", "code-d1-1", "code-d1-2"), codeDimensions);
        }
        {
            List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(getServiceContextAdministrador(), datasetVersionUrn, "dim2");
            DatasetsAsserts.assertEqualsCodeDimensionsCollection(mockCodeDimensionsWithIdentifiers(datasetVersion, "dim2", "code-d2-1", "code-d2-2"), codeDimensions);
        }
        {
            List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(getServiceContextAdministrador(), datasetVersionUrn, "dim3");
            DatasetsAsserts.assertEqualsCodeDimensionsCollection(mockCodeDimensionsWithIdentifiers(datasetVersion, "dim3", "code-d3-1"), codeDimensions);
        }
    }

    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testRetrieveCoverageForDatasetVersionDimensionImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        TaskMockUtils.mockTaskInProgressForDatasetVersion(datasetVersionUrn, true);
        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, datasetVersionUrn));
        {
            List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(getServiceContextAdministrador(), datasetVersionUrn, "dim-none");
            Assert.assertEquals(0, codeDimensions.size());
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES_NAME)
    public void testFilterCoverageForDatasetVersionDimension() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        {
            List<CodeDimension> codeDimensions = datasetService.filterCoverageForDatasetVersionDimension(getServiceContextAdministrador(), datasetVersionUrn, "TIME_PERIOD", "Enero");
            Assert.assertEquals(1, codeDimensions.size());
        }
    }

    @Test
    @MetamacMock(DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES_NAME)
    public void testFilterCoverageForDatasetVersionDimensionImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        TaskMockUtils.mockTaskInProgressForDatasetVersion(datasetVersionUrn, true);
        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, datasetVersionUrn));
        {
            List<CodeDimension> codeDimensions = datasetService.filterCoverageForDatasetVersionDimension(getServiceContextAdministrador(), datasetVersionUrn, "TIME_PERIOD", "Enero");
            Assert.assertEquals(1, codeDimensions.size());
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testRetrieveDatasetVersionDimensionsIds() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        List<String> dimensionIds = datasetService.retrieveDatasetVersionDimensionsIds(getServiceContextAdministrador(), datasetVersionUrn);
        assertEquals(dimensionIds, Arrays.asList("dim1", "dim2", "dim3"));
    }

    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testRetrieveDatasetVersionDimensionsIdsImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        TaskMockUtils.mockTaskInProgressForDatasetVersion(datasetVersionUrn, true);
        expectedMetamacException(new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_TASK_IN_PROGRESS, datasetVersionUrn));

        List<String> dimensionIds = datasetService.retrieveDatasetVersionDimensionsIds(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(dimensionIds, Arrays.asList("dim1", "dim2", "dim3"));
    }

    @Override
    @Test
    @MetamacMock({STATISTIC_OFFICIALITY_01_BASIC_NAME, STATISTIC_OFFICIALITY_02_BASIC_NAME})
    public void testFindStatisticOfficialities() throws Exception {
        List<StatisticOfficiality> officialities = datasetService.findStatisticOfficialities(getServiceContextAdministrador());
        assertEquals(2, officialities.size());
    }

    private static void checkNewDatasetVersionCreated(DatasetVersion previous, DatasetVersion next) throws MetamacException {
        BaseAsserts.assertEqualsVersioningSiemacMetadata(previous.getSiemacMetadataStatisticalResource(), next.getSiemacMetadataStatisticalResource());

        // Non inherited fields
        assertEquals(0, next.getGeographicCoverage().size());
        assertEquals(0, next.getTemporalCoverage().size());
        assertEquals(0, next.getMeasureCoverage().size());
        assertNull(next.getDateStart());
        assertNull(next.getDateEnd());
        assertNull(next.getDateNextUpdate());
        assertNull(next.getBibliographicCitation());

        // Inherited
        BaseAsserts.assertEqualsExternalItemCollection(previous.getGeographicGranularities(), next.getGeographicGranularities());
        BaseAsserts.assertEqualsExternalItemCollection(previous.getTemporalGranularities(), next.getTemporalGranularities());
        BaseAsserts.assertEqualsExternalItemCollection(previous.getStatisticalUnit(), next.getStatisticalUnit());
        BaseAsserts.assertEqualsExternalItem(previous.getRelatedDsd(), next.getRelatedDsd());
        BaseAsserts.assertEqualsExternalItem(previous.getUpdateFrequency(), next.getUpdateFrequency());
        BaseAsserts.assertEqualsStatisticOfficiality(previous.getStatisticOfficiality(), next.getStatisticOfficiality());
    }

    private static String buildDatasetUrn(String maintainerCode, String operationCode, int datasetSequentialId, String versionNumber) {
        StringBuilder strBuilder = new StringBuilder("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=");
        strBuilder.append(maintainerCode).append(":").append(operationCode).append("_").append(String.format("%06d", datasetSequentialId)).append("(").append(versionNumber).append(")");
        return strBuilder.toString();
    }

    private URL buildURLForFile(String filename) throws Exception {
        return new URL("file", null, filename);
    }

    private ExternalItem generateOtherDsdVersion(ExternalItem relatedDsd) {
        ExternalItem dsd = StatisticalResourcesDoMocks.mockDsdExternalItem();
        dsd.setCode(relatedDsd.getCode());
        dsd.setUrn(StatisticalResourcesDoMocks.mockDsdUrn(relatedDsd.getCode(), "02.035"));
        assertFalse(dsd.getUrn().equals(relatedDsd.getUrn()));
        return dsd;
    }
}
