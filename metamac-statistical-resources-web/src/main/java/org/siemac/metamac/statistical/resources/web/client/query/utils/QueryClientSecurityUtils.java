package org.siemac.metamac.statistical.resources.web.client.query.utils;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

public class QueryClientSecurityUtils extends BaseClientSecurityUtils {

    // ------------------------------------------------------------------------
    // QUERY VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canCreateQuery() {
        return SharedQueriesSecurityUtils.canCreateQuery(getMetamacPrincipal());
    }

    public static boolean canUpdateQueryVersion(QueryVersionDto queryVersionDto) {
        return SharedQueriesSecurityUtils.canUpdateQueryVersion(getMetamacPrincipal());
    }

    public static boolean canMarkQueryVersionAsDiscontinued() {
        return SharedQueriesSecurityUtils.canMarkQueryVersionAsDiscontinued(getMetamacPrincipal());
    }

    public static boolean canDeleteQueryVersion(QueryVersionDto queryVersionDto) {
        return canDeleteQueryVersion(queryVersionDto.getProcStatus());
    }

    public static boolean canDeleteQueryVersion(QueryVersionBaseDto queryVersionDto) {
        return canDeleteQueryVersion(queryVersionDto.getProcStatus());
    }

    public static boolean canDeleteQueryVersion(ProcStatusEnum queryProcStatus) {
        if (ProcStatusEnum.PUBLISHED.equals(queryProcStatus)) {
            return false;
        }
        return SharedQueriesSecurityUtils.canDeleteQueryVersion(getMetamacPrincipal());
    }

    public static boolean canSendQueryVersionToProductionValidation() {
        return SharedQueriesSecurityUtils.canSendQueryVersionToProductionValidation(getMetamacPrincipal());
    }

    public static boolean canSendQueryVersionToDiffusionValidation() {
        return SharedQueriesSecurityUtils.canSendQueryVersionToDiffusionValidation(getMetamacPrincipal());
    }

    public static boolean canSendQueryVersionToValidationRejected() {
        return SharedQueriesSecurityUtils.canSendQueryVersionToValidationRejected(getMetamacPrincipal());
    }

    public static boolean canPublishQueryVersion() {
        return SharedQueriesSecurityUtils.canPublishQueryVersion(getMetamacPrincipal());
    }

    public static boolean canCancelProgrammedPublication() {
        return SharedQueriesSecurityUtils.canCancelPublicationQueryVersion(getMetamacPrincipal());
    }

    public static boolean canProgramQueryVersionPublication() {
        return SharedQueriesSecurityUtils.canProgramQueryVersionPublication(getMetamacPrincipal());
    }
}
