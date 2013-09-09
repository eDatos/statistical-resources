package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;


public class QueryLifecycleTestUtils {

    public static void prepareToProductionValidation(QueryVersion queryVersion) {
        LifecycleTestUtils.prepareToProductionValidation(queryVersion);
        prepareToLifecycleCommonQueryVersion(queryVersion);
    }

    public static void prepareToDiffusionValidation(QueryVersion queryVersion) {
        prepareToProductionValidation(queryVersion);
        LifecycleTestUtils.prepareToDiffusionValidation(queryVersion);
    }

    public static void prepareToValidationRejected(QueryVersion queryVersion) {
        prepareToProductionValidation(queryVersion);
        LifecycleTestUtils.prepareToValidationRejected(queryVersion);
    }
    
    public static void prepareToPublished(QueryVersion queryVersion) {
        prepareToDiffusionValidation(queryVersion);
        LifecycleTestUtils.prepareToPublished(queryVersion);
    }
    
    public static void prepareToVersioning(QueryVersion queryVersion) {
        prepareToPublished(queryVersion);
        if (!ProcStatusEnum.PUBLISHED.equals(queryVersion.getDatasetVersion().getSiemacMetadataStatisticalResource().getProcStatus())) {
            DatasetLifecycleTestUtils.prepareToVersioning(queryVersion.getDatasetVersion());
        }
        LifecycleTestUtils.createPublished(queryVersion);
    }

    public static void prepareToLifecycleCommonQueryVersion(QueryVersion queryVersion) {
        //TODO: structure?
    }

}
