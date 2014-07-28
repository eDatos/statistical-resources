package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ShowUnauthorizedDatasetWarningMessageEvent extends GwtEvent<ShowUnauthorizedDatasetWarningMessageEvent.ShowUnauthorizedDatasetWarningMessageHandler> {

    public interface ShowUnauthorizedDatasetWarningMessageHandler extends EventHandler {

        void onShowUnauthorizedDatasetWarningMessage(ShowUnauthorizedDatasetWarningMessageEvent event);
    }

    private static Type<ShowUnauthorizedDatasetWarningMessageHandler> TYPE = new Type<ShowUnauthorizedDatasetWarningMessageHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ShowUnauthorizedDatasetWarningMessageHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String urn) {
        if (TYPE != null) {
            source.fireEvent(new ShowUnauthorizedDatasetWarningMessageEvent(urn));
        }
    }

    private final String urn;

    public ShowUnauthorizedDatasetWarningMessageEvent(String urn) {
        this.urn = urn;
    }

    public String getUrn() {
        return urn;
    }

    @Override
    protected void dispatch(ShowUnauthorizedDatasetWarningMessageHandler handler) {
        handler.onShowUnauthorizedDatasetWarningMessage(this);
    }

    public static Type<ShowUnauthorizedDatasetWarningMessageHandler> getType() {
        return TYPE;
    }
}
