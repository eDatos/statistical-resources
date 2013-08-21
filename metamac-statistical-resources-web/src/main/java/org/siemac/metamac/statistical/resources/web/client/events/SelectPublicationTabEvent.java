package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.web.client.enums.PublicationTabTypeEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectPublicationTabEvent extends GwtEvent<SelectPublicationTabEvent.SelectPublicationTabHandler> {

    public interface SelectPublicationTabHandler extends EventHandler {

        void onSelectPublicationTab(SelectPublicationTabEvent event);
    }

    private static Type<SelectPublicationTabHandler> TYPE = new Type<SelectPublicationTabHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectPublicationTabHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, PublicationTabTypeEnum publicatioTabType) {
        if (TYPE != null) {
            source.fireEvent(new SelectPublicationTabEvent(publicatioTabType));
        }
    }

    private final PublicationTabTypeEnum publicationTabType;

    public SelectPublicationTabEvent(PublicationTabTypeEnum tabType) {
        this.publicationTabType = tabType;
    }

    public PublicationTabTypeEnum getPublicationTabTypeEnum() {
        return publicationTabType;
    }

    @Override
    protected void dispatch(SelectPublicationTabHandler handler) {
        handler.onSelectPublicationTab(this);
    }

    public static Type<SelectPublicationTabHandler> getType() {
        return TYPE;
    }
}
