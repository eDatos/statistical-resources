package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createVersioned;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataVersioningNewResource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataVersioningPreviousResource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourceVersioned;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.util.ReflectionUtils;

public class LifecycleFillerTest extends StatisticalResourcesBaseTest {

    private LifecycleFiller                         lifecycleFiller                         = new LifecycleFiller();

    @InjectMocks
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks = new StatisticalResourcesNotPersistedDoMocks();

    private StatisticalResourcesPersistedDoMocks    statisticalResourcesPersistedDoMocks    = new StatisticalResourcesPersistedDoMocks();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Field field = ReflectionUtils.findField(StatisticalResourcesNotPersistedDoMocks.class, "statisticalResourcesPersistedDoMocks");
        field.setAccessible(true);
        ReflectionUtils.setField(field, statisticalResourcesNotPersistedDoMocks, statisticalResourcesPersistedDoMocks);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToProductionValidationActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToProductionValidation();

        lifecycleFiller.applySendToProductionValidationActions(getServiceContextWithoutPrincipal(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(mockedResource);

    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToDiffusionValidationActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation();

        lifecycleFiller.applySendToDiffusionValidationActions(getServiceContextWithoutPrincipal(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(mockedResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToValidationRejectedActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToValidationRejected();

        lifecycleFiller.applySendToValidationRejectedActions(getServiceContextWithoutPrincipal(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(mockedResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersionWithoutPreviousVersion() throws Exception {
        DatasetVersion resource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic(VersionUtil.createInitialVersion(VersionPatternEnum.XXX_YYY));

        lifecycleFiller.applySendToPublishedActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersionWithoutPreviousVersion() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic(VersionUtil.createInitialVersion(VersionPatternEnum.XXX_YYY));

        lifecycleFiller.applySendToPublishedActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersionWithPreviousVersionErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION));

        DatasetVersion resource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic("002.000");

        lifecycleFiller.applySendToPublishedActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersionWithPreviousVersionErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION));

        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic("002.000");

        lifecycleFiller.applySendToPublishedActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersionWithPreviousVersion() throws Exception {
        DatasetVersion resource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        DatasetVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();

        prepareToPublished(resource);
        createPublished(previousResource);

        lifecycleFiller.applySendToPublishedActions(getServiceContextWithoutPrincipal(), resource, previousResource);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersionWithPreviousVersion() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        prepareToPublished(resource);
        createPublished(previousResource);

        lifecycleFiller.applySendToPublishedActions(getServiceContextWithoutPrincipal(), resource, previousResource);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsForUndefinedResourceType() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
        HasLifecycle mockedPreviousResource = mockHasLifecycleStatisticalResourcePublished();

        lifecycleFiller.applySendToPublishedActions(getServiceContextWithoutPrincipal(), mockedResource, mockedPreviousResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplyVersioningNewResourceActions() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        createVersioned(resource);
        createPublished(previousResource);

        lifecycleFiller.applyVersioningNewResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource, VersionTypeEnum.MAJOR);
        assertNotNullAutomaticallyFilledMetadataVersioningNewResource(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplyVersioningNewResourceActionsErrorUndefinedType() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type"));
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourceVersioned();
        HasLifecycle mockedPreviousResource = mockHasLifecycleStatisticalResourcePublished();

        lifecycleFiller.applyVersioningNewResourceActions(getServiceContextWithoutPrincipal(), mockedResource, mockedPreviousResource, VersionTypeEnum.MAJOR);
    }

    @Test
    public void testLifeCycleResourceApplyVersioningPreviousResourceActions() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        createVersioned(resource);
        createPublished(previousResource);

        lifecycleFiller.applyVersioningPreviousResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource, VersionTypeEnum.MAJOR);

        assertNotNullAutomaticallyFilledMetadataVersioningPreviousResource(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplyVersioningPreviousResourceActionsErrorUndefinedType() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type"));
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourceVersioned();
        HasLifecycle mockedPreviousResource = mockHasLifecycleStatisticalResourcePublished();

        lifecycleFiller.applyVersioningPreviousResourceActions(getServiceContextWithoutPrincipal(), mockedResource, mockedPreviousResource, VersionTypeEnum.MAJOR);
    }
}
