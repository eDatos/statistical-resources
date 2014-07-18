package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedConstraintsSecurityUtils;

public class ConstraintsSecurityUtils extends SecurityUtils {

    public static void canFindContentConstraintsForArtefact(ServiceContext ctx) throws MetamacException {
        if (!SharedConstraintsSecurityUtils.canFindContentConstraintsForArtefact(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCreateContentConstraint(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedConstraintsSecurityUtils.canCreateContentConstraint(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveContentConstraintByUrn(ServiceContext ctx, String operationCode, ProcStatusEnum procStatus) throws MetamacException {
        if (SharedConstraintsSecurityUtils.canRetrieveContentConstraintByUrn(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteContentConstraint(ServiceContext ctx, String operationCode, ProcStatusEnum procStatus) throws MetamacException {
        if (!SharedConstraintsSecurityUtils.canDeleteContentConstraint(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSaveForContentConstraint(ServiceContext ctx, String operationCode, ProcStatusEnum procStatus) throws MetamacException {
        if (!SharedConstraintsSecurityUtils.canSaveForContentConstraint(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void deleteRegion(ServiceContext ctx, String operationCode, ProcStatusEnum procStatus) throws MetamacException {
        if (!SharedConstraintsSecurityUtils.canDeleteContentConstraint(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveRegionForContentConstraint(ServiceContext ctx, String operationCode, ProcStatusEnum procStatus) throws MetamacException {
        if (!SharedConstraintsSecurityUtils.canRetrieveRegionForContentConstraint(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

}
