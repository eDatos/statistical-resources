package org.siemac.metamac.statistical.resources.web.client.collection.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.view.handlers.LifeCycleUiHandlers;


public interface CollectionMetadataTabUiHandlers extends LifeCycleUiHandlers {
    
    void retrieveAgencies(int firstResult, int maxResults, String queryText);
    
    void saveCollection(CollectionDto collectionDto);
}
