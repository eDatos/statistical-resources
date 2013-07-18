package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterForm;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class SearchSingleExternalItemPaginatedWindow<T extends MetamacWebCriteria> extends SearchRelatedResourceBasePaginatedWindow<ExternalItemDto, T> {

    public SearchSingleExternalItemPaginatedWindow(String title, int maxResults, FilterForm<T> filter, SearchPaginatedAction<T> action) {
        super(title, maxResults, filter, action);
    }

}
