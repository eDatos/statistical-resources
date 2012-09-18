package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetDatasourcesTabUiHandlers extends UiHandlers {

    void retrieveDatasourcesByDataset(String datasetUrn, int firstResult, int maxResults);
    
}
