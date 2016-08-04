package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.SrmItemSchemeFilterForm;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;

public class SearchSingleSrmItemSchemeWindow extends SearchRelatedResourceBasePaginatedWindow<ExternalItemDto, SrmExternalResourceRestCriteria> {

    public SearchSingleSrmItemSchemeWindow(String title, int maxResults, SearchPaginatedAction<SrmExternalResourceRestCriteria> action) {
        super(title, maxResults, new SrmItemSchemeFilterForm(), action);
    }

    public MetamacWebCriteria getWebCriteria() {
        return getFilterForm().getSearchCriteria();
    }
}
