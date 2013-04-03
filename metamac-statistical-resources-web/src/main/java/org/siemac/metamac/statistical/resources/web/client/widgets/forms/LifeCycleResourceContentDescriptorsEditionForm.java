package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;

public class LifeCycleResourceContentDescriptorsEditionForm extends GroupDynamicForm {

    public LifeCycleResourceContentDescriptorsEditionForm() {
        super(getConstants().formContentDescriptors());

        MultiLanguageTextAreaItem description = new MultiLanguageTextAreaItem(LifeCycleResourceDS.DESCRIPTION, getConstants().nameableStatisticalResourceDescription());

        setFields(description);
    }

    public void setLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        setValue(LifeCycleResourceDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(lifeCycleStatisticalResourceDto.getDescription()));
    }

    public LifeCycleStatisticalResourceDto getLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        lifeCycleStatisticalResourceDto.setDescription((InternationalStringDto) getValue(LifeCycleResourceDS.DESCRIPTION));
        return lifeCycleStatisticalResourceDto;
    }
}
