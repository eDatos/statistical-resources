package org.siemac.metamac.statistical.resources.web.client.gin;


import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.ResourcesPlaceManager;
import org.siemac.metamac.statistical.resources.web.client.ResourcesWebConstants;
import org.siemac.metamac.statistical.resources.web.client.ResourcesWebMessages;
import org.siemac.metamac.statistical.resources.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.UnauthorizedPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.view.ErrorPageViewImpl;
import org.siemac.metamac.statistical.resources.web.client.view.MainPageViewImpl;
import org.siemac.metamac.statistical.resources.web.client.view.UnauthorizedPageViewImpl;
import org.siemac.metamac.statistical.resources.web.client.widgets.presenter.ResourcesToolStripPresenterWidget;
import org.siemac.metamac.statistical.resources.web.client.widgets.view.ResourcesToolStripViewImpl;

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
        install(new DefaultModule(ResourcesPlaceManager.class));

        // Constants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.datasetsListPage); //TODO: default place

        // Gate keeper
        bind(LoggedInGatekeeper.class).in(Singleton.class);

        // Presenters
        bindPresenter(MainPagePresenter.class, MainPagePresenter.MainPageView.class, MainPageViewImpl.class, MainPagePresenter.MainPageProxy.class);

        // Error pages
        bindPresenter(ErrorPagePresenter.class, ErrorPagePresenter.ErrorPageView.class, ErrorPageViewImpl.class, ErrorPagePresenter.ErrorPageProxy.class);
        bindPresenter(UnauthorizedPagePresenter.class, UnauthorizedPagePresenter.UnauthorizedPageView.class, UnauthorizedPageViewImpl.class, UnauthorizedPagePresenter.UnauthorizedPageProxy.class);

        // Presenter widgets
        bindSingletonPresenterWidget(ResourcesToolStripPresenterWidget.class, ResourcesToolStripPresenterWidget.ResourcesToolStripView.class, ResourcesToolStripViewImpl.class);

        // Interfaces
        bind(ResourcesWebConstants.class).in(Singleton.class);
        bind(ResourcesWebMessages.class).in(Singleton.class);
    }

}
