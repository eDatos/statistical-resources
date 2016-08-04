package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class RequestPublicationVersionsReloadEvent extends GwtEvent<RequestPublicationVersionsReloadEvent.RequestPublicationVersionsReloadHandler> {

    public interface RequestPublicationVersionsReloadHandler extends EventHandler {

        void onRequestPublicationVersionsReload(RequestPublicationVersionsReloadEvent event);
    }

    private static Type<RequestPublicationVersionsReloadHandler> TYPE = new Type<RequestPublicationVersionsReloadHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RequestPublicationVersionsReloadHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String publicationVersionUrn) {
        if (TYPE != null) {
            source.fireEvent(new RequestPublicationVersionsReloadEvent(publicationVersionUrn));
        }
    }

    private final String publicationVersionUrn;

    public RequestPublicationVersionsReloadEvent(String publicationVersionUrn) {
        this.publicationVersionUrn = publicationVersionUrn;
    }

    public String getPublicationVersionUrn() {
        return publicationVersionUrn;
    }

    @Override
    protected void dispatch(RequestPublicationVersionsReloadHandler handler) {
        handler.onRequestPublicationVersionsReload(this);
    }

    public static Type<RequestPublicationVersionsReloadHandler> getType() {
        return TYPE;
    }
}
