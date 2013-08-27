package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SetPublicationEvent extends GwtEvent<SetPublicationEvent.SetPublicationHandler> {

    public interface SetPublicationHandler extends EventHandler {

        void onSetPublicationVersion(SetPublicationEvent event);
    }

    private static Type<SetPublicationHandler> TYPE = new Type<SetPublicationHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SetPublicationHandler> getAssociatedType() {
        return TYPE;
    }

    // TODO HasEventBus should be used instead of HasHandlers ¿?
    public static void fire(HasHandlers source, PublicationVersionDto publicationVersionDto) {
        if (TYPE != null) {
            source.fireEvent(new SetPublicationEvent(publicationVersionDto));
        }
    }

    private final PublicationVersionDto publicationVersionDto;

    public SetPublicationEvent(PublicationVersionDto publicationVersionDto) {
        this.publicationVersionDto = publicationVersionDto;
    }

    public PublicationVersionDto getPublicationVersionDto() {
        return publicationVersionDto;
    }

    @Override
    protected void dispatch(SetPublicationHandler handler) {
        handler.onSetPublicationVersion(this);
    }

    public static Type<SetPublicationHandler> getType() {
        return TYPE;
    }
}
