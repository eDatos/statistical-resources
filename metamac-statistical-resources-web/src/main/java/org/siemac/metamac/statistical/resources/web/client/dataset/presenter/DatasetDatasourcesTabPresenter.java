package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetTabTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.events.SelectDatasetTabEvent;
import org.siemac.metamac.statistical.resources.web.client.events.SetDatasetEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasourcesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasourcesResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetCodelistsWithVariableAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetCodelistsWithVariableResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsVariableMappingAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsVariableMappingResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;

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

    private DatasetVersionDto datasetVersion;

    public interface DatasetDatasourcesTabView extends View, HasUiHandlers<DatasetDatasourcesTabUiHandlers> {

        void setDatasetVersion(DatasetVersionDto datasetVersionDto);
        void setDatasources(String datasetUrn, List<DatasourceDto> datasources);
        void setCodelistsInVariable(String dimensionId, GetCodelistsWithVariableResult result);
        void setDimensionVariablesForDataset(String urn, Map<String, String> mapping);
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

    private void setDataset(DatasetVersionDto datasetVersionDto) {
        this.datasetVersion = datasetVersionDto;
        getView().setDatasetVersion(datasetVersionDto);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaDataset, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectDatasetTabEvent.fire(this, DatasetTabTypeEnum.DATASOURCES);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
            String operationUrn = CommonUtils.generateStatisticalOperationUrn(operationCode);

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
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        String datasetUrn = CommonUtils.generateDatasetUrn(datasetCode);
        retrieveDataset(datasetUrn);
        retrieveDatasourcesByDataset(datasetUrn, 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
    }

    public void retrieveDataset(String datasetUrn) {
        dispatcher.execute(new GetDatasetVersionAction(datasetUrn), new WaitingAsyncCallbackHandlingError<GetDatasetVersionResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionResult result) {
                setDataset(result.getDatasetVersionDto());
                SetDatasetEvent.fire(DatasetDatasourcesTabPresenter.this, result.getDatasetVersionDto());
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
    public void deleteDatasources(List<String> datasourcesUrns) {
        dispatcher.execute(new DeleteDatasourcesAction(datasourcesUrns), new WaitingAsyncCallbackHandlingError<DeleteDatasourcesResult>(this) {

            @Override
            public void onWaitSuccess(DeleteDatasourcesResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetDatasourcesTabPresenter.this, getMessages().datasourcesDeleted());
                retrieveDatasourcesByDataset(datasetVersion.getUrn(), 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
            }
        });
    }

    @Override
    public void retrieveAlternativeCodelistsForVariable(final String dimensionId, String variableUrn, int firstResult, int maxResults, MetamacVersionableWebCriteria criteria) {
        dispatcher.execute(new GetCodelistsWithVariableAction(variableUrn, firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetCodelistsWithVariableResult>(this) {

            @Override
            public void onWaitSuccess(GetCodelistsWithVariableResult result) {
                getView().setCodelistsInVariable(dimensionId, result);
            }
        });
    }

    @Override
    public void retrieveDimensionVariablesForDataset(final String datasetVersionUrn) {
        dispatcher.execute(new GetDatasetDimensionsVariableMappingAction(datasetVersionUrn), new WaitingAsyncCallbackHandlingError<GetDatasetDimensionsVariableMappingResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetDimensionsVariableMappingResult result) {
                getView().setDimensionVariablesForDataset(datasetVersionUrn, result.getDimensionsMapping());
            }
        });
    }
    //
    // IMPORTATION
    //

    @Override
    public void datasourcesImportationSucceed(String fileName) {
        ShowMessageEvent.fireSuccessMessage(DatasetDatasourcesTabPresenter.this, getMessages().datasourcesImportationPlanned());
        placeManager.revealCurrentPlace();
    }

    @Override
    public void datasourcesImportationFailed(String errorMessage) {
        ShowMessageEvent.fireErrorMessage(DatasetDatasourcesTabPresenter.this, errorMessage);
    }
}
