package org.siemac.metamac.statistical.resources.web.client.base.presenter;

import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListResult;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.Proxy;

public abstract class StatisticalResourceBaseListPresenter<V extends StatisticalResourceBaseListPresenter.StatisticalResourceBaseListView, P extends Proxy<?>> extends LifeCycleBaseListPresenter<V, P>
        implements
            NewStatisticalResourceUiHandlers {

    protected DispatchAsync dispatcher;

    public StatisticalResourceBaseListPresenter(EventBus eventBus, V listView, P proxy, DispatchAsync dispatcher) {
        super(eventBus, listView, proxy);
        this.dispatcher = dispatcher;
    }

    public interface StatisticalResourceBaseListView extends LifeCycleBaseListView {

        void setAgencySchemesForMaintainer(GetAgencySchemesPaginatedListResult result);
        void setAgenciesForMaintainer(GetAgenciesPaginatedListResult result);
    }

    @Override
    public void retrieveAgencySchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
        dispatcher.execute(new GetAgencySchemesPaginatedListAction(firstResult, maxResults, webCriteria), new WaitingAsyncCallbackHandlingError<GetAgencySchemesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetAgencySchemesPaginatedListResult result) {
                getView().setAgencySchemesForMaintainer(result);
            }
        });
    }

    @Override
    public void retrieveAgencies(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
        dispatcher.execute(new GetAgenciesPaginatedListAction(firstResult, maxResults, webCriteria), new WaitingAsyncCallbackHandlingError<GetAgenciesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetAgenciesPaginatedListResult result) {
                getView().setAgenciesForMaintainer(result);
            }
        });
    }
}
