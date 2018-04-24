package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.web.client.enums.MultidatasetTabTypeEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectMultidatasetTabEvent extends GwtEvent<SelectMultidatasetTabEvent.SelectMultidatasetTabHandler> {

    public interface SelectMultidatasetTabHandler extends EventHandler {

        void onSelectMultidatasetTab(SelectMultidatasetTabEvent event);
    }

    private static Type<SelectMultidatasetTabHandler> TYPE = new Type<SelectMultidatasetTabHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectMultidatasetTabHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, MultidatasetTabTypeEnum publicatioTabType) {
        if (TYPE != null) {
            source.fireEvent(new SelectMultidatasetTabEvent(publicatioTabType));
        }
    }

    private final MultidatasetTabTypeEnum multidatasetTabType;

    public SelectMultidatasetTabEvent(MultidatasetTabTypeEnum tabType) {
        multidatasetTabType = tabType;
    }

    public MultidatasetTabTypeEnum getMultidatasetTabTypeEnum() {
        return multidatasetTabType;
    }

    @Override
    protected void dispatch(SelectMultidatasetTabHandler handler) {
        handler.onSelectMultidatasetTab(this);
    }

    public static Type<SelectMultidatasetTabHandler> getType() {
        return TYPE;
    }
}
