package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;

public class SharedQueriesSecurityUtils extends SharedSecurityUtils {

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------
    
    public static boolean canRetrieveQueryByUrn(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveQueries(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canCreateQuery(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canFindQueriesByCondition(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateQuery(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }
    
    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public static boolean canCreateDatasource(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }
}
