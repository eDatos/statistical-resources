package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createVersioned;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPublished;

import java.lang.reflect.Field;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.util.ReflectionUtils;

/*
 * No spring context, we set the SUT (Software under test) dependencies with mocked objects. Unit testing style ;)
 */
@RunWith(MockitoJUnitRunner.class)
public class SiemacLifecycleFillerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks = new StatisticalResourcesNotPersistedDoMocks();

    private StatisticalResourcesPersistedDoMocks    statisticalResourcesPersistedDoMocks    = new StatisticalResourcesPersistedDoMocks();

    @InjectMocks
    private SiemacLifecycleFiller                   siemacLifecycleFiller                   = new SiemacLifecycleFiller();

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private LifecycleFiller                         lifecycleFiller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Field field = ReflectionUtils.findField(StatisticalResourcesNotPersistedDoMocks.class, "statisticalResourcesPersistedDoMocks");
        field.setAccessible(true);
        ReflectionUtils.setField(field, statisticalResourcesNotPersistedDoMocks, statisticalResourcesPersistedDoMocks);
    }

    @After
    public void checkMockitoUsage() {
        validateMockitoUsage();
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceApplySendToProductionValidationActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToProductionValidation();

        siemacLifecycleFiller.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac
        verify(lifecycleFiller, times(1)).applySendToProductionValidationActions(any(ServiceContext.class), any(HasLifecycle.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceApplySendToDiffusionValidationActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToDiffusionValidation();

        siemacLifecycleFiller.applySendToDiffusionValidationActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac
        verify(lifecycleFiller, times(1)).applySendToDiffusionValidationActions(any(ServiceContext.class), any(HasLifecycle.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceApplySendToValidationRejectedActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToValidationRejected();

        siemacLifecycleFiller.applySendToValidationRejectedActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac
        verify(lifecycleFiller, times(1)).applySendToValidationRejectedActions(any(ServiceContext.class), any(HasLifecycle.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceApplySendToPublishedActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToPublished();
        HasSiemacMetadata previosMockedVersion = mockHasSiemacMetadataPublished();

        siemacLifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), mockedResource, previosMockedVersion);

        verify(lifecycleFiller, times(1)).applySendToPublishedActions(any(ServiceContext.class), any(HasLifecycle.class), any(HasLifecycle.class));
        assertNotNull(mockedResource.getSiemacMetadataStatisticalResource().getCopyrightedDate());
        assertEquals(Integer.valueOf(mockedResource.getLifeCycleStatisticalResource().getValidFrom().getYear()), mockedResource.getSiemacMetadataStatisticalResource().getCopyrightedDate());
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceApplyVersioningActionsErrorUndefinedResourceType() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type"));
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToPublished();
        HasSiemacMetadata previosMockedVersion = mockHasSiemacMetadataPublished();

        siemacLifecycleFiller.applyVersioningNewResourceActions(getServiceContextAdministrador(), mockedResource, previosMockedVersion, VersionTypeEnum.MAJOR);
    }

    @Test
    public void testSiemacResourceApplyVersioningActions() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        createVersioned(resource);
        createPublished(previousResource);

        siemacLifecycleFiller.applyVersioningNewResourceActions(getServiceContextAdministrador(), resource, previousResource, VersionTypeEnum.MAJOR);

        verify(lifecycleFiller, times(1)).applyVersioningNewResourceActions(any(ServiceContext.class), any(HasLifecycle.class), any(HasLifecycle.class), any(VersionTypeEnum.class));
        assertNotNull(resource.getSiemacMetadataStatisticalResource().getLastUpdate());
        assertEquals(resource.getSiemacMetadataStatisticalResource().getCreationDate(), resource.getSiemacMetadataStatisticalResource().getLastUpdate());
    }

}
