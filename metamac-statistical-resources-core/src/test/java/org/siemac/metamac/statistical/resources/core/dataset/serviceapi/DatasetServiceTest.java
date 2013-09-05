package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersionCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersionNotChecksDataset;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_09_OPER_0001_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_30_WITH_DATASOURCE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE_NAME;
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

import org.apache.commons.lang.BooleanUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
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
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorResult;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.DataMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/task-mockito.xml",
        "classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetServiceTest extends StatisticalResourcesBaseTest implements DatasetServiceTestBase {

    @Autowired
    private DatasetService                          datasetService;

    @Autowired
    private DatasetVersionMockFactory               datasetVersionMockFactory;

    @Autowired
    private DatasetMockFactory                      datasetMockFactory;

    @Autowired
    private QueryVersionMockFactory                 queryMockFactory;

    @Autowired
    private DatasetRepositoriesServiceFacade        datasetRepositoriesServiceFacade;

    @Autowired
    private SrmRestInternalService                  srmRestInternalService;

    private StatisticalResourcesNotPersistedDoMocks persistedMocks = StatisticalResourcesNotPersistedDoMocks.getInstance();

    @Autowired
    private TaskService                             taskService;

    @Before
    public void setUp() throws MetamacException {
        Mockito.reset(datasetRepositoriesServiceFacade);
        Mockito.reset(srmRestInternalService);
        mockAllTaskInProgressForResource(false);
    }

    @After
    public void after() {
        Mockito.validateMockitoUsage();
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
        DatasetVersion expected = persistedMocks.mockDatasetVersion();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        String operationCode = actual.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEquals(operationCode + "_000001", actual.getSiemacMetadataStatisticalResource().getCode());
        assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested(), operationCode, 1, "001.000"), actual.getSiemacMetadataStatisticalResource()
                .getUrn());
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
        DatasetVersion expected = persistedMocks.mockDatasetVersion();

        DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEquals(operationCode + "_000004", actual.getSiemacMetadataStatisticalResource().getCode());
        assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested(), operationCode, 4, "001.000"), actual.getSiemacMetadataStatisticalResource()
                .getUrn());

        assertEqualsDatasetVersionNotChecksDataset(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME)
    public void testCreateDatasetVersionSameOperationExistingDatasetConsecutiveCode() throws Exception {
        DatasetVersion datasetVersionOper1 = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        String operationCode = datasetVersionOper1.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        {
            ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
            DatasetVersion expected = persistedMocks.mockDatasetVersion();
            DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
            assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
            assertEquals(operationCode + "_000004", actual.getSiemacMetadataStatisticalResource().getCode());
            assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested(), operationCode, 4, "001.000"), actual.getSiemacMetadataStatisticalResource()
                    .getUrn());
            assertEqualsDatasetVersionNotChecksDataset(expected, actual);
        }
        {
            ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
            DatasetVersion expected = persistedMocks.mockDatasetVersion();
            DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
            assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
            assertEquals(operationCode + "_000005", actual.getSiemacMetadataStatisticalResource().getCode());
            assertEquals(buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested(), operationCode, 5, "001.000"), actual.getSiemacMetadataStatisticalResource()
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
        datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), persistedMocks.mockDatasetVersion(), statisticalOperation);
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

        DatasetVersion expected = persistedMocks.mockDatasetVersion();
        datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, null);
    }

    @Test
    public void testCreateDatasetVersionErrorMetadataSiemacStatisticalResourceRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE));

        DatasetVersion expected = persistedMocks.mockDatasetVersionWithNullableSiemacStatisticalResource();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
    }

    @Override
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testUpdateDatasetVersion() throws Exception {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        expected.getSiemacMetadataStatisticalResource().setTitle(persistedMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(persistedMocks.mockInternationalString());

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

        mockTaskInProgressForResource(datasetVersionUrn, true);

        expected.getSiemacMetadataStatisticalResource().setTitle(persistedMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(persistedMocks.mockInternationalString());

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn));

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
        dataset.setRelatedDsdChanged(true);

        DateTime oldLastUpdate = dataset.getSiemacMetadataStatisticalResource().getLastUpdate();

        DatasetVersion updatedDataset = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);

        assertEquals(newDsd.getUrn(), updatedDataset.getRelatedDsd().getUrn());
        assertEquals(0, updatedDataset.getDatasources().size());
        assertEquals(0, updatedDataset.getDimensionsCoverage().size());
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
    @MetamacMock({DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_NAME})
    public void testUpdateDatasetVersionNoChangeDsdClearDataRelatedMetadata() throws Exception {
        DatasetVersion dataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_NAME);

        ExternalItem oldDsd = dataset.getRelatedDsd();
        dataset.setRelatedDsdChanged(false);

        DateTime oldLastUpdate = dataset.getSiemacMetadataStatisticalResource().getLastUpdate();

        DatasetVersion updatedDataset = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);

        assertEquals(oldDsd.getUrn(), updatedDataset.getRelatedDsd().getUrn());
        assertEquals(1, updatedDataset.getDatasources().size());
        assertEquals(10, updatedDataset.getDimensionsCoverage().size());
        assertEquals(4, updatedDataset.getGeographicCoverage().size());
        assertEquals(3, updatedDataset.getTemporalCoverage().size());
        assertEquals(3, updatedDataset.getMeasureCoverage().size());
        assertNotNull(updatedDataset.getDateStart());
        assertNotNull(updatedDataset.getDateEnd());
        assertNotNull(updatedDataset.getSiemacMetadataStatisticalResource().getLastUpdate());
        assertFalse(updatedDataset.getSiemacMetadataStatisticalResource().getLastUpdate().isAfter(oldLastUpdate));
        assertEquals(Integer.valueOf(3), updatedDataset.getFormatExtentDimensions());
        assertEquals(Long.valueOf(36L), updatedDataset.getFormatExtentObservations());
        assertTrue(BooleanUtils.isNotTrue(updatedDataset.getUserModifiedDateNextUpdate()));
        assertNotNull(updatedDataset.getDateNextUpdate());
        assertNotNull(updatedDataset.getDatasetRepositoryId());
    }

    @Test
    @MetamacMock({DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE_NAME})
    public void testUpdateDatasetVersionChangeDsdClearDataRelatedMetadataKeepUserDateNextUpdate() throws Exception {
        DatasetVersion dataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE_NAME);

        ExternalItem newDsd = StatisticalResourcesDoMocks.mockDsdExternalItem();
        dataset.setRelatedDsd(newDsd);
        dataset.setRelatedDsdChanged(true);

        DateTime oldLastUpdate = dataset.getSiemacMetadataStatisticalResource().getLastUpdate();
        DateTime oldNextDateUpdate = dataset.getDateNextUpdate();

        DatasetVersion updatedDataset = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);

        assertEquals(newDsd.getUrn(), updatedDataset.getRelatedDsd().getUrn());
        assertEquals(0, updatedDataset.getDatasources().size());
        assertEquals(0, updatedDataset.getDimensionsCoverage().size());
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
        mockTaskInProgressForResource(urn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, urn));

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
        mockTaskInProgressForResource(urn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, urn));

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

        mockTaskInProgressForResource(datasetVersion37.getSiemacMetadataStatisticalResource().getUrn(), true);

        MetamacException expectedException = new MetamacException(ServiceExceptionType.IMPORTATION_DATASET_VERSION_ERROR, datasetVersion37.getSiemacMetadataStatisticalResource().getUrn());
        expectedException.getExceptionItems().get(0)
                .setExceptionItems(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersion37.getSiemacMetadataStatisticalResource().getUrn())));

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

        mockDsdAndDataRepositorySimpleDimensions();

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

        mockDsdAndDataRepositorySimpleDimensions();

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
        mockTaskInProgressForResource(datasetVersionUrn, true);
        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn));
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
        mockTaskInProgressForResource(datasetVersionUrn, true);
        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn));
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

        mockTaskInProgressForResource(datasetVersionUrn, true);
        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn));

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

    // ------------------------------------------------------------------------
    // DATASOURCES are tested in DatasetServiceDatasourceManagementTest.java
    // ------------------------------------------------------------------------

    @Override
    public void testCreateDatasource() throws Exception {
        // In DatasetServiceDatasourceManagementTest.java
    }

    @Override
    public void testUpdateDatasource() throws Exception {
        // In DatasetServiceDatasourceManagementTest.java
    }

    @Override
    public void testRetrieveDatasourceByUrn() throws Exception {
        // In DatasetServiceDatasourceManagementTest.java
    }

    @Override
    public void testDeleteDatasource() throws Exception {
        // In DatasetServiceDatasourceManagementTest.java
    }

    @Override
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        // In DatasetServiceDatasourceManagementTest.java
    }

    // ------------------------------------------------------------------------
    // ATTRIBUTES
    // ------------------------------------------------------------------------

    @Override
    public void testCreateAttributeInstance() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testRetrieveAttributeInstances() throws Exception {
        // TODO Auto-generated method stub

    }

    // ------------------------------------------------------------------------
    // PRIVATE UTILS
    // ------------------------------------------------------------------------

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

    private void mockDsdAndDataRepositorySimpleDimensions() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions(datasetRepositoriesServiceFacade, srmRestInternalService);
    }

    private void mockTaskInProgressForResource(String datasetVersionUrn, boolean status) throws MetamacException {
        TaskMockUtils.mockTaskInProgressForDatasetVersion(taskService, datasetVersionUrn, status);
    }

    private void mockAllTaskInProgressForResource(boolean status) throws MetamacException {
        TaskMockUtils.mockAllTaskInProgressForDatasetVersion(taskService, status);
    }
}
