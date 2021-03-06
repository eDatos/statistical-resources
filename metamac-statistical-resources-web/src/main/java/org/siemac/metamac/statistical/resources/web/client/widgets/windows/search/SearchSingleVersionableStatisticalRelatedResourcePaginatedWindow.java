package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.VersionableStatisticalResourceFilterForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.base.VersionableStatisticalResourceFilterBaseForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;

public class SearchSingleVersionableStatisticalRelatedResourcePaginatedWindow extends SearchRelatedResourceBasePaginatedWindow<RelatedResourceDto, VersionableStatisticalResourceWebCriteria> {

    private VersionableStatisticalResourceFilterBaseForm<VersionableStatisticalResourceWebCriteria> filterForm;

    public SearchSingleVersionableStatisticalRelatedResourcePaginatedWindow(String title, int maxResults, SearchPaginatedAction<VersionableStatisticalResourceWebCriteria> action) {
        super(title, maxResults, new VersionableStatisticalResourceFilterForm(), action);
        filterForm = (VersionableStatisticalResourceFilterForm) getFilterForm();
    }

    public void setStatisticalOperations(List<ExternalItemDto> statisticalOperations) {
        filterForm.setStatisticalOperations(statisticalOperations);
    }

    public void setSelectedStatisticalOperation(ExternalItemDto statisticalOperation) {
        filterForm.setSelectedStatisticalOperation(statisticalOperation);
    }

    @Override
    public VersionableStatisticalResourceWebCriteria getSearchCriteria() {
        return getFilterForm().getSearchCriteria();
    }
}
