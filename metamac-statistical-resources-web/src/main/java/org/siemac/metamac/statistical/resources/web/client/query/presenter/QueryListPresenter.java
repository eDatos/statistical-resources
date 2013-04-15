package org.siemac.metamac.statistical.resources.web.client.query.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueriesAction;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueriesResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueriesAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueriesResult;
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
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class QueryListPresenter extends Presenter<QueryListPresenter.QueryListView, QueryListPresenter.QueryListProxy> implements QueryListUiHandlers {

    public final static int                           QUERY_LIST_FIRST_RESULT                           = 0;
    public final static int                           QUERY_LIST_MAX_RESULTS                            = 30;

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.queriesListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface QueryListProxy extends Proxy<QueryListPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbQueries();
    }

    public interface QueryListView extends View, HasUiHandlers<QueryListUiHandlers> {

        void setQueryPaginatedList(GetQueriesResult queriesPaginatedList);
        void goToQueryListLastPageAfterCreate();
    }

    @Inject
    public QueryListPresenter(EventBus eventBus, QueryListView queryListView, QueryListProxy queryListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, queryListView, queryListProxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        retrieveQueries(QUERY_LIST_FIRST_RESULT, QUERY_LIST_MAX_RESULTS);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().queries());
    }

    @Override
    public void retrieveQueries(int firstResult, int maxResults) {
        dispatcher.execute(new GetQueriesAction(firstResult, maxResults), new WaitingAsyncCallback<GetQueriesResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(QueryListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().queryErrorRetrieveList()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(GetQueriesResult result) {
                getView().setQueryPaginatedList(result);
            }
        });

    }

    @Override
    public void deleteQueries(List<String> urnsFromSelected) {
        dispatcher.execute(new DeleteQueriesAction(urnsFromSelected), new WaitingAsyncCallback<DeleteQueriesResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(QueryListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().queryErrorDelete()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(DeleteQueriesResult result) {
                ShowMessageEvent.fire(QueryListPresenter.this, ErrorUtils.getMessageList(getMessages().queryDeleted()), MessageTypeEnum.SUCCESS);
                retrieveQueries(QUERY_LIST_FIRST_RESULT, QUERY_LIST_MAX_RESULTS);
            }
        });
    }

    @Override
    public void goToQuery(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.queryPage).with(PlaceRequestParams.queryParam, UrnUtils.removePrefix(urn)));
        }
    }
    
    @Override
    public void goToNewQuery() {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.queryPage));
    }


}
