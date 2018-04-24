package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedMultidatasetsSecurityUtils;

public class MultidatasetsSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // MULTIDATASETS
    // ------------------------------------------------------------------------

    public static void canFindMultidatasetsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canFindMultidatasets(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // ------------------------------------------------------------------------
    // MULTIDATASETS VERSIONS
    // ------------------------------------------------------------------------

    public static void canCreateMultidataset(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canCreateMultidataset(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateMultidatasetVersion(ServiceContext ctx, MultidatasetVersionDto multidatasetVersionDto) throws MetamacException {
        String operationCode = multidatasetVersionDto.getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetVersionDto.getProcStatus();
        if (!SharedMultidatasetsSecurityUtils.canUpdateMultidatasetVersion(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteMultidatasetVersion(ServiceContext ctx, MultidatasetVersionDto multidatasetVersionDto) throws MetamacException {
        String operationCode = multidatasetVersionDto.getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetVersionDto.getProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canDeleteMultidatasetVersion(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindMultidatasetsVersionsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canFindMultidatasetsVersionsByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveMultidatasetVersionByUrn(ServiceContext ctx, MultidatasetVersion multidatasetVersion) throws MetamacException {
        String operationCode = multidatasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetVersion.getSiemacMetadataStatisticalResource().getEffectiveProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canRetrieveMultidatasetVersionByUrn(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestMultidatasetVersion(ServiceContext ctx, String operationCode, ProcStatusEnum procStatus) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canRetrieveLatestMultidatasetVersion(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestPublishedMultidatasetVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canRetrieveLatestPublishedMultidatasetVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveMultidatasetVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canRetrieveMultidatasetVersions(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canVersionMultidataset(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canVersionMultidataset(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveMultidatasetVersionStructure(ServiceContext ctx, MultidatasetVersion multidatasetVersion) throws MetamacException {
        String operationCode = multidatasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetVersion.getSiemacMetadataStatisticalResource().getEffectiveProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canRetrieveMultidatasetVersionStructure(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendMultidatasetVersionToProductionValidation(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canSendMultidatasetVersionToProductionValidation(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendMultidatasetVersionToDiffusionValidation(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canSendMultidatasetVersionToDiffusionValidation(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendMultidatasetVersionToValidationRejected(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canSendMultidatasetVersionToValidationRejected(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canPublishMultidatasetVersion(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canPublishMultidatasetVersion(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canResendPublishedMultidatasetVersionStreamMessage(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedMultidatasetsSecurityUtils.canPublishMultidatasetVersion(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    public static void canCreateMultidatasetCube(ServiceContext ctx, MultidatasetVersion multidatasetVersion) throws MetamacException {
        String operationCode = multidatasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetVersion.getSiemacMetadataStatisticalResource().getEffectiveProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canCreateMultidatasetCube(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateMultidatasetCube(ServiceContext ctx, MultidatasetCube multidatasetCube) throws MetamacException {
        String operationCode = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getEffectiveProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canUpdateMultidatasetCube(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveMultidatasetCube(ServiceContext ctx, MultidatasetCube multidatasetCube) throws MetamacException {
        String operationCode = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getEffectiveProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canRetrieveMultidatasetCube(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteMultidatasetCube(ServiceContext ctx, MultidatasetCube multidatasetCube) throws MetamacException {
        String operationCode = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getEffectiveProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canDeleteMultidatasetCube(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateMultidatasetCubeLocation(ServiceContext ctx, MultidatasetCube multidatasetCube) throws MetamacException {
        String operationCode = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = multidatasetCube.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getEffectiveProcStatus();

        if (!SharedMultidatasetsSecurityUtils.canUpdateMultidatasetCubeLocation(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }

    }

}
