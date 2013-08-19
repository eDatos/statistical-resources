package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;


public class HasLifecycleMocks {
    
    // PREPARE TO PRODUCTION VALIDATION
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToProductionValidation() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToProductionValidation(mockedResource);
        return mockedResource;
    }
    
    // PREPARE TO DIFFUSION VALIDATION
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToDiffusionValidation(mockedResource);
        return mockedResource;
    }
    
    // PREPARE TO VALIDATION REJECTED
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToValidationRejected() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToValidationRejected(mockedResource);
        return mockedResource;
    }
    
    // PREPARE TO PUBLISHED
    public static HasLifecycle mockHasLifecycleStatisticalResourcePrepareToPublished() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        prepareToPublished(mockedResource);
        return mockedResource;
    }
    
    // PUBLISHED
    public static HasLifecycle mockHasLifecycleStatisticalResourcePublished() {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResource();
        createPublished(mockedResource);
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
