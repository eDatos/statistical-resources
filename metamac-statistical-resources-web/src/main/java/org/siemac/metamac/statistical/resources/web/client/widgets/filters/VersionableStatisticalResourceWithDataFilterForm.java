package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;

public class VersionableStatisticalResourceWithDataFilterForm<T extends DatasetVersionWebCriteria> extends VersionableStatisticalResourceFilterForm<T> {

    private Boolean hasData;

    public VersionableStatisticalResourceWithDataFilterForm() {
        super();
    }

    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }

    // IMPORTANT: This method must be inherited if you change the WebCriteria in T
    @SuppressWarnings("unchecked")
    @Override
    public T getSearchCriteria() {
        DatasetVersionWebCriteria searchCriteria = new DatasetVersionWebCriteria();
        populateDatasetVersionWebCriteria(searchCriteria);
        return (T) searchCriteria;
    }

    protected void populateDatasetVersionWebCriteria(DatasetVersionWebCriteria searchCriteria) {
        searchCriteria.setHasData(hasData);
        populateVersionableStatisticalResourceSearchCriteria(searchCriteria);
    }
}
