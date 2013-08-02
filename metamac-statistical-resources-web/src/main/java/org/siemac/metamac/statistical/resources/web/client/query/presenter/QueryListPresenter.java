package org.siemac.metamac.statistical.resources.web.client.query.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.LifeCycleBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueryVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsResult;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
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

public class QueryListPresenter extends LifeCycleBaseListPresenter<QueryListPresenter.QueryListView, QueryListPresenter.QueryListProxy> implements QueryListUiHandlers {

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

    public interface QueryListView extends LifeCycleBaseListPresenter.LifeCycleBaseListView, HasUiHandlers<QueryListUiHandlers> {

        void setQueryPaginatedList(GetQueryVersionsResult queriesPaginatedList);
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

        retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
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
    public void retrieveQueries(int firstResult, int maxResults, StatisticalResourceWebCriteria criteria) {
        dispatcher.execute(new GetQueryVersionsAction(firstResult, maxResults, criteria), new WaitingAsyncCallback<GetQueryVersionsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(QueryListPresenter.this, caught);
            }

            @Override
            public void onWaitSuccess(GetQueryVersionsResult result) {
                getView().setQueryPaginatedList(result);
            }
        });

    }

    @Override
    public void deleteQueries(List<String> urnsFromSelected) {
        dispatcher.execute(new DeleteQueryVersionsAction(urnsFromSelected), new WaitingAsyncCallback<DeleteQueryVersionsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(QueryListPresenter.this, caught);
            }

            @Override
            public void onWaitSuccess(DeleteQueryVersionsResult result) {
                ShowMessageEvent.fireSuccessMessage(QueryListPresenter.this, getMessages().queryDeleted());
                retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
            }
        });
    }

    @Override
    public void goToQuery(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(PlaceRequestUtils.buildRelativeQueryPlaceRequest(urn));
        }
    }

    @Override
    public void goToNewQuery() {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.queryPage));
    }
}
