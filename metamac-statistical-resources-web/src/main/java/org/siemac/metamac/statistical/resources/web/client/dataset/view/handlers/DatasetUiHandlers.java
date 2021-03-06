package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetUiHandlers extends UiHandlers {

    public void goToDatasetMetadata();
    public void goToDatasetConstraints();
    public void goToDatasetDatasources();
    public void goToDatasetAttributes();
    public void goToDatasetCategorisations();
    public void goToDatasetVersion(String urn);
}
