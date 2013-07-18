package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleFilterForm;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchMultipleRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class SearchMultipleExternalItemPaginatedWindow extends SearchMultipleRelatedResourceBasePaginatedWindow<ExternalItemDto, MetamacWebCriteria> {

    public SearchMultipleExternalItemPaginatedWindow(String title, int maxResults, SearchPaginatedAction<MetamacWebCriteria> action) {
        super(title, maxResults, new SimpleFilterForm(), action);
    }

}
