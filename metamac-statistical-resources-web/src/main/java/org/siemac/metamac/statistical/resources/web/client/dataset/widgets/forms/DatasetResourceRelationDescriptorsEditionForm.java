package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsEditionForm;

import com.gwtplatform.mvp.client.UiHandlers;

public class DatasetResourceRelationDescriptorsEditionForm extends StatisticalResourceResourceRelationDescriptorsEditionForm {

    private DatasetMetadataTabUiHandlers uiHandlers;

    @Override
    public void setUiHandlers(UiHandlers uiHandlers) {
        this.uiHandlers = (DatasetMetadataTabUiHandlers) uiHandlers;
    }

    @Override
    public void retrieveResourcesForReplaces(int firstResult, int maxResults, String criteria) {
        uiHandlers.retrieveDatasetsForReplaces(firstResult, maxResults, criteria);
    }

    @Override
    public void retrieveResourcesForIsReplacedBy(int firstResult, int maxResults, String criteria) {
        uiHandlers.retrieveDatasetsForIsReplacedBy(firstResult, maxResults, criteria);
    }
}
