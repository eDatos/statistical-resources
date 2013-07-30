package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;

public class LifeCycleResourceContentDescriptorsEditionForm extends GroupDynamicForm {

    protected ProcStatusEnum procStatus;

    public LifeCycleResourceContentDescriptorsEditionForm() {
        super(getConstants().formContentDescriptors());

        final MultilanguageRichTextEditorItem description = new MultilanguageRichTextEditorItem(LifeCycleResourceDS.DESCRIPTION, getConstants().nameableStatisticalResourceDescription());
        description.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? description.getValue() != null : true;
            }
        });

        setFields(description);
    }

    public void setLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        this.procStatus = lifeCycleStatisticalResourceDto.getProcStatus();

        setValue(LifeCycleResourceDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(lifeCycleStatisticalResourceDto.getDescription()));
    }

    public LifeCycleStatisticalResourceDto getLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        lifeCycleStatisticalResourceDto.setDescription((InternationalStringDto) getValue(LifeCycleResourceDS.DESCRIPTION));
        return lifeCycleStatisticalResourceDto;
    }
}
