package org.siemac.metamac.statistical.resources.web.client.gin;

import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesPlaceManager;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWebConstants;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWebMessages;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationStructureTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.view.PublicationListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.collection.view.PublicationMetadataTabViewImpl;
import org.siemac.metamac.statistical.resources.web.client.collection.view.PublicationStructureTabViewImpl;
import org.siemac.metamac.statistical.resources.web.client.collection.view.PublicationViewImpl;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.DatasetDatasourcesTabViewImpl;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.DatasetListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.DatasetMetadataTabViewImpl;
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
        
        bindPresenter(DatasetListPresenter.class, DatasetListPresenter.DatasetListView.class, DatasetListViewImpl.class, DatasetListPresenter.DatasetListProxy.class);
        bindPresenter(DatasetPresenter.class, DatasetPresenter.DatasetView.class, DatasetViewImpl.class, DatasetPresenter.DatasetProxy.class);
        bindPresenter(DatasetMetadataTabPresenter.class, DatasetMetadataTabPresenter.DatasetMetadataTabView.class, DatasetMetadataTabViewImpl.class, DatasetMetadataTabPresenter.DatasetMetadataTabProxy.class);
        bindPresenter(DatasetDatasourcesTabPresenter.class, DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView.class, DatasetDatasourcesTabViewImpl.class, DatasetDatasourcesTabPresenter.DatasetDatasourcesTabProxy.class);
        
        bindPresenter(PublicationListPresenter.class, PublicationListPresenter.PublicationListView.class, PublicationListViewImpl.class, PublicationListPresenter.PublicationListProxy.class);
        bindPresenter(PublicationPresenter.class, PublicationPresenter.PublicationView.class, PublicationViewImpl.class, PublicationPresenter.PublicationProxy.class);
        bindPresenter(PublicationMetadataTabPresenter.class, PublicationMetadataTabPresenter.PublicationMetadataTabView.class, PublicationMetadataTabViewImpl.class, PublicationMetadataTabPresenter.PublicationMetadataTabProxy.class);
        bindPresenter(PublicationStructureTabPresenter.class, PublicationStructureTabPresenter.PublicationStructureTabView.class, PublicationStructureTabViewImpl.class, PublicationStructureTabPresenter.PublicationStructureTabProxy.class);

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
