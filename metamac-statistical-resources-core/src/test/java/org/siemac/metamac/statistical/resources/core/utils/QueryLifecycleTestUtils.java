package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public class QueryLifecycleTestUtils {

    public static void prepareToProductionValidation(QueryVersion queryVersion) {
        LifecycleTestUtils.prepareToProductionValidationLifecycle(queryVersion);
        prepareToLifecycleCommonQueryVersion(queryVersion);
    }

    public static void fillAsProductionValidation(QueryVersion queryVersion) {
        LifecycleTestUtils.fillAsProductionValidationLifecycle(queryVersion);
    }

    public static void prepareToDiffusionValidation(QueryVersion queryVersion) {
        prepareToProductionValidation(queryVersion);
        LifecycleTestUtils.prepareToDiffusionValidationLifecycle(queryVersion);
    }

    public static void fillAsDiffusionValidation(QueryVersion queryVersion) {
        LifecycleTestUtils.fillAsDiffusionValidationLifecycle(queryVersion);
    }

    public static void prepareToValidationRejected(QueryVersion queryVersion) {
        prepareToProductionValidation(queryVersion);
        LifecycleTestUtils.prepareToValidationRejectedFromProductionValidationLifecycle(queryVersion);
    }

    public static void fillAsValidationRejected(QueryVersion queryVersion) {
        LifecycleTestUtils.fillAsValidationRejectedFromProductionValidationLifecycle(queryVersion);
    }

    public static void prepareToPublished(QueryVersion queryVersion) {
        prepareToDiffusionValidation(queryVersion);
        LifecycleTestUtils.prepareToPublishingLifecycle(queryVersion);

    }
    public static void fillAsPublished(QueryVersion queryVersion) {
        LifecycleTestUtils.fillAsPublishedLifecycle(queryVersion);
    }

    public static void prepareToVersioning(QueryVersion queryVersion) {
        prepareToPublished(queryVersion);
        if (!ProcStatusEnum.PUBLISHED.equals(queryVersion.getDatasetVersion().getSiemacMetadataStatisticalResource().getProcStatus())) {
            DatasetLifecycleTestUtils.prepareToVersioning(queryVersion.getDatasetVersion());
        }
        LifecycleTestUtils.prepareToVersioningLifecycle(queryVersion);
    }

    public static void fillAsVersioned(QueryVersion queryVersion) {
        LifecycleTestUtils.fillAsVersionedLifecycle(queryVersion);
    }

    public static void prepareToLifecycleCommonQueryVersion(QueryVersion queryVersion) {
        if (queryVersion.getType() == null) {
            queryVersion.setType(QueryTypeEnum.FIXED);
        }
        if (queryVersion.getStatus() == null) {
            queryVersion.setStatus(QueryStatusEnum.ACTIVE);
        }
        // TODO: structure?
    }

}
