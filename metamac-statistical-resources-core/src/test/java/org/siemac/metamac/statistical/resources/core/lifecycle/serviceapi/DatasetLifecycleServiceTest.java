package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.base.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.base.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.validators.DatasetLifecycleServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.DatasetLifecycleServiceImpl;
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
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetLifecycleServiceTest extends StatisticalResourcesBaseTest implements DatasetLifecycleServiceTestBase {

    @InjectMocks
    protected DatasetLifecycleService datasetLifecycleService = new DatasetLifecycleServiceImpl();
    
    @Mock
    private SiemacLifecycleChecker siemacLifecycleChecker;
    
    @Mock
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    @Mock
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;
    
    @Mock
    private DatasetVersionRepository datasetVersionRepository;
    
    protected DatasetVersionMockFactory datasetVersionMockFactory = new DatasetVersionMockFactory();
    
    protected DatasetService datasetService;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @After
    public void validateMockitoUsage() {
        Mockito.validateMockitoUsage();
    }
    
    @Override
    @Test
    public void testSendToProductionValidation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);
        
        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME)
    public void testSendToProductionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersion.setStatisticOfficiality(null);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);
        
        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
//    @Test
//    public void testSendToProductionValidationDatasetVersionRequired() throws Exception {
//        expectedMetamacException(new MetamacException (ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION));
//        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), null);
//    }
//    
    
    @Test
    @Ignore
    @MetamacMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME)
    public void testSendToProductionValidationRequiredFields() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);
 
        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);
        
        Mockito.verify(lifecycleCommonMetadataChecker,Mockito.times(1)).checkDatasetVersionCommonMetadata(Mockito.any(DatasetVersion.class), Mockito.anyString(), Mockito.anyListOf(MetamacExceptionItem.class));
    }
    
    @Override
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendToDiffusionValidation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);
        
        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME)
    public void testSendToDiffusionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME));
        datasetVersion.setStatisticOfficiality(null);
        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);
    }
    
    
    @Test
    @Ignore
    public void testSendToDiffusionValidationDatasetVersionRequired() throws Exception {
        //SHOULD BE TEST in dataset validator unit tests
//        expectedMetamacException(new MetamacException (ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION));
//        
//        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), null);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME)
    public void testSendToDiffusionValidationRequiredFields() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);
 
        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);
        
        Mockito.verify(lifecycleCommonMetadataChecker,Mockito.times(1)).checkDatasetVersionCommonMetadata(Mockito.any(DatasetVersion.class), Mockito.anyString(), Mockito.anyListOf(MetamacExceptionItem.class));
    }
}
