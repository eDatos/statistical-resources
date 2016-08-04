package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.HashMap;
import java.util.List;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewQueryWindow extends CustomWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 300;
    private static final String FIELD_SAVE             = "save-query";

    private CustomDynamicForm   form;

    public NewQueryWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem codeItem = new RequiredTextItem(QueryDS.CODE, getConstants().identifiableStatisticalResourceCode());
        codeItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem nameItem = new RequiredTextItem(QueryDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        nameItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().queryCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(codeItem, nameItem, saveItem);

        addItem(form);
        show();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public QueryVersionDto getNewQueryDto() {
        QueryVersionDto queryDto = new QueryVersionDto();
        queryDto.setCode(form.getValueAsString(QueryDS.CODE));
        queryDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(QueryDS.TITLE)));
        queryDto.setSelection(new HashMap<String, List<CodeItemDto>>());
        return queryDto;
    }

    public boolean validateForm() {
        return form.validate();
    }
}
