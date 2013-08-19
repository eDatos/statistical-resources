package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;

public interface PublicationUiHandlers extends UiHandlers {

    void goToPublicationMetadata();
    void goToPublicationStructure();
    public void goToPublicationVersion(String urn);
}
