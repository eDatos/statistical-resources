package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DatasetDS;
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

        RequiredTextItem nameItem = new RequiredTextItem(DatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        nameItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().datasetCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(nameItem, saveItem);

        addItem(form);
        show();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public DatasetDto getNewDatasetDto(String operationUrn) {
        DatasetDto datasetDto = new DatasetDto();
        datasetDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(DatasetDS.TITLE)));
        // FIXME: set language and maintainer from data
        mockExternalItems(datasetDto);
        return datasetDto;
    }

    private void mockExternalItems(DatasetDto dataset) {
        dataset.setLanguage(mockLanguage("es", "Español"));
        dataset.addLanguage(mockLanguage("es", "Español"));
        dataset.setMaintainer(mockMaintainer("es", "ISTAC"));
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

    public boolean validateForm() {
        return form.validate();
    }

    public void setUiHandlers(DatasetListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
