package org.siemac.metamac.statistical.resources.web.client.gin;

import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionListPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionStructureTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.UnauthorizedPagePresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({DispatchAsyncModule.class, ClientModule.class})
public interface StatisticalResourcesWebGinjector extends Ginjector {

    PlaceManager getPlaceManager();
    EventBus getEventBus();
    DispatchAsync getDispatcher();

    LoggedInGatekeeper getLoggedInGatekeeper();

    Provider<MainPagePresenter> getMainPagePresenter();

    AsyncProvider<OperationListPresenter> getOperationListPresenter();
    AsyncProvider<OperationResourcesPresenter> getOperationResourcesPresenter();
    AsyncProvider<OperationPresenter> getOperationPresenter();
    AsyncProvider<DatasetListPresenter> getDatasetListPresenter();
    AsyncProvider<DatasetPresenter> getDatasetPresenter();
    AsyncProvider<DatasetMetadataTabPresenter> getDatasetMetadataTabPresenter();
    AsyncProvider<DatasetDatasourcesTabPresenter> getDatasetDatasourcesTabPresenter();
    AsyncProvider<CollectionListPresenter> getCollectionListPresenter();
    AsyncProvider<CollectionPresenter> getCollectionPresenter();
    AsyncProvider<CollectionMetadataTabPresenter> getCollectionMetadataTabPresenter();
    AsyncProvider<CollectionStructureTabPresenter> getCollectionStructureTabPresenter();

    AsyncProvider<ErrorPagePresenter> getErrorPagePresenter();
    AsyncProvider<UnauthorizedPagePresenter> getUnauthorizedPagePresenter();

}
