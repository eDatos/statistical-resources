package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class StatisticalResourceCommonMetadataForm extends NavigationEnabledDynamicForm {
    final int FIRST_RESULT = 0;
    final int MAX_RESULTS = 8;
    private BaseUiHandlers uiHandlers;
    
    
    public StatisticalResourceCommonMetadataForm() {
        super(getConstants().formCommonMetadata());
        
        ViewTextItem commonConfigurationView = new ViewTextItem(StatisticalResourceDS.COMMON_METADATA_VIEW, getConstants().commonMetadata());
       
        setFields(commonConfigurationView);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(StatisticalResourceDS.COMMON_METADATA_VIEW, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getCommonMetadata()));
    }

    public void setBaseUiHandlers(BaseUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
    
    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
