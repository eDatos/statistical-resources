package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;


public class LifecycleTestUtils {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToProductionValidation(SiemacMetadataStatisticalResource resource) {
        StatisticalResourcesPersistedDoMocks.prepareToProductionValidationSiemacResource(resource);
    }
    
    public static void prepareToProductionValidation(LifeCycleStatisticalResource resource) {
        StatisticalResourcesPersistedDoMocks.prepareToProductionValidationLifecycleResource(resource);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    
    public static void prepareToDiffusionValidation(SiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.setProductionValidationDate(new DateTime());
        resource.setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }
    
    public static void prepareToDiffusionValidation(LifeCycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.setProductionValidationDate(new DateTime());
        resource.setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToValidationRejected(SiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation(resource);
    }
    
    public static void prepareToValidationRejected(LifeCycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.setProductionValidationDate(new DateTime());
        resource.setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }
    
}
