package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleVersionableFilterForm;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class SearchSingleSrmItemSchemeWindow extends SearchRelatedResourceBasePaginatedWindow<ExternalItemDto, MetamacVersionableWebCriteria> {

    public SearchSingleSrmItemSchemeWindow(String title, int maxResults, SearchPaginatedAction<MetamacVersionableWebCriteria> action) {
        super(title, maxResults, new SimpleVersionableFilterForm<MetamacVersionableWebCriteria>(), action);
    }

    public MetamacWebCriteria getWebCriteria() {
        return getFilterForm().getSearchCriteria();
    }
}
