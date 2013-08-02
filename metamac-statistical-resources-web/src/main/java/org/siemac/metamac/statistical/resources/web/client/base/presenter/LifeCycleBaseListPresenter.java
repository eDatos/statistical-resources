package org.siemac.metamac.statistical.resources.web.client.base.presenter;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

public abstract class LifeCycleBaseListPresenter<V extends LifeCycleBaseListPresenter.LifeCycleBaseListView, P extends Proxy<?>> extends Presenter<V, P> implements UiHandlers {

    public LifeCycleBaseListPresenter(EventBus eventBus, V listView, P proxy) {
        super(eventBus, listView, proxy);
    }

    public interface LifeCycleBaseListView extends View {

    }
}
