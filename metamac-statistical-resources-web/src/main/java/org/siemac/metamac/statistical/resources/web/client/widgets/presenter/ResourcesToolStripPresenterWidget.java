package org.siemac.metamac.statistical.resources.web.client.widgets.presenter;

import org.siemac.metamac.statistical.resources.web.client.NameTokens;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class ResourcesToolStripPresenterWidget extends PresenterWidget<ResourcesToolStripPresenterWidget.ResourcesToolStripView> {

    private final PlaceManager placeManager;

    public interface ResourcesToolStripView extends View {

        HasClickHandlers getGoDatasetsList();
        HasClickHandlers getGoCollectionsList();
        HasClickHandlers getGoQueriesList();
    }

    @Inject
    public ResourcesToolStripPresenterWidget(EventBus eventBus, ResourcesToolStripView operationsToolStripView, PlaceManager placeManager) {
        super(eventBus, operationsToolStripView);
        this.placeManager = placeManager;
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getGoDatasetsList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                placeManager.revealPlace(new PlaceRequest(NameTokens.datasetsListPage));
            }
        }));

        registerHandler(getView().getGoCollectionsList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                placeManager.revealPlace(new PlaceRequest(NameTokens.collectionsListPage));
            }
        }));
    }

}
