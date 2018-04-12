package org.siemac.metamac.statistical.resources.web.client.operation.presenter;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripLayoutEnum;
import org.siemac.metamac.statistical.resources.web.client.events.DeselectMenuButtonsEvent;
import org.siemac.metamac.statistical.resources.web.client.events.SelectMenuButtonEvent;
import org.siemac.metamac.statistical.resources.web.client.events.SelectMenuLayoutEvent;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter.OperationProxy;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter.OperationView;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

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

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContent                          = new Type<RevealContentHandler<?>>();

    @ProxyCodeSplit
    @NameToken(NameTokens.operationPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface OperationProxy extends Proxy<OperationPresenter>, Place {
    }

    public interface OperationView extends View {
    }

    @Inject
    public OperationPresenter(EventBus eventBus, OperationView view, OperationProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
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
        dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

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
    protected void onReset() {
        super.onReset();
        selectToolStripButtonsBasedOnUrl();
    }

    private void selectToolStripButtonsBasedOnUrl() {
        SelectMenuLayoutEvent.fire(this, StatisticalResourcesToolStripLayoutEnum.OPERATION_RESOURCES);

        if (PlaceRequestUtils.isNameTokenInPlaceHierarchy(placeManager, NameTokens.datasetsListPage)) {
            SelectMenuButtonEvent.fire(this, StatisticalResourcesToolStripButtonEnum.DATASETS);
        } else if (PlaceRequestUtils.isNameTokenInPlaceHierarchy(placeManager, NameTokens.publicationsListPage)) {
            SelectMenuButtonEvent.fire(this, StatisticalResourcesToolStripButtonEnum.PUBLICATIONS);
        } else if (PlaceRequestUtils.isNameTokenInPlaceHierarchy(placeManager, NameTokens.queriesListPage)) {
            SelectMenuButtonEvent.fire(this, StatisticalResourcesToolStripButtonEnum.QUERIES);
        } else if (PlaceRequestUtils.isNameTokenInPlaceHierarchy(placeManager, NameTokens.multidatasetsListPage)) {
            SelectMenuButtonEvent.fire(this, StatisticalResourcesToolStripButtonEnum.MULTIDATASETS);
        } else {
            DeselectMenuButtonsEvent.fire(this);
        }
    }

    private void goToOperationResources() {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.operationResourcesPage));
    }
}
