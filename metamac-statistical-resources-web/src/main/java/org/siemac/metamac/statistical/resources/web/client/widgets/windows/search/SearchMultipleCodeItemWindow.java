package org.siemac.metamac.statistical.resources.web.client.widgets.windows.search;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.selectors.CodeItemMultiListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchAction;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleFilterForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchItem;
import org.siemac.metamac.web.common.client.widgets.windows.base.BaseSearchWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class SearchMultipleCodeItemWindow extends BaseSearchWindow {

    private CodeItemMultiListGrid multiListGrid;

    public SearchMultipleCodeItemWindow(String title, SearchAction<MetamacWebCriteria> action) {
        super(title);
        customize(title, action);
    }

    private void customize(String title, final SearchAction<MetamacWebCriteria> action) {
        final SimpleFilterForm filterForm = new SimpleFilterForm();
        filterForm.setSearchAction(action);

        multiListGrid = new CodeItemMultiListGrid();

        SearchItem<MetamacWebCriteria> searchItem = new SearchItem<MetamacWebCriteria>(String.valueOf(FORM_ITEM_CUSTOM_WIDTH), filterForm, multiListGrid);
        searchItem.setShowTitle(false);

        setSearchItem(searchItem);
        setWidth(700);
    }

    public List<CodeItemDto> getSelectedResources() {
        return multiListGrid.getSelectedResources();
    }

    public void setResources(List<CodeItemDto> resources) {
        multiListGrid.setResources(resources);
    }

    public void setSelectedResources(List<CodeItemDto> resources) {
        multiListGrid.setSelectedResources(resources);
    }

}
