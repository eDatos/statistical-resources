package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;

public class QueriesSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // QUERIES VERSIONS
    // ------------------------------------------------------------------------

    public static void canFindQueriesByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canFindQueriesByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // ------------------------------------------------------------------------
    // QUERIES VERSIONS
    // ------------------------------------------------------------------------

    public static void canRetrieveQueryVersionByUrn(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        String operationCode = queryVersion.getLifeCycleStatisticalResource().getStatisticalOperation().getCode();
        ProcStatusEnum procStatus = queryVersion.getLifeCycleStatisticalResource().getEffectiveProcStatus();
        if (!SharedQueriesSecurityUtils.canRetrieveQueryVersionByUrn(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveQueriesVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveQueriesVersions(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCreateQuery(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canCreateQuery(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindQueriesVersionsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canFindQueriesVersionsByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canUpdateQueryVersion(getMetamacPrincipal(ctx), queryVersionDto.getStatisticalOperation().getCode(), queryVersionDto.getProcStatus())) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canMarkQueryVersionAsDiscontinued(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canMarkQueryVersionAsDiscontinued(getMetamacPrincipal(ctx), queryVersionDto.getStatisticalOperation().getCode(), queryVersionDto.getProcStatus())) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canDeleteQueryVersion(getMetamacPrincipal(ctx), queryVersionDto.getStatisticalOperation().getCode(), queryVersionDto.getProcStatus())) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestQueryVersion(ServiceContext ctx, String operationCode, ProcStatusEnum procStatus) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveLatestQueryVersion(getMetamacPrincipal(ctx), operationCode, procStatus)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveLatestPublishedQueryVersion(ServiceContext ctx) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canRetrieveLatestPublishedQueryVersion(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendQueryVersionToProductionValidation(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canSendQueryVersionToProductionValidation(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }

    }

    public static void canSendQueryVersionToDiffusionValidation(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canSendQueryVersionToDiffusionValidation(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendQueryVersionToValidationRejected(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canSendQueryVersionToValidationRejected(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canPublishQueryVersion(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canPublishQueryVersion(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canCancelPublicationQueryVersion(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canCancelPublicationQueryVersion(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canVersionQueryVersion(ServiceContext ctx, String operationCode) throws MetamacException {
        if (!SharedQueriesSecurityUtils.canVersionQueryVersion(getMetamacPrincipal(ctx), operationCode)) {
            throwExceptionIfOperationNotAllowed(ctx);
        }

    }

}
