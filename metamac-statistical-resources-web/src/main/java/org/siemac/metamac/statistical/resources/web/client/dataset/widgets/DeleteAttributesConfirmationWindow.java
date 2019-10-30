package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.resources.GlobalResources;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DeleteAttributesConfirmationWindow extends Window {

    private IButton yesButton;
    private IButton noButton;

    public DeleteAttributesConfirmationWindow(String title, String message1, String message2) {
        super();
        setWidth(360);
        setHeight(180);
        setTitle(title);
        setShowMinimizeButton(false);
        setIsModal(true);
        setShowModalMask(true);
        setAutoCenter(true);
        addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClickEvent event) {
                hide();
            }
        });

        Label label1 = new Label(message1);
        label1.setAutoHeight();
        label1.setIcon(GlobalResources.RESOURCE.ask().getURL());
        label1.setIconSize(32);
        label1.setIconSpacing(10);

        Label label2 = new Label(message2);
        label2.setAutoHeight();

        yesButton = new IButton(MetamacWebCommon.getConstants().yes());
        yesButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                DeleteAttributesConfirmationWindow.this.hide();
            }
        });

        noButton = new IButton(MetamacWebCommon.getConstants().no());
        noButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                DeleteAttributesConfirmationWindow.this.hide();
            }
        });

        HLayout buttonsLayout = new HLayout(2);
        buttonsLayout.addMember(yesButton);
        buttonsLayout.addMember(noButton);
        buttonsLayout.setAlign(Alignment.CENTER);

        VLayout layout = new VLayout();
        layout.setMembersMargin(20);
        layout.addMember(label1);
        layout.addMember(label2);
        layout.addMember(buttonsLayout);
        layout.setMargin(10);

        addItem(layout);
    }

    public IButton getYesButton() {
        return yesButton;
    }

    public IButton getNoButton() {
        return noButton;
    }

}
