package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
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

    private PlaceManager  placeManager;
    private DispatchAsync dispatcher;
    
    private ExternalItemDto operation;
    
    @ContentSlot
    public static final Type<RevealContentHandler<?>>    TYPE_SetContextAreaMetadata = new Type<RevealContentHandler<?>>();
    
    @ContentSlot
    public static final Type<RevealContentHandler<?>>    TYPE_SetContextAreaDatasources = new Type<RevealContentHandler<?>>();

    public interface DatasetView extends View, HasUiHandlers<DatasetUiHandlers> {
        void setDataset(DatasetDto datasetDto);
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
    public String getTitle() {
        return getConstants().dataset();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            retrieveDataset(datasetCode);
            getView().showMetadata();
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }
    
    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    DatasetPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(DatasetPresenter.this, result.getOperation());
                }
            });
        }
    }
    
    public void retrieveDataset(String datasetIdentifier) {
        String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetIdentifier);
        dispatcher.execute(new GetDatasetAction(urn), new WaitingAsyncCallback<GetDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorRetrieve()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(GetDatasetResult result) {
                getView().setDataset(result.getDatasetDto());
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
