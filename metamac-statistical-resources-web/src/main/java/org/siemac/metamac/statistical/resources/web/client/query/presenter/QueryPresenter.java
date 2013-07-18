package org.siemac.metamac.statistical.resources.web.client.query.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionCoverageAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionCoverageResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryResult;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryAction;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

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
        void setDatasetsForQuery(GetDatasetsResult result);
        void setStatisticalOperationsForDatasetSelection(GetStatisticalOperationsPaginatedListResult result);
        void setDatasetDimensionsIds(List<String> datasetDimensionsIds);
        void setDatasetDimensionCodes(String dimensionId, List<CodeItemDto> codesDimension);
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
        dispatcher.execute(new GetQueryAction(urn), new WaitingAsyncCallbackHandlingError<GetQueryResult>(this) {

            @Override
            public void onWaitSuccess(GetQueryResult result) {
                getView().setQueryDto(result.getQueryDto());
            }
        });
    }

    @Override
    public void saveQuery(QueryDto queryDto) {
        dispatcher.execute(new SaveQueryAction(queryDto), new WaitingAsyncCallbackHandlingError<SaveQueryResult>(this) {

            @Override
            public void onWaitSuccess(SaveQueryResult result) {
                ShowMessageEvent.fireSuccessMessage(QueryPresenter.this, getMessages().querySaved());
                getView().setQueryDto(result.getSavedQuery());
                updateUrlIfNeeded(result.getSavedQuery());
            }

            private void updateUrlIfNeeded(QueryDto query) {
                String queryParam = placeManager.getCurrentPlaceRequest().getParameter(PlaceRequestParams.queryParam, null);
                if (queryParam == null) {
                    String queryCodeWithVersion = query.getCode() + "(" + query.getVersionLogic() + ")";
                    placeManager.revealRelativePlace(new PlaceRequest(NameTokens.queryPage).with(PlaceRequestParams.queryParam, queryCodeWithVersion), -1);
                }
            }
        });
    }

    @Override
    public void retrieveDatasetsForQuery(int firstResult, int maxResults, VersionableStatisticalResourceWebCriteria criteria) {
        dispatcher.execute(new GetDatasetsAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetDatasetsResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetsResult result) {
                getView().setDatasetsForQuery(result);
            }

        });
    }

    @Override
    public void retrieveStatisticalOperationsForDatasetSelection() {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(0, Integer.MAX_VALUE, null), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setStatisticalOperationsForDatasetSelection(result);
            }
        });
    }

    @Override
    public void retrieveDimensionsForDataset(String urn) {
        dispatcher.execute(new GetDatasetDimensionsAction(urn), new WaitingAsyncCallbackHandlingError<GetDatasetDimensionsResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetDimensionsResult result) {
                getView().setDatasetDimensionsIds(result.getDatasetDimensionsIds());
            }
        });
    }

    @Override
    public void retrieveDimensionCodesForDataset(String urn, final String dimensionId, MetamacWebCriteria webCriteria) {
        dispatcher.execute(new GetDatasetDimensionCoverageAction(urn, dimensionId), new WaitingAsyncCallbackHandlingError<GetDatasetDimensionCoverageResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetDimensionCoverageResult result) {
                getView().setDatasetDimensionCodes(dimensionId, result.getCodesDimension());
            };

        });
    }

    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }

}
