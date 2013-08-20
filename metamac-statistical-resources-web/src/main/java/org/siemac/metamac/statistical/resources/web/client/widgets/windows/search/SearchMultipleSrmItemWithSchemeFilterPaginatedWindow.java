package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.ItemSchemeWithSchemeFilterForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchMultipleRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;

public class SearchMultipleSrmItemWithSchemeFilterPaginatedWindow extends SearchMultipleRelatedResourceBasePaginatedWindow<ExternalItemDto, ItemSchemeWebCriteria> {

    public SearchMultipleSrmItemWithSchemeFilterPaginatedWindow(String title, int maxResults, SearchPaginatedAction<MetamacVersionableWebCriteria> filterSearchAction,
            SearchPaginatedAction<ItemSchemeWebCriteria> action) {
        super(title, maxResults, new ItemSchemeWithSchemeFilterForm(maxResults, filterSearchAction), action);
    }

    public void setFilterResources(List<ExternalItemDto> resources) {
        getFilter().setFilterResources(resources);
    }

    public void refreshFilterSourcePaginationInfo(int firstResult, int elementsInPage, int totalResults) {
        getFilter().refreshFilterSourcePaginationInfo(firstResult, elementsInPage, totalResults);
    }

    public ItemSchemeWithSchemeFilterForm getFilter() {
        return (ItemSchemeWithSchemeFilterForm) getFilterForm();
    }
}
