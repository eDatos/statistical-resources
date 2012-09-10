package org.siemac.metamac.statistical.resources.web.client.collection.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface CollectionListUiHandlers extends UiHandlers {

    void createCollection(CollectionDto collectionDto);
    void deleteCollection(List<String> urns);
    void retrieveCollections(int firstResult, int maxResults, String collection);
    void goToCollection(String urn);

}
