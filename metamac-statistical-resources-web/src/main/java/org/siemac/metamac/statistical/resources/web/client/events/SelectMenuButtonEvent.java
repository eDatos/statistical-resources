package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectMenuButtonEvent extends GwtEvent<SelectMenuButtonEvent.SelectMenuButtonHandler> {

    public interface SelectMenuButtonHandler extends EventHandler {

        void onSelectMenuButton(SelectMenuButtonEvent event);
    }

    private static Type<SelectMenuButtonHandler> TYPE = new Type<SelectMenuButtonHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectMenuButtonHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, StatisticalResourcesToolStripButtonEnum resourceType) {
        if (TYPE != null) {
            source.fireEvent(new SelectMenuButtonEvent(resourceType));
        }
    }

    private final StatisticalResourcesToolStripButtonEnum resourceType;

    public SelectMenuButtonEvent(StatisticalResourcesToolStripButtonEnum resourceType) {
        this.resourceType = resourceType;
    }

    public StatisticalResourcesToolStripButtonEnum getResourceType() {
        return resourceType;
    }

    @Override
    protected void dispatch(SelectMenuButtonHandler handler) {
        handler.onSelectMenuButton(this);
    }

    public static Type<SelectMenuButtonHandler> getType() {
        return TYPE;
    }
}
