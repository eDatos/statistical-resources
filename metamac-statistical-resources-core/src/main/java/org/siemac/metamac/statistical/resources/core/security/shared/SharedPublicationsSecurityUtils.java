package org.siemac.metamac.statistical.resources.core.security.shared;

import org.siemac.metamac.sso.client.MetamacPrincipal;

public class SharedPublicationsSecurityUtils extends SharedSecurityUtils {

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    public static boolean canCreatePublication(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdatePublication(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canDeletePublication(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canFindPublicationsByCondition(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrievePublicationByUrn(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveLatestPublicationVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveLatestPublishedPublicationVersion(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }
    
    public static boolean canRetrievePublicationVersions(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canVersionPublication(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrievePublicationStructure(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------

    // TODO: Comprobar si en la seguridad de los capitulos tambien hay que tener en cuenta algo relacionado con la version de la publicacion

    public static boolean canCreateChapter(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateChapter(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateChapterLocation(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveChapter(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canDeleteChapter(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    // TODO: Comprobar si en la seguridad de los cubos tambien hay que tener en cuenta algo relacionado con la version de la publicacion

    public static boolean canCreateCube(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateCube(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canUpdateCubeLocation(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canRetrieveCube(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }

    public static boolean canDeleteCube(MetamacPrincipal metamacPrincipal) {
        // TODO: Poner los roles correctos
        return isAnyResourcesRole(metamacPrincipal);
    }
}
