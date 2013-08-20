package org.siemac.metamac.statistical.resources.web.client.query.utils;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

public class QueryClientSecurityUtils extends BaseClientSecurityUtils {

    // ------------------------------------------------------------------------
    // QUERY VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canCreateQuery() {
        return SharedQueriesSecurityUtils.canCreateQuery(getMetamacPrincipal());
    }

    public static boolean canUpdatePublicationVersion(QueryVersionDto queryVersionDto) {
        return SharedQueriesSecurityUtils.canUpdateQueryVersion(getMetamacPrincipal());
    }

    public static boolean canDeletePublicationVersion(QueryVersionDto queryVersionDto) {
        return SharedQueriesSecurityUtils.canDeleteQueryVersion(getMetamacPrincipal());
    }

    public static boolean canMarkQueryVersionAsDiscontinued() {
        return SharedQueriesSecurityUtils.canMarkQueryVersionAsDiscontinued(getMetamacPrincipal());
    }

    public static boolean canDeleteQueryVersion() {
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
}
