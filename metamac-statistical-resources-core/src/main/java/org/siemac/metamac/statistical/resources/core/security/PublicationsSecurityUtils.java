package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedPublicationsSecurityUtils;

public class PublicationsSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    public static void canCreatePublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canCreatePublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdatePublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canUpdatePublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeletePublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canDeletePublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindPublicationsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canFindPublicationsByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrievePublicationByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canRetrievePublicationByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrievePublicationVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canRetrievePublicationVersions(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canVersionPublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canVersionPublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
}
