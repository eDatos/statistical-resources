package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedDatasetsSecurityUtils;

public class ResourcesSecurityUtils {

    //
    // CONCEPT SCHEMES
    //

    public static void canRetrieveDatasetByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveDatasetByUrn(getMetamacPrincipal(ctx))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canRetrieveDatasetVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRetrieveDatasetVersions(getMetamacPrincipal(ctx))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canCreateDataset(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canCreateDataset(getMetamacPrincipal(ctx))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canUpdateDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canUpdateDataset(getMetamacPrincipal(ctx), getProcStatus(datasetDto), getType(datasetDto),
                getOperacionCode(datasetDto))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canDeleteDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canDeleteDataset(getMetamacPrincipal(ctx), getType(datasetDto), getOperacionCode(datasetDto))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canFindDatasetsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canFindDatasetsByCondition(getMetamacPrincipal(ctx))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canSendDatasetToProductionValidation(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canSendDatasetToProductionValidation(getMetamacPrincipal(ctx), getType(datasetDto), getOperacionCode(datasetDto))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canSendDatasetToDiffusionValidation(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canSendDatasetToDiffusionValidation(getMetamacPrincipal(ctx), getType(datasetDto), getOperacionCode(datasetDto))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canRejectDatasetValidation(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canRejectDatasetValidation(getMetamacPrincipal(ctx), getProcStatus(datasetDto), getType(datasetDto),
                getOperacionCode(datasetDto))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    public static void canVersioningDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (!SharedDatasetsSecurityUtils.canVersioningDataset(getMetamacPrincipal(ctx), getType(datasetDto), getOperacionCode(datasetDto))) {
            throw new MetamacException(ServiceExceptionType.SECURITY_ACTION_NOT_ALLOWED, ctx.getUserId());
        }
    }

    /**
     * Retrieves MetamacPrincipal in ServiceContext
     */
    public static MetamacPrincipal getMetamacPrincipal(ServiceContext ctx) throws MetamacException {
        Object principalProperty = ctx.getProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE);
        if (principalProperty == null) {
            throw new MetamacException(ServiceExceptionType.SECURITY_PRINCIPAL_NOT_FOUND);
        }
        MetamacPrincipal metamacPrincipal = (MetamacPrincipal) principalProperty;
        if (!metamacPrincipal.getUserId().equals(ctx.getUserId())) {
            throw new MetamacException(ServiceExceptionType.SECURITY_PRINCIPAL_NOT_FOUND);
        }
        return metamacPrincipal;
    }

    //
    // PRIVATE METHODS
    //

    private static StatisticalResourceProcStatusEnum getProcStatus(DatasetDto datasetDto) {
        return datasetDto != null ? datasetDto.getLifeCycleMetadata().getProcStatus() : null;
    }

    private static StatisticalResourceTypeEnum getType(DatasetDto datasetDto) {
        return datasetDto != null ? datasetDto.getContentMetadata().getType() : null;
    }

    private static String getOperacionCode(DatasetDto datasetDto) {
        return datasetDto != null && datasetDto.getRelatedOperation() != null ? datasetDto.getRelatedOperation().getCode() : null;
    }

}
