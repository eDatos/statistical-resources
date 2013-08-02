package org.siemac.metamac.statistical.resources.web.client.base.view;

import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NewStatisticalResourceWindow;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListResult;

public abstract class StatisticalResourceBaseListViewImpl<H extends NewStatisticalResourceUiHandlers> extends LifeCycleBaseListViewImpl<H>
        implements
            StatisticalResourceBaseListPresenter.StatisticalResourceBaseListView {

    @Override
    public void setAgencySchemesForMaintainer(GetAgencySchemesPaginatedListResult result) {
        getNewStatisticalResourceWindow().setAgencySchemesForMaintainer(result.getAgencySchemes(), result.getFirstResultOut(), result.getAgencySchemes().size(), result.getTotalResults());
    }

    @Override
    public void setAgenciesForMaintainer(GetAgenciesPaginatedListResult result) {
        getNewStatisticalResourceWindow().setExternalItemsForMaintainer(result.getAgencies(), result.getFirstResultOut(), result.getAgencies().size(), result.getTotalResults());
    }

    protected abstract NewStatisticalResourceWindow getNewStatisticalResourceWindow();
}
