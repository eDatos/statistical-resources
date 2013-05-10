package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

@RunWith(MockitoJUnitRunner.class)
public class LifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private LifecycleChecker                        lifecycleChecker                        = new LifecycleChecker();
    
    @Mock
    private LifecycleCommonMetadataChecker          lifecycleCommonMetadataChecker;

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

        lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems); 

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

            lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);

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
                lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    MetamacAsserts.assertEqualsMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, mockedResource.getLifeCycleStatisticalResource().getUrn(),
                            validStatus), e);
                }
            }
        }
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
                lifecycleChecker.checkSendToDiffusionValidation(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleChecker.checkSendToDiffusionValidation(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    assertEquals("Error with procstatus " + procStatus, ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
                }
            }
        }
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
                lifecycleChecker.checkSendToValidationRejected(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleChecker.checkSendToValidationRejected(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2, new String[]{mockedResource.getLifeCycleStatisticalResource().getUrn(),
                            "PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
                }
            }
        }
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
                lifecycleChecker.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleChecker.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
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
        lifecycleChecker.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
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
        lifecycleChecker.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
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
        lifecycleChecker.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
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
        lifecycleChecker.checkSendToPublished(mockedResource, baseMetadata, exceptionItems);
        assertEquals(0, exceptionItems.size());
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
            Boolean result = lifecycleChecker.isFirstVersion(mockedResource);
            assertEquals(Boolean.TRUE, result);
        }
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().setVersionLogic(null);
            Boolean result = lifecycleChecker.isFirstVersion(mockedResource);
            assertEquals(Boolean.FALSE, result);
        }
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
            mockedResource.getLifeCycleStatisticalResource().setVersionLogic("002.000");
            Boolean result = lifecycleChecker.isFirstVersion(mockedResource);
            assertEquals(Boolean.FALSE, result);
        }
    }

    @Test
    public void testCheckOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny() throws Exception {
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            Boolean result = lifecycleChecker.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
            assertEquals(Boolean.TRUE, result);
        }
        {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
            Boolean result = lifecycleChecker.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
            assertEquals(Boolean.FALSE, result);
        }
        {
            for (VersionRationaleTypeEnum versionRationaleType : VersionRationaleTypeEnum.values()) {
                HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
                when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

                mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
                mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType));
                Boolean result = lifecycleChecker.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
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
                    Boolean result = lifecycleChecker.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
                    assertEquals(Boolean.FALSE, result);
                }
            }
        }
    }
}
