package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.VersionableStatisticalResourceFilterForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.VersionableStatisticalResourceWithDataFilterForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;

public class SearchSingleDatasetVersionRelatedResourcePaginatedWindow extends SearchRelatedResourceBasePaginatedWindow<RelatedResourceDto, DatasetVersionWebCriteria> {

    private VersionableStatisticalResourceFilterForm<DatasetVersionWebCriteria> filterForm;

    public SearchSingleDatasetVersionRelatedResourcePaginatedWindow(String title, int maxResults, SearchPaginatedAction<DatasetVersionWebCriteria> action) {
        super(title, maxResults, new VersionableStatisticalResourceWithDataFilterForm<DatasetVersionWebCriteria>(), action);
        filterForm = (VersionableStatisticalResourceFilterForm<DatasetVersionWebCriteria>) getFilterForm();
    }

    public void setStatisticalOperations(List<ExternalItemDto> statisticalOperations) {
        filterForm.setStatisticalOperations(statisticalOperations);
    }

    public DatasetVersionWebCriteria getSearchCriteria() {
        return getFilterForm().getSearchCriteria();
    }
}
