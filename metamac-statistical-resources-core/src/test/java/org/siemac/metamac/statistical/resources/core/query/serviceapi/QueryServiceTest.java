package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersionCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_04_BASIC_ORDERED_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_05_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_06_BASIC_ACTIVE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_08_BASIC_DISCONTINUED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_11_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_14_VALIDATION_REJECTED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_15_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_21_FOR_QUERY_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItemRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class QueryServiceTest extends StatisticalResourcesBaseTest implements QueryServiceTestBase {

    @Autowired
    private QueryService                            queryService;

    @Autowired
    private DatasetService                          datasetService;

    @Autowired
    private QueryVersionMockFactory                 queryVersionMockFactory;

    @Autowired
    private QueryMockFactory                        queryMockFactory;

    @Autowired
    private DatasetVersionMockFactory               datasetVersionMockFactory;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Autowired
    private StatisticalResourceRepository           statisticalResourceRepository;

    @Autowired
    private DatasetVersionRepository                datasetVersionRepository;

    @Autowired
    private QuerySelectionItemRepository            querySelectionItemRepository;

    @Autowired
    private QueryRepository                         queryRepository;

    @Override
    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testRetrieveQueryVersionByUrn() throws MetamacException {
        QueryVersion actual = queryService.retrieveQueryVersionByUrn(getServiceContextWithoutPrincipal(), (queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME))
                .getLifeCycleStatisticalResource().getUrn());
        assertEqualsQueryVersion(queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME), actual);
    }

    @Test
    public void testRetrieveQueryVersionByUrnParameterRequired() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionSingleParameters.URN));
        queryService.retrieveQueryVersionByUrn(getServiceContextWithoutPrincipal(), EMPTY);
    }

    @Override
    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestQueryVersionByQueryUrn() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion actual = queryService.retrieveLatestQueryVersionByQueryUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsQueryVersion(queryVersionMockFactory.retrieveMock(QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME), actual);
    }

    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestQueryVersionByQueryUrnErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.QUERY_URN));
        queryService.retrieveLatestQueryVersionByQueryUrn(getServiceContextWithoutPrincipal(), null);
    }

    @Override
    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestPublishedQueryVersionByQueryUrn() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion actual = queryService.retrieveLatestPublishedQueryVersionByQueryUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsQueryVersion(queryVersionMockFactory.retrieveMock(QUERY_VERSION_21_FOR_QUERY_03_NAME), actual);
    }

    @Test
    @MetamacMock({QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLatestPublishedQueryVersionByQueryUrnErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.QUERY_URN));
        queryService.retrieveLatestPublishedQueryVersionByQueryUrn(getServiceContextWithoutPrincipal(), null);
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testFindQueryVersionsByCondition() throws Exception {
        // Find all
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).orderBy(QueryVersionProperties.lifeCycleStatisticalResource().code()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<QueryVersion> queriesPagedResult = queryService.findQueryVersionsByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(3, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
            assertEquals((queryVersionMockFactory.retrieveMock(QUERY_VERSION_03_BASIC_ORDERED_02_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
            assertEquals((queryVersionMockFactory.retrieveMock(QUERY_VERSION_04_BASIC_ORDERED_03_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }

        // Find code
        {
            String code = (queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getCode();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.lifeCycleStatisticalResource().code()).eq(code)
                    .build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<QueryVersion> queriesPagedResult = queryService.findQueryVersionsByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }

        // Find URN
        {
            String urn = (queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.lifeCycleStatisticalResource().urn()).eq(urn).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<QueryVersion> queriesPagedResult = queryService.findQueryVersionsByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }

        // Find title
        {
            String titleQueryVersion = (queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class)
                    .withProperty(QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label()).eq(titleQueryVersion).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<QueryVersion> queriesPagedResult = queryService.findQueryVersionsByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryVersionMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryVersion() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        QueryVersion expected = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME), true);
        QueryVersion actual = queryService.createQueryVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED_NAME)
    public void testCreateQueryVersionIncremental() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        QueryVersion expected = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED_NAME), true);
        expected.setType(QueryTypeEnum.AUTOINCREMENTAL);
        expected.setLatestTemporalCodeInCreation("2012");
        QueryVersion actual = queryService.createQueryVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME})
    public void testCreateQueryVersionDiscontinued() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        QueryVersion expected = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME), false);
        QueryVersion actual = queryService.createQueryVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEqualsQueryVersion(expected, actual);
        assertEquals(QueryStatusEnum.DISCONTINUED, actual.getStatus());
    }

    @Test
    @MetamacMock({DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME})
    public void testCreateQueryVersionActive() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        QueryVersion expected = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME), true);
        QueryVersion actual = queryService.createQueryVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEqualsQueryVersion(expected, actual);
        assertEquals(QueryStatusEnum.ACTIVE, actual.getStatus());
    }

    @Test
    @MetamacMock({DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME})
    public void testCreateQueryVersionErrorParameterStatisticalOperationRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.STATISTICAL_OPERATION));

        QueryVersion expected = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(
                datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME), true);
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), expected, null);
    }

    @Test
    @MetamacMock({DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME})
    public void testCreateQueryVersionErrorParameterQueryVersionRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.QUERY_VERSION));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), null, statisticalOperation);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryWithSelection() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        QueryVersion expected = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME), true);
        QueryVersion actual = queryService.createQueryVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    public void testCreateQueryVersionErrorDatasetVersionRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__DATASET_VERSION));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        QueryVersion query = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(null, true);
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), query, statisticalOperation);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryVersionErrorSelectionRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__SELECTION));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        QueryVersion query = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithSelectionNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), query, statisticalOperation);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryVersionErrorNameableResourceRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__LIFE_CYCLE_STATISTICAL_RESOURCE));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        QueryVersion query = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithStatisticalResourceNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), query, statisticalOperation);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryVersionWithSelectionNull() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__SELECTION));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        QueryVersion expected = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithSelectionNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryVersionErrorSelectionIncorrectDimensionNull() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY_VERSION__SELECTION));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        QueryVersion query = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithSelectionIncorrectDimensionNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), query, statisticalOperation);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryVersionErrorTimeSelectionMustBeEmptyWhenTypeIsLatestData() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_UNEXPECTED, ServiceExceptionParameters.QUERY_VERSION__SELECTION__TIME_PERIOD));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        QueryVersion query = statisticalResourcesNotPersistedDoMocks.mockQueryVersionLatestDataWithTimeSelectionNotEmpty(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), query, statisticalOperation);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryVersionErrorSelectionIncorrectCodesNull() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY_VERSION__SELECTION));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        QueryVersion query = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithSelectionIncorrectCodesNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQueryVersion(getServiceContextWithoutPrincipal(), query, statisticalOperation);
    }

    @Test
    @MetamacMock({DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME,
            QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testUpdateDatasetVersionQueryVersionChangesQueryStatusFromDiscontinuedToActive() throws Exception {
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_08_BASIC_DISCONTINUED_NAME);
        expected.setDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME));

        QueryVersion actual = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), expected);
        assertEquals(QueryStatusEnum.ACTIVE, actual.getStatus());
    }

    @Test
    @MetamacMock({DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME,
            QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testUpdateDatasetVersionQueryVersionChangesQueryStatusFromPendingReviewToActive() throws Exception {
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME);
        expected.setDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME));

        QueryVersion actual = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), expected);
        assertEquals(QueryStatusEnum.ACTIVE, actual.getStatus());
    }

    @Test
    @MetamacMock({DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME,
            QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME})
    public void testUpdateDatasetVersionQueryChangesQueryStatusFromActiveToDiscontinued() throws Exception {
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_06_BASIC_ACTIVE_NAME);
        expected.setDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME));

        QueryVersion actual = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), expected);
        assertEquals(QueryStatusEnum.DISCONTINUED, actual.getStatus());
    }

    @Override
    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testUpdateQueryVersion() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME);
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);
    }

    @Test
    @MetamacMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryVersionLatestData() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setLatestDataNumber(Integer.valueOf(13));

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);
    }

    @Test
    @MetamacMock({QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME, QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME})
    public void testUpdateQueryVersionErrorDuplicatedUrn() throws Exception {
        String duplicatedCode = queryVersionMockFactory.retrieveMock(QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME).getLifeCycleStatisticalResource().getCode();
        String duplicatedUrn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.IDENTIFIABLE_STATISTICAL_RESOURCE_URN_DUPLICATED, duplicatedUrn));

        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME);
        query.getLifeCycleStatisticalResource().setCode(duplicatedCode);
        queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock({QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME, QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME})
    public void testUpdateQueryVersionChangeCodeUpdateUrn() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME);
        String newCode = "newCode";

        queryVersion.getLifeCycleStatisticalResource().setCode(newCode);
        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), queryVersion);

        assertEquals(newCode, updatedQueryVersion.getLifeCycleStatisticalResource().getCode());

        String[] maintainerCodes = new String[]{updatedQueryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        assertEquals(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerCodes, newCode, "001.000"), updatedQueryVersion.getLifeCycleStatisticalResource().getUrn());
    }

    @Test
    @MetamacMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryVersionErrorLatestDataNumberNull() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_UNEXPECTED, ServiceExceptionParameters.QUERY_VERSION__LATEST_DATA_NUMBER));

        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setType(QueryTypeEnum.FIXED);
        queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryVersionErrorLatestDataNumberRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__LATEST_DATA_NUMBER));

        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setLatestDataNumber(null);
        queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryVersionErrorLatestDataNumberIncorrectValue() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY_VERSION__LATEST_DATA_NUMBER));

        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setLatestDataNumber(Integer.valueOf(0));
        queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testUpdateQueryVersionErrorStatusRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__STATUS));

        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME);
        query.setStatus(null);
        queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testUpdateQueryVersionErrorDatasetVersionRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__DATASET_VERSION));

        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME);
        query.setDatasetVersion(null);
        queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testUpdateQueryVersionDraftProcStatus() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME);
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testUpdateQueryVersionProductionValidationProcStatus() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME);
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testUpdateQueryVersionDiffusionValidationProcStatus() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME);
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testUpdateQueryValidationRejectedProcStatus() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_14_VALIDATION_REJECTED_NAME);
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testUpdateQueryVersionPublishedProcStatus() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_15_PUBLISHED_NAME);
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);
    }

    @Test
    @MetamacMock({QUERY_VERSION_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME})
    public void testUpdateDatasetVersionQueryVersion() throws Exception {
        int datasetVersionsBefore = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), null, null).getValues().size();

        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME);
        query.setDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));

        QueryVersion updatedQueryVersion = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), query);
        assertEqualsQueryVersion(query, updatedQueryVersion);

        int datasetVersionsAfter = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), null, null).getValues().size();
        assertEquals(datasetVersionsBefore, datasetVersionsAfter);
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueryVersions() throws Exception {
        List<QueryVersion> expected = queryVersionMockFactory.retrieveMocks(QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_04_BASIC_ORDERED_03_NAME);

        List<QueryVersion> actual = queryService.retrieveQueryVersions(getServiceContextWithoutPrincipal());

        assertEqualsQueryVersionCollection(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME})
    public void testMarkQueryVersionAsDiscontinued() throws Exception {
        // Get entity
        QueryVersion queryMock = queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME);

        // Modify entity
        QueryVersion queryModified = queryMock;
        queryModified.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());
        QueryVersion queryAfterUpdate = queryService.markQueryVersionAsDiscontinued(getServiceContextWithoutPrincipal(), queryModified);

        // Checks
        QueryAsserts.assertEqualsInternationalString(queryMock.getLifeCycleStatisticalResource().getTitle(), queryModified.getLifeCycleStatisticalResource().getTitle()); // title has to be the
                                                                                                                                                                          // original
        assertEquals(queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn(), queryAfterUpdate.getLifeCycleStatisticalResource()
                .getUrn());
        assertEquals(QueryStatusEnum.DISCONTINUED, queryAfterUpdate.getStatus());
    }

    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME})
    public void testMarkQueryVersionAsDiscontinuedErrorUnexpectedStatusActive() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_06_BASIC_ACTIVE_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, QueryStatusEnum.PENDING_REVIEW.getName()));

        queryService.markQueryVersionAsDiscontinued(getServiceContextWithoutPrincipal(), queryVersionMockFactory.retrieveMock(QUERY_VERSION_06_BASIC_ACTIVE_NAME));
    }

    @Test
    @MetamacMock({QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME})
    public void testMarkQueryVersionAsDiscontinuedErrorUnexpectedStatusDiscontinued() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_08_BASIC_DISCONTINUED_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, QueryStatusEnum.PENDING_REVIEW.getName()));

        queryService.markQueryVersionAsDiscontinued(getServiceContextWithoutPrincipal(), queryVersionMockFactory.retrieveMock(QUERY_VERSION_08_BASIC_DISCONTINUED_NAME));
    }

    @Override
    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryVersion() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME);
        String urn = queryVersion.getLifeCycleStatisticalResource().getUrn();
        
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn));

        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), urn);
        queryService.retrieveQueryVersionByUrn(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryVersionDeletingQueryRoot() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME);
        String urn = queryVersion.getLifeCycleStatisticalResource().getUrn();
        String queryUrn = queryVersion.getQuery().getIdentifiableStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, queryUrn));

        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), urn);
        queryRepository.retrieveByUrn(queryUrn);
    }

    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryVersionErrorUrnRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionSingleParameters.URN));
        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), null);
    }

    @Test
    @MetamacMock({QUERY_VERSION_01_WITH_SELECTION_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryVersionErrorNotExists() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, URN_NOT_EXISTS));
        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_06_BASIC_ACTIVE_NAME, QUERY_VERSION_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryVersionAndCheckDependencies() throws Exception {
        QueryVersion query = queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME);
        String urn = query.getLifeCycleStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn));

        // Initial situation
        int lifeCycleStatisticalResourcesBefore = statisticalResourceRepository.findAll().size();
        int datasetVersionsBefore = datasetVersionRepository.findAll().size();
        int querySelectionItemsBefore = querySelectionItemRepository.findAll().size();

        // Expected result
        int lifeCycleStatisticalResourcesExpected = lifeCycleStatisticalResourcesBefore - 2; // -2 query version and root query should not be present
        int datasetVersionsExpected = datasetVersionsBefore;
        int querySelectionItemsExpected = querySelectionItemsBefore - query.getSelection().size();

        // Delete
        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), urn);

        // Final situation
        int lifeCycleStatisticalResourcesAfter = statisticalResourceRepository.findAll().size();
        int datasetVersionsAfter = datasetVersionRepository.findAll().size();
        int querySelectionItemsAfter = querySelectionItemRepository.findAll().size();

        // Checks
        assertEquals(lifeCycleStatisticalResourcesExpected, lifeCycleStatisticalResourcesAfter);
        assertEquals(datasetVersionsExpected, datasetVersionsAfter);
        assertEquals(querySelectionItemsExpected, querySelectionItemsAfter);
        queryService.retrieveQueryVersionByUrn(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testDeleteQueryVersionErrorInvalidProcStatusProductionValidation() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, VALIDATION_REJECTED"));

        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testDeleteQueryVersionErrorInvalidProcStatusDifussionValidation() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, VALIDATION_REJECTED"));

        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testDeleteQueryVersionErrorInvalidProcStatusValidationRejected() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, VALIDATION_REJECTED"));

        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_VERSION_11_DRAFT_NAME, QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME, QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME, QUERY_VERSION_14_VALIDATION_REJECTED_NAME,
            QUERY_VERSION_15_PUBLISHED_NAME})
    public void testDeleteQueryVersionErrorInvalidProcStatusPublished() throws Exception {
        String urn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_15_PUBLISHED_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, VALIDATION_REJECTED"));

        queryService.deleteQueryVersion(getServiceContextWithoutPrincipal(), urn);
    }
}
