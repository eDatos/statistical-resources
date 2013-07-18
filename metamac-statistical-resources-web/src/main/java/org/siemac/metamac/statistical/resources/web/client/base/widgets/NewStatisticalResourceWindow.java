package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchLanguageLinkItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchSrmLinkItemWithSchemeFilterItem;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.widgets.CustomWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class NewStatisticalResourceWindow extends CustomWindow {

    private NewStatisticalResourceUiHandlers        uiHandlers;

    protected CustomDynamicForm                     form;

    protected SearchLanguageLinkItem                languageItem;

    protected SearchSrmLinkItemWithSchemeFilterItem maintainerItem;

    public NewStatisticalResourceWindow(String title) {
        super(title);
        languageItem = createLanguageItem();
        languageItem.setRequired(true);

        maintainerItem = createMaintainerItem();
        maintainerItem.setRequired(true);
    }

    protected void setSiemacUiHandlers(NewStatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
        languageItem.setUiHandlers(uiHandlers);
    }

    protected void populateSiemacResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        dto.setLanguage(getExternalItemValue(form.getItem(StatisticalResourceDS.LANGUAGE)));
        dto.getLanguages().clear();
        dto.addLanguage(getExternalItemValue(form.getItem(StatisticalResourceDS.LANGUAGE)));
        dto.setMaintainer(getExternalItemValue(form.getItem(StatisticalResourceDS.MAINTAINER)));
    }

    public boolean validateForm() {
        return form.validate();
    }

    // ***********************************************************
    // LANGUAGE
    // ***********************************************************
    public void setDefaultLanguage(ExternalItemDto defaultLanguage) {
        if (defaultLanguage != null) {
            setExternalItemValue(form.getItem(StatisticalResourceDS.LANGUAGE), defaultLanguage);
        }
    }

    public void setExternalItemsForLanguage(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (languageItem != null) {
            languageItem.setResources(externalItemsDtos, firstResult, elementsInPage, totalResults);
        }
    }

    private SearchLanguageLinkItem createLanguageItem() {
        return new SearchLanguageLinkItem(StatisticalResourceDS.LANGUAGE, getConstants().siemacMetadataStatisticalResourceLanguage(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS);
    }

    // ***********************************************************
    // MAINTAINER
    // ***********************************************************
    public void setDefaultMaintainer(ExternalItemDto defaultMaintainer) {
        if (defaultMaintainer != null) {
            setExternalItemValue(form.getItem(StatisticalResourceDS.MAINTAINER), defaultMaintainer);
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
        return new SearchSrmLinkItemWithSchemeFilterItem(StatisticalResourceDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                uiHandlers.retrieveAgencies(firstResult, maxResults, webCriteria);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveAgencySchemes(firstResult, maxResults, webCriteria);
            }
        };
    }
}
