package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class DatasetPresenter extends Presenter<DatasetPresenter.DatasetView, DatasetPresenter.DatasetProxy> implements DatasetUiHandlers {

    private PlaceManager                              placeManager;
    private DispatchAsync                             dispatcher;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaMetadata    = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaDatasources = new Type<RevealContentHandler<?>>();

    public interface DatasetView extends View, HasUiHandlers<DatasetUiHandlers> {

        void setDataset(DatasetVersionDto datasetDto);
        void showMetadata();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetProxy extends Proxy<DatasetPresenter>, Place {
    }

    @Inject
    public DatasetPresenter(EventBus eventBus, DatasetView view, DatasetProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String getTitle(PlaceRequest placeRequest) {
        return PlaceRequestUtils.getDatasetBreadCrumbTitle(placeRequest);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {

    }

    @Override
    protected void onReveal() {
        super.onReveal();

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
            String operationUrn = CommonUtils.generateStatisticalOperationUrn(operationCode);

            if (!CommonUtils.isUrnFromSelectedStatisticalOperation(operationUrn)) {
                retrieveOperation(operationUrn);
            } else {
                loadInitialData();
            }

        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationResult result) {
                StatisticalResourcesDefaults.setSelectedStatisticalOperation(result.getOperation());
                loadInitialData();
            }
        });
    }

    private void loadInitialData() {
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        retrieveDataset(datasetCode);
        getView().showMetadata();
    }

    public void retrieveDataset(String datasetIdentifier) {
        String urn = CommonUtils.generateDatasetUrn(datasetIdentifier);
        dispatcher.execute(new GetDatasetVersionAction(urn), new WaitingAsyncCallbackHandlingError<GetDatasetVersionResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionResult result) {
                getView().setDataset(result.getDatasetVersionDto());
            }
        });
    }

    @Override
    public void goToDatasetMetadata() {
        List<PlaceRequest> hierarchy = PlaceRequestUtils.getHierarchyUntilNameToken(placeManager, NameTokens.datasetPage);
        hierarchy.add(new PlaceRequest(NameTokens.datasetMetadataPage));
        placeManager.revealPlaceHierarchy(hierarchy);
    }

    @Override
    public void goToDatasetDatasources() {
        List<PlaceRequest> hierarchy = PlaceRequestUtils.getHierarchyUntilNameToken(placeManager, NameTokens.datasetPage);
        hierarchy.add(new PlaceRequest(NameTokens.datasetDatasourcesPage));
        placeManager.revealPlaceHierarchy(hierarchy);
    }
}
