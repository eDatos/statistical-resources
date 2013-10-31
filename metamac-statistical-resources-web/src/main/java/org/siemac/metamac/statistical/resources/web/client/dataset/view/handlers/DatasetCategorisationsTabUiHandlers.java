package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface DatasetCategorisationsTabUiHandlers extends BaseUiHandlers {

    void createCategorisations(String datasetVersionUrn, List<String> categoryUrns);
    void deleteCategorisations(String datasetVersionUrn, List<String> urns);
    void endCategorisationsValidity(String datasetVersionUrn, List<String> urn, Date validTo);

    void retrieveCategorySchemesForCategorisations(int firstResult, int maxResults, MetamacWebCriteria categorySchemeWebCriteria);
    void retrieveCategoriesForCategorisations(int firstResult, int maxResults, ItemSchemeWebCriteria categoryWebCriteria);
}
