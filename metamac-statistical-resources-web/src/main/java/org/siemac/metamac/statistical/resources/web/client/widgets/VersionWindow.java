package org.siemac.metamac.statistical.resources.web.client.widgets;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;

import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class VersionWindow extends CustomWindow {

    private static final String FIELD_VERSION = "version-ind";
    private static final String FIELD_SAVE    = "save-ind";

    private CustomDynamicForm   form;

    public VersionWindow(String title) {
        super(title);
        setHeight(100);
        setWidth(330);

        SelectItem versionItem = new SelectItem(FIELD_VERSION, StatisticalResourcesWeb.getConstants().lifeCycleVersionType());
        versionItem.setRequired(true);
        versionItem.setValueMap(CommonUtils.getVersionTypeHashMap());

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, MetamacWebCommon.getConstants().actionSave());

        form = new CustomDynamicForm();
        form.setFields(versionItem, saveItem);

        addItem(form);
        show();
    }

    public VersionTypeEnum getSelectedVersion() {
        String value = form.getValueAsString(FIELD_VERSION);
        return (value != null && !value.isEmpty()) ? VersionTypeEnum.valueOf(value) : null;
    }

    public boolean validateForm() {
        return form.validate();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

}
