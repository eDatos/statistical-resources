package org.siemac.metamac.statistical.resources.web.client.widgets.windows;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.RelatedResourceBaseDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.DsdFilterForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterForm;


public class SearchSingleDsdPaginatedWindow extends SearchRelatedResourceBasePaginatedWindow<ExternalItemDto,DsdWebCriteria>  {

    private DsdFilterForm filterForm;
    
    public SearchSingleDsdPaginatedWindow(String title, int maxResults, SearchPaginatedAction<DsdWebCriteria> action) {
        super(title, maxResults, new DsdFilterForm(), action);
        filterForm = (DsdFilterForm)getFilterForm();
    }
    
    private SearchSingleDsdPaginatedWindow(String title, int maxResults, FilterForm<DsdWebCriteria> filter, SearchPaginatedAction<DsdWebCriteria> action) {
        super(title, maxResults, filter, action);
    }
    
    public void setStatisticalOperations(List<ExternalItemDto> statisticalOperations) {
        filterForm.setStatisticalOperations(statisticalOperations);
    }
    
    public void setSelectedStatisticalOperation(ExternalItemDto statisticalOperation) {
        filterForm.setStatisticalOperation(statisticalOperation);
    }
    
    public void setFixedDsdCode(String dsdCode) {
        filterForm.setFixedDsdCode(dsdCode);
    }
    
    public void setOnlyLastVersion(boolean onlyLastVersion) {
        filterForm.setOnlyLastVersion(onlyLastVersion);
    }
    
    public DsdWebCriteria getDsdWebCriteria() {
        return filterForm.getSearchCriteria();
    }
}
