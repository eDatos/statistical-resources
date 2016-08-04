package org.siemac.metamac.statistical.resources.web.client.widgets;

import java.util.Date;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTimeItem;

import com.smartgwt.client.widgets.form.fields.DateItem;
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
        dateItem.setRequired(true);

        CustomTimeItem timeItem = new CustomTimeItem(FIELD_TIME, StatisticalResourcesWeb.getConstants().lifeCycleProgramPublicationHour());
        timeItem.setRequired(true);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, MetamacWebCommon.getConstants().actionSave());

        form = new CustomDynamicForm();
        form.setFields(dateItem, timeItem, saveItem);

        addItem(form);
        show();
    }

    public boolean validateForm() {
        return form.validate(false);
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    @SuppressWarnings("deprecation")
    public Date getSelectedDate() {
        Date date = ((DateItem) form.getItem(FIELD_DATE)).getValueAsDate();
        int hours = ((CustomTimeItem) form.getItem(FIELD_TIME)).getHours();
        int minutes = ((CustomTimeItem) form.getItem(FIELD_TIME)).getMinutes();
        date.setHours(hours);
        date.setMinutes(minutes);
        return date;
    }
}
