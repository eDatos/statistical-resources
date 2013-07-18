package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;

public class StatisticalResourceCommonMetadataForm extends NavigationEnabledDynamicForm {

    private BaseUiHandlers uiHandlers;

    public StatisticalResourceCommonMetadataForm() {
        super(getConstants().formCommonMetadata());

        ExternalItemLinkItem commonConfigurationView = new ExternalItemLinkItem(StatisticalResourceDS.COMMON_METADATA_VIEW, getConstants().commonMetadata());

        setFields(commonConfigurationView);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setExternalItemValue(getItem(StatisticalResourceDS.COMMON_METADATA_VIEW), siemacMetadataStatisticalResourceDto.getCommonMetadata());
    }

    public void setBaseUiHandlers(BaseUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
