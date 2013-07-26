package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.StatisticalResourceFilterForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;

public class SearchSingleStatisticalRelatedResourcePaginatedWindow extends SearchRelatedResourceBasePaginatedWindow<RelatedResourceDto, StatisticalResourceWebCriteria> {

    private StatisticalResourceFilterForm<StatisticalResourceWebCriteria> filterForm;

    public SearchSingleStatisticalRelatedResourcePaginatedWindow(String title, int maxResults, SearchPaginatedAction<StatisticalResourceWebCriteria> action) {
        super(title, maxResults, new StatisticalResourceFilterForm<StatisticalResourceWebCriteria>(), action);
        filterForm = (StatisticalResourceFilterForm<StatisticalResourceWebCriteria>) getFilterForm();
    }

    public void setStatisticalOperations(List<ExternalItemDto> statisticalOperations) {
        filterForm.setStatisticalOperations(statisticalOperations);
    }

    public StatisticalResourceWebCriteria getSearchCriteria() {
        return getFilterForm().getSearchCriteria();
    }
}
