package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchMultipleSrmItemWithSchemeFilterPaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public abstract class SearchSrmItemListWithSchemeFilterItem extends ExternalItemListItem {

    private SearchMultipleSrmItemWithSchemeFilterPaginatedWindow window;

    public SearchSrmItemListWithSchemeFilterItem(String name, String title) {
        super(name, title, false);
    }

    public SearchSrmItemListWithSchemeFilterItem(String name, String title, int maxResults) {
        super(name, title, true);
        appendWindow(maxResults);
    }

    private void appendWindow(final int maxResults) {
        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                SearchPaginatedAction<MetamacWebCriteria> filterSearchAction = new SearchPaginatedAction<MetamacWebCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                        retrieveItemSchemes(firstResult, maxResults, webCriteria);
                    }
                };

                window = new SearchMultipleSrmItemWithSchemeFilterPaginatedWindow(getConstants().resourceSelection(), maxResults, filterSearchAction,
                        new SearchPaginatedAction<ItemSchemeWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                                retrieveItems(firstResult, maxResults, webCriteria);
                            }
                        });

                retrieveItems(0, maxResults, new ItemSchemeWebCriteria());

                window.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        setExternalItems(window.getSelectedResources());
                        window.markForDestroy();
                    }
                });
            }
        });
    }

    protected abstract void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria);

    protected abstract void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria);

    public void setFilterResources(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        if (window != null) {
            window.setFilterResources(externalItemsDtos);
            window.refreshFilterSourcePaginationInfo(firstResult, externalItemsDtos.size(), totalResults);
        }
    }

    public void setResources(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        if (window != null) {
            window.setResources(externalItemsDtos);
            window.refreshSourcePaginationInfo(firstResult, externalItemsDtos.size(), totalResults);
        }
    }
}