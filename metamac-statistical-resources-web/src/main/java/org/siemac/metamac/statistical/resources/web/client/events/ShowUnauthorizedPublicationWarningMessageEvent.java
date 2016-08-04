package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowUnauthorizedPublicationWarningMessageEvent extends GwtEvent<ShowUnauthorizedPublicationWarningMessageEvent.ShowUnauthorizedPublicationWarningMessageHandler> {

    public interface ShowUnauthorizedPublicationWarningMessageHandler extends EventHandler {

        void onShowUnauthorizedPublicationWarningMessage(ShowUnauthorizedPublicationWarningMessageEvent event);
    }

    private static Type<ShowUnauthorizedPublicationWarningMessageHandler> TYPE = new Type<ShowUnauthorizedPublicationWarningMessageHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ShowUnauthorizedPublicationWarningMessageHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String urn) {
        if (TYPE != null) {
            source.fireEvent(new ShowUnauthorizedPublicationWarningMessageEvent(urn));
        }
    }

    private final String urn;

    public ShowUnauthorizedPublicationWarningMessageEvent(String urn) {
        this.urn = urn;
    }

    public String getUrn() {
        return urn;
    }

    @Override
    protected void dispatch(ShowUnauthorizedPublicationWarningMessageHandler handler) {
        handler.onShowUnauthorizedPublicationWarningMessage(this);
    }

    public static Type<ShowUnauthorizedPublicationWarningMessageHandler> getType() {
        return TYPE;
    }
}
