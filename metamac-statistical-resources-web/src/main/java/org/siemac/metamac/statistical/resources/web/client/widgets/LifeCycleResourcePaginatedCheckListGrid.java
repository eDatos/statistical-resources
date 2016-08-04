package org.siemac.metamac.statistical.resources.web.client.widgets;

import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

public class LifeCycleResourcePaginatedCheckListGrid extends PaginatedCheckListGrid {

    public LifeCycleResourcePaginatedCheckListGrid(int maxResults, PaginatedAction action) {
        super(maxResults, new LifeCycleResourceCustomListGrid(), action);
        ListGridUtils.setCheckBoxSelectionType(getListGrid());
    }

    public void setUiHandlers(BaseUiHandlers uiHandlers) {
        ((LifeCycleResourceCustomListGrid) getListGrid()).setUiHandlers(uiHandlers);
    }
}
