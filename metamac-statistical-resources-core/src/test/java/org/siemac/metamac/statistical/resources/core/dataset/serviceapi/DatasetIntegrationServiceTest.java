package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetIntegrationServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetService                          datasetService;

    @Autowired
    private DatasetVersionMockFactory               datasetVersionMockFactory;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testCreateAndUpdateDatasourceMustHaveFilledStatisticalOperation() throws Exception {
        Datasource datasourceBeforeCreate = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        assertNull(datasourceBeforeCreate.getIdentifiableStatisticalResource().getStatisticalOperation());

        Datasource datasourceAfterCreate = datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME)
                .getSiemacMetadataStatisticalResource().getUrn(), datasourceBeforeCreate);
        assertNotNull(datasourceAfterCreate.getIdentifiableStatisticalResource().getStatisticalOperation());

        Datasource datasourceAfterUpdate = datasetService.updateDatasource(getServiceContextWithoutPrincipal(), datasourceAfterCreate);
        assertNotNull(datasourceAfterUpdate.getIdentifiableStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(datasourceAfterCreate.getIdentifiableStatisticalResource().getStatisticalOperation(), datasourceAfterUpdate.getIdentifiableStatisticalResource()
                .getStatisticalOperation());

    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    @Test
    public void testCreateAndUpdateDatasetMustHaveFilledStatisticalOperation() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        
        DatasetVersion datasetBeforeCreate = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        assertNull(datasetBeforeCreate.getDataset());

        DatasetVersion datasetAfterCreate = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), datasetBeforeCreate, statisticalOperation);
        assertNotNull(datasetAfterCreate.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation());
        
        DatasetVersion datasetAfterUpdate = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), datasetAfterCreate);
        assertNotNull(datasetAfterUpdate.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation());
        
        CommonAsserts.assertEqualsExternalItem(datasetAfterCreate.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation(), datasetAfterUpdate.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation());
    }
    
    
    
    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------
    
    @Test
    public void testCreateAndUpdateDatasetVersionMustHaveFilledStatisticalOperation() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        
        DatasetVersion datasetBeforeCreate = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        assertNull(datasetBeforeCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation());

        DatasetVersion datasetAfterCreate = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), datasetBeforeCreate, statisticalOperation);
        assertNotNull(datasetAfterCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation());
        
        DatasetVersion datasetAfterUpdate = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), datasetAfterCreate);
        assertNotNull(datasetAfterUpdate.getSiemacMetadataStatisticalResource().getStatisticalOperation());
        
        CommonAsserts.assertEqualsExternalItem(datasetAfterCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation(), datasetAfterUpdate.getSiemacMetadataStatisticalResource().getStatisticalOperation());
    }

}
