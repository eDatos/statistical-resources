package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public class DatasetResourceRelationDescriptorsEditionForm extends StatisticalResourceResourceRelationDescriptorsEditionForm {

    private DatasetMetadataTabUiHandlers uiHandlers;

    @Override
    public void setUiHandlers(UiHandlers uiHandlers) {
        this.uiHandlers = (DatasetMetadataTabUiHandlers) uiHandlers;
    }

    @Override
    public void retrieveResourcesForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        //FIXME: send criteria
        uiHandlers.retrieveDatasetsForReplaces(firstResult, maxResults, criteria != null? criteria.getCriteria() : null);
    }

    @Override
    public void retrieveResourcesForIsReplacedBy(int firstResult, int maxResults, MetamacWebCriteria criteria) {
         uiHandlers.retrieveDatasetsForIsReplacedBy(firstResult, maxResults, criteria != null? criteria.getCriteria() : null);
    }
    
    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }

}
