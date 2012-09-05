package org.siemac.metamac.statistical.resources.web.client.widgets.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.web.common.client.utils.UrnUtils;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class StatisticalResourcesToolStripPresenterWidget extends PresenterWidget<StatisticalResourcesToolStripPresenterWidget.StatisticalResourcesToolStripView> {

    private final PlaceManager placeManager;
    
    public interface StatisticalResourcesToolStripView extends View {

        HasClickHandlers getGoDatasetsList();
        HasClickHandlers getGoCollectionsList();
        HasClickHandlers getGoQueriesList();
    }

    @Inject
    public StatisticalResourcesToolStripPresenterWidget(EventBus eventBus, StatisticalResourcesToolStripView operationsToolStripView, PlaceManager placeManager) {
        super(eventBus, operationsToolStripView);
        this.placeManager = placeManager;
    }
    
    private List<PlaceRequest> getHierarchyUntilOperation() {
        List<PlaceRequest> filteredHierarchy = new ArrayList<PlaceRequest>();
        List<PlaceRequest> hierarchy = placeManager.getCurrentPlaceHierarchy();
        boolean found = false;
        for (int i = 0; i < hierarchy.size() && !found; i++) {
            PlaceRequest placeReq = hierarchy.get(i);
            if (placeReq.matchesNameToken(NameTokens.operationPage)) {
                found = true;
            }
            filteredHierarchy.add(placeReq);
        }
        
        return filteredHierarchy;
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getGoDatasetsList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<PlaceRequest> operationHierarchy = getHierarchyUntilOperation();
                operationHierarchy.add(new PlaceRequest(NameTokens.datasetsListPage));
                placeManager.revealPlaceHierarchy(operationHierarchy);
            }
        }));

        registerHandler(getView().getGoCollectionsList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<PlaceRequest> operationHierarchy = getHierarchyUntilOperation();
                operationHierarchy.add(new PlaceRequest(NameTokens.collectionsListPage));
                placeManager.revealPlaceHierarchy(operationHierarchy);
            }
        }));
        
        registerHandler(getView().getGoQueriesList().addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                List<PlaceRequest> operationHierarchy = getHierarchyUntilOperation();
                operationHierarchy.add(new PlaceRequest(NameTokens.queriesListPage));
                placeManager.revealPlaceHierarchy(operationHierarchy);
            }
        }));
    }

}
