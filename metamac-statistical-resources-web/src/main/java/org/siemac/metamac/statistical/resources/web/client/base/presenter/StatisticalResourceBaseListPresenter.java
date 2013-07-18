package org.siemac.metamac.statistical.resources.web.client.base.presenter;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultLanguageInfoAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultLanguageInfoResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetLanguagesCodesAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetLanguagesCodesResult;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

public abstract class StatisticalResourceBaseListPresenter<V extends StatisticalResourceBaseListPresenter.StatisticalResourceBaseListView, P extends Proxy<?>> extends Presenter<V, P>
        implements
            NewStatisticalResourceUiHandlers {

    protected DispatchAsync dispatcher;

    public StatisticalResourceBaseListPresenter(EventBus eventBus, V listView, P proxy, DispatchAsync dispatcher) {
        super(eventBus, listView, proxy);
        this.dispatcher = dispatcher;
    }

    public interface StatisticalResourceBaseListView extends View {

        void setDefaultLanguage(ExternalItemDto defaultLanguage);
        void setLanguagesCodes(GetLanguagesCodesResult result);
        void setDefaultAgency(ExternalItemDto agency);
        void setAgencySchemesForMaintainer(GetAgencySchemesPaginatedListResult result);
        void setAgenciesForMaintainer(GetAgenciesPaginatedListResult result);
    }

    @Override
    public void retrieveDefaultLanguage() {
        dispatcher.execute(new GetDefaultLanguageInfoAction(), new WaitingAsyncCallbackHandlingError<GetDefaultLanguageInfoResult>(this) {

            @Override
            public void onWaitSuccess(GetDefaultLanguageInfoResult result) {
                getView().setDefaultLanguage(result.getDefaultLanguage());
            }
        });
    }

    @Override
    public void retrieveLanguagesCodes(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        dispatcher.execute(new GetLanguagesCodesAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetLanguagesCodesResult>(this) {

            @Override
            public void onWaitSuccess(GetLanguagesCodesResult result) {
                getView().setLanguagesCodes(result);
            }
        });

    }

    @Override
    public void retrieveDefaultAgency() {
        dispatcher.execute(new GetDefaultAgencyAction(), new WaitingAsyncCallbackHandlingError<GetDefaultAgencyResult>(this) {

            @Override
            public void onWaitSuccess(GetDefaultAgencyResult result) {
                getView().setDefaultAgency(result.getAgency());
            }
        });
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
    public void retrieveAgencies(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
        dispatcher.execute(new GetAgenciesPaginatedListAction(firstResult, maxResults, webCriteria), new WaitingAsyncCallbackHandlingError<GetAgenciesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetAgenciesPaginatedListResult result) {
                getView().setAgenciesForMaintainer(result);
            }
        });
    }
}