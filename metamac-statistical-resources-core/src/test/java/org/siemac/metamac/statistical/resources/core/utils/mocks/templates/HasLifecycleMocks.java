package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.fillAsPublishedLifecycle;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidationLifecycle;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidationLifecycle;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublishingLifecycle;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejectedFromProductionValidationLifecycle;

import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;

public class HasLifecycleMocks {

    // PREPARE TO PRODUCTION VALIDATION
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToProductionValidation() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToProductionValidationLifecycle(mockedResource);
        return mockedResource;
    }

    // PREPARE TO DIFFUSION VALIDATION
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToDiffusionValidationLifecycle(mockedResource);
        return mockedResource;
    }

    // PREPARE TO VALIDATION REJECTED
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToValidationRejected() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToValidationRejectedFromProductionValidationLifecycle(mockedResource);
        return mockedResource;
    }

    // PREPARE TO PUBLISHED
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToPublished() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToPublishingLifecycle(mockedResource);
        return mockedResource;
    }

    // PUBLISHED
    public static HasLifecycle mockHasLifecycleStatisticalResourcePublished() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        fillAsPublishedLifecycle(mockedResource);
        return mockedResource;
    }

    // VERSIONED
    public static HasLifecycle mockHasLifecycleStatisticalResourceVersioned() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        return mockedResource;
    }

    private static HasLifecycle mockHasLifecycleStatisticalResource() {
        HasLifecycle mockedResource = mock(HasLifecycle.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        return mockedResource;
    }
}
