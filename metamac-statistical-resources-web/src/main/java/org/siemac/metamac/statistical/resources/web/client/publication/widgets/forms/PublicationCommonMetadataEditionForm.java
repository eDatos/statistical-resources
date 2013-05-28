package org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms;

import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceCommonMetadataEditionForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;


public class PublicationCommonMetadataEditionForm extends StatisticalResourceCommonMetadataEditionForm {

    private PublicationMetadataTabUiHandlers uiHandlers;
    
    @Override
    protected void retrieveCommonConfigurations(CommonConfigurationWebCriteria criteria) {
        uiHandlers.retrieveCommonConfigurations(criteria);
    }
    
    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(PublicationMetadataTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
    
    

}
