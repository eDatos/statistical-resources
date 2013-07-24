package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;

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

public class DatasetListPresenter extends StatisticalResourceBaseListPresenter<DatasetListPresenter.DatasetListView, DatasetListPresenter.DatasetListProxy> implements DatasetListUiHandlers {

    private final PlaceManager                        placeManager;

    private ExternalItemDto                           operation;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetsListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetListProxy extends Proxy<DatasetListPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbDatasets();
    }

    public interface DatasetListView extends StatisticalResourceBaseListPresenter.StatisticalResourceBaseListView, HasUiHandlers<DatasetListUiHandlers> {

        void setDatasetPaginatedList(String operationUrn, GetDatasetVersionsResult datasetsPaginatedList);
        void goToDatasetListLastPageAfterCreate();
        void setDsdsForRelatedDsd(GetDsdsPaginatedListResult result);
        void setStatisticalOperationsForDsdSelection(List<ExternalItemDto> results, ExternalItemDto defaultSelected);
    }

    @Inject
    public DatasetListPresenter(EventBus eventBus, DatasetListView datasetListView, DatasetListProxy datasetListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, datasetListView, datasetListProxy, dispatcher);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            retrieveDatasetsByStatisticalOperation(operationUrn, 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);

            retrieveDefaultLanguage();
            retrieveDefaultAgency();
        }
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().datasets());
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    DatasetListPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(DatasetListPresenter.this, result.getOperation());
                }
            });
        }
    }

    @Override
    public void retrieveDatasetsByStatisticalOperation(String operationUrn, int firstResult, int maxResults, String criteria) {
        final String statisticalOperationUrn = operationUrn;
        VersionableStatisticalResourceWebCriteria webCriteria = new VersionableStatisticalResourceWebCriteria();
        webCriteria.setStatisticalOperationUrn(statisticalOperationUrn);
        webCriteria.setCriteria(criteria);
        dispatcher.execute(new GetDatasetVersionsAction(firstResult, maxResults, webCriteria), new WaitingAsyncCallbackHandlingError<GetDatasetVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionsResult result) {
                getView().setDatasetPaginatedList(statisticalOperationUrn, result);
            }
        });
    }

    @Override
    public void createDataset(DatasetVersionDto datasetDto) {
        dispatcher.execute(new SaveDatasetVersionAction(datasetDto, operation.getCode()), new WaitingAsyncCallbackHandlingError<SaveDatasetVersionResult>(this) {

            @Override
            public void onWaitSuccess(SaveDatasetVersionResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetListPresenter.this, getMessages().datasetSaved());
                retrieveDatasetsByStatisticalOperation(operation.getUrn(), 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
                getView().goToDatasetListLastPageAfterCreate();
            }
        });
    }

    @Override
    public void deleteDatasets(List<String> urnsFromSelected) {
        dispatcher.execute(new DeleteDatasetVersionsAction(urnsFromSelected), new WaitingAsyncCallbackHandlingError<DeleteDatasetVersionsResult>(this) {

            @Override
            public void onWaitSuccess(DeleteDatasetVersionsResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetListPresenter.this, getMessages().datasetDeleted());
                retrieveDatasetsByStatisticalOperation(DatasetListPresenter.this.operation.getUrn(), 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
            }
        });
    }

    @Override
    public void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria) {
        dispatcher.execute(new GetDsdsPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetDsdsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetDsdsPaginatedListResult result) {
                getView().setDsdsForRelatedDsd(result);
            }
        });
    }

    @Override
    public void retrieveStatisticalOperationsForDsdSelection() {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(0, Integer.MAX_VALUE, null), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setStatisticalOperationsForDsdSelection(result.getOperationsList(), operation);
            }
        });
    }

    @Override
    public void goToDataset(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.datasetPage).with(PlaceRequestParams.datasetParam, UrnUtils.removePrefix(urn)));
        }
    }

}
