package org.siemac.metamac.statistical.resources.web.client.collection.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.CollectionDS;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewCollectionWindow extends CustomWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 300;

    private static final String FIELD_SAVE             = "save-con";

    private CustomDynamicForm   form;

    public NewCollectionWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem identifierItem = new RequiredTextItem(CollectionDS.IDENTIFIER, getConstants().collectionIdentifier());
        identifierItem.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        identifierItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem nameItem = new RequiredTextItem(CollectionDS.TITLE, getConstants().collectionTitle());
        nameItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().collectionCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(identifierItem, nameItem, saveItem);

        addItem(form);
        show();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public CollectionDto getNewCollectionDto() {
        CollectionDto conceptDto = new CollectionDto();
        conceptDto.setIdentifier(form.getValueAsString(CollectionDS.IDENTIFIER));
        conceptDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(CollectionDS.TITLE)));
        return conceptDto;
    }

    public boolean validateForm() {
        return form.validate();
    }

}
