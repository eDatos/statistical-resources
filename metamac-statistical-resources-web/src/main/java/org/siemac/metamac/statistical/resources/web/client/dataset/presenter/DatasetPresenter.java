package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class DatasetPresenter extends Presenter<DatasetPresenter.DatasetView, DatasetPresenter.DatasetProxy> {

    public interface DatasetView extends View {
    }

    public interface DatasetProxy extends Proxy<DatasetPresenter> {
    }

    @Inject
    public DatasetPresenter(EventBus eventBus, DatasetView view, DatasetProxy proxy) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        
    }
}

