package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;


public class DatasetDatasourcesTabViewImpl extends ViewImpl implements DatasetDatasourcesTabView {

    private VLayout panel;
    
    public DatasetDatasourcesTabViewImpl() {
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);
    }
    
    @Override
    public Widget asWidget() {
        return panel;
    }

}
