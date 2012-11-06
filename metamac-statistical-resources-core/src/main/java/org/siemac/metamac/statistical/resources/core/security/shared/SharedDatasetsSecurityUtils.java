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

    public static boolean canUpdateDatasource(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasourceByUrn(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canDeleteDatasource(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveDatasourcesByDatasetVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

}
