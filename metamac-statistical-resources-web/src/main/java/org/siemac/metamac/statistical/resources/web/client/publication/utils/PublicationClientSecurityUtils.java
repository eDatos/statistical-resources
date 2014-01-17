package org.siemac.metamac.statistical.resources.web.client.publication.utils;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedPublicationsSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

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

    public static boolean canDeletePublicationVersion(ProcStatusEnum publicationProcStatus) {
        if (ProcStatusEnum.PUBLISHED.equals(publicationProcStatus)) {
            return false;
        }
        return SharedPublicationsSecurityUtils.canDeletePublicationVersion(getMetamacPrincipal());
    }

    public static boolean canVersionPublication() {
        return SharedPublicationsSecurityUtils.canVersionPublication(getMetamacPrincipal());
    }

    public static boolean canSendPublicationVersionToProductionValidation() {
        return SharedPublicationsSecurityUtils.canSendPublicationVersionToProductionValidation(getMetamacPrincipal());
    }

    public static boolean canSendPublicationVersionToDiffusionValidation() {
        return SharedPublicationsSecurityUtils.canSendPublicationVersionToDiffusionValidation(getMetamacPrincipal());
    }

    public static boolean canSendPublicationVersionToValidationRejected() {
        return SharedPublicationsSecurityUtils.canSendPublicationVersionToValidationRejected(getMetamacPrincipal());
    }

    public static boolean canPublishPublicationVersion() {
        return SharedPublicationsSecurityUtils.canPublishPublicationVersion(getMetamacPrincipal());
    }

    public static boolean canProgramPublicationPublicationVersion() {
        return SharedPublicationsSecurityUtils.canProgramPublicationPublicationVersion(getMetamacPrincipal());
    }

    public static boolean canCancelPublicationPublicationVersion() {
        return SharedPublicationsSecurityUtils.canCancelPublicationPublicationVersion(getMetamacPrincipal());
    }

    public static boolean canPreviewDataPublicationVersion() {
        return SharedPublicationsSecurityUtils.canPreviewDataPublicationVersion(getMetamacPrincipal());
    }

    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------

    public static boolean canCreateChapter() {
        return SharedPublicationsSecurityUtils.canCreateChapter(getMetamacPrincipal());
    }

    public static boolean canUpdateChapter() {
        return SharedPublicationsSecurityUtils.canUpdateChapter(getMetamacPrincipal());
    }

    public static boolean canUpdateChapterLocation() {
        return SharedPublicationsSecurityUtils.canUpdateChapterLocation(getMetamacPrincipal());
    }

    public static boolean canDeleteChapter() {
        return SharedPublicationsSecurityUtils.canDeleteChapter(getMetamacPrincipal());
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    public static boolean canCreateCube() {
        return SharedPublicationsSecurityUtils.canCreateCube(getMetamacPrincipal());
    }

    public static boolean canUpdateCube() {
        return SharedPublicationsSecurityUtils.canUpdateCube(getMetamacPrincipal());
    }

    public static boolean canUpdateCubeLocation() {
        return SharedPublicationsSecurityUtils.canUpdateCubeLocation(getMetamacPrincipal());
    }

    public static boolean canDeleteCube() {
        return SharedPublicationsSecurityUtils.canDeleteCube(getMetamacPrincipal());
    }

}
