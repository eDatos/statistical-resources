package org.siemac.metamac.statistical.resources.web.client.widgets.presenter;

import java.util.List;

import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.web.common.client.widgets.toolstrip.presenter.MetamacToolStripPresenterWidget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class StatisticalResourcesToolStripPresenterWidget extends MetamacToolStripPresenterWidget<StatisticalResourcesToolStripPresenterWidget.StatisticalResourcesToolStripView> {

    public interface StatisticalResourcesToolStripView extends MetamacToolStripPresenterWidget.MetamacToolStripView {

        HasClickHandlers getGoDatasetsList();
        HasClickHandlers getGoPublicationsList();
        HasClickHandlers getGoQueriesList();
    }

    @Inject
    public StatisticalResourcesToolStripPresenterWidget(EventBus eventBus, StatisticalResourcesToolStripView operationsToolStripView, PlaceManager placeManager) {
        super(eventBus, operationsToolStripView, placeManager);
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getGoDatasetsList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<PlaceRequest> operationHierarchy = getHierarchyUntilNameToken(NameTokens.operationPage);
                operationHierarchy.add(PlaceRequestUtils.buildRelativeDatasetsPlaceRequest());
                getPlaceManager().revealPlaceHierarchy(operationHierarchy);
            }
        }));

        registerHandler(getView().getGoPublicationsList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<PlaceRequest> operationHierarchy = getHierarchyUntilNameToken(NameTokens.operationPage);
                operationHierarchy.add(PlaceRequestUtils.buildRelativePublicationsPlaceRequest());
                getPlaceManager().revealPlaceHierarchy(operationHierarchy);
            }
        }));

        registerHandler(getView().getGoQueriesList().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<PlaceRequest> operationHierarchy = getHierarchyUntilNameToken(NameTokens.operationPage);
                operationHierarchy.add(PlaceRequestUtils.buildRelativeQueriesPlaceRequest());
                getPlaceManager().revealPlaceHierarchy(operationHierarchy);
            }
        }));
    }
}
