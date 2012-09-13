package org.siemac.metamac.statistical.resources.web.client.collection.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.view.handlers.LifeCycleUiHandlers;

public interface CollectionUiHandlers extends LifeCycleUiHandlers {

    void saveCollection(CollectionDto collectionDto);

}
