package org.siemac.metamac.statistical.resources.web.client.collection.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.CollectionUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionResult;
import org.siemac.metamac.statistical.resources.web.shared.collection.SaveCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.SaveCollectionResult;
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

public class CollectionPresenter extends Presenter<CollectionPresenter.CollectionView, CollectionPresenter.CollectionProxy> implements CollectionUiHandlers {

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    private ExternalItemDto                           operation;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.collectionPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface CollectionProxy extends Proxy<CollectionPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return StatisticalResourcesWeb.getConstants().breadcrumbCollection();
    }

    public interface CollectionView extends View, HasUiHandlers<CollectionUiHandlers> {

        void setCollection(CollectionDto collectionDto);
    }

    @Inject
    public CollectionPresenter(EventBus eventBus, CollectionView collectionView, CollectionProxy collectionProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, collectionView, collectionProxy);
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

    @Override
    public void saveCollection(CollectionDto collectionDto) {
        dispatcher.execute(new SaveCollectionAction(collectionDto), new WaitingAsyncCallback<SaveCollectionResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorSave()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(SaveCollectionResult result) {
                getView().setCollection(result.getSavedCollection());
            }
        });
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(CollectionPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    CollectionPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(CollectionPresenter.this, result.getOperation());
                }
            });
        }
    }

    private void retrieveCollection(String urn) {
        dispatcher.execute(new GetCollectionAction(urn), new WaitingAsyncCallback<GetCollectionResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorRetrieve()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetCollectionResult result) {
                getView().setCollection(result.getCollectionDto());
            }
        });
    }

}
