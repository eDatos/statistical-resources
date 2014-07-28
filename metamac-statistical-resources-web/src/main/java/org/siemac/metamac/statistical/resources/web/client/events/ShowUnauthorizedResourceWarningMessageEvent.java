package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowUnauthorizedResourceWarningMessageEvent extends GwtEvent<ShowUnauthorizedResourceWarningMessageEvent.ShowUnauthorizedResourceWarningMessageHandler> {

    public interface ShowUnauthorizedResourceWarningMessageHandler extends EventHandler {

        void onShowUnauthorizedResourceWarningMessage(ShowUnauthorizedResourceWarningMessageEvent event);
    }

    private static Type<ShowUnauthorizedResourceWarningMessageHandler> TYPE = new Type<ShowUnauthorizedResourceWarningMessageHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ShowUnauthorizedResourceWarningMessageHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String urn) {
        if (TYPE != null) {
            source.fireEvent(new ShowUnauthorizedResourceWarningMessageEvent(urn));
        }
    }

    private final String urn;

    public ShowUnauthorizedResourceWarningMessageEvent(String resourceUrn) {
        this.urn = resourceUrn;
    }

    public String getUrn() {
        return urn;
    }

    @Override
    protected void dispatch(ShowUnauthorizedResourceWarningMessageHandler handler) {
        handler.onShowUnauthorizedResourceWarningMessage(this);
    }

    public static Type<ShowUnauthorizedResourceWarningMessageHandler> getType() {
        return TYPE;
    }
}
