package org.siemac.metamac.statistical.resources.web.client.base.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.base.presenter.LifeCycleBaseListPresenter;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class LifeCycleBaseListViewImpl<C extends UiHandlers> extends ViewWithUiHandlers<C> implements LifeCycleBaseListPresenter.LifeCycleBaseListView {

    protected VLayout                  panel;
    protected SearchSectionStack       searchSectionStack;

    protected ToolStrip                toolStrip;
    protected CustomToolStripButton    newButton;
    protected CustomToolStripButton    deleteButton;

    protected DeleteConfirmationWindow deleteConfirmationWindow;

    protected PaginatedCheckListGrid   listGrid;

    public LifeCycleBaseListViewImpl() {
        super();

        // Search

        searchSectionStack = new SearchSectionStack();

        // ToolStrip

        toolStrip = new ToolStrip();
        toolStrip.setWidth100();

        panel = new VLayout();
        panel.addMember(toolStrip);
        panel.addMember(searchSectionStack);

        // Delete confirmation window

        deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().lifeCycleDeleteConfirmationTitle(), getConstants().lifeCycleDeleteConfirmation());
        deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);
    }
}
