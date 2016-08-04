package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;

public class SiemacMetadataCommonMetadataForm extends NavigationEnabledDynamicForm {

    private BaseUiHandlers uiHandlers;

    public SiemacMetadataCommonMetadataForm() {
        super(getConstants().formCommonMetadata());

        ExternalItemLinkItem commonConfigurationView = new ExternalItemLinkItem(SiemacMetadataDS.COMMON_METADATA_VIEW, getConstants().commonMetadata());

        setFields(commonConfigurationView);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(SiemacMetadataDS.COMMON_METADATA_VIEW, siemacMetadataStatisticalResourceDto.getCommonMetadata());
    }

    public void setBaseUiHandlers(BaseUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
