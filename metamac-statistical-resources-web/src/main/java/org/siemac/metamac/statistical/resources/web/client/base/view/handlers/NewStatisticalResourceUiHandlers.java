package org.siemac.metamac.statistical.resources.web.client.base.view.handlers;

import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface NewStatisticalResourceUiHandlers extends UiHandlers {

    // AGENCY
    void retrieveAgencySchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria);
    void retrieveAgencies(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria);

}
