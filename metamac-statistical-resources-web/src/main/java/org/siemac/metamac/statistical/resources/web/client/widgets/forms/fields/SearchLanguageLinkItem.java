package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleExternalItemPaginatedWindow;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterForm;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleFilterForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class SearchLanguageLinkItem extends SearchExternalItemLinkItem {

    private SearchSingleExternalItemPaginatedWindow<MetamacWebCriteria> window;
    private NewStatisticalResourceUiHandlers                            uiHandlers;

    public SearchLanguageLinkItem(String name, String title, int maxResults) {
        super(name, title);
        appendWindow(maxResults);
    }

    private void appendWindow(final int maxResults) {
        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                FilterForm<MetamacWebCriteria> filter = new SimpleFilterForm<MetamacWebCriteria>();
                window = new SearchSingleExternalItemPaginatedWindow<MetamacWebCriteria>(getConstants().resourceSelection(), maxResults, filter, new SearchPaginatedAction<MetamacWebCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria criteria) {
                        uiHandlers.retrieveLanguagesCodes(firstResult, maxResults, criteria);
                    }
                });

                // Load resources (to populate the selection window)
                uiHandlers.retrieveLanguagesCodes(0, maxResults, new MetamacWebCriteria());

                window.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = window.getSelectedResource();
                        window.markForDestroy();
                        // Set selected resource in form
                        setExternalItem(selectedResource);
                    }
                });
            }
        });
    }

    public void setResources(List<ExternalItemDto> externalItemsDtos, int firstResult, int elementsInPage, int totalResults) {
        if (window != null) {
            window.setResources(externalItemsDtos);
            window.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    public void setUiHandlers(NewStatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
