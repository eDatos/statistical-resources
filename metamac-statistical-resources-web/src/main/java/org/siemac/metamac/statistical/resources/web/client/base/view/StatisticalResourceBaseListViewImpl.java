package org.siemac.metamac.statistical.resources.web.client.base.view;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NewStatisticalResourceWindow;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetLanguagesCodesResult;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public abstract class StatisticalResourceBaseListViewImpl<H extends NewStatisticalResourceUiHandlers> extends ViewWithUiHandlers<H>
        implements
            StatisticalResourceBaseListPresenter.StatisticalResourceBaseListView {

    protected ExternalItemDto defaultLanguage;
    protected ExternalItemDto defaultAgency;

    @Override
    public void setDefaultLanguage(ExternalItemDto defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    @Override
    public void setDefaultAgency(ExternalItemDto agency) {
        defaultAgency = agency;
    }

    @Override
    public void setLanguagesCodes(GetLanguagesCodesResult result) {
        getNewStatisticalResourceWindow().setExternalItemsForLanguage(result.getLanguagesCodes(), result.getFirstResultOut(), result.getLanguagesCodes().size(), result.getTotalResults());
    }

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