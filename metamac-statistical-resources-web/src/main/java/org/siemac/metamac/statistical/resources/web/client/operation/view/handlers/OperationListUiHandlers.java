package org.siemac.metamac.statistical.resources.web.client.operation.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;


public interface OperationListUiHandlers extends UiHandlers {

    public void retrieveOperations(int firstResult, int maxResults, final String operation);
    public void goToOperation(String urn);
}
