package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class RequestMultidatasetVersionsReloadEvent extends GwtEvent<RequestMultidatasetVersionsReloadEvent.RequestMultidatasetVersionsReloadHandler> {

    public interface RequestMultidatasetVersionsReloadHandler extends EventHandler {

        void onRequestMultidatasetVersionsReload(RequestMultidatasetVersionsReloadEvent event);
    }

    private static Type<RequestMultidatasetVersionsReloadHandler> TYPE = new Type<RequestMultidatasetVersionsReloadHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RequestMultidatasetVersionsReloadHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String multidatasetVersionUrn) {
        if (TYPE != null) {
            source.fireEvent(new RequestMultidatasetVersionsReloadEvent(multidatasetVersionUrn));
        }
    }

    private final String multidatasetVersionUrn;

    public RequestMultidatasetVersionsReloadEvent(String multidatasetVersionUrn) {
        this.multidatasetVersionUrn = multidatasetVersionUrn;
    }

    public String getMultidatasetVersionUrn() {
        return multidatasetVersionUrn;
    }

    @Override
    protected void dispatch(RequestMultidatasetVersionsReloadHandler handler) {
        handler.onRequestMultidatasetVersionsReload(this);
    }

    public static Type<RequestMultidatasetVersionsReloadHandler> getType() {
        return TYPE;
    }
}
