package org.siemac.metamac.statistical.resources.web.client.operation.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.operation.view.handlers.OperationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
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

public class OperationListPresenter extends Presenter<OperationListPresenter.OperationListView, OperationListPresenter.OperationListProxy> implements OperationListUiHandlers {

    public final static int     OPERATION_LIST_FIRST_RESULT = 0;
    public final static int     OPERATION_LIST_MAX_RESULTS  = 30;

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.operationsListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface OperationListProxy extends Proxy<OperationListPresenter>, Place {
    }

    public interface OperationListView extends View, HasUiHandlers<OperationListUiHandlers> {

        void setOperationPaginatedList(GetStatisticalOperationsPaginatedListResult datasetsPaginatedList);
    }

    @Inject
    public OperationListPresenter(EventBus eventBus, OperationListView operationListView, OperationListProxy operationListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, operationListView, operationListProxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbOperations();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().statisticalDatasets());

        retrieveOperations(OPERATION_LIST_FIRST_RESULT, OPERATION_LIST_MAX_RESULTS, null);
    }

    @Override
    public void retrieveOperations(int firstResult, int maxResults, final String operation) {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(firstResult, maxResults, operation), new WaitingAsyncCallback<GetStatisticalOperationsPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(OperationListPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieveList()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setOperationPaginatedList(result);
            }
        });
    }

    @Override
    public void goToOperation(String urn) {
        if (!StringUtils.isBlank(urn)) {
            // placeManager.revealRelativePlace(new PlaceRequest(NameTokens.operationPage));
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.operationPage).with(PlaceRequestParams.operationParam, UrnUtils.removePrefix(urn)));
        }
    }

}
