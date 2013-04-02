package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.PublicationDS;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewPublicationWindow extends CustomWindow {

    private static final int    FORM_ITEM_CUSTOM_WIDTH = 300;

    private static final String FIELD_SAVE             = "save-con";

    private CustomDynamicForm   form;

    public NewPublicationWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem identifierItem = new RequiredTextItem(PublicationDS.CODE, getConstants().identifiableStatisticalResourceCode());
        identifierItem.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        identifierItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem nameItem = new RequiredTextItem(PublicationDS.TITLE, getConstants().nameableStatisticalResourceTitle());
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

    public PublicationDto getNewPublicationDto() {
        PublicationDto collectionDto = new PublicationDto();
        collectionDto.setCode(form.getValueAsString(PublicationDS.CODE));
        collectionDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(PublicationDS.TITLE)));
        return collectionDto;
    }

    public boolean validateForm() {
        return form.validate();
    }
}
