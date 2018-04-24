package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;

public class SharedMultidatasetsSecurityUtils extends SharedSecurityUtils {

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    public static boolean canFindMultidatasets(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // PUBLICATIONS VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canCreateMultidataset(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canUpdateMultidatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canDeleteMultidatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canFindMultidatasetsVersionsByCondition(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveMultidatasetVersionByUrn(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveLatestMultidatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveLatestPublishedMultidatasetVersion(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canRetrieveMultidatasetVersions(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canVersionMultidataset(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canRetrieveMultidatasetVersionStructure(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canSendMultidatasetVersionToProductionValidation(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canSendMultidatasetVersionToDiffusionValidation(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION, StatisticalResourcesRoleEnum.TECNICO_APOYO_PRODUCCION);
    }

    public static boolean canSendMultidatasetVersionToValidationRejected(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION, StatisticalResourcesRoleEnum.TECNICO_APOYO_PRODUCCION,
                StatisticalResourcesRoleEnum.TECNICO_DIFUSION, StatisticalResourcesRoleEnum.TECNICO_APOYO_DIFUSION);
    }

    public static boolean canPublishMultidatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, DIFFUSION_ROLES);
    }

    public static boolean canPreviewDataMultidatasetVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    public static boolean canCreateMultidatasetCube(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canUpdateMultidatasetCube(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canUpdateMultidatasetCubeLocation(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveMultidatasetCube(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canDeleteMultidatasetCube(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

}
