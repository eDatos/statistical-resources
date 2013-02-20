package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_04_BASIC_ORDERED_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_05_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_06_BASIC_ACTIVE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_08_BASIC_DISCONTINUED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_09_BASIC_PENDING_REVIEW_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.*;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItemRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
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
public class QueryServiceTest extends StatisticalResourcesBaseTest implements QueryServiceTestBase {

    @Autowired
    private QueryService                            queryService;

    @Autowired
    private DatasetService                          datasetService;

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

    @Override
    @Test
    @MetamacMock(QUERY_01_WITH_SELECTION_NAME)
    public void testRetrieveQueryByUrn() throws MetamacException {
        Query actual = queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), (queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME)).getLifeCycleStatisticalResource().getUrn());
        assertEqualsQuery(queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME), actual);
    }

    @Test
    public void testRetrieveQueryByUrnParameterRequired() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionSingleParameters.URN), 1);
        queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), EMPTY);
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
    public void testFindQueriesByCondition() throws Exception {
        // Find all
        {
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).orderBy(QueryProperties.lifeCycleStatisticalResource().code()).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(3, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
            assertEquals((queryMockFactory.retrieveMock(QUERY_03_BASIC_ORDERED_02_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
            assertEquals((queryMockFactory.retrieveMock(QUERY_04_BASIC_ORDERED_03_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }

        // Find code
        {
            String code = (queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getCode();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.lifeCycleStatisticalResource().code()).eq(code).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }

        // Find URN
        {
            String urn = (queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.lifeCycleStatisticalResource().urn()).eq(urn).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }

        // Find title
        {
            String titleQuery = (queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.lifeCycleStatisticalResource().title().texts().label())
                    .eq(titleQuery).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals((queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME)).getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++)
                    .getLifeCycleStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQuery() throws Exception {
        Query expected = statisticalResourcesNotPersistedDoMocks.mockQueryWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        Query actual = queryService.createQuery(getServiceContextWithoutPrincipal(), expected);
        assertEqualsQuery(expected, actual);
        // TODO METAMAC-1161: Pte. status al crear
        assertEquals(QueryStatusEnum.ACTIVE, actual.getStatus());
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryWithSelection() throws Exception {
        Query expected = statisticalResourcesNotPersistedDoMocks.mockQueryWithSelectionAndDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        Query actual = queryService.createQuery(getServiceContextWithoutPrincipal(), expected);
        assertEqualsQuery(expected, actual);
    }

    @Test
    public void testCreateQueryErrorDatasetVersionRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY__DATASET_VERSION), 1);

        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithDatasetVersion(null);
        queryService.createQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryErrorNameableResourceRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY__LIFE_CYCLE_STATISTICAL_RESOURCE), 1);

        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithStatisticalResourceNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQuery(getServiceContextWithoutPrincipal(), query);
    }

    // TODO METAMAC-1161: pte. status al crear. de momento lo cumplimenta el servicio.
    // @Test
    // public void testCreateQueryErrorStatusRequired() throws Exception {
    // // Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithStatusNull();
    // try {
    // queryService.createQuery(getServiceContextWithoutPrincipal(), query);
    // } catch (MetamacException e) {
    // assertEquals(1, e.getExceptionItems().size());
    // BaseAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, 1, new String[]{ServiceExceptionParameters.QUERY__STATUS}, e.getExceptionItems().get(0));
    // }
    // }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryWithSelectionNull() throws Exception {
        Query expected = statisticalResourcesNotPersistedDoMocks.mockQueryWithSelectionNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        Query actual = queryService.createQuery(getServiceContextWithoutPrincipal(), expected);

        assertEqualsQuery(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryErrorSelectionIncorrectDimensionNull() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY__SELECTION), 1);

        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithSelectionIncorrectDimensionNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryErrorSelectionIncorrectCodesNull() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY__SELECTION), 1);

        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithSelectionIncorrectCodesNull(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        queryService.createQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Override
    @Test
    @MetamacMock(QUERY_01_WITH_SELECTION_NAME)
    public void testUpdateQuery() throws Exception {
        Query query = queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME);
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        assertEqualsQuery(query, updatedQuery);
    }

    @Test
    @MetamacMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryLatestData() throws Exception {
        Query query = queryMockFactory.retrieveMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setLatestDataNumber(Integer.valueOf(13));

        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        assertEqualsQuery(query, updatedQuery);
    }

    @Test
    @MetamacMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryErrorLatestDataNumberNull() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_UNEXPECTED, ServiceExceptionParameters.QUERY__LATEST_DATA_NUMBER), 1);

        Query query = queryMockFactory.retrieveMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setType(QueryTypeEnum.FIXED);
        queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryErrorLatestDataNumberRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY__LATEST_DATA_NUMBER), 1);

        Query query = queryMockFactory.retrieveMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setLatestDataNumber(null);
        queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME)
    public void testUpdateQueryErrorLatestDataNumberIncorrectValue0() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY__LATEST_DATA_NUMBER), 1);

        Query query = queryMockFactory.retrieveMock(QUERY_10_ACTIVE_LATEST_DATA_5_NAME);
        query.setLatestDataNumber(Integer.valueOf(0));
        queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_01_WITH_SELECTION_NAME)
    public void testUpdateQueryErrorStatusRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY__STATUS), 1);

        Query query = queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME);
        query.setStatus(null);
        queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock(QUERY_01_WITH_SELECTION_NAME)
    public void testUpdateQueryErrorDatasetVersionRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY__DATASET_VERSION), 1);

        Query query = queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME);
        query.setDatasetVersion(null);
        queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
    }

    @Test
    @MetamacMock({QUERY_05_BASIC_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME})
    public void testUpdateDatasetVersionQuery() throws Exception {
        int datasetVersionsBefore = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), null, null).getValues().size();

        Query query = queryMockFactory.retrieveMock(QUERY_05_BASIC_NAME);
        query.setDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));

        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        assertEqualsQuery(query, updatedQuery);

        int datasetVersionsAfter = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), null, null).getValues().size();
        assertEquals(datasetVersionsBefore, datasetVersionsAfter);
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueries() throws Exception {
        List<Query> expected = queryMockFactory.retrieveMocks(QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME);

        List<Query> actual = queryService.retrieveQueries(getServiceContextWithoutPrincipal());

        assertEqualsQueryCollection(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_09_BASIC_PENDING_REVIEW_NAME, QUERY_02_BASIC_ORDERED_01_NAME})
    public void testMarkQueryAsDiscontinued() throws Exception {
        // Get entity
        Query queryMock = queryMockFactory.retrieveMock(QUERY_09_BASIC_PENDING_REVIEW_NAME);

        // Modify entity
        Query queryModified = queryMock;
        queryModified.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());
        Query queryAfterUpdate = queryService.markQueryAsDiscontinued(getServiceContextWithoutPrincipal(), queryModified);

        // Checks
        QueryAsserts.assertEqualsInternationalString(queryMock.getLifeCycleStatisticalResource().getTitle(), queryModified.getLifeCycleStatisticalResource().getTitle()); // title has to be the
                                                                                                                                                                          // original
        assertEquals(queryMockFactory.retrieveMock(QUERY_09_BASIC_PENDING_REVIEW_NAME).getLifeCycleStatisticalResource().getUrn(), queryAfterUpdate.getLifeCycleStatisticalResource().getUrn());
        assertEquals(QueryStatusEnum.DISCONTINUED, queryAfterUpdate.getStatus());
    }

    @Test
    @MetamacMock({QUERY_09_BASIC_PENDING_REVIEW_NAME, QUERY_06_BASIC_ACTIVE_NAME, QUERY_08_BASIC_DISCONTINUED_NAME})
    public void testMarkQueryAsDiscontinuedErrorUnexpectedStatusActive() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, QueryStatusEnum.PENDING_REVIEW), 1);
        queryService.markQueryAsDiscontinued(getServiceContextWithoutPrincipal(), queryMockFactory.retrieveMock(QUERY_06_BASIC_ACTIVE_NAME));
    }

    @Test
    @MetamacMock({QUERY_09_BASIC_PENDING_REVIEW_NAME, QUERY_06_BASIC_ACTIVE_NAME, QUERY_08_BASIC_DISCONTINUED_NAME})
    public void testMarkQueryAsDiscontinuedErrorUnexpectedStatusDiscontinued() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, QueryStatusEnum.PENDING_REVIEW), 1);
        queryService.markQueryAsDiscontinued(getServiceContextWithoutPrincipal(), queryMockFactory.retrieveMock(QUERY_08_BASIC_DISCONTINUED_NAME));
    }

    @Override
    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_06_BASIC_ACTIVE_NAME, QUERY_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQuery() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
        queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_06_BASIC_ACTIVE_NAME, QUERY_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryErrorUrnRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionSingleParameters.URN), 1);
        queryService.deleteQuery(getServiceContextWithoutPrincipal(), null);
    }

    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_06_BASIC_ACTIVE_NAME, QUERY_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryErrorNotExists() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, URN_NOT_EXISTS), 1);
        queryService.deleteQuery(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_06_BASIC_ACTIVE_NAME, QUERY_08_BASIC_DISCONTINUED_NAME})
    public void testDeleteQueryAndCheckDependencies() throws Exception {
        Query query = queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME);
        String urn = query.getLifeCycleStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn), 1);

        // Initial situation
        int lifeCycleStatisticalResourcesBefore = statisticalResourceRepository.findAll().size();
        int datasetVersionsBefore = datasetVersionRepository.findAll().size();
        int querySelectionItemsBefore = querySelectionItemRepository.findAll().size();

        // Expected result
        int lifeCycleStatisticalResourcesExpected = lifeCycleStatisticalResourcesBefore - 1;
        int datasetVersionsExpected = datasetVersionsBefore;
        int querySelectionItemsExpected = querySelectionItemsBefore - query.getSelection().size();

        // Delete
        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);

        // Final situation
        int lifeCycleStatisticalResourcesAfter = statisticalResourceRepository.findAll().size();
        int datasetVersionsAfter = datasetVersionRepository.findAll().size();
        int querySelectionItemsAfter = querySelectionItemRepository.findAll().size();

        // Checks
        assertEquals(lifeCycleStatisticalResourcesExpected, lifeCycleStatisticalResourcesAfter);
        assertEquals(datasetVersionsExpected, datasetVersionsAfter);
        assertEquals(querySelectionItemsExpected, querySelectionItemsAfter);
        queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusProductionValidation() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_12_PRODUCTION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusDifussionValidation() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_13_DIFUSSION_VALIDATION_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusValidationRejected() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_14_VALIDATION_REJECTED_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusPublicationProgrammed() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_15_PUBLICATION_PROGRAMMED_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusPublicationPending() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_16_PUBLICATION_PENDING_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusPublicationFailed() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_17_PUBLICATION_FAILED_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusPublished() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_18_PUBLISHED_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({QUERY_11_DRAFT_NAME, QUERY_12_PRODUCTION_VALIDATION_NAME, QUERY_13_DIFUSSION_VALIDATION_NAME, QUERY_14_VALIDATION_REJECTED_NAME, QUERY_15_PUBLICATION_PROGRAMMED_NAME,
            QUERY_16_PUBLICATION_PENDING_NAME, QUERY_17_PUBLICATION_FAILED_NAME, QUERY_18_PUBLISHED_NAME, QUERY_19_ARCHIVED_NAME})
    public void testDeleteQueryErrorInvalidProcStatusArchived() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_19_ARCHIVED_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_INVALID_STATUS, StatisticalResourceProcStatusEnum.DRAFT), 1);

        queryService.deleteQuery(getServiceContextWithoutPrincipal(), urn);
    }
}
