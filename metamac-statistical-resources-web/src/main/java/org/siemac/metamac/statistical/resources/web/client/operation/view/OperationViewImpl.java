package org.siemac.metamac.statistical.resources.web.client.operation.view;

import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;

public class OperationViewImpl extends ViewImpl implements OperationPresenter.OperationView {

    private VLayout panel;
    private VLayout contentPanel;

    @Inject
    public OperationViewImpl() {

        contentPanel = new VLayout();

        panel = new VLayout();
        panel.addMember(contentPanel);
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == OperationPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                panel.addMember(content, 0);
            }
        } else if (slot == OperationPresenter.TYPE_SetContextAreaContent) {
            if (content != null) {
                contentPanel.setMembers((VLayout) content);
            }
        } else {
            // To support inheritance in your views it is good practice to call super.setInSlot when you can't handle the call.
            // Who knows, maybe the parent class knows what to do with this slot.
            super.setInSlot(slot, content);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
