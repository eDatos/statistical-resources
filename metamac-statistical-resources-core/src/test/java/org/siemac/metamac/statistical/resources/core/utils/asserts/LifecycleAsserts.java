package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class LifecycleAsserts extends CommonAsserts {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public static void assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(HasLifecycle resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource, true);
    }

    private static void assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(HasLifecycle resource, boolean checkStatus) {
        assertNotNull(resource.getLifeCycleStatisticalResource().getProductionValidationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getProductionValidationUser());
        if (checkStatus) {
            assertEquals(ProcStatusEnum.PRODUCTION_VALIDATION, resource.getLifeCycleStatisticalResource().getProcStatus());
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public static void assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(HasLifecycle resource) {
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(resource, true);
    }

    private static void assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(HasLifecycle resource, boolean checkStatus) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource, false);

        assertNotNull(resource.getLifeCycleStatisticalResource().getDiffusionValidationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getDiffusionValidationUser());

        if (checkStatus) {
            assertEquals(ProcStatusEnum.DIFFUSION_VALIDATION, resource.getLifeCycleStatisticalResource().getProcStatus());
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public static void assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(HasLifecycle resource) {
        assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(resource, true);
    }

    private static void assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(HasLifecycle resource, boolean checkStatus) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource, false);

        assertNotNull(resource.getLifeCycleStatisticalResource().getRejectValidationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getRejectValidationUser());

        if (checkStatus) {
            assertEquals(ProcStatusEnum.VALIDATION_REJECTED, resource.getLifeCycleStatisticalResource().getProcStatus());
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public static void assertNotNullAutomaticallyFilledMetadataSendToPublished(HasLifecycle resource, HasLifecycle previousVersion) {
        assertNotNullAutomaticallyFilledMetadataSendToPublished(resource, previousVersion, true);
    }

    private static void assertNotNullAutomaticallyFilledMetadataSendToPublished(HasLifecycle resource, HasLifecycle previousVersion, boolean checkStatus) {
        assertNotNullAutomaticallyFilledMetadataForAPublishedResource(resource);

        if (previousVersion != null) {
            assertNotNullAutomaticallyFilledMetadataForAPublishedResource(previousVersion);
            assertNotNull(previousVersion.getLifeCycleStatisticalResource().getValidTo());
            assertEquals(resource.getLifeCycleStatisticalResource().getValidFrom(), previousVersion.getLifeCycleStatisticalResource().getValidTo());
        }
    }

    private static void assertNotNullAutomaticallyFilledMetadataForAPublishedResource(HasLifecycle resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource, false);
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(resource, false);
        
        assertNotNull(resource.getLifeCycleStatisticalResource().getPublicationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getPublicationUser());
        assertNotNull(resource.getLifeCycleStatisticalResource().getValidFrom());
        assertEquals(ProcStatusEnum.PUBLISHED, resource.getLifeCycleStatisticalResource().getProcStatus());

    }


    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    public static void assertNotNullAutomaticallyFilledMetadataVersioningNewResource(HasLifecycle resource, HasLifecycle previousVersion) throws MetamacException {
        assertNotNullAutomaticallyFilledMetadataForAVersionedResource(resource, previousVersion);
        assertNullAutomaticallyFilledMetadataForAVersionedResource(resource);
    }
    
    public static void assertNotNullAutomaticallyFilledMetadataVersioningPreviousResource(HasLifecycle resource, HasLifecycle previousVersion) throws MetamacException {
        assertNotNullAutomaticallyFilledForAPreviosVersionOfVersionedResource(resource, previousVersion);
    }
    
    private static void assertNotNullAutomaticallyFilledForAPreviosVersionOfVersionedResource(HasLifecycle resource, HasLifecycle previousVersion) throws MetamacException {
        assertFalse(previousVersion.getLifeCycleStatisticalResource().getLastVersion());
        assertNotNull(previousVersion.getLifeCycleStatisticalResource().getIsReplacedByVersion());
        assertEqualsRelatedResource(previousVersion.getLifeCycleStatisticalResource().getIsReplacedByVersion(), RelatedResourceUtils.createRelatedResourceForHasLifecycleResource(resource));
        assertNotNullAutomaticallyFilledMetadataForAPublishedResource(previousVersion);
    }

    private static void assertNotNullAutomaticallyFilledMetadataForAVersionedResource(HasLifecycle resource, HasLifecycle previousVersion) throws MetamacException {
        assertNotNull(resource.getLifeCycleStatisticalResource().getCreationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getCreationUser());
        assertEquals(ProcStatusEnum.DRAFT, resource.getLifeCycleStatisticalResource().getProcStatus());
        assertTrue(resource.getLifeCycleStatisticalResource().getLastVersion());
        assertNotNull(resource.getLifeCycleStatisticalResource().getReplacesVersion());
        assertEqualsRelatedResource(resource.getLifeCycleStatisticalResource().getReplacesVersion(), RelatedResourceUtils.createRelatedResourceForHasLifecycleResource(previousVersion));
        assertTrue(Double.valueOf(resource.getLifeCycleStatisticalResource().getVersionLogic()) > Double.valueOf(previousVersion.getLifeCycleStatisticalResource().getVersionLogic()));
    }
    
    private static void assertNullAutomaticallyFilledMetadataForAVersionedResource(HasLifecycle resource) {
        // Production validation
        assertNull(resource.getLifeCycleStatisticalResource().getProductionValidationDate());
        assertNull(resource.getLifeCycleStatisticalResource().getProductionValidationUser());
        
        // Diffusion validation
        assertNull(resource.getLifeCycleStatisticalResource().getDiffusionValidationDate());
        assertNull(resource.getLifeCycleStatisticalResource().getDiffusionValidationUser());
        
        // Reject validation
        assertNull(resource.getLifeCycleStatisticalResource().getRejectValidationDate());
        assertNull(resource.getLifeCycleStatisticalResource().getRejectValidationUser());
        
        // Publication 
        assertNull(resource.getLifeCycleStatisticalResource().getPublicationDate());
        assertNull(resource.getLifeCycleStatisticalResource().getPublicationUser());
        assertNull(resource.getLifeCycleStatisticalResource().getValidFrom());
        assertNull(resource.getLifeCycleStatisticalResource().getValidTo());
    }

}
