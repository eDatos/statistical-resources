package  org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;

/*
 * No spring context, we set the SUT (Software under test) dependencies with mocked objects. Unit testing style ;) 
 */
public class SiemacLifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private SiemacLifecycleChecker siemacLifecycleServiceImpl;
    
    @Mock
    private LifecycleChecker lifecycleService;
    
    @Mock
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    @Before
    public void setUp() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        siemacLifecycleServiceImpl = new SiemacLifecycleChecker();
        MockitoAnnotations.initMocks(this);
    }
    
    @After
    public void checkMockitoUsage() {
        validateMockitoUsage();
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceCheckSendToProductionValidationRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        
        siemacLifecycleServiceImpl.checkSendToProductionValidation(new SiemacMetadataStatisticalResource(), baseMetadata, exceptionItems);
        
        //NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());
        
        verify(lifecycleService,times(1)).checkSendToProductionValidation(any(LifeCycleStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker,times(1)).checkSiemacCommonMetadata(any(SiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }
    
    @Test
    public void testSiemacResourceApplySendToProductionValidationActions() throws Exception {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        prepareToProductionValidation(resource);
        
        siemacLifecycleServiceImpl.applySendToProductionValidationActions(getServiceContextAdministrador(),resource);
        
        //No specific actions for siemac
        
        verify(lifecycleService,times(1)).applySendToProductionValidationActions(any(ServiceContext.class),any(LifeCycleStatisticalResource.class));
    }
    

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    @Test
    public void testSiemacResourceCheckSendToDiffusionValidationRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        
        siemacLifecycleServiceImpl.checkSendToDiffusionValidation(resource, baseMetadata, exceptionItems);
        
        //NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());
        
        verify(lifecycleService,times(1)).checkSendToDiffusionValidation(any(LifeCycleStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker,times(1)).checkSiemacCommonMetadata(any(SiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }
    
    @Test
    public void testSiemacResourceApplySendToDiffusionValidationActions() throws Exception {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        prepareToDiffusionValidation(resource);
        
        siemacLifecycleServiceImpl.applySendToDiffusionValidationActions(getServiceContextAdministrador(),resource);
     
        //No specific actions for siemac
        
        verify(lifecycleService,times(1)).applySendToDiffusionValidationActions(any(ServiceContext.class),any(LifeCycleStatisticalResource.class));
    }
    

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------
    
    @Test
    public void testSiemacResourceCheckSendToValidationRejectedRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        prepareToValidationRejected(resource);
        
        siemacLifecycleServiceImpl.checkSendToValidationRejected(resource, baseMetadata, exceptionItems);
        
        //NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());
        
        verify(lifecycleService,times(1)).checkSendToValidationRejected(any(LifeCycleStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker,times(1)).checkSiemacCommonMetadata(any(SiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }
    
    @Test
    public void testSiemacResourceApplySendToValidationRejectedActions() throws Exception {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        prepareToValidationRejected(resource);
        
        siemacLifecycleServiceImpl.applySendToValidationRejectedActions(getServiceContextAdministrador(),resource);
     
        //No specific actions for siemac
        
        verify(lifecycleService,times(1)).applySendToValidationRejectedActions(any(ServiceContext.class),any(LifeCycleStatisticalResource.class));
    }
    
}
