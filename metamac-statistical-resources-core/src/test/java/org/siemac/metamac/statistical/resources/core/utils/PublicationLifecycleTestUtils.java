package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;


public class PublicationLifecycleTestUtils {

    public static void prepareToProductionValidation(PublicationVersion publicationVersion) {
        LifecycleTestUtils.prepareToProductionValidation(publicationVersion);
        prepareToLifecycleCommonPublicationVersion(publicationVersion);
    }

    public static void prepareToDiffusionValidation(PublicationVersion publicationVersion) {
        prepareToProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToDiffusionValidation(publicationVersion);
    }

    public static void prepareToValidationRejected(PublicationVersion publicationVersion) {
        prepareToProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToValidationRejected(publicationVersion);
    }
    
    public static void prepareToPublished(PublicationVersion publicationVersion) {
        prepareToDiffusionValidation(publicationVersion);
        LifecycleTestUtils.prepareToPublished(publicationVersion);
    }
    
    public static void prepareToVersioning(PublicationVersion publicationVersion) {
        prepareToPublished(publicationVersion);
        LifecycleTestUtils.createPublished(publicationVersion);
    }

    public static void prepareToLifecycleCommonPublicationVersion(PublicationVersion publicationVersion) {
        // Inherited fields that need customization based on Resource's type
        /*String code = buildPublicationCode(publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), 1);
        publicationVersion.getSiemacMetadataStatisticalResource().setCode(code);
        publicationVersion.getSiemacMetadataStatisticalResource().setUrn(buildPublicationVersionUrn(code, publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        publicationVersion.getSiemacMetadataStatisticalResource().setType(StatisticalResourceTypeEnum.COLLECTION);*/
        
        //TODO: structure?
    }

}
