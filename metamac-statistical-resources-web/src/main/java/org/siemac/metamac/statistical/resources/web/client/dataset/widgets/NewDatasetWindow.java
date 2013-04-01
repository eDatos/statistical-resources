package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewDatasetWindow extends CustomWindow {

    private static final int      FORM_ITEM_CUSTOM_WIDTH = 300;
    private static final String   FIELD_SAVE             = "save-sch";

    private CustomDynamicForm     form;

    private DatasetListUiHandlers uiHandlers;

    public NewDatasetWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem identifierItem = new RequiredTextItem(DatasetDS.CODE, getConstants().identifiableStatisticalResourceCode());
        identifierItem.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        identifierItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem nameItem = new RequiredTextItem(DatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        nameItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().datasetCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(identifierItem, nameItem, saveItem);

        addItem(form);
        show();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public DatasetDto getNewDatasetDto(String operationUrn) {
        DatasetDto datasetDto = new DatasetDto();
        // FIXME: code can not be updated
        // datasetDto.setCode(form.getValueAsString(DatasetDS.IDENTIFIER));
        datasetDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(DatasetDS.TITLE)));
        // datasetDto.setOperationUrn(operationUrn);
        return datasetDto;
    }

    public boolean validateForm() {
        return form.validate();
    }

    public void setUiHandlers(DatasetListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
