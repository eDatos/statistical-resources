package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;

import com.smartgwt.client.widgets.form.fields.FormItem;

public abstract class AttributeBaseForm extends GroupDynamicForm {

    public AttributeBaseForm() {
        super(StringUtils.EMPTY);
    }

    protected void clearForm() {
        setFields(new FormItem[0]);
        clearValues();
        clearErrors(true);
    }
}
