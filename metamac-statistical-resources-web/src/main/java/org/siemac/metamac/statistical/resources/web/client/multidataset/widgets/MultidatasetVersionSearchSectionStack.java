package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets;

import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.SiemacMetadataResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.shared.criteria.MultidatasetVersionWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class MultidatasetVersionSearchSectionStack extends SiemacMetadataResourceSearchSectionStack {

    private MultidatasetListUiHandlers uiHandlers;

    public MultidatasetVersionSearchSectionStack() {
    }

    public MultidatasetVersionWebCriteria getMultidatasetVersionWebCriteria() {
        return (MultidatasetVersionWebCriteria) getSiemacMetadataStatisticalResourceWebCriteria(new MultidatasetVersionWebCriteria());
    }

    @Override
    public void retrieveResources() {
        getUiHandlers().retrieveMultidatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getMultidatasetVersionWebCriteria());
    }

    public void setUiHandlers(MultidatasetListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public MultidatasetListUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    //
    // RELATED RESOURCES
    //

    // Statistical operations

    @Override
    public void retrieveStatisticalOperations(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        getUiHandlers().retrieveStatisticalOperationsForSearchSection(firstResult, maxResults, criteria);
    }
}
