package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;

public class LifeCycleResourceContentDescriptorsForm extends GroupDynamicForm {

    protected ProcStatusEnum procStatus;

    public LifeCycleResourceContentDescriptorsForm() {
        super(getConstants().formContentDescriptors());

        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(LifeCycleResourceDS.DESCRIPTION, getConstants().nameableStatisticalResourceDescription());

        setFields(description);
    }

    public void setLifeCycleResource(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        this.procStatus = lifeCycleStatisticalResourceDto.getProcStatus();

        setValue(LifeCycleResourceDS.DESCRIPTION, lifeCycleStatisticalResourceDto.getDescription());
    }
}
