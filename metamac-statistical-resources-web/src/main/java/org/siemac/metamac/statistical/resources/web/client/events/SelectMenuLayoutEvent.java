package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripLayoutEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectMenuLayoutEvent extends GwtEvent<SelectMenuLayoutEvent.SelectMenuLayoutHandler> {

    public interface SelectMenuLayoutHandler extends EventHandler {

        void onSelectMenuLayout(SelectMenuLayoutEvent event);
    }

    private static Type<SelectMenuLayoutHandler> TYPE = new Type<SelectMenuLayoutHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectMenuLayoutHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, StatisticalResourcesToolStripLayoutEnum resourceType) {
        if (TYPE != null) {
            source.fireEvent(new SelectMenuLayoutEvent(resourceType));
        }
    }

    private final StatisticalResourcesToolStripLayoutEnum resourceType;

    public SelectMenuLayoutEvent(StatisticalResourcesToolStripLayoutEnum resourceType) {
        this.resourceType = resourceType;
    }

    public StatisticalResourcesToolStripLayoutEnum getResourceType() {
        return resourceType;
    }

    @Override
    protected void dispatch(SelectMenuLayoutHandler handler) {
        handler.onSelectMenuLayout(this);
    }

    public static Type<SelectMenuLayoutHandler> getType() {
        return TYPE;
    }
}
