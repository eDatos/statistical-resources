package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceCommonMetadataEditionForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;


public class DatasetCommonMetadataEditionForm extends StatisticalResourceCommonMetadataEditionForm {

    private DatasetMetadataTabUiHandlers uiHandlers;
    
    @Override
    protected void retrieveCommonConfigurations(CommonConfigurationWebCriteria criteria) {
        uiHandlers.retrieveCommonConfigurations(criteria);
    }
    
    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(DatasetMetadataTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
    
    

}
