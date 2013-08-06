package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_30_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_02_BASIC_NAME;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.DatasetServiceImpl;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mock.Mocks;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorResult;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/task-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
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
    private TaskService                             taskService;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        Datasource actual = datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME)
                .getSiemacMetadataStatisticalResource().getUrn(), expected);
        expected.setDatasetVersion(expectedDatasetVersion);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasourceErrorIdentifiableResourceRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASOURCE__IDENTIFIABLE_STATISTICAL_RESOURCE));

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceWithIdentifiableAndDatasetVersionNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME));
        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                expected);
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasourceErrorDatasetVersionMustBeEmpty() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_UNEXPECTED, ServiceExceptionParameters.DATASOURCE__DATASET_VERSION));

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasource(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME));
        datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                expected);
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

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasource() throws Exception {
        String datasourceUrn = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME).getIdentifiableStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASOURCE_NOT_FOUND, datasourceUrn));

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);
        datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), datasourceUrn);
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
        DatasetVersion expectedDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DateTime oldLastUpdate = expectedDatasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate();

        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        Datasource actual = datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME)
                .getSiemacMetadataStatisticalResource().getUrn(), expected);

        // retrieve dataset version, lastupdate must have been changed
        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), expectedDatasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        assertNotNull(datasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate());
        assertFalse(datasetVersion.getSiemacMetadataStatisticalResource().getLastUpdate().equals(oldLastUpdate));
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testUpdateLastUpdateDateOnDatasourceDeletion() throws Exception {
        Datasource datasource = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);
        String datasourceUrn = datasource.getIdentifiableStatisticalResource().getUrn();

        DateTime oldLastUpdate = datasource.getDatasetVersion().getSiemacMetadataStatisticalResource().getLastUpdate();

        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);

        // retrieve dataset version, lastupdate must have been changed
        DatasetVersion datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasource.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn());
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
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testUpdateDatasetVersionErrorFinal() throws Exception {
        DatasetVersion finalDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, finalDataset.getSiemacMetadataStatisticalResource().getUrn(),
                "DRAFT, VALIDATION_REJECTED, PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"));

        datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), finalDataset);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testUpdateDatasetVersionErrorIncorrectCode() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__CODE));

        DatasetVersion dataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        dataset.getSiemacMetadataStatisticalResource().setCode("@12345");
        datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);
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

        DatasetVersion datasetNewVersion = datasetService.versioningDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn(),
                VersionTypeEnum.MINOR);
        assertNotNull(datasetNewVersion);
        assertFalse(datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic().equals(datasetNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        checkNewDatasetVersionCreated(datasetVersion, datasetNewVersion);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME)
    public void testImportDatasourcesInDatasetVersion() throws Exception {

        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        List<URL> urls = Arrays.asList(new File("prueba.px").toURI().toURL());
        datasetService.importDatasourcesInDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), urls);
    }

    @Test
    @MetamacMock({DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME, DATASET_VERSION_30_WITH_DATASOURCE_NAME})
    public void testImportDatasourcesInDatasetVersionFileAlreadyAssignedInOtherDataset() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        
        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.INVALID_FILE_FOR_DATASET_VERSION, "datasource_06.px", datasetVersion.getSiemacMetadataStatisticalResource().getUrn()))));
        
        List<URL> urls = Arrays.asList(new File("datasource_06.px").toURI().toURL());
        datasetService.importDatasourcesInDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), urls);
    }

    @Override
    @MetamacMock({DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME})
    public void testImportDatasourcesInStatisticalOperation() throws Exception {
        Assert.fail("Unimplemented");

    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME)
    public void testProccessDatasetFileImportationResult() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME).getSiemacMetadataStatisticalResource().getUrn();

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
    public void testRetrieveCoverageForDatasetVersionDimension() throws Exception {
        Assert.fail("Unimplemented");

    }

    @Override
    public void testRetrieveDatasetVersionDimensionsIds() throws Exception {
        Assert.fail("Unimplemented");
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
}
