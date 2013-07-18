package org.siemac.metamac.statistical.resources.web.client.utils;

import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.HasHandlers;

public abstract class WaitingAsyncCallbackHandlingError<T> extends WaitingAsyncCallback<T> {

    private HasHandlers source;

    public WaitingAsyncCallbackHandlingError(HasHandlers source) {
        this.source = source;
    }

    @Override
    public void onWaitFailure(Throwable caught) {
        ShowMessageEvent.fireErrorMessage(source, caught);
    }

}
