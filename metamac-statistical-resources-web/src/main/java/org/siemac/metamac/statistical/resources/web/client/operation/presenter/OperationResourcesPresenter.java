package org.siemac.metamac.statistical.resources.web.client.operation.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter.OperationResourcesProxy;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter.OperationResourcesView;
import org.siemac.metamac.statistical.resources.web.client.operation.view.handlers.OperationResourcesUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.PublicationVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.QueryVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsResult;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallback;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
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

public class OperationResourcesPresenter extends Presenter<OperationResourcesView, OperationResourcesProxy> implements OperationResourcesUiHandlers {

    private final PlaceManager  placeManager;
    private final DispatchAsync dispatcher;

    public interface OperationResourcesView extends View, HasUiHandlers<OperationResourcesUiHandlers> {

        void setDatasets(List<DatasetVersionBaseDto> datasetDtos);
        void setPublications(List<PublicationVersionBaseDto> publicationDtos);
        void setQueries(List<QueryVersionBaseDto> queryVersionBaseDtos);
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbOperationResources();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.operationResourcesPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface OperationResourcesProxy extends Proxy<OperationResourcesPresenter>, Place {
    }

    @Inject
    public OperationResourcesPresenter(EventBus eventBus, OperationResourcesView view, OperationResourcesProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
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
        SetTitleEvent.fire(this, getConstants().breadcrumbOperationResources());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationParam = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationParam)) {
            String operationUrn = CommonUtils.generateStatisticalOperationUrn(operationParam);

            if (!CommonUtils.isUrnFromSelectedStatisticalOperation(operationUrn)) {
                retrieveOperation(operationUrn);
            } else {
                loadInitialData();
            }

        } else {
            StatisticalResourcesWeb.showErrorPage();
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
        retrieveResourcesByStatisticalOperation(StatisticalResourcesDefaults.getSelectedStatisticalOperation().getUrn());
    }

    private void retrieveResourcesByStatisticalOperation(String urn) {

        // DATASETS

        DatasetVersionWebCriteria datasetWebCriteria = new DatasetVersionWebCriteria();
        datasetWebCriteria.setStatisticalOperationUrn(urn);

        dispatcher.execute(new GetDatasetVersionsAction(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, datasetWebCriteria), new WaitingAsyncCallback<GetDatasetVersionsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(OperationResourcesPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetDatasetVersionsResult result) {
                getView().setDatasets(result.getDatasetVersionBaseDtos());
            }
        });

        // PUBLICATIONS

        PublicationVersionWebCriteria publicationWebCriteria = new PublicationVersionWebCriteria();
        publicationWebCriteria.setStatisticalOperationUrn(urn);

        dispatcher.execute(new GetPublicationVersionsAction(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, publicationWebCriteria),
                new WaitingAsyncCallback<GetPublicationVersionsResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fireErrorMessage(OperationResourcesPresenter.this, caught);
                    }
                    @Override
                    public void onWaitSuccess(GetPublicationVersionsResult result) {
                        getView().setPublications(result.getPublicationBaseDtos());
                    }
                });

        // QUERIES

        QueryVersionWebCriteria queryVersionWebCriteria = new QueryVersionWebCriteria();
        queryVersionWebCriteria.setStatisticalOperationUrn(urn);
        dispatcher.execute(new GetQueryVersionsAction(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, queryVersionWebCriteria), new WaitingAsyncCallback<GetQueryVersionsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(OperationResourcesPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetQueryVersionsResult result) {
                getView().setQueries(result.getQueryVersionBaseDtos());
            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goToDataset(DatasetVersionBaseDto datasetVersionBaseDto) {
        if (datasetVersionBaseDto != null) {
            placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteDatasetPlaceRequest(datasetVersionBaseDto.getStatisticalOperation().getUrn(), datasetVersionBaseDto.getUrn()));
        }
    }

    @Override
    public void goToPublication(PublicationVersionBaseDto publicationVersionBaseDto) {
        if (publicationVersionBaseDto != null) {
            placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsolutePublicationPlaceRequest(publicationVersionBaseDto.getStatisticalOperation().getUrn(), publicationVersionBaseDto.getUrn()));
        }
    }

    @Override
    public void goToQuery(QueryVersionBaseDto queryVersionBaseDto) {
        if (queryVersionBaseDto != null) {
            placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteQueryPlaceRequest(queryVersionBaseDto.getStatisticalOperation().getUrn(), queryVersionBaseDto.getUrn()));
        }
    }
}
