package org.siemac.metamac.statistical.resources.web.client.widgets;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;

import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class ProgramPublicationWindow extends CustomWindow {

    private static final String FIELD_DATE = "pub-date";
    private static final String FIELD_TIME = "pub-time";
    private static final String FIELD_SAVE = "save";

    private CustomDynamicForm   form;

    public ProgramPublicationWindow(String title) {
        super(title);
        setHeight(130);
        setWidth(330);

        DateItem dateItem = new DateItem(FIELD_DATE, StatisticalResourcesWeb.getConstants().lifeCycleProgramPublicationDate());

        TimeItem timeItem = new TimeItem(FIELD_TIME, StatisticalResourcesWeb.getConstants().lifeCycleProgramPublicationHour());

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, MetamacWebCommon.getConstants().actionSave());

        form = new CustomDynamicForm();
        form.setFields(dateItem, timeItem, saveItem);

        addItem(form);
        show();
    }

    public boolean validateForm() {
        return form.validate();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

}
