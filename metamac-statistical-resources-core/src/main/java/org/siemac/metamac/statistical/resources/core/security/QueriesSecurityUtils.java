package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;

public class QueriesSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    public static void canRetrieveQueryByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueryByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveQueries(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueries(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCreateQuery(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canCreateQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindQueriesByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canFindQueriesByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateQuery(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canUpdateQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canMarkQueryAsDiscontinued(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canMarkQueryAsDiscontinued(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }        
    }

    public static void canDeleteQuery(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canDeleteQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        } 
    }

}
