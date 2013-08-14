package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.SiemacMetadataResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;

public class DatasetVersionSearchSectionStack extends SiemacMetadataResourceSearchSectionStack {

    private DatasetListUiHandlers uiHandlers;

    public DatasetVersionSearchSectionStack() {
    }

    @Override
    protected void createAdvancedSearchForm() {
        super.createAdvancedSearchForm();

        // TODO geographical granularity
        // TODO temporal granularity
        CustomDateItem dateStart = new CustomDateItem(DatasetDS.DATE_START, getConstants().datasetDateStart());
        CustomDateItem dateEnd = new CustomDateItem(DatasetDS.DATE_END, getConstants().datasetDateEnd());
        // TODO related DSD
        CustomDateItem dateNextUpdate = new CustomDateItem(DatasetDS.DATE_NEXT_UPDATE, getConstants().datasetDateNextUpdate());

        advancedSearchForm.addFieldsInThePenultimePosition(dateStart, dateEnd, dateNextUpdate);
    }

    public DatasetVersionWebCriteria getDatasetVersionWebCriteria() {
        DatasetVersionWebCriteria criteria = (DatasetVersionWebCriteria) getSiemacMetadataStatisticalResourceWebCriteria(new DatasetVersionWebCriteria());
        criteria.setDateStart(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_START)).getValueAsDate());
        criteria.setDateEnd(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_END)).getValueAsDate());
        criteria.setDateNextUpdate(((CustomDateItem) advancedSearchForm.getItem(DatasetDS.DATE_NEXT_UPDATE)).getValueAsDate());
        return criteria;
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
