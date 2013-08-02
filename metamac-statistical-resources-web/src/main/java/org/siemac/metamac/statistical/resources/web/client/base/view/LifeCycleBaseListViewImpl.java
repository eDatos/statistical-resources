package org.siemac.metamac.statistical.resources.web.client.base.view;

import org.siemac.metamac.statistical.resources.web.client.base.presenter.LifeCycleBaseListPresenter;

import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public abstract class LifeCycleBaseListViewImpl<C extends UiHandlers> extends ViewWithUiHandlers<C> implements LifeCycleBaseListPresenter.LifeCycleBaseListView {

    protected VLayout   panel;
    protected ToolStrip toolStrip;

    public LifeCycleBaseListViewImpl() {
        super();

        toolStrip = new ToolStrip();
        toolStrip.setWidth100();

        panel = new VLayout();
        panel.addMember(toolStrip);
    }
}
