package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NewStatisticalResourceWindow;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetDS;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetListUiHandlers;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewMultidatasetWindow extends NewStatisticalResourceWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 300;

    private static final String FIELD_SAVE             = "save-con";

    public NewMultidatasetWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem nameItem = new RequiredTextItem(MultidatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        nameItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().multidatasetCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(nameItem, languageItem, maintainerItem, saveItem);

        addItem(form);
        show();
    }

    public void setUiHandlers(MultidatasetListUiHandlers uiHandlers) {
        super.setSiemacUiHandlers(uiHandlers);
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public MultidatasetVersionDto getNewMultidatasetDto() {
        MultidatasetVersionDto multidatasetDto = new MultidatasetVersionDto();
        multidatasetDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(MultidatasetDS.TITLE)));
        populateSiemacResourceDto(multidatasetDto);
        return multidatasetDto;
    }

}
