package org.siemac.metamac.statistical.resources.web.client.gin;

import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
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
public interface ResourcesWebGinjector extends Ginjector {

    PlaceManager getPlaceManager();
    EventBus getEventBus();
    DispatchAsync getDispatcher();

    LoggedInGatekeeper getLoggedInGatekeeper();

    Provider<MainPagePresenter> getMainPagePresenter();


    AsyncProvider<ErrorPagePresenter> getErrorPagePresenter();
    AsyncProvider<UnauthorizedPagePresenter> getUnauthorizedPagePresenter();

}
