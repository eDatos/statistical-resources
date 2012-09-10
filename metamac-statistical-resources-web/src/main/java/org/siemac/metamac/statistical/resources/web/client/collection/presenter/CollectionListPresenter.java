package org.siemac.metamac.statistical.resources.web.client.collection.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.CollectionListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent.SetOperationHandler;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
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
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class CollectionListPresenter extends Presenter<CollectionListPresenter.CollectionListView, CollectionListPresenter.CollectionListProxy>
        implements
            CollectionListUiHandlers,
            SetOperationHandler {

    public final static int                           COLLECTION_LIST_FIRST_RESULT                        = 0;
    public final static int                           COLLECTION_LIST_MAX_RESULTS                         = 30;

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    private ExternalItemDto                           operation;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.collectionsListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface CollectionListProxy extends Proxy<CollectionListPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return StatisticalResourcesWeb.getConstants().breadcrumbCollections();
    }

    public interface CollectionListView extends View, HasUiHandlers<CollectionListUiHandlers> {

        void setCollectionPaginatedList(List<CollectionDto> collectionDtos, int firstResult, int totalResults);
        // void clearSearchSection();
    }

    @Inject
    public CollectionListPresenter(EventBus eventBus, CollectionListView collectionListView, CollectionListProxy collectionListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, collectionListView, collectionListProxy);
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
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            retrieveCollections(operationUrn, COLLECTION_LIST_FIRST_RESULT, COLLECTION_LIST_MAX_RESULTS, null);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().collections());
    }

    @ProxyEvent
    @Override
    public void onSetOperation(SetOperationEvent event) {
        this.operation = event.getOperation();
    }

    @Override
    public void retrieveCollections(int firstResult, int maxResults, String collection) {
        retrieveCollections(operation != null ? operation.getUrn() : null, firstResult, maxResults, collection);
    }

    private void retrieveCollections(String operationUrn, int firstResult, int maxResults, String collection) {
        dispatcher.execute(new GetCollectionPaginatedListAction(operationUrn, firstResult, maxResults, collection), new WaitingAsyncCallback<GetCollectionPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorRetrieveList()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetCollectionPaginatedListResult result) {
                getView().setCollectionPaginatedList(result.getCollectionList(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    @Override
    public void createCollection(CollectionDto collectionDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteCollection(List<String> urns) {
        // TODO Auto-generated method stub

    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(CollectionListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    CollectionListPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(CollectionListPresenter.this, result.getOperation());
                }
            });
        }
    }

    @Override
    public void goToCollection(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.collectionPage).with(PlaceRequestParams.collectionParam, UrnUtils.removePrefix(urn)));
        }
    }

}
