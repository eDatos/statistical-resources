package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.createPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;

public class HasSiemacMetadataMocks {
    
    // PREPARE TO PRODUCTION VALIDATION
    public static HasSiemacMetadata mockHasSiemacMetadataPrepareToProductionValidation() {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadata();
        prepareToProductionValidation(mockedResource);
        return mockedResource;
    }
    
    // PREPARE TO DIFFUSION VALIDATION
    public static HasSiemacMetadata mockHasSiemacMetadataPrepareToDiffusionValidation() {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadata();
        prepareToDiffusionValidation(mockedResource);
        return mockedResource;
    }
    
    // PREPARE TO VALIDATION REJECTED
    public static HasSiemacMetadata mockHasSiemacMetadataPrepareToValidationRejected() {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadata();
        prepareToValidationRejected(mockedResource);
        return mockedResource;
    }
    
    // PREPARE TO PUBLISHED
    public static HasSiemacMetadata mockHasSiemacMetadataPrepareToPublished() {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadata();
        prepareToPublished(mockedResource);
        return mockedResource;
    }
    
    // PUBLISHED
    public static HasSiemacMetadata mockHasSiemacMetadataPublished() {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadata();
        createPublished(mockedResource);
        return mockedResource;
    }
    
    private static HasSiemacMetadata mockHasSiemacMetadata() {
        HasSiemacMetadata mockedResource = mock(HasSiemacMetadata.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());
        return mockedResource;
    }
}
