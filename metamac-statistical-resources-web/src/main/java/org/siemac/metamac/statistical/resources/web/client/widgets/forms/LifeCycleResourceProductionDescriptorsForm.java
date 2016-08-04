package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;

public class LifeCycleResourceProductionDescriptorsForm extends GroupDynamicForm {

    public LifeCycleResourceProductionDescriptorsForm() {
        super(getConstants().formProductionDescriptors());

        ExternalItemLinkItem maintainer = new ExternalItemLinkItem(SiemacMetadataDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer());
        setFields(maintainer);
    }

    public void setLifeCycleResourceDto(LifeCycleStatisticalResourceDto dto) {
        setValue(SiemacMetadataDS.MAINTAINER, dto.getMaintainer());
    }
}
