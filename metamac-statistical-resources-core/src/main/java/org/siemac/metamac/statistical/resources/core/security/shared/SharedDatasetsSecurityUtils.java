package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;

public class SharedDatasetsSecurityUtils extends SharedSecurityUtils {

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public static boolean canCreateDatasource(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateDatasource(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasourceByUrn(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canDeleteDatasource(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasourcesByDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    public static boolean canFindDatasetsByCondition(MetamacPrincipal metamacPrincipal) {
        // TODO Auto-generated method stub
        return isAnyResourcesRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canCreateDataset(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canDeleteDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canFindDatasetsVersionsByCondition(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasetVersionByUrn(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasetVersions(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canVersionDataset(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canSendDatasetVersionToProductionValidation(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canSendDatasetVersionToDiffusionValidation(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }
    
    public static boolean canSendDatasetVersionToValidationRejected(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveLatestDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveLatestPublishedDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasetVersionDimensionsIds(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveCoverageForDatasetVersionDimension(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }
    
    public static boolean canFilterCoverageForDatasetVersionDimension(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canFindStatisticOfficialities(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canImportDatasourcesInDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canImportDatasourcesInStatisticalOperation(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }
}
