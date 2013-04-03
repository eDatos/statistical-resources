package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;

public class StatisticalResourceIntellectualPropertyDescriptorsForm extends GroupDynamicForm {

    public StatisticalResourceIntellectualPropertyDescriptorsForm() {
        super(getConstants().formIntellectualPropertyDescriptors());

        // TODO add fields
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        // TODO
    }
}
