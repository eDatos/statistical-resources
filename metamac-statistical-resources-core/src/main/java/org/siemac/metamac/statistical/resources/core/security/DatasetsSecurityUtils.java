package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedDatasetsSecurityUtils;

public class DatasetsSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public static void canCreateDatasource(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canCreateDatasource(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateDatasource(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canUpdateDatasource(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveDatasourceByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveDatasourceByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteDatasource(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canDeleteDatasource(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveDatasourcesByDatasetVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveDatasourcesByDatasetVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public static void canCreateDataset(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canCreateDataset(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
}
