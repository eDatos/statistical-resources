package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import java.util.List;

import org.jasig.cas.client.util.CommonUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiersMetadataDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;
import org.siemac.metamac.web.common.client.widgets.actions.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalPaginatedItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewDatasetWindow extends CustomWindow {

    private static final int      FORM_ITEM_CUSTOM_WIDTH = 300;
    private static final String   FIELD_SAVE             = "save-sch";

    private CustomDynamicForm     form;

    private DatasetListUiHandlers uiHandlers;

    public NewDatasetWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem identifierItem = new RequiredTextItem(DatasetDS.IDENTIFIER, getConstants().datasetIdentifier());
        identifierItem.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        identifierItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem nameItem = new RequiredTextItem(DatasetDS.TITLE, getConstants().datasetTitle());
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

        IdentifiersMetadataDto identifiers = new IdentifiersMetadataDto();
        identifiers.setIdentifier(form.getValueAsString(DatasetDS.IDENTIFIER));
        identifiers.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(DatasetDS.TITLE)));

        datasetDto.setIdentifiersMetadata(identifiers);
        datasetDto.setOperationUrn(operationUrn);
        return datasetDto;
    }

    public boolean validateForm() {
        return form.validate();
    }

    public void setUiHandlers(DatasetListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

}
