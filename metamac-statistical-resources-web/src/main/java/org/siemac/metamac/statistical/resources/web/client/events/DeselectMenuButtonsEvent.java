package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class DeselectMenuButtonsEvent extends GwtEvent<DeselectMenuButtonsEvent.DeselectMenuButtonHandler> {

    public interface DeselectMenuButtonHandler extends EventHandler {

        void onDeselectMenuButton(DeselectMenuButtonsEvent event);
    }

    private static Type<DeselectMenuButtonHandler> TYPE = new Type<DeselectMenuButtonHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<DeselectMenuButtonHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        if (TYPE != null) {
            source.fireEvent(new DeselectMenuButtonsEvent());
        }
    }

    public DeselectMenuButtonsEvent() {
    }

    @Override
    protected void dispatch(DeselectMenuButtonHandler handler) {
        handler.onDeselectMenuButton(this);
    }

    public static Type<DeselectMenuButtonHandler> getType() {
        return TYPE;
    }
}
