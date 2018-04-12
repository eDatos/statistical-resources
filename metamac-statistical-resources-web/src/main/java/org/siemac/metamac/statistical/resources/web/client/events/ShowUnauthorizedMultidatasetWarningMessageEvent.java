package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowUnauthorizedMultidatasetWarningMessageEvent extends GwtEvent<ShowUnauthorizedMultidatasetWarningMessageEvent.ShowUnauthorizedMultidatasetWarningMessageHandler> {

    public interface ShowUnauthorizedMultidatasetWarningMessageHandler extends EventHandler {

        void onShowUnauthorizedMultidatasetWarningMessage(ShowUnauthorizedMultidatasetWarningMessageEvent event);
    }

    private static Type<ShowUnauthorizedMultidatasetWarningMessageHandler> TYPE = new Type<ShowUnauthorizedMultidatasetWarningMessageHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ShowUnauthorizedMultidatasetWarningMessageHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String urn) {
        if (TYPE != null) {
            source.fireEvent(new ShowUnauthorizedMultidatasetWarningMessageEvent(urn));
        }
    }

    private final String urn;

    public ShowUnauthorizedMultidatasetWarningMessageEvent(String urn) {
        this.urn = urn;
    }

    public String getUrn() {
        return urn;
    }

    @Override
    protected void dispatch(ShowUnauthorizedMultidatasetWarningMessageHandler handler) {
        handler.onShowUnauthorizedMultidatasetWarningMessage(this);
    }

    public static Type<ShowUnauthorizedMultidatasetWarningMessageHandler> getType() {
        return TYPE;
    }
}
