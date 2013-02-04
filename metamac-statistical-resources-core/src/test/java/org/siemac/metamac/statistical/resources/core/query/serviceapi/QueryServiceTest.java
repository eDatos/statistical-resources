package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion06ForQueries;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_04_BASIC_ORDERED_03_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_05_WITH_DATASET_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery01Basic;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery02BasicOrdered01;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery03BasicOrdered02;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery04BasicOrdered03;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.getQuery05WithDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryCollection;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesNotPersistedDoMocks;
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
    protected QueryService                            queryService;

    @Autowired
    protected DatasetService                          datasetService;

    @Autowired
    protected QueryRepository                         queryRepository;

    @Autowired
    protected QueryMockFactory                        queryMockFactory;

    @Autowired
    protected StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Override
    @Test
    @MetamacMock(QUERY_01_BASIC_NAME)
    public void testRetrieveQueryByUrn() throws MetamacException {
        Query actual = queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), getQuery01Basic().getLifeCycleStatisticalResource().getUrn());
        assertEqualsQuery(getQuery01Basic(), actual);
    }

    @Test
    public void testRetrieveQueryByUrnParameterRequired() throws MetamacException {
        try {
            queryService.retrieveQueryByUrn(getServiceContextWithoutPrincipal(), EMPTY);
            fail("parameter required");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionSingleParameters.URN}, e.getExceptionItems().get(0));
        }
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
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getLifeCycleStatisticalResource().getUrn());
            assertEquals(getQuery03BasicOrdered02().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getLifeCycleStatisticalResource().getUrn());
            assertEquals(getQuery04BasicOrdered03().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getLifeCycleStatisticalResource().getUrn());
        }

        // Find code
        {
            String code = getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getCode();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.lifeCycleStatisticalResource().code()).eq(code).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getLifeCycleStatisticalResource().getUrn());
        }

        // Find URN
        {
            String urn = getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn();
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.lifeCycleStatisticalResource().urn()).eq(urn).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getLifeCycleStatisticalResource().getUrn());
        }

        // Find title
        {
            String titleQuery = getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getTitle().getLocalisedLabel("es");
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).withProperty(QueryProperties.lifeCycleStatisticalResource().title().texts().label())
                    .eq(titleQuery).build();
            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<Query> queriesPagedResult = queryService.findQueriesByCondition(getServiceContextAdministrador(), conditions, pagingParameter);

            // Validate
            assertEquals(1, queriesPagedResult.getTotalRows());
            int i = 0;
            assertEquals(getQuery02BasicOrdered01().getLifeCycleStatisticalResource().getUrn(), queriesPagedResult.getValues().get(i++).getLifeCycleStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    public void testCreateQuery() throws Exception {
        Query expected = statisticalResourcesNotPersistedDoMocks.mockQuery();
        Query actual = queryService.createQuery(getServiceContextWithoutPrincipal(), expected);
        assertEqualsQuery(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryWithDatasetVersion() throws Exception {
        Query expected = statisticalResourcesNotPersistedDoMocks.mockQueryWithDatasetVersion(getDatasetVersion06ForQueries());
        Query actual = queryService.createQuery(getServiceContextWithoutPrincipal(), expected);
        assertEqualsQuery(expected, actual);
    }

    @Test
    public void testCreateQueryErrorNameableResourceRequired() throws Exception {
        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithStatisticalResourceNull();
        try {
            queryService.createQuery(getServiceContextWithoutPrincipal(), query);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            BaseAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, 1, new String[]{ServiceExceptionParameters.QUERY__LIFE_CYCLE_STATISTICAL_RESOURCE}, e
                    .getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock(QUERY_01_BASIC_NAME)
    public void testUpdateQuery() throws Exception {
        Query query = getQuery01Basic();
        query.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesDoMocks.mockInternationalString());

        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        assertEqualsQuery(query, updatedQuery);
    }

    @Test
    @MetamacMock({QUERY_05_WITH_DATASET_VERSION_NAME, DATASET_VERSION_06_FOR_QUERIES_NAME})
    public void testUpdateDatasetVersionQuery() throws Exception {
        int datasetVersionsBefore = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), null, null).getValues().size();
        
        Query query = getQuery05WithDatasetVersion();
        query.setDatasetVersion(getDatasetVersion06ForQueries());

        Query updatedQuery = queryService.updateQuery(getServiceContextWithoutPrincipal(), query);
        assertEqualsQuery(query, updatedQuery);
        
        int datasetVersionsAfter = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), null, null).getValues().size();
        assertEquals(datasetVersionsBefore, datasetVersionsAfter);
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME})
    public void testRetrieveQueries() throws Exception {
        List<Query> expected = queryMockFactory.getMocks(QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_04_BASIC_ORDERED_03_NAME);

        List<Query> actual = queryService.retrieveQueries(getServiceContextWithoutPrincipal());

        assertEqualsQueryCollection(expected, actual);
    }
}
