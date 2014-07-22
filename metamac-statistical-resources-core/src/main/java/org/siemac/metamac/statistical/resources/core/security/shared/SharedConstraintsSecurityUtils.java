package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class SharedConstraintsSecurityUtils extends SharedSecurityUtils {

    public static boolean canFindContentConstraintsForArtefact(MetamacPrincipal metamacPrincipal) {
        return isAnyStatisticalResourceRole(metamacPrincipal);
    }

    public static boolean canCreateContentConstraint(MetamacPrincipal metamacPrincipal, String operationCode) {
        return isOperationAllowed(metamacPrincipal, operationCode, PRODUCTION_ROLES);
    }

    public static boolean canRetrieveContentConstraintByUrn(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canDeleteContentConstraint(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canSaveForContentConstraint(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canModifyStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }

    public static boolean canRetrieveRegionForContentConstraint(MetamacPrincipal metamacPrincipal, String operationCode, ProcStatusEnum procStatus) {
        return canRetrieveStatisticalResource(metamacPrincipal, operationCode, procStatus);
    }
}
