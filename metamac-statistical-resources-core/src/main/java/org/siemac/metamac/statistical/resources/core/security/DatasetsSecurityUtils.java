package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
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
    // DATASETS
    // ------------------------------------------------------------------------

    public static void canFindDatasetsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canFindDatasetsByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    public static void canCreateDataset(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canCreateDataset(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateDatasetVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canUpdateDatasetVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteDatasetVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canDeleteDatasetVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindDatasetsVersionsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canFindDatasetsVersionsByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveDatasetVersionByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveDatasetVersionByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveDatasetVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveDatasetVersions(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canVersionDataset(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canVersionDataset(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendDatasetVersionToProductionValidation(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canSendDatasetVersionToProductionValidation(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendDatasetVersionToDiffusionValidation(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canSendDatasetVersionToDiffusionValidation(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
    
    public static void canSendDatasetVersionToValidationRejected(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canSendDatasetVersionToValidationRejected(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestDatasetVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveLatestDatasetVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestPublishedDatasetVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveLatestPublishedDatasetVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveDatasetVersionDimensionsIds(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveDatasetVersionDimensionsIds(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveCoverageForDatasetVersionDimension(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveCoverageForDatasetVersionDimension(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindStatisticOfficialities(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canFindStatisticOfficialities(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canImportDatasourcesInDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canImportDatasourcesInDatasetVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
    
    public static void canImportDatasourcesInStatisticalOperation(ServiceContext ctx, String statisticalOperationUrn) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canImportDatasourcesInStatisticalOperation(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
}
