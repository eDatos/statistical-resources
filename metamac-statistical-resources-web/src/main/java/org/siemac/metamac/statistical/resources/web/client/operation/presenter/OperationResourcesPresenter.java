package org.siemac.metamac.statistical.resources.web.client.operation.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent.SetOperationHandler;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter.OperationResourcesProxy;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter.OperationResourcesView;
import org.siemac.metamac.statistical.resources.web.client.operation.view.handlers.OperationResourcesUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationPaginatedListResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class OperationResourcesPresenter extends Presenter<OperationResourcesView, OperationResourcesProxy> implements OperationResourcesUiHandlers, SetOperationHandler {

    public final static int     RESOURCE_LIST_FIRST_RESULT = 0;
    public final static int     RESOURCE_LIST_MAX_RESULTS  = 10;

    private final PlaceManager  placeManager;
    private final DispatchAsync dispatcher;

    private ExternalItemDto     operation;

    public interface OperationResourcesView extends View, HasUiHandlers<OperationResourcesUiHandlers> {

        void setDatasets(List<DatasetDto> datasetDtos);
        void setPublications(List<PublicationDto> collectionDtos);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.operationResourcesPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface OperationResourcesProxy extends Proxy<OperationResourcesPresenter>, Place {
    }

    @Inject
    public OperationResourcesPresenter(EventBus eventBus, OperationResourcesView view, OperationResourcesProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationParam = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationParam)) {
            String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationParam);
            retrieveOperation(urn);
            retrieveResourcesByStatisticalOperation(urn);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().statisticalResources());
    }

    @ProxyEvent
    @Override
    public void onSetOperation(SetOperationEvent event) {
        this.operation = event.getOperation();
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(OperationResourcesPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    OperationResourcesPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(OperationResourcesPresenter.this, operation);
                }
            });
        }
    }

    private void retrieveResourcesByStatisticalOperation(String urn) {
        // DataSets
        dispatcher.execute(new GetDatasetsByStatisticalOperationPaginatedListAction(urn, RESOURCE_LIST_FIRST_RESULT, RESOURCE_LIST_MAX_RESULTS),
                new WaitingAsyncCallback<GetDatasetsByStatisticalOperationPaginatedListResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(OperationResourcesPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorRetrieveList()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(GetDatasetsByStatisticalOperationPaginatedListResult result) {
                        getView().setDatasets(result.getDatasetsList());
                    }
                });

        // Publications
        dispatcher.execute(new GetPublicationPaginatedListAction(urn, RESOURCE_LIST_FIRST_RESULT, RESOURCE_LIST_MAX_RESULTS, null), new WaitingAsyncCallback<GetPublicationPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(OperationResourcesPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorRetrieveList()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetPublicationPaginatedListResult result) {
                getView().setPublications(result.getPublicationList());
            }
        });
    }

    @Override
    public void goToDataset(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.datasetPage).with(PlaceRequestParams.datasetParam, UrnUtils.removePrefix(urn)));
        }
    }

    @Override
    public void goToPublication(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.collectionPage).with(PlaceRequestParams.collectionParam, UrnUtils.removePrefix(urn)));
        }
    }

}
