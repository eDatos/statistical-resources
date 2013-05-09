package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

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
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class LifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private LifecycleChecker                        lifecycleService                        = new LifecycleChecker();

    @InjectMocks
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks = new StatisticalResourcesNotPersistedDoMocks();

    @Mock
    private LifecycleCommonMetadataChecker          lifecycleCommonMetadataChecker;

    @Mock
    private StatisticalResourcesPersistedDoMocks    statisticalResourcesPersistedDoMocks;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationProperCheckingCalls() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        lifecycleService.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);

        assertEquals(0, exceptionItems.size());

        verify(lifecycleCommonMetadataChecker).checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
    }

    /*
     * For first version only MAJOR_NEW_RESOURCE version rationale type is permitted
     */
    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationIncorrectVersionRationaleType() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        mockedResource.getLifeCycleStatisticalResource().setVersionLogic(VersionUtil.PATTERN_XXX_YYY_INITIAL_VERSION);

        for (VersionRationaleTypeEnum versionRationaleType2Test : VersionRationaleTypeEnum.values()) {
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            mockedResource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(versionRationaleType2Test));

            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

            lifecycleService.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);

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
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            prepareToProductionValidation(mockedResource);
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);

            if (ProcStatusEnum.DRAFT.equals(procStatus) || ProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    MetamacAsserts.assertEqualsMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, mockedResource.getLifeCycleStatisticalResource().getUrn(),
                            validStatus), e);
                }
            }
        }
    }

    @Test
    public void testLifeCycleResourceApplySendToProductionValidationActions() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToProductionValidation(mockedResource);

        lifecycleService.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);

        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(mockedResource);
        assertEquals(ProcStatusEnum.PRODUCTION_VALIDATION, mockedResource.getLifeCycleStatisticalResource().getProcStatus());
    }

    private void assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(HasLifecycleStatisticalResource resource) {
        assertNotNull(resource.getLifeCycleStatisticalResource().getProductionValidationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getProductionValidationUser());
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToDiffusionValidationProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            prepareToDiffusionValidation(mockedResource);
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);

            if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToDiffusionValidation(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToDiffusionValidation(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    assertEquals("Error with procstatus " + procStatus, ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
                }
            }
        }
    }

    @Test
    public void testLifeCycleResourceApplySendToDiffusionValidationActions() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToDiffusionValidation(mockedResource);

        lifecycleService.applySendToDiffusionValidationActions(getServiceContextAdministrador(), mockedResource);

        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(mockedResource);
        assertEquals(ProcStatusEnum.DIFFUSION_VALIDATION, mockedResource.getLifeCycleStatisticalResource().getProcStatus());

    }

    private void assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(HasLifecycleStatisticalResource resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNull(resource.getLifeCycleStatisticalResource().getDiffusionValidationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getDiffusionValidationUser());

    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToValidationRejectedProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            prepareToValidationRejected(mockedResource);
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);

            if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus) || ProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToValidationRejected(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToValidationRejected(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2, new String[]{mockedResource.getLifeCycleStatisticalResource().getUrn(),
                            "PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
                }
            }
        }
    }

    @Test
    public void testLifeCycleResourceApplySendToValidationRejectedActions() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToValidationRejected(mockedResource);

        lifecycleService.applySendToValidationRejectedActions(getServiceContextAdministrador(), mockedResource);

        assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(mockedResource);
        assertEquals(ProcStatusEnum.VALIDATION_REJECTED, mockedResource.getLifeCycleStatisticalResource().getProcStatus());

    }

    private void assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(HasLifecycleStatisticalResource resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNull(resource.getLifeCycleStatisticalResource().getRejectValidationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getRejectValidationUser());
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            prepareToPublished(mockedResource);
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);

            if (ProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleService.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleService.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2, new String[]{mockedResource.getLifeCycleStatisticalResource().getUrn(),
                            "DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
                }
            }
        }
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusErrorRequiredValidFrom() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToPublished(mockedResource);
        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(null);

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleService.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
        assertEquals(1, exceptionItems.size());
        MetamacAsserts.assertEqualsMetamacExceptionItem(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VALID_FROM)),
                exceptionItems.get(0));
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusErrorIncorrectValidFrom() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToPublished(mockedResource);
        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusMinutes(40));

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleService.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
        assertEquals(1, exceptionItems.size());
        MetamacAsserts.assertEqualsMetamacExceptionItem(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, addParameter(baseMetadata, ServiceExceptionSingleParameters.VALID_FROM)),
                exceptionItems.get(0));
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusOkFutureValidFrom() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToPublished(mockedResource);
        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().plusMinutes(120));

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleService.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
        assertEquals(0, exceptionItems.size());
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusOkValidFromDelayTime() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToPublished(mockedResource);
        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusMinutes(10));

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleService.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
        assertEquals(0, exceptionItems.size());
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersion() throws Exception {
        DatasetVersion resource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        DatasetVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();

        prepareToPublished(resource);
        createPublished(previousResource);

        lifecycleService.applySendToPublishedActions(getServiceContextAdministrador(), resource, previousResource);

        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, previousResource);
    }
    
    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersion() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        prepareToPublished(resource);
        createPublished(previousResource);

        lifecycleService.applySendToPublishedActions(getServiceContextAdministrador(), resource, previousResource);

        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsError() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type"));

        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        HasLifecycleStatisticalResource mockedPreviousResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedPreviousResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToPublished(mockedResource);
        createPublished(mockedPreviousResource);

        lifecycleService.applySendToPublishedActions(getServiceContextAdministrador(), mockedResource, mockedPreviousResource);
    }

    private void assertNotNullAutomaticallyFilledMetadataSendToPublished(HasLifecycleStatisticalResource resource, HasSiemacMetadataStatisticalResource previousResource) {
        
        // Actual Resource
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(resource);
        assertNotNull(resource.getLifeCycleStatisticalResource().getPublicationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getPublicationUser());
        assertNotNull(resource.getLifeCycleStatisticalResource().getValidFrom());
        assertEquals(resource.getLifeCycleStatisticalResource().getPublicationDate(), resource.getLifeCycleStatisticalResource().getValidFrom());
        assertEquals(ProcStatusEnum.PUBLISHED, resource.getLifeCycleStatisticalResource().getProcStatus());

        if (previousResource != null) {
            // Previous Resource
            assertNotNullAutomaticallyFilledMetadataOldPublished(previousResource);
            assertEquals(ProcStatusEnum.PUBLISHED, previousResource.getLifeCycleStatisticalResource().getProcStatus());
            
            // Common
            assertEquals(resource.getLifeCycleStatisticalResource().getValidFrom(), previousResource.getLifeCycleStatisticalResource().getValidTo());
        }
    }
    
    private void assertNotNullAutomaticallyFilledMetadataOldPublished(HasLifecycleStatisticalResource previousResource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(previousResource);
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(previousResource);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(previousResource, null);
        assertNotNull(previousResource.getLifeCycleStatisticalResource().getIsReplacedByVersion());
        assertNotNull(previousResource.getLifeCycleStatisticalResource().getValidTo());
    }

    
    // ------------------------------------------------------------------------------------------------------
    // UTILS
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testIsFirstVersion() throws Exception {
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().setVersionLogic(VersionUtil.PATTERN_XXX_YYY_INITIAL_VERSION);
            Boolean result = lifecycleService.isFirstVersion(mockedResource);
            assertEquals(Boolean.TRUE, result);
        }
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().setVersionLogic(null);
            Boolean result = lifecycleService.isFirstVersion(mockedResource);
            assertEquals(Boolean.FALSE, result);
        }
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
            mockedResource.getLifeCycleStatisticalResource().setVersionLogic("002.000");
            Boolean result = lifecycleService.isFirstVersion(mockedResource);
            assertEquals(Boolean.FALSE, result);
        }
    }

    @Test
    public void testCheckOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny() throws Exception {
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
            assertEquals(Boolean.TRUE, result);
        }
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
            Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
            assertEquals(Boolean.FALSE, result);
        }
        {
            for (VersionRationaleTypeEnum versionRationaleType : VersionRationaleTypeEnum.values()) {
                HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
                when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

                mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
                mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType));
                Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
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
                    HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
                    when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

                    mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
                    mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType01));
                    mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType02));
                    Boolean result = lifecycleService.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
                    assertEquals(Boolean.FALSE, result);
                }
            }
        }
    }
}
