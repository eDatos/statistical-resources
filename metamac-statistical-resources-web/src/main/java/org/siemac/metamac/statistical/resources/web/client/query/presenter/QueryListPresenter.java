package org.siemac.metamac.statistical.resources.web.client.query.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.LifeCycleBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.QueryVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueryVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusAction.Builder;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusResult;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class QueryListPresenter extends LifeCycleBaseListPresenter<QueryListPresenter.QueryListView, QueryListPresenter.QueryListProxy> implements QueryListUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

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

        // Search
        void clearSearchSection();
        QueryVersionWebCriteria getQueryVersionWebCriteria();
        void setStatisticalOperationsForDatasetVersionSelectionInSearchSection(List<ExternalItemDto> results);
        void setDatasetVersionsForSearchSection(GetDatasetVersionsResult result);
        void setStatisticalOperationsForSearchSection(GetStatisticalOperationsPaginatedListResult result);
    }

    @Inject
    public QueryListPresenter(EventBus eventBus, QueryListView queryListView, QueryListProxy queryListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, queryListView, queryListProxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
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
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode)) {
            String operationUrn = CommonUtils.generateStatisticalOperationUrn(operationCode);

            if (!CommonUtils.isUrnFromSelectedStatisticalOperation(operationUrn)) {
                retrieveOperation(operationUrn);
            } else {
                loadInitialData();
            }
        }
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
        getView().clearSearchSection();

        QueryVersionWebCriteria queryVersionWebCriteria = new QueryVersionWebCriteria();
        queryVersionWebCriteria.setStatisticalOperationUrn(StatisticalResourcesDefaults.getSelectedStatisticalOperation().getUrn());

        retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, queryVersionWebCriteria);
    }

    @Override
    public void retrieveQueries(int firstResult, int maxResults, QueryVersionWebCriteria criteria) {
        dispatcher.execute(new GetQueryVersionsAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetQueryVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetQueryVersionsResult result) {
                getView().setQueryPaginatedList(result);
            }
        });
    }

    @Override
    public void deleteQueries(List<String> urnsFromSelected) {
        dispatcher.execute(new DeleteQueryVersionsAction(urnsFromSelected), new WaitingAsyncCallbackHandlingError<DeleteQueryVersionsResult>(this) {

            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getQueryVersionWebCriteria());
            }
            @Override
            public void onWaitSuccess(DeleteQueryVersionsResult result) {
                fireSuccessMessage(getMessages().queriesDeleted());
                retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getQueryVersionWebCriteria());
            }
        });
    }

    //
    // LIFECYCLE
    //

    @Override
    public void sendToProductionValidation(List<QueryVersionBaseDto> queryVersionDtos) {
        UpdateQueryVersionsProcStatusAction action = new UpdateQueryVersionsProcStatusAction(queryVersionDtos, LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION);
        updateQueryProcStatus(action, getMessages().lifeCycleResourcesSentToProductionValidation());
    }

    @Override
    public void sendToDiffusionValidation(List<QueryVersionBaseDto> queryVersionDtos) {
        UpdateQueryVersionsProcStatusAction action = new UpdateQueryVersionsProcStatusAction(queryVersionDtos, LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION);
        updateQueryProcStatus(action, getMessages().lifeCycleResourcesSentToDiffusionValidation());
    }

    @Override
    public void rejectValidation(List<QueryVersionBaseDto> queryVersionDtos, String reasonOfRejection) {
        UpdateQueryVersionsProcStatusAction.Builder actionBuilder = new Builder(queryVersionDtos, LifeCycleActionEnum.REJECT_VALIDATION).reasonOfRejection(reasonOfRejection);
        updateQueryProcStatus(actionBuilder.build(), getMessages().lifeCycleResourcesRejectValidation());
    }

    @Override
    public void publish(List<QueryVersionBaseDto> queryVersionDtos) {
        UpdateQueryVersionsProcStatusAction action = new UpdateQueryVersionsProcStatusAction(queryVersionDtos, LifeCycleActionEnum.PUBLISH);
        updateQueryProcStatus(action, getMessages().lifeCycleResourcesPublish());
    }

    @Override
    public void programPublication(List<QueryVersionBaseDto> queryVersionDtos, Date validFrom) {
        UpdateQueryVersionsProcStatusAction.Builder builder = new Builder(queryVersionDtos, LifeCycleActionEnum.PROGRAM_PUBLICATION);
        builder.validFrom(validFrom);
        updateQueryProcStatus(builder.build(), getMessages().lifeCycleResourcesProgramPublication());
    }

    @Override
    public void cancelProgrammedPublication(List<QueryVersionBaseDto> queryVersionDtos) {
        UpdateQueryVersionsProcStatusAction action = new UpdateQueryVersionsProcStatusAction(queryVersionDtos, LifeCycleActionEnum.CANCEL_PROGRAMMED_PUBLICATION);

        updateQueryProcStatus(action, getMessages().lifeCycleResourcesCancelProgrammedPublication());
    }

    @Override
    public void version(List<QueryVersionBaseDto> queryVersionDtos, VersionTypeEnum versionType) {
        Builder builder = new Builder(queryVersionDtos, LifeCycleActionEnum.VERSION);
        builder.versionType(versionType);
        updateQueryProcStatus(builder.build(), getMessages().lifeCycleResourcesVersion());
    }

    private void updateQueryProcStatus(UpdateQueryVersionsProcStatusAction action, final String successMessage) {
        dispatcher.execute(action, new WaitingAsyncCallbackHandlingError<UpdateQueryVersionsProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdateQueryVersionsProcStatusResult result) {
                fireSuccessMessage(successMessage);
                retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getQueryVersionWebCriteria());
            }
            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getQueryVersionWebCriteria());
            }
        });
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void retrieveStatisticalOperationsForDatasetVersionSelectionInSearchSection() {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(0, Integer.MAX_VALUE, null), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setStatisticalOperationsForDatasetVersionSelectionInSearchSection(result.getOperationsList());
            }
        });
    }

    @Override
    public void retrieveDatasetVersionsForSearchSection(int firstResult, int maxResults, DatasetVersionWebCriteria criteria) {
        dispatcher.execute(new GetDatasetVersionsAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetDatasetVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionsResult result) {
                getView().setDatasetVersionsForSearchSection(result);
            }
        });
    }

    @Override
    public void retrieveStatisticalOperationsForSearchSection(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(firstResult, maxResults, criteria),
                new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

                    @Override
                    public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                        getView().setStatisticalOperationsForSearchSection(result);
                    }
                });
    }

    //
    // NAVIGATION
    //

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

    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }
}
