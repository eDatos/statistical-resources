package org.siemac.metamac.statistical.resources.web.client.widgets.windows;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchMultipleRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;


public class SearchMultipleRelatedResourcePaginatedWindow extends SearchMultipleRelatedResourceBasePaginatedWindow<RelatedResourceDto> {

    public SearchMultipleRelatedResourcePaginatedWindow(String title, int maxResults, SearchPaginatedAction<MetamacWebCriteria> action) {
        super(title, maxResults, action);
    }

}
