package org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms;

import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsEditionForm;

import com.gwtplatform.mvp.client.UiHandlers;

public class PublicationResourceRelationDescriptorsEditionForm extends StatisticalResourceResourceRelationDescriptorsEditionForm {

    private PublicationMetadataTabUiHandlers uiHandlers;

    @Override
    public void setUiHandlers(UiHandlers uiHandlers) {
        this.uiHandlers = (PublicationMetadataTabUiHandlers) uiHandlers;
    }

    @Override
    public void retrieveResourcesForReplaces(int firstResult, int maxResults, String criteria) {
        uiHandlers.retrievePublicationsForReplaces(firstResult, maxResults, criteria);
    }

    @Override
    public void retrieveResourcesForIsReplacedBy(int firstResult, int maxResults, String criteria) {
        uiHandlers.retrievePublicationsForIsReplacedBy(firstResult, maxResults, criteria);
    }
}
