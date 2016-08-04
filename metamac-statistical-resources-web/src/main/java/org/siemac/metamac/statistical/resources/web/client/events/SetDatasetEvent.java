package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SetDatasetEvent extends GwtEvent<SetDatasetEvent.SetDatasetHandler> {

    public interface SetDatasetHandler extends EventHandler {

        void onSetDatasetVersion(SetDatasetEvent event);
    }

    private static Type<SetDatasetHandler> TYPE = new Type<SetDatasetHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SetDatasetHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, DatasetVersionDto datasetVersionDto) {
        if (TYPE != null) {
            source.fireEvent(new SetDatasetEvent(datasetVersionDto));
        }
    }

    private final DatasetVersionDto datasetVersionDto;

    public SetDatasetEvent(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
    }

    public DatasetVersionDto getDatasetVersionDto() {
        return datasetVersionDto;
    }

    @Override
    protected void dispatch(SetDatasetHandler handler) {
        handler.onSetDatasetVersion(this);
    }

    public static Type<SetDatasetHandler> getType() {
        return TYPE;
    }
}
