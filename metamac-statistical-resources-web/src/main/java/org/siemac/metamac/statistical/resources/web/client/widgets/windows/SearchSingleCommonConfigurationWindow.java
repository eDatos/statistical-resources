package org.siemac.metamac.statistical.resources.web.client.widgets.windows;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.CommonConfigurationFilterForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterForm;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;


public class SearchSingleCommonConfigurationWindow extends SearchRelatedResourceBasePaginatedWindow<ExternalItemDto,CommonConfigurationWebCriteria>  {

    private CommonConfigurationFilterForm filterForm;
    
    public SearchSingleCommonConfigurationWindow(String title, int maxResults, SearchPaginatedAction<CommonConfigurationWebCriteria> action) {
        super(title, maxResults, new CommonConfigurationFilterForm(), action);
        filterForm = (CommonConfigurationFilterForm)getFilterForm();
    }
    
    private SearchSingleCommonConfigurationWindow(String title, int maxResults, FilterForm<CommonConfigurationWebCriteria> filter, SearchPaginatedAction<CommonConfigurationWebCriteria> action) {
        super(title, maxResults, filter, action);
    }
    
    public void setOnlyEnabled(boolean onlyEnabled) {
        filterForm.setShowOnlyEnabled(onlyEnabled);
    }
    
    public CommonConfigurationWebCriteria getWebCriteria() {
        return filterForm.getSearchCriteria();
    }
}
