package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ChangeWaitPopupVisibilityEvent extends GwtEvent<ChangeWaitPopupVisibilityEvent.ChangeWaitPopupVisibilityHandler> {

    public interface ChangeWaitPopupVisibilityHandler extends EventHandler {

        void onChangeWaitPopupVisibility(ChangeWaitPopupVisibilityEvent event);
    }

    private static Type<ChangeWaitPopupVisibilityHandler> TYPE = new Type<ChangeWaitPopupVisibilityHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ChangeWaitPopupVisibilityHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, boolean showPopup) {
        if (TYPE != null) {
            source.fireEvent(new ChangeWaitPopupVisibilityEvent(showPopup));
        }
    }

    private final boolean showPopup;

    public ChangeWaitPopupVisibilityEvent(boolean showPopup) {
        this.showPopup = showPopup;
    }

    public boolean isShowPopup() {
        return showPopup;
    }

    @Override
    protected void dispatch(ChangeWaitPopupVisibilityHandler handler) {
        handler.onChangeWaitPopupVisibility(this);
    }

    public static Type<ChangeWaitPopupVisibilityHandler> getType() {
        return TYPE;
    }
}
