package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetListResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
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

public class DatasetListPresenter extends Presenter<DatasetListPresenter.DatasetListView, DatasetListPresenter.DatasetListProxy> implements DatasetListUiHandlers {

    public final static int                           DATASET_LIST_FIRST_RESULT                           = 0;
    public final static int                           DATASET_LIST_MAX_RESULTS                            = 30;

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    private String                                    operationUrn;

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

    public interface DatasetListView extends View, HasUiHandlers<DatasetListUiHandlers> {

        void setDatasetPaginatedList(String operationUrn, GetDatasetsByStatisticalOperationPaginatedListResult datasetsPaginatedList);
        void goToDatasetListLastPageAfterCreate();
    }

    @Inject
    public DatasetListPresenter(EventBus eventBus, DatasetListView datasetListView, DatasetListProxy datasetListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, datasetListView, datasetListProxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode)) {
            operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveDataSetsByStatisticalOperation(operationUrn, DATASET_LIST_FIRST_RESULT, DATASET_LIST_MAX_RESULTS);
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

    @Override
    public void retrieveDataSetsByStatisticalOperation(String operationUrn, int firstResult, int maxResults) {
        final String statisticalOperationUrn = operationUrn;
        dispatcher.execute(new GetDatasetsByStatisticalOperationPaginatedListAction(operationUrn, firstResult, maxResults),
                new WaitingAsyncCallback<GetDatasetsByStatisticalOperationPaginatedListResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(DatasetListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorRetrieveList()), MessageTypeEnum.ERROR);
                    }

                    @Override
                    public void onWaitSuccess(GetDatasetsByStatisticalOperationPaginatedListResult result) {
                        getView().setDatasetPaginatedList(statisticalOperationUrn, result);
                    }
                });

    }

    @Override
    public void createDataSet(DataSetDto datasetDto) {
        dispatcher.execute(new SaveDatasetAction(datasetDto), new WaitingAsyncCallback<SaveDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorSave()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(SaveDatasetResult result) {
                ShowMessageEvent.fire(DatasetListPresenter.this, ErrorUtils.getMessageList(getMessages().datasetSaved()), MessageTypeEnum.SUCCESS);
                retrieveDataSetsByStatisticalOperation(operationUrn, DATASET_LIST_FIRST_RESULT, DATASET_LIST_MAX_RESULTS);
                getView().goToDatasetListLastPageAfterCreate();
            }
        });
    }

    @Override
    public void deleteDataSets(List<Long> idsFromSelected) {
        dispatcher.execute(new DeleteDatasetListAction(idsFromSelected), new WaitingAsyncCallback<DeleteDatasetListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorDelete()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(DeleteDatasetListResult result) {
                ShowMessageEvent.fire(DatasetListPresenter.this, ErrorUtils.getMessageList(getMessages().datasetDeleted()), MessageTypeEnum.SUCCESS);
                retrieveDataSetsByStatisticalOperation(DatasetListPresenter.this.operationUrn, DATASET_LIST_FIRST_RESULT, DATASET_LIST_MAX_RESULTS);
            }
        });
    }

    @Override
    public void goToDataSet(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.datasetPage).with(PlaceRequestParams.datasetParam, UrnUtils.removePrefix(urn)));
        }
    }

}
