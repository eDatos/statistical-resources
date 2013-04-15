package org.siemac.metamac.statistical.resources.web.client.query.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryResult;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryAction;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryResult;
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


public class QueryPresenter extends Presenter<QueryPresenter.QueryView, QueryPresenter.QueryProxy> implements QueryUiHandlers {
    
    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();
    
    @ProxyCodeSplit
    @NameToken(NameTokens.queryPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface QueryProxy extends Proxy<QueryPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbQueries();
    }
    
    public interface QueryView extends View, HasUiHandlers<QueryUiHandlers> {
        void setQueryDto(QueryDto queryDto);
        void newQueryDto();
    }
    

    @Inject
    public QueryPresenter(EventBus eventBus, QueryView queryView, QueryProxy queryProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, queryView, queryProxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String queryCode = PlaceRequestUtils.getQueryParamFromUrl(placeManager);
        if (StringUtils.isBlank(queryCode)) {
            getView().newQueryDto();
        } else {
            retrieveQuery(queryCode);
        }
    }
    
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    public void retrieveQuery(String queryCode) {
        String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_QUERY_PREFIX, queryCode);
        dispatcher.execute(new GetQueryAction(urn), new WaitingAsyncCallback<GetQueryResult>() {
            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(QueryPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().queryErrorRetrieve()), MessageTypeEnum.ERROR);
                
            }
            
            @Override
            public void onWaitSuccess(GetQueryResult result) {
                getView().setQueryDto(result.getQueryDto());
            }
        });
    }
    
    
    @Override
    public void saveQuery(QueryDto queryDto) {
        dispatcher.execute(new SaveQueryAction(queryDto), new WaitingAsyncCallback<SaveQueryResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(QueryPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().queryErrorSave()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(SaveQueryResult result) {
                ShowMessageEvent.fire(QueryPresenter.this, ErrorUtils.getMessageList(getMessages().querySaved()), MessageTypeEnum.SUCCESS);
                getView().setQueryDto(result.getSavedQuery());
                updateUrlIfNeeded(result.getSavedQuery());
            }
            
            private void updateUrlIfNeeded(QueryDto query) {
                String queryParam = placeManager.getCurrentPlaceRequest().getParameter(PlaceRequestParams.queryParam, null);
                if (queryParam == null) {
                    placeManager.revealRelativePlace(new PlaceRequest(NameTokens.queryPage).with(PlaceRequestParams.queryParam, query.getCode()), -1);
                }
            }
        });
    }
}
