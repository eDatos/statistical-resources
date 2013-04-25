package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public class LifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private LifecycleChecker               lifecycleService = new LifecycleChecker();

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
        resource.setProcStatus(ProcStatusEnum.DRAFT);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);

        assertEquals(0, exceptionItems.size());

        verify(lifecycleCommonMetadataChecker).checkLifecycleCommonMetadata(resource, baseMetadata, exceptionItems);
    }

    /*
     * For first version only MAJOR_NEW_RESOURCE version rationale type is permitted
     */
    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationIncorrectVersionRationaleType() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        resource.setProcStatus(ProcStatusEnum.DRAFT);
        resource.setVersionLogic(VersionUtil.PATTERN_XXX_YYY_INITIAL_VERSION);

        for (VersionRationaleTypeEnum versionRationaleType2Test : VersionRationaleTypeEnum.values()) {
            resource.getVersionRationaleTypes().clear();
            resource.addVersionRationaleType(new VersionRationaleType(versionRationaleType2Test));

            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

            lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);

            if (VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(versionRationaleType2Test)) {
                assertEquals(0, exceptionItems.size());
            } else {
                MetamacException expected = new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, addParameter(baseMetadata,
                        ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES))));
                MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
            }
        }
    }

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        String validStatus = ProcStatusEnum.DRAFT.name() + ", " + ProcStatusEnum.VALIDATION_REJECTED.name();

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToProductionValidation(resource);
            resource.setProcStatus(procStatus);

            if (ProcStatusEnum.DRAFT.equals(procStatus) || ProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    MetamacAsserts.assertEqualsMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, resource.getUrn(), validStatus), e);
                }
            }
        }
    }

    @Test
    public void testLifeCycleResourceApplySendToProductionValidationActions() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToProductionValidation(resource);

        lifecycleService.applySendToProductionValidationActions(getServiceContextAdministrador(), resource);

        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertEquals(ProcStatusEnum.PRODUCTION_VALIDATION, resource.getProcStatus());
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

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToDiffusionValidation(resource);
            resource.setProcStatus(procStatus);

            if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToDiffusionValidation(resource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToDiffusionValidation(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    assertEquals("Error with procstatus " + procStatus, ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
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
        assertEquals(ProcStatusEnum.DIFFUSION_VALIDATION, resource.getProcStatus());

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

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToValidationRejected(resource);
            resource.setProcStatus(procStatus);

            if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus) || ProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToValidationRejected(resource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToValidationRejected(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2,
                            new String[]{resource.getUrn(), "PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
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
        assertEquals(ProcStatusEnum.VALIDATION_REJECTED, resource.getProcStatus());

    }

    private void assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(LifeCycleStatisticalResource resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNull(resource.getRejectValidationDate());
        assertNotNull(resource.getRejectValidationUser());
    }

    // ------------------------------------------------------------------------------------------------------
    // UTILS
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testIsFirstVersion() throws Exception {
        {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            resource.setVersionLogic(VersionUtil.PATTERN_XXX_YYY_INITIAL_VERSION);
            Boolean result = lifecycleService.isFirstVersion(resource);
            assertEquals(Boolean.TRUE, result);
        }
        {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            resource.setVersionLogic(null);
            Boolean result = lifecycleService.isFirstVersion(resource);
            assertEquals(Boolean.FALSE, result);
        }
        {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            resource.setVersionLogic("002.000");
            Boolean result = lifecycleService.isFirstVersion(resource);
            assertEquals(Boolean.FALSE, result);
        }
    }

    @Test
    public void testCheckOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny() throws Exception {
        {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            resource.getVersionRationaleTypes().clear();
            Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(resource);
            assertEquals(Boolean.TRUE, result);
        }
        {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            resource.getVersionRationaleTypes().clear();
            resource.getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
            resource.getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
            Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(resource);
            assertEquals(Boolean.FALSE, result);
        }
        {
            for (VersionRationaleTypeEnum versionRationaleType : VersionRationaleTypeEnum.values()) {
                LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
                resource.getVersionRationaleTypes().clear();
                resource.getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType));
                Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(resource);
                if (VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(versionRationaleType)) {
                    assertEquals(Boolean.TRUE, result);
                } else {
                    assertEquals(Boolean.FALSE, result);
                }
            }
        }
        {
            for (VersionRationaleTypeEnum versionRationaleType01 : VersionRationaleTypeEnum.values()) {
                for (VersionRationaleTypeEnum versionRationaleType02 : VersionRationaleTypeEnum.values()) {
                    LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
                    resource.getVersionRationaleTypes().clear();
                    resource.getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType01));
                    resource.getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType02));
                    Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(resource);
                    assertEquals(Boolean.FALSE, result);
                }
            }
        }
    }
}
