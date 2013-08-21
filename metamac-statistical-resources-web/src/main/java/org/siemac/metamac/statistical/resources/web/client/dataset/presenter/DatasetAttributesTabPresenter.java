package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetTabTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.events.SelectDatasetTabEvent;

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

public class DatasetAttributesTabPresenter extends Presenter<DatasetAttributesTabPresenter.DatasetAttributesTabView, DatasetAttributesTabPresenter.DatasetAttributesTabProxy>
        implements
            DatasetAttributesTabUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    public interface DatasetAttributesTabView extends View, HasUiHandlers<DatasetAttributesTabUiHandlers> {

    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetAttributesPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetAttributesTabProxy extends Proxy<DatasetAttributesTabPresenter>, Place {
    }

    @Inject
    public DatasetAttributesTabPresenter(EventBus eventBus, DatasetAttributesTabView view, DatasetAttributesTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbDatasetAttributes();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaAttributes, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectDatasetTabEvent.fire(this, DatasetTabTypeEnum.ATTRIBUTES);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

    }

    //
    // NAVIGATION
    //

    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }
}
