package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml", "classpath:spring/statistical-resources/include/siemac-lifecycle-mockito.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetLifecycleServiceTest extends StatisticalResourcesBaseTest implements DatasetLifecycleServiceTestBase {

    @Autowired
    protected DatasetLifecycleService datasetLifecycleService;
    
    @Autowired
    protected DatasetVersionMockFactory datasetVersionMockFactory;
    
    @Autowired
    protected DatasetService datasetService;
    
    @Override
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendToProductionValidation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendToProductionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersion.setStatisticOfficiality(null);
        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    @Test
    public void testSendToProductionValidationDatasetVersionRequired() throws Exception {
        expectedMetamacException(new MetamacException (ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION));
        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), null);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME)
    public void testSendToProductionValidationRequiredFields() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        
        expectedMetamacException(new MetamacException ( 
                Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__DATE_NEXT_UPDATE),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY)
                        )));
        
        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendToDiffusionValidation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        
        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendToDiffusionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        datasetVersion.setStatisticOfficiality(null);
        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    
    @Test
    public void testSendToDiffusionValidationDatasetVersionRequired() throws Exception {
        expectedMetamacException(new MetamacException (ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION));
        
        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), null);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME)
    public void testSendToDiffusionValidationRequiredFields() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        
        expectedMetamacException(new MetamacException ( 
                Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__DATE_NEXT_UPDATE),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY)
                        )));
        
        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);
    }
}
