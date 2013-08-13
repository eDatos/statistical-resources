package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.SiemacMetadataResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;

public class DatasetVersionSearchSectionStack extends SiemacMetadataResourceSearchSectionStack {

    private DatasetListUiHandlers uiHandlers;

    public DatasetVersionSearchSectionStack() {
    }

    public DatasetVersionWebCriteria getDatasetVersionWebCriteria() {
        return (DatasetVersionWebCriteria) getSiemacMetadataStatisticalResourceWebCriteria(new DatasetVersionWebCriteria());
    }

    @Override
    public void retrieveResources() {
        getUiHandlers().retrieveDatasetsByStatisticalOperation(null, 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getDatasetVersionWebCriteria());
    }

    public void setUiHandlers(DatasetListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public DatasetListUiHandlers getUiHandlers() {
        return uiHandlers;
    }
}
