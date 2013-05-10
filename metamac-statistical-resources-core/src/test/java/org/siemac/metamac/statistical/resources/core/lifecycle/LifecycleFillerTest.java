package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToValidationRejected;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
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
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToProductionValidation(mockedResource);

        lifecycleFiller.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);

        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(mockedResource);
        
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToDiffusionValidationActions() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToDiffusionValidation(mockedResource);

        lifecycleFiller.applySendToDiffusionValidationActions(getServiceContextAdministrador(), mockedResource);

        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(mockedResource);
    }


    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToValidationRejectedActions() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToValidationRejected(mockedResource);

        lifecycleFiller.applySendToValidationRejectedActions(getServiceContextAdministrador(), mockedResource);

        assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(mockedResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

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

        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        HasLifecycleStatisticalResource mockedPreviousResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedPreviousResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        prepareToPublished(mockedResource);
        createPublished(mockedPreviousResource);

        lifecycleFiller.applySendToPublishedActions(getServiceContextAdministrador(), mockedResource, mockedPreviousResource);
    }
}
