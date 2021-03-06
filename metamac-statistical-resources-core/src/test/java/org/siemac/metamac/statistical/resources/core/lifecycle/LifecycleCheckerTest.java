package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePublished;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.ExternalItemChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.RelatedResourceChecker;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticalResourcesMockFactory;

@RunWith(MockitoJUnitRunner.class)
public class LifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private final LifecycleChecker         lifecycleChecker = new LifecycleChecker();

    @Mock
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Mock
    private ExternalItemChecker            externalItemChecker;

    @SuppressWarnings("unused")
    @Mock
    private RelatedResourceChecker         relatedResourceChecker;

    private static String                  baseMetadata     = ServiceExceptionSingleParameters.DATASET_VERSION;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationProperCheckingCalls() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToProductionValidation();

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);

        assertEquals(0, exceptionItems.size());

        verify(lifecycleCommonMetadataChecker).checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
    }

    /*
     * For first version only MAJOR_NEW_RESOURCE version rationale type is permitted
     */
    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationIncorrectVersionRationaleType() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToProductionValidation();
        mockedResource.getLifeCycleStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.INIT_VERSION);

        for (VersionRationaleTypeEnum versionRationaleType2Test : VersionRationaleTypeEnum.values()) {
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            mockedResource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(versionRationaleType2Test));

            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

            lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);

            if (VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(versionRationaleType2Test)) {
                assertEquals(0, exceptionItems.size());
            } else {
                MetamacException expected = new MetamacException(
                        Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES))));
                MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
            }
        }
    }

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationProcStatus() throws Exception {

        String validStatus = ProcStatusEnum.DRAFT.name() + ", " + ProcStatusEnum.VALIDATION_REJECTED.name();

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToProductionValidation();
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);
            if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(1));
            }

            if (ProcStatusEnum.DRAFT.equals(procStatus) || ProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleChecker.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    MetamacAsserts.assertEqualsMetamacException(
                            new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, mockedResource.getLifeCycleStatisticalResource().getUrn(), validStatus), e);
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToDiffusionValidationProcStatus() throws Exception {

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation();
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);
            if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(1));
            }

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

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToValidationRejected();
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);
            if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(1));
            }

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
                    MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2,
                            new String[]{mockedResource.getLifeCycleStatisticalResource().getUrn(), "PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatus() throws Exception {

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);
            if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(1));
            }

            HasLifecycle mockedPreviousVersion = mockHasLifecycleStatisticalResourcePublished();

            if (ProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleChecker.checkSendToPublished(mockedResource, mockedPreviousVersion, baseMetadata, exceptionItems);

                verifyExternalItemExternallyPublished(mockedResource.getLifeCycleStatisticalResource().getStatisticalOperation());
                verifyExternalItemExternallyPublished(mockedResource.getLifeCycleStatisticalResource().getMaintainer());

                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleChecker.checkSendToPublished(mockedResource, mockedPreviousVersion, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2,
                            new String[]{mockedResource.getLifeCycleStatisticalResource().getUrn(), "DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
                }
            }
        }
    }

    private void verifyExternalItemExternallyPublished(ExternalItem item) throws MetamacException {
        verify(externalItemChecker).checkExternalItemsExternallyPublished(Mockito.eq(item), anyString(), Mockito.anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusOkFutureValidFrom() throws Exception {

        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().plusMinutes(120));

        HasLifecycle mockedPreviousVersion = mockHasLifecycleStatisticalResourcePublished();

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleChecker.checkSendToPublished(mockedResource, mockedPreviousVersion, baseMetadata, exceptionItems);
        assertEquals(0, exceptionItems.size());
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusOkValidFromDelayTime() throws Exception {

        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusMinutes(10));

        HasLifecycle mockedPreviousVersion = mockHasLifecycleStatisticalResourcePublished();

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleChecker.checkSendToPublished(mockedResource, mockedPreviousVersion, baseMetadata, exceptionItems);
        assertEquals(0, exceptionItems.size());
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusErrorRequiredPreviousVersion() throws Exception {

        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
        mockedResource.getLifeCycleStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.SECOND_VERSION);
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusMinutes(10));

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleChecker.checkSendToPublished(mockedResource, null, baseMetadata, exceptionItems);
        assertEquals(1, exceptionItems.size());
    }

    @Test
    public void testLifeCycleResourceCheckSendToPublishedProcStatusWithPreviousVersionNullInAnInitialVersion() throws Exception {

        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
        mockedResource.getLifeCycleStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.INIT_VERSION);
        mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusMinutes(10));

        ArrayList<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleChecker.checkSendToPublished(mockedResource, null, baseMetadata, exceptionItems);
        assertEquals(0, exceptionItems.size());
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckVersioning() throws Exception {

        for (ProcStatusEnum procStatus : ProcStatusEnum.values()) {
            HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePublished();
            mockedResource.getLifeCycleStatisticalResource().setLastVersion(Boolean.TRUE);
            mockedResource.getLifeCycleStatisticalResource().setProcStatus(procStatus);
            if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                mockedResource.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(1));
            }

            if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleChecker.checkVersioning(mockedResource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleChecker.checkVersioning(mockedResource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    assertEquals("Error with procstatus " + procStatus, ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // UTILS
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testCheckOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny() throws Exception {
        {
            HasLifecycle mockedResource = mock(HasLifecycle.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            Boolean result = lifecycleChecker.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
            assertEquals(Boolean.TRUE, result);
        }
        {
            HasLifecycle mockedResource = mock(HasLifecycle.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
            mockedResource.getLifeCycleStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
            Boolean result = lifecycleChecker.checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(mockedResource);
            assertEquals(Boolean.FALSE, result);
        }
        {
            for (VersionRationaleTypeEnum versionRationaleType : VersionRationaleTypeEnum.values()) {
                HasLifecycle mockedResource = mock(HasLifecycle.class);
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
                    HasLifecycle mockedResource = mock(HasLifecycle.class);
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
