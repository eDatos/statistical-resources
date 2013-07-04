package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;

public class QueriesSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    public static void canRetrieveQueryVersionByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueryByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveQueriesVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueries(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCreateQuery(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canCreateQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindQueriesVersionsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canFindQueriesByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateQueryVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canUpdateQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canMarkQueryVersionAsDiscontinued(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canMarkQueryAsDiscontinued(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }        
    }

    public static void canDeleteQueryVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canDeleteQuery(getMetamacPrincipal(ctx))) {
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
