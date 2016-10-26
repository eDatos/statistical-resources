package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;

public class SharedQueriesSecurityUtils extends SharedSecurityUtils {

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    public static boolean canFindQueriesByCondition(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // QUERIES VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canRetrieveQueryVersionByUrn(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveQueriesVersions(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canCreateQuery(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canFindQueriesVersionsByCondition(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canUpdateQueryVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canMarkQueryVersionAsDiscontinued(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canDeleteQueryVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveLatestQueryVersion(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canPreviewQueryData(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveLatestPublishedQueryVersion(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canSendQueryVersionToProductionValidation(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canSendQueryVersionToDiffusionValidation(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION, StatisticalResourcesRoleEnum.TECNICO_APOYO_PRODUCCION);
    }

    public static boolean canSendQueryVersionToValidationRejected(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION, StatisticalResourcesRoleEnum.TECNICO_APOYO_PRODUCCION,
                StatisticalResourcesRoleEnum.TECNICO_DIFUSION, StatisticalResourcesRoleEnum.TECNICO_APOYO_DIFUSION);
    }

    public static boolean canPublishQueryVersion(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, DIFFUSION_ROLES);
    }

    public static boolean canVersionQueryVersion(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

}
