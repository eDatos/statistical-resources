package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetConstraintsTabPresenter.DatasetConstraintsTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetConstraintsTabUiHandlers;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetConstraintsTabViewImpl extends ViewWithUiHandlers<DatasetConstraintsTabUiHandlers> implements DatasetConstraintsTabView {

    private VLayout panel;

    public DatasetConstraintsTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();

        // TODO
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
