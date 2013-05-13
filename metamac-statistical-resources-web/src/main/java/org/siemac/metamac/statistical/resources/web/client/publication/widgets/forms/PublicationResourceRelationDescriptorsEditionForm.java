package org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms;

import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public class PublicationResourceRelationDescriptorsEditionForm extends StatisticalResourceResourceRelationDescriptorsEditionForm {

    private PublicationMetadataTabUiHandlers uiHandlers;

    @Override
    public void setUiHandlers(UiHandlers uiHandlers) {
        this.uiHandlers = (PublicationMetadataTabUiHandlers) uiHandlers;
    }

    @Override
    public void retrieveResourcesForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        //FIXME: send criteria 
        uiHandlers.retrievePublicationsForReplaces(firstResult, maxResults, criteria.getCriteria());
    }

    @Override
    public void retrieveResourcesForIsReplacedBy(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        //FIXME: send criteria 
        uiHandlers.retrievePublicationsForIsReplacedBy(firstResult, maxResults, criteria != null? criteria.getCriteria() : null);
    }
    
    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
