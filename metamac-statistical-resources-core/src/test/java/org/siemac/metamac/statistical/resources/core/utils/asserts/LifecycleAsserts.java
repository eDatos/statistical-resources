package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

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
            // Actual version
            assertReplacesVersionCorrectlyFilled(resource, previousVersion);
            // Previous version
            assertNotNullAutomaticallyFilledMetadataOldPublished(previousVersion);
            // Common
            assertEquals(resource.getLifeCycleStatisticalResource().getValidFrom(), previousVersion.getLifeCycleStatisticalResource().getValidTo());
        }
    }

    private static void assertReplacesVersionCorrectlyFilled(HasLifecycle resource, HasLifecycle previousVersion) {
        assertNotNull(resource.getLifeCycleStatisticalResource().getReplacesVersion());
        if (previousVersion instanceof DatasetVersion) {
            assertEquals(previousVersion.getLifeCycleStatisticalResource().getUrn(), resource.getLifeCycleStatisticalResource().getReplacesVersion().getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn());
        } else if (previousVersion instanceof PublicationVersion) {
            assertEquals(previousVersion.getLifeCycleStatisticalResource().getUrn(), resource.getLifeCycleStatisticalResource().getReplacesVersion().getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn());
        } else {
            fail("unknown resource type");
        }
    }

    private static void assertNotNullAutomaticallyFilledMetadataForAPublishedResource(HasLifecycle resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource, false);
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(resource, false);
        
        assertNotNull(resource.getLifeCycleStatisticalResource().getPublicationDate());
        assertNotNull(resource.getLifeCycleStatisticalResource().getPublicationUser());
        assertNotNull(resource.getLifeCycleStatisticalResource().getValidFrom());
        assertEquals(resource.getLifeCycleStatisticalResource().getPublicationDate(), resource.getLifeCycleStatisticalResource().getValidFrom());
        assertEquals(ProcStatusEnum.PUBLISHED, resource.getLifeCycleStatisticalResource().getProcStatus());

    }

    private static void assertNotNullAutomaticallyFilledMetadataOldPublished(HasLifecycle previousResource) {
        assertNotNullAutomaticallyFilledMetadataForAPublishedResource(previousResource);
        assertNotNull(previousResource.getLifeCycleStatisticalResource().getIsReplacedByVersion());
        assertNotNull(previousResource.getLifeCycleStatisticalResource().getValidTo());
    }
}
