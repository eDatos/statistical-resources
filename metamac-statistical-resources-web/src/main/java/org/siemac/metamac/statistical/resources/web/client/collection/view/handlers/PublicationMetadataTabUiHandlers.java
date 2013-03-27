package org.siemac.metamac.statistical.resources.web.client.collection.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.view.handlers.LifeCycleUiHandlers;


public interface PublicationMetadataTabUiHandlers extends LifeCycleUiHandlers {
    
    void retrieveAgencies(int firstResult, int maxResults, String queryText);
    
    void savePublication(PublicationDto collectionDto);
}
