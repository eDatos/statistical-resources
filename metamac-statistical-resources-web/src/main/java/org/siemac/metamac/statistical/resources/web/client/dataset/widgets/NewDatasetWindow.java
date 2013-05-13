package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.SearchSingleDsdPaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchViewTextItem;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

public class NewDatasetWindow extends CustomWindow {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 8;
    
    private static final int               FORM_ITEM_CUSTOM_WIDTH = 300;
    private static final String            FIELD_SAVE             = "save-sch";

    private CustomDynamicForm              form;

    private DatasetListUiHandlers          uiHandlers;

    private ExternalItemDto                relatedDsdDto;
    private SearchViewTextItem             relatedDsd;
    private SearchSingleDsdPaginatedWindow searchDsdWindow;

    public NewDatasetWindow(String title) {
        super(title);
        setAutoSize(true);

        RequiredTextItem nameItem = new RequiredTextItem(DatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        nameItem.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        relatedDsd = createDsdsItem(DatasetDS.RELATED_DSD, getConstants().datasetRelatedDSD());
        relatedDsd.setRequired(true);

        CustomButtonItem saveItem = new CustomButtonItem(FIELD_SAVE, getConstants().datasetCreate());

        form = new CustomDynamicForm();
        form.setMargin(5);
        form.setFields(nameItem, relatedDsd, saveItem);

        addItem(form);
        show();
    }

    public HasClickHandlers getSave() {
        return form.getItem(FIELD_SAVE);
    }

    public DatasetDto getNewDatasetDto(String operationUrn) {
        DatasetDto datasetDto = new DatasetDto();
        datasetDto.setTitle(InternationalStringUtils.updateInternationalString(new InternationalStringDto(), form.getValueAsString(DatasetDS.TITLE)));
        datasetDto.setRelatedDsd(relatedDsdDto);
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

    private void setRelatedDsd(ExternalItemDto relatedDsdDto) {
        form.setValue(DatasetDS.RELATED_DSD, ExternalItemUtils.getExternalItemName(relatedDsdDto));
        form.setValue(DatasetDS.RELATED_DSD_VIEW, ExternalItemUtils.getExternalItemName(relatedDsdDto));

        this.relatedDsdDto = relatedDsdDto;
    }
    

    public void setExternalItemsForRelatedDsd(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setResources(externalItemsDtos);
            searchDsdWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }
    
    public void setStatisticalOperationsForRelatedDsd(List<ExternalItemDto> externalItemsDtos, ExternalItemDto defaultSelected) {
        if (searchDsdWindow != null) {
            searchDsdWindow.setStatisticalOperations(externalItemsDtos);
            searchDsdWindow.setSelectedStatisticalOperation(defaultSelected);
            searchDsdWindow.setFixedDsdCode(null);
            searchDsdWindow.setOnlyLastVersion(true);
            
            retrieveResourcesForRelatedDsd(FIRST_RESULT, MAX_RESULTS, searchDsdWindow.getDsdWebCriteria());
        }
    }

    private SearchViewTextItem createDsdsItem(String name, String title) {

        final SearchViewTextItem dsdItem = new SearchViewTextItem(name, title);
        dsdItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDsdWindow = new SearchSingleDsdPaginatedWindow(getConstants().resourceSelection(), MAX_RESULTS, new SearchPaginatedAction<DsdWebCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, DsdWebCriteria criteria) {
                        retrieveResourcesForRelatedDsd(firstResult, maxResults, criteria);
                    }
                });

                // Load resources (to populate the selection window)
                retrieveStatisticalOperationsForDsdSelection();
                
                searchDsdWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchDsdWindow.getSelectedResource();
                        searchDsdWindow.markForDestroy();
                        // Set selected resource in form
                        setRelatedDsd(selectedResource);
                        form.validate(false);
                    }
                });
            }
        });
        return dsdItem;
    }
    

    public void retrieveStatisticalOperationsForDsdSelection() {
        uiHandlers.retrieveStatisticalOperationsForDsdSelection();
    }
    
    public void retrieveResourcesForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria) {
        uiHandlers.retrieveDsdsForRelatedDsd(firstResult, maxResults, criteria);
    }
}
