package org.siemac.metamac.statistical.resources.web.client.operation.view;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetListGrid;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;

public class OperationViewImpl extends ViewImpl implements OperationPresenter.OperationView {

    /* Visual components */
    private VLayout    panel;
    private VLayout    contentPanel;
    private TitleLabel operationTitle;

    @Inject
    public OperationViewImpl() {

        operationTitle = new TitleLabel();
        operationTitle.setStyleName("sectionTitleLeftMargin");

        contentPanel = new VLayout();

        panel = new VLayout();
        panel.addMember(operationTitle);
        panel.addMember(contentPanel);
    }

    @Override
    public void setOperation(ExternalItemDto operation) {
        operationTitle.setContents(InternationalStringUtils.getLocalisedString(operation.getTitle()));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == OperationPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                panel.addMember(content, 1);
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
