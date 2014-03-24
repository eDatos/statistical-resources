package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import org.siemac.metamac.statistical.resources.web.client.widgets.filters.base.VersionableStatisticalResourceFilterBaseForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;

public class VersionableStatisticalResourceWithDataFilterForm extends VersionableStatisticalResourceFilterBaseForm<DatasetVersionWebCriteria> {

    private Boolean hasData;

    public VersionableStatisticalResourceWithDataFilterForm() {
        super();
    }

    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }

    @Override
    public DatasetVersionWebCriteria getSearchCriteria() {
        DatasetVersionWebCriteria searchCriteria = super.getSearchCriteria();
        searchCriteria.setHasData(hasData);
        return searchCriteria;
    }

    @Override
    protected DatasetVersionWebCriteria buildEmptySearchCriteria() {
        return new DatasetVersionWebCriteria();
    }
}
