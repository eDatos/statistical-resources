package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SetMultidatasetEvent extends GwtEvent<SetMultidatasetEvent.SetMultidatasetHandler> {

    public interface SetMultidatasetHandler extends EventHandler {

        void onSetMultidatasetVersion(SetMultidatasetEvent event);
    }

    private static Type<SetMultidatasetHandler> TYPE = new Type<SetMultidatasetHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SetMultidatasetHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, MultidatasetVersionDto multidatasetVersionDto) {
        if (TYPE != null) {
            source.fireEvent(new SetMultidatasetEvent(multidatasetVersionDto));
        }
    }

    private final MultidatasetVersionDto multidatasetVersionDto;

    public SetMultidatasetEvent(MultidatasetVersionDto multidatasetVersionDto) {
        this.multidatasetVersionDto = multidatasetVersionDto;
    }

    public MultidatasetVersionDto getMultidatasetVersionDto() {
        return multidatasetVersionDto;
    }

    @Override
    protected void dispatch(SetMultidatasetHandler handler) {
        handler.onSetMultidatasetVersion(this);
    }

    public static Type<SetMultidatasetHandler> getType() {
        return TYPE;
    }
}
