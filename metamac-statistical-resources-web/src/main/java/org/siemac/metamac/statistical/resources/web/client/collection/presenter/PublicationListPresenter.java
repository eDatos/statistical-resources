package org.siemac.metamac.statistical.resources.web.client.collection.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.PublicationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent.SetOperationHandler;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationListAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationListResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationResult;
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

public class PublicationListPresenter extends Presenter<PublicationListPresenter.PublicationListView, PublicationListPresenter.PublicationListProxy>
        implements
            PublicationListUiHandlers,
            SetOperationHandler {

    public final static int                           PUBLICATION_LIST_FIRST_RESULT                       = 0;
    public final static int                           PUBLICATION_LIST_MAX_RESULTS                        = 30;

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    private ExternalItemDto                           operation;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.collectionsListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface PublicationListProxy extends Proxy<PublicationListPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return StatisticalResourcesWeb.getConstants().breadcrumbCollections();
    }

    public interface PublicationListView extends View, HasUiHandlers<PublicationListUiHandlers> {

        void setPublicationPaginatedList(List<PublicationDto> PublicationDtos, int firstResult, int totalResults);
        // void clearSearchSection();
    }

    @Inject
    public PublicationListPresenter(EventBus eventBus, PublicationListView collectionListView, PublicationListProxy collectionListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
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
            retrievePublications(operationUrn, PUBLICATION_LIST_FIRST_RESULT, PUBLICATION_LIST_MAX_RESULTS, null);
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
    public void retrievePublications(int firstResult, int maxResults, String collection) {
        retrievePublications(operation != null ? operation.getUrn() : null, firstResult, maxResults, collection);
    }

    private void retrievePublications(String operationUrn, int firstResult, int maxResults, String collection) {
        dispatcher.execute(new GetPublicationPaginatedListAction(operationUrn, firstResult, maxResults, collection), new WaitingAsyncCallback<GetPublicationPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorRetrieveList()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetPublicationPaginatedListResult result) {
                getView().setPublicationPaginatedList(result.getPublicationList(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    @Override
    public void createPublication(PublicationDto publicationDto) {
        // publicationDto.setOperation(operation);
        dispatcher.execute(new SavePublicationAction(publicationDto), new WaitingAsyncCallback<SavePublicationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorCreate()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(SavePublicationResult result) {
                retrievePublications(PUBLICATION_LIST_FIRST_RESULT, PUBLICATION_LIST_MAX_RESULTS, null);
            }
        });

    }

    @Override
    public void deletePublication(List<String> urns) {
        dispatcher.execute(new DeletePublicationListAction(urns), new WaitingAsyncCallback<DeletePublicationListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorDelete()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(DeletePublicationListResult result) {
                ShowMessageEvent.fire(PublicationListPresenter.this, ErrorUtils.getMessageList(getMessages().collectionDeleted()), MessageTypeEnum.SUCCESS);
                retrievePublications(PublicationListPresenter.this.operation.getUrn(), PUBLICATION_LIST_FIRST_RESULT, PUBLICATION_LIST_MAX_RESULTS, null);
            };
        });
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(PublicationListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    PublicationListPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(PublicationListPresenter.this, result.getOperation());
                }
            });
        }
    }

    @Override
    public void goToPublication(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.collectionPage).with(PlaceRequestParams.collectionParam, UrnUtils.removePrefix(urn)));
        }
    }
}
