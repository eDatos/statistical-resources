package org.siemac.metamac.statistical.resources.web.client.operation.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent.SetOperationHandler;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter.OperationProxy;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter.OperationView;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.presenter.StatisticalResourcesToolStripPresenterWidget;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class OperationPresenter extends Presenter<OperationView, OperationProxy> implements SetOperationHandler {

    private final DispatchAsync                          dispatcher;
    private final PlaceManager                           placeManager;

    private StatisticalResourcesToolStripPresenterWidget toolStripPresenterWidget;

    private ExternalItemDto                              operation;

    @ContentSlot
    public static final Type<RevealContentHandler<?>>    TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                           TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ContentSlot
    public static final Type<RevealContentHandler<?>>    TYPE_SetContextAreaContent                          = new Type<RevealContentHandler<?>>();

    @ProxyCodeSplit
    @NameToken(NameTokens.operationPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface OperationProxy extends Proxy<OperationPresenter>, Place {
    }

    public interface OperationView extends View {

        void setOperation(ExternalItemDto operation);
    }

    @Inject
    public OperationPresenter(EventBus eventBus, OperationView view, OperationProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager,
            StatisticalResourcesToolStripPresenterWidget toolStripPresenterWidget) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        this.toolStripPresenterWidget = toolStripPresenterWidget;
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbOperation();
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationParam = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationParam)) {
            String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationParam);
            retrieveOperation(urn);
            goToOperationResources();
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fireErrorMessage(OperationPresenter.this, caught);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    SetOperationEvent.fire(OperationPresenter.this, result.getOperation());
                    setOperation(result.getOperation());
                }
            });
        }
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        setInSlot(TYPE_SetContextAreaContentOperationResourcesToolBar, toolStripPresenterWidget);
    }

    @Override
    protected void onReset() {
        super.onReset();
        selectToolStripButtonsBasedOnUrl();
    }

    @ProxyEvent
    @Override
    public void onSetOperation(SetOperationEvent event) {
        setOperation(event.getOperation());
    }

    private void selectToolStripButtonsBasedOnUrl() {
        if (PlaceRequestUtils.isNameTokenInPlaceHierarchy(placeManager, NameTokens.datasetsListPage)) {
            toolStripPresenterWidget.selectButton(StatisticalResourcesToolStripButtonEnum.DATASETS.name());
        } else if (PlaceRequestUtils.isNameTokenInPlaceHierarchy(placeManager, NameTokens.publicationsListPage)) {
            toolStripPresenterWidget.selectButton(StatisticalResourcesToolStripButtonEnum.PUBLICATIONS.name());
        } else if (PlaceRequestUtils.isNameTokenInPlaceHierarchy(placeManager, NameTokens.queriesListPage)) {
            toolStripPresenterWidget.selectButton(StatisticalResourcesToolStripButtonEnum.QUERIES.name());
        } else {
            toolStripPresenterWidget.deselectButtons();
        }
    }

    private void goToOperationResources() {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.operationResourcesPage));
    }

    private void setOperation(ExternalItemDto operation) {
        this.operation = operation;
        getView().setOperation(operation);
    }

}
