package org.siemac.metamac.statistical.resources.web.client.collection.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface CollectionUiHandlers extends UiHandlers {

    void saveCollection(CollectionDto collectionDto);

}
