package org.siemac.metamac.statistical.resources.web.client.multidataset.presenter;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.events.RequestMultidatasetVersionsReloadEvent;
import org.siemac.metamac.statistical.resources.web.client.events.RequestMultidatasetVersionsReloadEvent.RequestMultidatasetVersionsReloadHandler;
import org.siemac.metamac.statistical.resources.web.client.events.SetMultidatasetEvent;
import org.siemac.metamac.statistical.resources.web.client.events.SetMultidatasetEvent.SetMultidatasetHandler;
import org.siemac.metamac.statistical.resources.web.client.events.ShowUnauthorizedMultidatasetWarningMessageEvent;
import org.siemac.metamac.statistical.resources.web.client.events.ShowUnauthorizedMultidatasetWarningMessageEvent.ShowUnauthorizedMultidatasetWarningMessageHandler;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetVersionsOfMultidatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetVersionsOfMultidatasetResult;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

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
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class MultidatasetPresenter extends Presenter<MultidatasetPresenter.MultidatasetView, MultidatasetPresenter.MultidatasetProxy>
        implements
            MultidatasetUiHandlers,
            RequestMultidatasetVersionsReloadHandler,
            SetMultidatasetHandler,
            ShowUnauthorizedMultidatasetWarningMessageHandler {

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaMultidataset = new Type<RevealContentHandler<?>>();

    @ProxyCodeSplit
    @NameToken(NameTokens.multidatasetPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MultidatasetProxy extends Proxy<MultidatasetPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle(PlaceRequest placeRequest) {
        return PlaceRequestUtils.getMultidatasetBreadCrumbTitle(placeRequest);
    }

    public interface MultidatasetView extends View, HasUiHandlers<MultidatasetUiHandlers> {

        void setMultidataset(MultidatasetVersionDto multidatasetDto);
        void setMultidatasetVersionsAndSelectCurrent(String currentUrn, List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos);
        void showUnauthorizedResourceWarningMessage();
        void selectMetadataTab();
    }

    @Inject
    public MultidatasetPresenter(EventBus eventBus, MultidatasetView multidatasetView, MultidatasetProxy multidatasetProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, multidatasetView, multidatasetProxy);
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

        // Redirect to metadata tab
        getView().selectMetadataTab();
        if (NameTokens.multidatasetPage.equals(placeManager.getCurrentPlaceRequest().getNameToken())) {
            goToMultidatasetMetadata();
        }
    }
    
    @Override
    protected void onReveal() {
        super.onReveal();

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String multidatasetCode = PlaceRequestUtils.getMultidatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(multidatasetCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);

            if (!CommonUtils.isUrnFromSelectedStatisticalOperation(operationUrn)) {
                retrieveOperation(operationUrn);
            } else {
                loadInitialData();
            }

        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    @ProxyEvent
    @Override
    public void onRequestMultidatasetVersionsReload(RequestMultidatasetVersionsReloadEvent event) {
        retrieveMultidatasetVersions(event.getMultidatasetVersionUrn());
        goToMultidatasetVersion(event.getMultidatasetVersionUrn());
    }

    @ProxyEvent
    @Override
    public void onSetMultidatasetVersion(SetMultidatasetEvent event) {
        getView().setMultidataset(event.getMultidatasetVersionDto());
    }

    @ProxyEvent
    @Override
    public void onShowUnauthorizedMultidatasetWarningMessage(ShowUnauthorizedMultidatasetWarningMessageEvent event) {
        retrieveMultidatasetVersions(event.getUrn());
        getView().showUnauthorizedResourceWarningMessage();
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
        String multidatasetIdentifier = PlaceRequestUtils.getMultidatasetParamFromUrl(placeManager);
        String multidatasetVersionUrn = CommonUtils.generateMultidatasetUrn(multidatasetIdentifier);
        retrieveMultidatasetVersions(multidatasetVersionUrn);
    }

    private void retrieveMultidatasetVersions(final String urn) {
        dispatcher.execute(new GetVersionsOfMultidatasetAction(urn), new WaitingAsyncCallbackHandlingError<GetVersionsOfMultidatasetResult>(this) {

            @Override
            public void onWaitSuccess(GetVersionsOfMultidatasetResult result) {
                getView().setMultidatasetVersionsAndSelectCurrent(urn, result.getMultidatasetVersionBaseDtos());
            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goToMultidatasetVersion(String urn) {
        List<PlaceRequest> hierarchy = PlaceRequestUtils.getHierarchyUntilNameToken(placeManager, NameTokens.multidatasetsListPage);
        hierarchy.add(PlaceRequestUtils.buildRelativeMultidatasetPlaceRequest(urn));
        placeManager.revealPlaceHierarchy(hierarchy);
    }

    @Override
    public void goToMultidatasetMetadata() {
        List<PlaceRequest> hierarchy = PlaceRequestUtils.getHierarchyUntilNameToken(placeManager, NameTokens.multidatasetPage);
        hierarchy.add(new PlaceRequest(NameTokens.multidatasetMetadataPage));
        placeManager.revealPlaceHierarchy(hierarchy);
    }
}
