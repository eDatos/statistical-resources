package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasourceListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasourceListResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;

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

public class DatasetDatasourcesTabPresenter extends Presenter<DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView, DatasetDatasourcesTabPresenter.DatasetDatasourcesTabProxy>
        implements
            DatasetDatasourcesTabUiHandlers {

    private DispatchAsync     dispatcher;
    private PlaceManager      placeManager;

    private ExternalItemDto   operation;
    private DatasetVersionDto dataset;

    public interface DatasetDatasourcesTabView extends View, HasUiHandlers<DatasetDatasourcesTabUiHandlers> {

        void setDatasources(String datasetUrn, List<DatasourceDto> datasources);
        void setDatasource(DatasourceDto datasourceDto);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetDatasourcesPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetDatasourcesTabProxy extends Proxy<DatasetDatasourcesTabPresenter>, Place {
    }

    @Inject
    public DatasetDatasourcesTabPresenter(EventBus eventBus, DatasetDatasourcesTabView view, DatasetDatasourcesTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbDatasources();
    }

    private void setDataset(DatasetVersionDto datasetDto) {
        this.dataset = datasetDto;
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            String datasetUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetCode);
            retrieveOperation(operationUrn);
            retrieveDataset(datasetUrn);
            retrieveDatasourcesByDataset(datasetUrn, 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    DatasetDatasourcesTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(DatasetDatasourcesTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    public void retrieveDataset(String datasetUrn) {
        dispatcher.execute(new GetDatasetAction(datasetUrn), new WaitingAsyncCallbackHandlingError<GetDatasetResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetResult result) {
                setDataset(result.getDatasetVersionDto());
            }
        });
    }

    @Override
    public void retrieveDatasourcesByDataset(final String datasetUrn, int firstResult, int maxResults) {
        dispatcher.execute(new GetDatasourcesByDatasetAction(datasetUrn), new WaitingAsyncCallbackHandlingError<GetDatasourcesByDatasetResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasourcesByDatasetResult result) {
                getView().setDatasources(datasetUrn, result.getDatasourcesList());
            }
        });
    }

    @Override
    public void saveDatasource(DatasourceDto datasourceDto) {
        dispatcher.execute(new SaveDatasourceAction(dataset.getUrn(), datasourceDto), new WaitingAsyncCallbackHandlingError<SaveDatasourceResult>(this) {

            @Override
            public void onWaitSuccess(SaveDatasourceResult result) {
                getView().setDatasource(result.getDatasourceSaved());
                retrieveDatasourcesByDataset(dataset.getUrn(), 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
            }
        });
    }

    @Override
    public void deleteDatasources(List<String> datasourcesUrns) {
        dispatcher.execute(new DeleteDatasourceListAction(datasourcesUrns), new WaitingAsyncCallbackHandlingError<DeleteDatasourceListResult>(this) {

            @Override
            public void onWaitSuccess(DeleteDatasourceListResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetDatasourcesTabPresenter.this, getMessages().datasourcesDeleted());
                retrieveDatasourcesByDataset(dataset.getUrn(), 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
            }
        });

    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaDatasources, this);
    }
}
