package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;

public class QueriesSecurityUtils extends StatisticalResourcesSecurityUtils {

    public static void canRetrieveQueryByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueryByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfNotAllowed(ctx);
        }
    }

    public static void canRetrieveQueries(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueries(getMetamacPrincipal(ctx))) {
            throwExceptionIfNotAllowed(ctx);
        }
    }
    
    public static void canCreateQuery(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canCreateQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfNotAllowed(ctx);
        }
    }

    public static void canFindQueriesByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canFindQueriesByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfNotAllowed(ctx);
        }
    }
    
    
    public static void canUpdateQuery(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canUpdateQuery(getMetamacPrincipal(ctx))) {
            throwExceptionIfNotAllowed(ctx);
        }
    }
    
    private static void throwExceptionIfNotAllowed(ServiceContext ctx) throws MetamacException {
        throw new MetamacException(ServiceExceptionType.SECURITY_OPERATION_NOT_ALLOWED, ctx.getUserId());
    }



}
