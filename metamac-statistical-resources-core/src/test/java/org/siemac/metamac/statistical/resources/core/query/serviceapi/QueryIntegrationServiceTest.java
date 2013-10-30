package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;
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
public class QueryIntegrationServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private QueryService queryService;

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateAndUpdateQueryMustHaveFilledStatisticalOperation() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        QueryVersion queryBeforeCreate = notPersistedDoMocks.mockQueryVersionWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME), true);
        assertNull(queryBeforeCreate.getQuery());

        QueryVersion queryAfterCreate = queryService.createQueryVersion(getServiceContextWithoutPrincipal(), queryBeforeCreate, statisticalOperation);
        assertNotNull(queryAfterCreate.getQuery().getIdentifiableStatisticalResource().getStatisticalOperation());

        QueryVersion queryAfterUpdate = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), queryAfterCreate);
        assertNotNull(queryAfterUpdate.getQuery().getIdentifiableStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(queryAfterCreate.getQuery().getIdentifiableStatisticalResource().getStatisticalOperation(), queryAfterUpdate.getQuery()
                .getIdentifiableStatisticalResource().getStatisticalOperation());
    }

    // ------------------------------------------------------------------------
    // QUERIES VERSIONS
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateAndUpdateQueryVersionMustHaveFilledStatisticalOperation() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        QueryVersion queryVersionBeforeCreate = notPersistedDoMocks.mockQueryVersionWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME), true);
        assertNull(queryVersionBeforeCreate.getLifeCycleStatisticalResource().getStatisticalOperation());

        QueryVersion queryVersionAfterCreate = queryService.createQueryVersion(getServiceContextWithoutPrincipal(), queryVersionBeforeCreate, statisticalOperation);
        assertNotNull(queryVersionAfterCreate.getLifeCycleStatisticalResource().getStatisticalOperation());

        QueryVersion queryVersionAfterUpdate = queryService.updateQueryVersion(getServiceContextWithoutPrincipal(), queryVersionAfterCreate);
        assertNotNull(queryVersionAfterUpdate.getLifeCycleStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(queryVersionAfterCreate.getLifeCycleStatisticalResource().getStatisticalOperation(), queryVersionAfterUpdate.getLifeCycleStatisticalResource()
                .getStatisticalOperation());
    }
}
