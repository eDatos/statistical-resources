package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.view.handlers.LifeCycleUiHandlers;

public interface DatasetMetadataTabUiHandlers extends LifeCycleUiHandlers {

    void retrieveDataset(String datasetIdentifier);
    
    void saveDataset(DatasetDto datasetDto);
}
