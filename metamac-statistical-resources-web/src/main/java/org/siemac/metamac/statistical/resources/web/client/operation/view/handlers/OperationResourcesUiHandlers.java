package org.siemac.metamac.statistical.resources.web.client.operation.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;

public interface OperationResourcesUiHandlers extends UiHandlers {

    void goToDataset(String urn);
    void goToPublication(String urn);

}
