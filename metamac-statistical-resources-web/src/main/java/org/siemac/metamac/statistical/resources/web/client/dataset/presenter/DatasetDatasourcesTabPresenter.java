package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceResult;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
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

    public final static int DATASOURCE_LIST_FIRST_RESULT = 0;
    public final static int DATASOURCE_LIST_MAX_RESULTS  = 30;

    private DispatchAsync   dispatcher;
    private PlaceManager    placeManager;

    private ExternalItemDto operation;
    private DatasetDto      dataset;

    public interface DatasetDatasourcesTabView extends View, HasUiHandlers<DatasetDatasourcesTabUiHandlers> {

        void setDatasourcesPaginatedList(String datasetUrn, GetDatasourcesByDatasetPaginatedListResult datasourcesPaginatedList);
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

    private void setDataset(DatasetDto datasetDto) {
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
            retrieveDatasourcesByDataset(datasetUrn, DATASOURCE_LIST_FIRST_RESULT, DATASOURCE_LIST_MAX_RESULTS);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(DatasetDatasourcesTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    DatasetDatasourcesTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(DatasetDatasourcesTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    public void retrieveDataset(String datasetUrn) {
        dispatcher.execute(new GetDatasetAction(datasetUrn), new WaitingAsyncCallback<GetDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetDatasourcesTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorRetrieve()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetDatasetResult result) {
                setDataset(result.getDatasetDto());
            }
        });
    }

    @Override
    public void retrieveDatasourcesByDataset(final String datasetUrn, int firstResult, int maxResults) {
        dispatcher.execute(new GetDatasourcesByDatasetPaginatedListAction(datasetUrn, firstResult, maxResults), new WaitingAsyncCallback<GetDatasourcesByDatasetPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetDatasourcesTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasourceErrorRetrieveList()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetDatasourcesByDatasetPaginatedListResult result) {
                getView().setDatasourcesPaginatedList(datasetUrn, result);
            }
        });
    }

    @Override
    public void saveDatasource(DatasourceDto datasourceDto) {
        datasourceDto.setDatasetVersionUrn(dataset.getUrn());
        dispatcher.execute(new SaveDatasourceAction(datasourceDto), new WaitingAsyncCallback<SaveDatasourceResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetDatasourcesTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasourceErrorSave()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(SaveDatasourceResult result) {
                getView().setDatasource(result.getDatasourceSaved());
                retrieveDatasourcesByDataset(dataset.getUrn(), DATASOURCE_LIST_FIRST_RESULT, DATASOURCE_LIST_MAX_RESULTS);
            }
        });
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaDatasources, this);
    }
}
