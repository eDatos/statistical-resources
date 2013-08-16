package org.siemac.metamac.statistical.resources.web.client.operation.presenter;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter.OperationProxy;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter.OperationView;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
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
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class OperationPresenter extends Presenter<OperationView, OperationProxy> {

    private final DispatchAsync                          dispatcher;
    private final PlaceManager                           placeManager;

    private StatisticalResourcesToolStripPresenterWidget toolStripPresenterWidget;

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
    public static String getTranslatedTitle(PlaceRequest placeRequest) {
        return PlaceRequestUtils.getOperationBreadCrumbTitle(placeRequest);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode)) {
            String statisticalOperationUrn = CommonUtils.generateStatisticalOperationUrn(operationCode);

            if (CommonUtils.isUrnFromSelectedStatisticalOperation(statisticalOperationUrn)) {
                goToOperationResources();
            } else {
                retrieveOperation(statisticalOperationUrn);
            }

        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(OperationPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetStatisticalOperationResult result) {
                StatisticalResourcesDefaults.setSelectedStatisticalOperation(result.getOperation());
                goToOperationResources();
            }
        });
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
}
