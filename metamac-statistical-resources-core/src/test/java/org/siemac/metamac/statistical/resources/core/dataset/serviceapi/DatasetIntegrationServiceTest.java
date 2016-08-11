package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;

import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.utils.DataMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/rest-services-mockito.xml",
        "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetIntegrationServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetService                   datasetService;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Autowired
    private SrmRestInternalService           srmRestInternalService;

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testCreateAndUpdateDatasourceMustHaveFilledStatisticalOperation() throws Exception {
        mockDsdAndDataRepositorySimpleDimensions();

        Datasource datasourceBeforeCreate = notPersistedDoMocks.mockDatasourceForPersist();
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
        DatasetVersion datasetBeforeCreate = notPersistedDoMocks.mockDatasetVersion();
        assertNull(datasetBeforeCreate.getDataset());

        mockDsdAndCreateDatasetRepository(datasetBeforeCreate, statisticalOperation);

        DatasetVersion datasetAfterCreate = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), datasetBeforeCreate, statisticalOperation);
        assertNotNull(datasetAfterCreate.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation());

        DatasetVersion datasetAfterUpdate = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), datasetAfterCreate);
        assertNotNull(datasetAfterUpdate.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(datasetAfterCreate.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation(), datasetAfterUpdate.getDataset()
                .getIdentifiableStatisticalResource().getStatisticalOperation());
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    @Test
    public void testCreateAndUpdateDatasetVersionMustHaveFilledStatisticalOperation() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        DatasetVersion datasetBeforeCreate = notPersistedDoMocks.mockDatasetVersion();
        assertNull(datasetBeforeCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation());

        mockDsdAndCreateDatasetRepository(datasetBeforeCreate, statisticalOperation);

        DatasetVersion datasetAfterCreate = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), datasetBeforeCreate, statisticalOperation);
        assertNotNull(datasetAfterCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation());

        DatasetVersion datasetAfterUpdate = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), datasetAfterCreate);
        assertNotNull(datasetAfterUpdate.getSiemacMetadataStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(datasetAfterCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation(), datasetAfterUpdate.getSiemacMetadataStatisticalResource()
                .getStatisticalOperation());
    }

    private void mockDsdAndDataRepositorySimpleDimensions() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensionsNoAttributes(datasetRepositoriesServiceFacade, srmRestInternalService);
    }

    private void mockDsdAndCreateDatasetRepository(DatasetVersion expected, ExternalItem statisticalOperation) throws Exception, ApplicationException {
        String urn = buildDatasetUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested(), statisticalOperation.getCode(), 1, "001.000");
        DataMockUtils.mockDsdAndCreateDatasetRepository(datasetRepositoriesServiceFacade, srmRestInternalService, urn);
    }

}
