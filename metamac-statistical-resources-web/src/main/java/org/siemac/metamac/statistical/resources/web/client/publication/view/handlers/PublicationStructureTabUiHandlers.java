package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

public interface PublicationStructureTabUiHandlers extends BaseUiHandlers {

    void updateElementLocation(String publicationVersionUrn, String elementUrn, String parentTargetUrn, Long orderInLevel);
}
