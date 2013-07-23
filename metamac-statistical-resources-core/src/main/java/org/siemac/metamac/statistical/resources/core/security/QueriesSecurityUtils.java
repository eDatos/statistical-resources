package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;

public class QueriesSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // QUERIES VERSIONS
    // ------------------------------------------------------------------------

    public static void canFindQueriesByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canFindQueriesByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // ------------------------------------------------------------------------
    // QUERIES VERSIONS
    // ------------------------------------------------------------------------

    public static void canRetrieveQueryVersionByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueryVersionByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveQueriesVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueriesVersions(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCreateQuery(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canCreateQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindQueriesVersionsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canFindQueriesVersionsByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateQueryVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canUpdateQueryVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canMarkQueryVersionAsDiscontinued(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canMarkQueryVersionAsDiscontinued(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteQueryVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canDeleteQueryVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestQueryVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveLatestQueryVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestPublishedQueryVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveLatestPublishedQueryVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
}
