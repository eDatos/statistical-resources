package org.siemac.metamac.statistical.resources.core.security.shared;

import java.util.Date;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;

public class SharedDatasetsSecurityUtils extends SharedSecurityUtils {

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public static boolean canCreateDatasource(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canUpdateDatasource(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canRetrieveDatasourceByUrn(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canDeleteDatasource(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canRetrieveDatasourcesByDatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    public static boolean canFindDatasetsByCondition(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canCreateDataset(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canUpdateDatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canDeleteDatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canFindDatasetsVersionsByCondition(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasetVersionByUrn(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveDatasetVersions(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canVersionDataset(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canSendDatasetVersionToProductionValidation(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canSendDatasetVersionToDiffusionValidation(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION, StatisticalResourcesRoleEnum.TECNICO_APOYO_PRODUCCION);
    }

    public static boolean canSendDatasetVersionToValidationRejected(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION, StatisticalResourcesRoleEnum.TECNICO_APOYO_PRODUCCION);
    }

    public static boolean canPublishDataset(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canCancelPublicationDataset(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveLatestDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveLatestPublishedDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasetVersionDimensionsIds(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveCoverageForDatasetVersionDimension(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canFilterCoverageForDatasetVersionDimension(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canFindStatisticOfficialities(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canImportDatasourcesInDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canImportDatasources(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canImportDatasourcesInStatisticalOperation(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasetVersionMainCoverages(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canCreateAttributeInstance(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canUpdateAttributeInstance(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canDeleteAttributeInstance(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveAttributeInstances(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canPreviewDatasetData(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // CATEGORISATIONS
    // ------------------------------------------------------------------------

    public static boolean canCreateCategorisation(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveCategorisationByUrn(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canDeleteDatasetCategorisation(MetamacPrincipal metamacPrincipal, Date validFromEffective) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        if (isAnyStatisticalResourceRole(metamacPrincipal)) {
            return validFromEffective == null;
        }
        return false;
    }

    public static boolean canRetrieveCategorisationsByDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canEndCategorisationValidity(MetamacPrincipal metamacPrincipal, Date validFromEffective, Date validToEffective) {
        // TODO: Poner los roles correctos (METAMAC-1845)
        if (isAnyStatisticalResourceRole(metamacPrincipal)) {
            return validFromEffective != null && validFromEffective.before(new Date()) && validToEffective == null;
        }
        return false;
    }

}
