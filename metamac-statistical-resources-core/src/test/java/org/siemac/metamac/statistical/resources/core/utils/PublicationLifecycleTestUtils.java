package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public class PublicationLifecycleTestUtils {

    // *****************************************************
    // PRODUCTION VALIDATION
    // *****************************************************

    public static void prepareToProductionValidation(PublicationVersion publicationVersion) {
        LifecycleTestUtils.prepareToProductionValidationSiemac(publicationVersion);
        prepareToLifecycleCommonPublicationVersion(publicationVersion);
    }

    public static void fillAsProductionValidation(PublicationVersion publicationVersion) {
        prepareToProductionValidation(publicationVersion);
        LifecycleTestUtils.fillAsProductionValidationSiemac(publicationVersion);
    }

    // *****************************************************
    // DIFFUSION VALIDATION
    // *****************************************************

    public static void prepareToDiffusionValidation(PublicationVersion publicationVersion) {
        fillAsProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToDiffusionValidationSiemac(publicationVersion);
    }

    public static void fillAsDiffusionValidation(PublicationVersion publicationVersion) {
        prepareToDiffusionValidation(publicationVersion);
        LifecycleTestUtils.fillAsDiffusionValidationSiemac(publicationVersion);
    }

    // *****************************************************
    // VALIDATION REJECTED
    // *****************************************************

    public static void prepareToValidationRejected(PublicationVersion publicationVersion) {
        fillAsProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToValidationRejectedFromProductionValidationSiemac(publicationVersion);
    }

    public static void fillAsValidationRejected(PublicationVersion publicationVersion) {
        prepareToValidationRejected(publicationVersion);
        LifecycleTestUtils.fillAsValidationRejectedFromProductionValidationSiemac(publicationVersion);
    }

    // *****************************************************
    // PUBLISHING
    // *****************************************************

    public static void prepareToPublished(PublicationVersion publicationVersion) {
        fillAsDiffusionValidation(publicationVersion);
        LifecycleTestUtils.prepareToPublishingSiemac(publicationVersion);
    }

    public static void fillAsPublished(PublicationVersion publicationVersion) {
        prepareToPublished(publicationVersion);
        LifecycleTestUtils.fillAsPublishedSiemac(publicationVersion);
    }

    // *****************************************************
    // VERSIONING
    // *****************************************************

    public static void prepareToVersioning(PublicationVersion publicationVersion) {
        fillAsPublished(publicationVersion);
        LifecycleTestUtils.prepareToVersioningSiemac(publicationVersion);
    }

    public static void fillAsVersioned(PublicationVersion publicationVersion) {
        prepareToVersioning(publicationVersion);
        LifecycleTestUtils.fillAsVersionedSiemac(publicationVersion);
    }

    private static void prepareToLifecycleCommonPublicationVersion(PublicationVersion publicationVersion) {
        // Inherited fields that need customization based on Resource's type

        // FIXME: structure?
    }

}
