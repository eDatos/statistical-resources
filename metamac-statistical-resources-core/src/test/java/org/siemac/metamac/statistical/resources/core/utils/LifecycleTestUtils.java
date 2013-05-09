package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class LifecycleTestUtils {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToProductionValidation(HasSiemacMetadataStatisticalResource resource) {
        StatisticalResourcesPersistedDoMocks.prepareToProductionValidationSiemacResource(resource);
    }

    public static void prepareToProductionValidation(HasLifecycleStatisticalResource resource) {
        StatisticalResourcesPersistedDoMocks.prepareToProductionValidationLifecycleResource(resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToDiffusionValidation(HasSiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }

    public static void prepareToDiffusionValidation(HasLifecycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToValidationRejected(HasSiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation(resource);
    }

    public static void prepareToValidationRejected(HasLifecycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToPublished(HasSiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation(resource);
        prepareToDiffusionValidation(resource);
    }

    public static void prepareToPublished(HasLifecycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        prepareToDiffusionValidation(resource);
        resource.getLifeCycleStatisticalResource().setDiffusionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setDiffusionValidationUser("DIFFUSION_VALIDATION_USER");
        resource.getLifeCycleStatisticalResource().setValidFrom(new DateTime());
    }
    
    
    public static void createPublished(HasSiemacMetadataStatisticalResource resource) {
        prepareToPublished(resource);
    }
    
    public static void createPublished(HasLifecycleStatisticalResource resource) {
        prepareToPublished(resource);
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
    }

}
