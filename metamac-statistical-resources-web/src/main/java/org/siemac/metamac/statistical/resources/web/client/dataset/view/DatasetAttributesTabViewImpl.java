package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetAttributesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetAttributesTabPresenter.DatasetAttributesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetAttributesTabViewImpl extends ViewWithUiHandlers<DatasetAttributesTabUiHandlers> implements DatasetAttributesTabView {

    private VLayout panel;

    public DatasetAttributesTabViewImpl() {
        panel = new VLayout();

    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
    }
}
