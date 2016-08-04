package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceSimpleFilterPaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class SearchSingleRelatedResourcePaginatedWindow extends SearchRelatedResourceSimpleFilterPaginatedWindow<RelatedResourceDto> {

    public SearchSingleRelatedResourcePaginatedWindow(String title, int maxResults, SearchPaginatedAction<MetamacWebCriteria> action) {
        super(title, maxResults, action);
    }

}
