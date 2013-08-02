package org.siemac.metamac.statistical.resources.web.client.query.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionCoverageAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionCoverageResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryVersionResult;
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

    private ExternalItemDto                           operation;

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

        void setQueryDto(QueryVersionDto queryDto);
        void newQueryDto();
        void setDatasetsForQuery(GetDatasetVersionsResult result);
        void setStatisticalOperationsForDatasetSelection(GetStatisticalOperationsPaginatedListResult result);
        void setDatasetDimensionsIds(List<String> datasetDimensionsIds);
        void setDatasetDimensionCodes(String dimensionId, List<CodeItemDto> codesDimension);

        void setAgencySchemesForMaintainer(GetAgencySchemesPaginatedListResult result);
        void setAgenciesForMaintainer(GetAgenciesPaginatedListResult result);
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

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String queryCode = PlaceRequestUtils.getQueryParamFromUrl(placeManager);
        if (StringUtils.isBlank(queryCode)) {
            getView().newQueryDto();
        } else {
            retrieveQuery(queryCode);
        }

        if (!StringUtils.isBlank(operationCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
        }
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    public void retrieveQuery(String queryCode) {
        String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_QUERY_PREFIX, queryCode);
        dispatcher.execute(new GetQueryVersionAction(urn), new WaitingAsyncCallbackHandlingError<GetQueryVersionResult>(this) {

            @Override
            public void onWaitSuccess(GetQueryVersionResult result) {
                getView().setQueryDto(result.getQueryVersionDto());
            }
        });
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    QueryPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(QueryPresenter.this, result.getOperation());
                }
            });
        }
    }

    @Override
    public void retrieveAgencySchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
        dispatcher.execute(new GetAgencySchemesPaginatedListAction(firstResult, maxResults, webCriteria), new WaitingAsyncCallbackHandlingError<GetAgencySchemesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetAgencySchemesPaginatedListResult result) {
                getView().setAgencySchemesForMaintainer(result);
            }
        });
    }

    @Override
    public void retrieveAgencies(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
        dispatcher.execute(new GetAgenciesPaginatedListAction(firstResult, maxResults, webCriteria), new WaitingAsyncCallbackHandlingError<GetAgenciesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetAgenciesPaginatedListResult result) {
                getView().setAgenciesForMaintainer(result);
            }
        });
    }

    @Override
    public void saveQuery(QueryVersionDto queryDto) {
        dispatcher.execute(new SaveQueryVersionAction(queryDto, operation.getCode()), new WaitingAsyncCallbackHandlingError<SaveQueryVersionResult>(this) {

            @Override
            public void onWaitSuccess(SaveQueryVersionResult result) {
                ShowMessageEvent.fireSuccessMessage(QueryPresenter.this, getMessages().querySaved());
                getView().setQueryDto(result.getSavedQueryVersionDto());
                updateUrlIfNeeded(result.getSavedQueryVersionDto());
            }

            private void updateUrlIfNeeded(QueryVersionDto query) {
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
        dispatcher.execute(new GetDatasetVersionsAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetDatasetVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionsResult result) {
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
                getView().setDatasetDimensionsIds(result.getDatasetVersionDimensionsIds());
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

    @Override
    public void goToQueries() {
        placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteQueriesPlaceRequest(operation.getUrn()));
    }
}
