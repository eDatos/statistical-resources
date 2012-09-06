package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;

public class SharedDatasetsSecurityUtils extends SharedSecurityUtils {

    //
    // CONCEPT SCHEMES
    //

    public static boolean canRetrieveDatasetByUrn(MetamacPrincipal metamacPrincipal) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasetVersions(MetamacPrincipal metamacPrincipal) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canCreateDataset(MetamacPrincipal metamacPrincipal) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateDataset(MetamacPrincipal metamacPrincipal, StatisticalResourceProcStatusEnum procStatus, StatisticalResourceTypeEnum type) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canDeleteDataset(MetamacPrincipal metamacPrincipal, StatisticalResourceTypeEnum type) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canFindDatasetsByCondition(MetamacPrincipal metamacPrincipal) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canSendDatasetToProductionValidation(MetamacPrincipal metamacPrincipal, StatisticalResourceTypeEnum type) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canSendDatasetToDiffusionValidation(MetamacPrincipal metamacPrincipal, StatisticalResourceTypeEnum type) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRejectDatasetValidation(MetamacPrincipal metamacPrincipal, StatisticalResourceProcStatusEnum procStatus, StatisticalResourceTypeEnum type) {
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canVersioningDataset(MetamacPrincipal metamacPrincipal, StatisticalResourceTypeEnum type) {
        return isAnyResourcesRole(metamacPrincipal);
    }

}
