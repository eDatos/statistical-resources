package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;

public class SharedDatasetsSecurityUtils extends SharedSecurityUtils {

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public static boolean canCreateDatasource(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

}
