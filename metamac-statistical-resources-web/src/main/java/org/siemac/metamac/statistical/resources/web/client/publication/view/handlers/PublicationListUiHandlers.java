package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface PublicationListUiHandlers extends UiHandlers {

    void createPublication(PublicationDto collectionDto);
    void deletePublication(List<String> urns);
    void retrievePublications(int firstResult, int maxResults, String collection);
    void goToPublication(String urn);
}
