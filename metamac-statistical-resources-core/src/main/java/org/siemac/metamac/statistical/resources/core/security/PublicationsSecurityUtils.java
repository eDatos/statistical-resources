package org.siemac.metamac.statistical.resources.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedPublicationsSecurityUtils;

public class PublicationsSecurityUtils extends SecurityUtils {

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    public static void canCreatePublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canCreatePublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdatePublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canUpdatePublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeletePublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canDeletePublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindPublicationsByCondition(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canFindPublicationsByCondition(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrievePublicationByUrn(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canRetrievePublicationByUrn(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrievePublicationVersions(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canRetrievePublicationVersions(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canVersionPublication(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canVersionPublication(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrievePublicationStructure(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canRetrievePublicationStructure(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
    
    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------
    
    public static void canCreateChapter(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canCreateChapter(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateChapter(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canUpdateChapter(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateChapterLocation(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canUpdateChapterLocation(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveChapter(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canRetrieveChapter(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteChapter(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canDeleteChapter(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------
    
    public static void canCreateCube(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canCreateCube(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateCube(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canUpdateCube(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canUpdateCubeLocation(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canUpdateCubeLocation(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canRetrieveCube(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canRetrieveCube(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canDeleteCube(ServiceContext ctx) throws MetamacException {
        if (!SharedPublicationsSecurityUtils.canDeleteCube(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

}
