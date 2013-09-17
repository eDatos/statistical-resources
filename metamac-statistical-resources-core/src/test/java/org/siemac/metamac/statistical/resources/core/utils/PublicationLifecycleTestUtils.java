package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public class PublicationLifecycleTestUtils {

    public static void prepareToProductionValidation(PublicationVersion publicationVersion) {
        LifecycleTestUtils.prepareToProductionValidationSiemac(publicationVersion);
        prepareToLifecycleCommonPublicationVersion(publicationVersion);
    }

    public static void fillAsProductionValidation(PublicationVersion publicationVersion) {
        LifecycleTestUtils.fillAsProductionValidationSiemac(publicationVersion);
    }

    public static void prepareToDiffusionValidation(PublicationVersion publicationVersion) {
        prepareToProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToDiffusionValidationSiemac(publicationVersion);
    }

    public static void fillAsDiffusionValidation(PublicationVersion publicationVersion) {
        LifecycleTestUtils.fillAsDiffusionValidationSiemac(publicationVersion);
    }

    public static void prepareToValidationRejected(PublicationVersion publicationVersion) {
        prepareToProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToValidationRejectedFromProductionValidationSiemac(publicationVersion);
    }

    public static void fillAsValidationRejected(PublicationVersion publicationVersion) {
        LifecycleTestUtils.fillAsValidationRejectedFromProductionValidationSiemac(publicationVersion);
    }

    public static void prepareToPublished(PublicationVersion publicationVersion) {
        prepareToDiffusionValidation(publicationVersion);
        LifecycleTestUtils.prepareToPublishingSiemac(publicationVersion);
    }

    public static void fillAsPublished(PublicationVersion publicationVersion) {
        LifecycleTestUtils.fillAsPublishedSiemac(publicationVersion);
    }

    public static void prepareToVersioning(PublicationVersion publicationVersion) {
        prepareToPublished(publicationVersion);
        LifecycleTestUtils.prepareToVersioningSiemac(publicationVersion);
    }

    public static void fillAsVersioned(PublicationVersion publicationVersion) {
        LifecycleTestUtils.fillAsVersionedSiemac(publicationVersion);
    }

    public static void prepareToLifecycleCommonPublicationVersion(PublicationVersion publicationVersion) {
        // Inherited fields that need customization based on Resource's type
        /*
         * String code = buildPublicationCode(publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), 1);
         * publicationVersion.getSiemacMetadataStatisticalResource().setCode(code);
         * publicationVersion.getSiemacMetadataStatisticalResource().setUrn(buildPublicationVersionUrn(code, publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
         * publicationVersion.getSiemacMetadataStatisticalResource().setType(StatisticalResourceTypeEnum.COLLECTION);
         */

        // FIXME: structure?
    }

    public static void prepareAsProductionValidation(PublicationVersion publicationVersion) {
        prepareToDiffusionValidation(publicationVersion);
    }

    public static void prepareAsDiffusionValidation(PublicationVersion publicationVersion) {
        prepareToPublished(publicationVersion);
    }

    public static void prepareAsValidationRejected(PublicationVersion publicationVersion) {
        prepareToPublished(publicationVersion);
    }

}
