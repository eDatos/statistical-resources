package org.siemac.metamac.statistical.resources.web.client.event;

import org.siemac.metamac.core.common.dto.ExternalItemDto;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SetOperationEvent extends GwtEvent<SetOperationEvent.SetOperationHandler> {

    public interface SetOperationHandler extends EventHandler {

        void onSetOperation(SetOperationEvent event);
    }

    private static Type<SetOperationHandler> TYPE = new Type<SetOperationHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SetOperationHandler> getAssociatedType() {
        return TYPE;
    }

    // TODO HasEventBus should be used instead of HasHandlers ¿?
    public static void fire(HasHandlers source, ExternalItemDto operation) {
        if (TYPE != null) {
            source.fireEvent(new SetOperationEvent(operation));
        }
    }

    private final ExternalItemDto operation;

    public SetOperationEvent(ExternalItemDto operation) {
        this.operation = operation;
    }

    public ExternalItemDto getOperation() {
        return operation;
    }

    @Override
    protected void dispatch(SetOperationHandler handler) {
        handler.onSetOperation(this);
    }

    public static Type<SetOperationHandler> getType() {
        return TYPE;
    }

}
