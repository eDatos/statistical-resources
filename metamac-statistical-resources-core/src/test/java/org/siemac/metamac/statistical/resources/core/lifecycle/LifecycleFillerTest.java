package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePublished;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
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

        lifecycleFiller.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(mockedResource);
        
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToDiffusionValidationActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation();

        lifecycleFiller.applySendToDiffusionValidationActions(getServiceContextAdministrador(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(mockedResource);
    }


    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToValidationRejectedActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToValidationRejected();
        
        lifecycleFiller.applySendToValidationRejectedActions(getServiceContextAdministrador(), mockedResource);
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

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersionWithoutPreviosVersion() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic(VersionUtil.createInitialVersion(VersionPatternEnum.XXX_YYY));

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }
    
    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersionWithoutPreviousVersionErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION));
        
        DatasetVersion resource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic("002.000");

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersionWithoutPreviosVersionErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION));
        
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic("002.000");

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), resource, null);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, null);
    }
    
    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersion() throws Exception {
        DatasetVersion resource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();
        DatasetVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();

        prepareToPublished(resource);
        createPublished(previousResource);

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), resource, previousResource);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersion() throws Exception {
        PublicationVersion resource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        prepareToPublished(resource);
        createPublished(previousResource);

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), resource, previousResource);
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsError() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type"));
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
        HasLifecycle mockedPreviousResource = mockHasLifecycleStatisticalResourcePublished();

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), mockedResource, mockedPreviousResource);
    }
}
