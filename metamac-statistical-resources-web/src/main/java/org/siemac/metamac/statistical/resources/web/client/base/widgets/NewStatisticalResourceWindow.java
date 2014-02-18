package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmLinkItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemSchemeRestCriteria;

public class NewStatisticalResourceWindow extends CustomWindow {

    private NewStatisticalResourceUiHandlers        uiHandlers;

    protected CustomDynamicForm                     form;
    protected ExternalItemLinkItem                  languageItem;

    protected SearchSrmLinkItemWithSchemeFilterItem maintainerItem;

    public NewStatisticalResourceWindow(String title) {
        super(title);
        languageItem = createLanguageItem();

        maintainerItem = createMaintainerItem();
        maintainerItem.setRequired(true);
    }

    protected void setSiemacUiHandlers(NewStatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    protected void populateSiemacResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        ExternalItemDto language = form.getValueAsExternalItemDto(SiemacMetadataDS.LANGUAGE);
        dto.setLanguage(language);
        dto.getLanguages().clear();
        if (language != null) {
            dto.addLanguage(language);
        }
        dto.setMaintainer(form.getValueAsExternalItemDto(SiemacMetadataDS.MAINTAINER));
    }

    public boolean validateForm() {
        return form.validate();
    }

    // ***********************************************************
    // LANGUAGE
    // ***********************************************************
    public void setDefaultLanguage(ExternalItemDto defaultLanguage) {
        if (defaultLanguage != null) {
            form.setValue(SiemacMetadataDS.LANGUAGE, defaultLanguage);
        }
    }

    private ExternalItemLinkItem createLanguageItem() {
        return new ExternalItemLinkItem(SiemacMetadataDS.LANGUAGE, getConstants().siemacMetadataStatisticalResourceLanguage());
    }

    // ***********************************************************
    // MAINTAINER
    // ***********************************************************
    public void setDefaultMaintainer(ExternalItemDto defaultMaintainer) {
        if (defaultMaintainer != null) {
            form.setValue(SiemacMetadataDS.MAINTAINER, defaultMaintainer);
            form.markForRedraw();
        }
    }
    public void setAgencySchemesForMaintainer(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (maintainerItem != null) {
            maintainerItem.setFilterResources(externalItemsDtos, firstResult, elementsInPage, totalResults);
        }
    }

    public void setExternalItemsForMaintainer(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (maintainerItem != null) {
            maintainerItem.setResources(externalItemsDtos, firstResult, elementsInPage, totalResults);
        }
    }

    private SearchSrmLinkItemWithSchemeFilterItem createMaintainerItem() {
        return new SearchSrmLinkItemWithSchemeFilterItem(SiemacMetadataDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                uiHandlers.retrieveAgencies(firstResult, maxResults, webCriteria);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmItemSchemeRestCriteria webCriteria) {
                uiHandlers.retrieveAgencySchemes(firstResult, maxResults, webCriteria);
            }
        };
    }
}
