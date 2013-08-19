package org.siemac.metamac.statistical.resources.web.client.query.utils;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedQueriesSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

// FIXME: use correct rules
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
}
