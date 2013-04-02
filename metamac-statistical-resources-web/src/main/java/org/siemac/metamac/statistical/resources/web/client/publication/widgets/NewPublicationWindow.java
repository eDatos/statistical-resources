package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
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
        PublicationDto publicationDto = new PublicationDto();
        publicationDto.setCode(form.getValueAsString(PublicationDS.CODE));
        publicationDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(PublicationDS.TITLE)));

        // FIXME Remove this mocks!! Languages and maintainer should be read from DATA
        publicationDto.setLanguage(mockLanguage("es", "Español"));
        publicationDto.getLanguages().add(mockLanguage("es", "Español"));
        publicationDto.setMaintainer(mockMaintainer("es", "ISTAC"));

        return publicationDto;
    }

    public boolean validateForm() {
        return form.validate();
    }

    private ExternalItemDto mockMaintainer(String locale, String label) {
        InternationalStringDto title = mockInternationalString(locale, label);
        return new ExternalItemDto("MAINTAINER-ISTAC", "FAKE-URI", "FAKE-URN", TypeExternalArtefactsEnum.AGENCY, title);
    }

    private ExternalItemDto mockLanguage(String locale, String label) {
        return new ExternalItemDto("LANG_ES", "CODE-URI", "FAKE-URN", TypeExternalArtefactsEnum.CODE, mockInternationalString(locale, label));
    }

    private InternationalStringDto mockInternationalString(String locale, String label) {
        InternationalStringDto title = new InternationalStringDto();
        LocalisedStringDto localised = new LocalisedStringDto();
        localised.setLabel(label);
        localised.setLocale(locale);
        title.addText(localised);
        return title;
    }
}
