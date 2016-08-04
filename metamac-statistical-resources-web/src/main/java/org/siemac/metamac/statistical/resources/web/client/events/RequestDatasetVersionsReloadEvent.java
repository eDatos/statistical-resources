package org.siemac.metamac.statistical.resources.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class RequestDatasetVersionsReloadEvent extends GwtEvent<RequestDatasetVersionsReloadEvent.RequestDatasetVersionsReloadHandler> {

    public interface RequestDatasetVersionsReloadHandler extends EventHandler {

        void onRequestDatasetVersionsReload(RequestDatasetVersionsReloadEvent event);
    }

    private static Type<RequestDatasetVersionsReloadHandler> TYPE = new Type<RequestDatasetVersionsReloadHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RequestDatasetVersionsReloadHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String datasetUrn) {
        if (TYPE != null) {
            source.fireEvent(new RequestDatasetVersionsReloadEvent(datasetUrn));
        }
    }

    private final String datasetVersionUrn;

    public RequestDatasetVersionsReloadEvent(String datasetVersionUrn) {
        this.datasetVersionUrn = datasetVersionUrn;
    }

    public String getDatasetVersionUrn() {
        return datasetVersionUrn;
    }

    @Override
    protected void dispatch(RequestDatasetVersionsReloadHandler handler) {
        handler.onRequestDatasetVersionsReload(this);
    }

    public static Type<RequestDatasetVersionsReloadHandler> getType() {
        return TYPE;
    }
}
