package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;
import org.siemac.metamac.statistical.resources.web.client.view.handlers.LifeCycleUiHandlers;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetMetadataTabUiHandlers extends LifeCycleUiHandlers {

    void retrieveDataset(String datasetIdentifier);
    
    void saveDataset(DataSetDto datasetDto);
}
