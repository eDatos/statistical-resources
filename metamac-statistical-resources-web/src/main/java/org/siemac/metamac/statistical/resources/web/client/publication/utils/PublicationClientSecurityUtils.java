package org.siemac.metamac.statistical.resources.web.client.publication.utils;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedPublicationsSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

// TODO: Add real rules not only "true"
public class PublicationClientSecurityUtils extends BaseClientSecurityUtils {

    // ------------------------------------------------------------------------
    // PUBLICATION VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canCreatePublication() {
        return SharedPublicationsSecurityUtils.canCreatePublication(getMetamacPrincipal());
    }

    public static boolean canUpdatePublicationVersion(PublicationVersionDto publicationVersionDto) {
        return SharedPublicationsSecurityUtils.canUpdatePublicationVersion(getMetamacPrincipal());
    }

    public static boolean canDeletePublicationVersion(PublicationVersionDto publicationVersionDto) {
        return SharedPublicationsSecurityUtils.canDeletePublicationVersion(getMetamacPrincipal());
    }
}
