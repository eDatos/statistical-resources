package  org.siemac.metamac.statistical.resources.core.base.lifecycle;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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
public class SiemacLifecycleServiceTest extends StatisticalResourcesBaseTest {

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
    
    @Test
    public void testSiemacResourceCheckSendToProductionValidationRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        
        siemacLifecycleServiceImpl.checkSendToProductionValidation(new SiemacMetadataStatisticalResource(), baseMetadata, exceptionItems);
        
        Assert.assertEquals(0, exceptionItems.size());
        
        verify(lifecycleService,times(1)).checkSendToProductionValidation(any(LifeCycleStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker,times(1)).checkSiemacCommonMetadata(any(SiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }
    

    
    
}
