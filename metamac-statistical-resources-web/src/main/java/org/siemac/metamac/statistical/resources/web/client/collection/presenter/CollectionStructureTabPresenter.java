package org.siemac.metamac.statistical.resources.web.client.collection.presenter;


import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.BreadCrumbsPanel;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionResult;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class CollectionStructureTabPresenter extends Presenter<CollectionStructureTabPresenter.CollectionStructureTabView, CollectionStructureTabPresenter.CollectionStructureTabProxy> {

    private DispatchAsync dispatcher;
    private PlaceManager placeManager;
    
    private ExternalItemDto operation;
    
    public interface CollectionStructureTabView extends View {
        void setCollection(CollectionDto collectionDto);
    }
    
    @ProxyCodeSplit
    @NameToken(NameTokens.collectionStructurePage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface CollectionStructureTabProxy extends Proxy<CollectionStructureTabPresenter>, Place {
    }

    @Inject
    public CollectionStructureTabPresenter(EventBus eventBus, CollectionStructureTabView view, CollectionStructureTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
    }
    
    @TitleFunction
    public String title() {
        return getConstants().breadcrumbStructure();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, CollectionPresenter.TYPE_SetContextAreaStructure, this);
    }
    
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String collectionCode = PlaceRequestUtils.getCollectionParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(collectionCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            String collectionUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, collectionCode);
            retrieveCollection(collectionUrn);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }
    
    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(CollectionStructureTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    CollectionStructureTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(CollectionStructureTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    private void retrieveCollection(String urn) {
        dispatcher.execute(new GetCollectionAction(urn), new WaitingAsyncCallback<GetCollectionResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionStructureTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorRetrieve()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetCollectionResult result) {
                getView().setCollection(result.getCollectionDto());
            }
        });
    }
    
}
