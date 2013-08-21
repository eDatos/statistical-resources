package org.siemac.metamac.statistical.resources.web.client.events;

import org.siemac.metamac.statistical.resources.web.client.dataset.enums.DatasetTabTypeEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectDatasetTabEvent extends GwtEvent<SelectDatasetTabEvent.SelectDatasetTabHandler> {

    public interface SelectDatasetTabHandler extends EventHandler {

        void onSelectDatasetTab(SelectDatasetTabEvent event);
    }

    private static Type<SelectDatasetTabHandler> TYPE = new Type<SelectDatasetTabHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectDatasetTabHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, DatasetTabTypeEnum datasetTabType) {
        if (TYPE != null) {
            source.fireEvent(new SelectDatasetTabEvent(datasetTabType));
        }
    }

    private final DatasetTabTypeEnum datasetTabType;

    public SelectDatasetTabEvent(DatasetTabTypeEnum tabType) {
        this.datasetTabType = tabType;
    }

    public DatasetTabTypeEnum getDatasetTabTypeEnum() {
        return datasetTabType;
    }

    @Override
    protected void dispatch(SelectDatasetTabHandler handler) {
        handler.onSelectDatasetTab(this);
    }

    public static Type<SelectDatasetTabHandler> getType() {
        return TYPE;
    }
}
