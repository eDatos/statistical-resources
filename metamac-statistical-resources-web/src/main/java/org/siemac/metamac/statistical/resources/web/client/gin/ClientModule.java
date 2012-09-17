package org.siemac.metamac.statistical.resources.web.client.gin;

import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesPlaceManager;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWebConstants;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWebMessages;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionListPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionStructureTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.view.CollectionListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.collection.view.CollectionMetadataTabViewImpl;
import org.siemac.metamac.statistical.resources.web.client.collection.view.CollectionStructureTabViewImpl;
import org.siemac.metamac.statistical.resources.web.client.collection.view.CollectionViewImpl;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.DatasetListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.DatasetViewImpl;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.view.OperationListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.operation.view.OperationResourcesViewImpl;
import org.siemac.metamac.statistical.resources.web.client.operation.view.OperationViewImpl;
import org.siemac.metamac.statistical.resources.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.UnauthorizedPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.view.ErrorPageViewImpl;
import org.siemac.metamac.statistical.resources.web.client.view.MainPageViewImpl;
import org.siemac.metamac.statistical.resources.web.client.view.UnauthorizedPageViewImpl;
import org.siemac.metamac.statistical.resources.web.client.widgets.presenter.StatisticalResourcesToolStripPresenterWidget;
import org.siemac.metamac.statistical.resources.web.client.widgets.view.StatisticalResourcesToolStripViewImpl;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        // Default implementation of standard resources
        // |_ bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        // |_ bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        // |_ bind(RootPresenter.class).asEagerSingleton();
        // |_ bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
        // |_ bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
        install(new DefaultModule(StatisticalResourcesPlaceManager.class));

        // Constants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.operationsListPage); // TODO: default place

        // Gate keeper
        bind(LoggedInGatekeeper.class).in(Singleton.class);

        // Presenters
        bindPresenter(MainPagePresenter.class, MainPagePresenter.MainPageView.class, MainPageViewImpl.class, MainPagePresenter.MainPageProxy.class);

        bindPresenter(OperationPresenter.class, OperationPresenter.OperationView.class, OperationViewImpl.class, OperationPresenter.OperationProxy.class);
        bindPresenter(OperationListPresenter.class, OperationListPresenter.OperationListView.class, OperationListViewImpl.class, OperationListPresenter.OperationListProxy.class);
        bindPresenter(OperationResourcesPresenter.class, OperationResourcesPresenter.OperationResourcesView.class, OperationResourcesViewImpl.class,
                OperationResourcesPresenter.OperationResourcesProxy.class);
        bindPresenter(DatasetPresenter.class, DatasetPresenter.DatasetView.class, DatasetViewImpl.class, DatasetPresenter.DatasetProxy.class);
        bindPresenter(DatasetListPresenter.class, DatasetListPresenter.DatasetListView.class, DatasetListViewImpl.class, DatasetListPresenter.DatasetListProxy.class);
        bindPresenter(CollectionListPresenter.class, CollectionListPresenter.CollectionListView.class, CollectionListViewImpl.class, CollectionListPresenter.CollectionListProxy.class);
        bindPresenter(CollectionPresenter.class, CollectionPresenter.CollectionView.class, CollectionViewImpl.class, CollectionPresenter.CollectionProxy.class);
        bindPresenter(CollectionMetadataTabPresenter.class, CollectionMetadataTabPresenter.CollectionMetadataTabView.class, CollectionMetadataTabViewImpl.class, CollectionMetadataTabPresenter.CollectionMetadataTabProxy.class);
        bindPresenter(CollectionStructureTabPresenter.class, CollectionStructureTabPresenter.CollectionStructureTabView.class, CollectionStructureTabViewImpl.class, CollectionStructureTabPresenter.CollectionStructureTabProxy.class);

        // Error pages
        bindPresenter(ErrorPagePresenter.class, ErrorPagePresenter.ErrorPageView.class, ErrorPageViewImpl.class, ErrorPagePresenter.ErrorPageProxy.class);
        bindPresenter(UnauthorizedPagePresenter.class, UnauthorizedPagePresenter.UnauthorizedPageView.class, UnauthorizedPageViewImpl.class, UnauthorizedPagePresenter.UnauthorizedPageProxy.class);

        // Presenter widgets
        bindSingletonPresenterWidget(StatisticalResourcesToolStripPresenterWidget.class, StatisticalResourcesToolStripPresenterWidget.StatisticalResourcesToolStripView.class,
                StatisticalResourcesToolStripViewImpl.class);

        // Interfaces
        bind(StatisticalResourcesWebConstants.class).in(Singleton.class);
        bind(StatisticalResourcesWebMessages.class).in(Singleton.class);
    }

}
