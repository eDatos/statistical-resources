package  org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class LifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private LifecycleChecker lifecycleService = new LifecycleChecker();

    @Mock
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationProperCheckingCalls() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        
        lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
        
        assertEquals(0,exceptionItems.size());
        
        verify(lifecycleCommonMetadataChecker).checkLifecycleCommonMetadata(resource, baseMetadata, exceptionItems);
    }
    
    /*
     * For first version only MAJOR_NEW_RESOURCE version rationale type is permitted
     */
    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationIncorrectVersionRationaleType() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        resource.setVersionLogic("01.000");
        
        for (StatisticalResourceVersionRationaleTypeEnum versionRationaleType2Test : StatisticalResourceVersionRationaleTypeEnum.values()) {
            resource.getVersionRationaleTypes().clear();
            resource.addVersionRationaleType(new VersionRationaleType(versionRationaleType2Test));
            
            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
            
            lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
            
            if (StatisticalResourceVersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(versionRationaleType2Test)) {
                assertEquals(0,exceptionItems.size());
            } else {
                MetamacException expected = new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT,addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES))));
                MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
            }
        }
    }
    
    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        String validStatus = StatisticalResourceProcStatusEnum.DRAFT.name()+", "+StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.name();
            
        for (StatisticalResourceProcStatusEnum procStatus : StatisticalResourceProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToProductionValidation(resource);
            resource.setProcStatus(procStatus);
            
            if (StatisticalResourceProcStatusEnum.DRAFT.equals(procStatus) || StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
                assertEquals(0,exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    MetamacAsserts.assertEqualsMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, resource.getUrn(),validStatus), e);
                }
            }
        }
    }
    

    @Test
    public void testLifeCycleResourceApplySendToProductionValidationActions() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToProductionValidation(resource);
        
        lifecycleService.applySendToProductionValidationActions(getServiceContextAdministrador(),resource);
        
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertEquals(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, resource.getProcStatus());
    }
    
    private void assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(LifeCycleStatisticalResource resource) {
        assertNotNull(resource.getProductionValidationDate());
        assertNotNull(resource.getProductionValidationUser());
    }

    
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToDiffusionValidationProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (StatisticalResourceProcStatusEnum procStatus : StatisticalResourceProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToDiffusionValidation(resource);
            resource.setProcStatus(procStatus);
            
            if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToDiffusionValidation(resource, baseMetadata, exceptionItems);
                assertEquals(0,exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToDiffusionValidation(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1,e.getExceptionItems().size());
                    assertEquals("Error with procstatus "+procStatus,ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
                }
            }
        }
    }
    
    @Test
    public void testLifeCycleResourceApplySendToDiffusionValidationActions() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToDiffusionValidation(resource);

        lifecycleService.applySendToDiffusionValidationActions(getServiceContextAdministrador(), resource);

        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(resource);
        assertEquals(StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION,resource.getProcStatus());
        
    }

    private void assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(LifeCycleStatisticalResource resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNull(resource.getDiffusionValidationDate());
        assertNotNull(resource.getDiffusionValidationUser());
        
    }
    

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToValidationRejectedProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (StatisticalResourceProcStatusEnum procStatus : StatisticalResourceProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToValidationRejected(resource);
            resource.setProcStatus(procStatus);

            if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus) || StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToValidationRejected(resource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToValidationRejected(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2, new String[]{resource.getUrn(), "PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
                }
            }
        }
    }
    
    
    @Test
    public void testLifeCycleResourceApplySendToValidationRejectedActions() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToValidationRejected(resource);

        lifecycleService.applySendToValidationRejectedActions(getServiceContextAdministrador(), resource);

        assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(resource);
        assertEquals(StatisticalResourceProcStatusEnum.VALIDATION_REJECTED,resource.getProcStatus());
        
    }

    private void assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(LifeCycleStatisticalResource resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNull(resource.getRejectValidationDate());
        assertNotNull(resource.getRejectValidationUser());
    }

    // ------------------------------------------------------------------------------------------------------
    // UTILS
    // ------------------------------------------------------------------------------------------------------

}
