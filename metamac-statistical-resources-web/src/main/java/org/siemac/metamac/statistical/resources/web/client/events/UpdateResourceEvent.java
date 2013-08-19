package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class UpdateResourceEvent extends GwtEvent<UpdateResourceEvent.UpdateResourceHandler> {

    public interface UpdateResourceHandler extends EventHandler {

        void onUpdateResource(UpdateResourceEvent event);
    }

    private static Type<UpdateResourceHandler> TYPE = new Type<UpdateResourceHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<UpdateResourceHandler> getAssociatedType() {
        return TYPE;
    }

    // TODO HasEventBus should be used instead of HasHandlers ¿?
    public static void fire(HasHandlers source, String urn, TypeRelatedResourceEnum resourceType) {
        if (TYPE != null) {
            source.fireEvent(new UpdateResourceEvent(urn, resourceType));
        }
    }

    private final String                  urn;
    private final TypeRelatedResourceEnum resourceType;

    public UpdateResourceEvent(String urn, TypeRelatedResourceEnum resourceType) {
        this.urn = urn;
        this.resourceType = resourceType;
    }

    public String getUrn() {
        return urn;
    }

    public TypeRelatedResourceEnum getResourceType() {
        return resourceType;
    }

    @Override
    protected void dispatch(UpdateResourceHandler handler) {
        handler.onUpdateResource(this);
    }

    public static Type<UpdateResourceHandler> getType() {
        return TYPE;
    }
}
