package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.facets.StatisticalOperationFacetFilter;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleVersionableFilterForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;
import org.siemac.metamac.web.common.client.widgets.filters.facets.OnlyLastVersionFacetFilter;

public class VersionableStatisticalResourceFilterForm<T extends VersionableStatisticalResourceWebCriteria> extends SimpleVersionableFilterForm<T> {

    private StatisticalOperationFacetFilter statisticalOperationFacet;
    private OnlyLastVersionFacetFilter      onlyLastVersionFacet;

    public VersionableStatisticalResourceFilterForm() {
        super();
        statisticalOperationFacet = new StatisticalOperationFacetFilter();
        onlyLastVersionFacet = new OnlyLastVersionFacetFilter();
        onlyLastVersionFacet.setColSpan(2);
        criteriaFacet.setColSpan(2);
    }

    public void setStatisticalOperations(List<ExternalItemDto> statisticalOperations) {
        statisticalOperationFacet.setStatisticalOperations(statisticalOperations);
    }

    public void setSelectedStatisticalOperation(ExternalItemDto statisticalOperation) {
        if (statisticalOperation != null) {
            statisticalOperationFacet.setSelectedStatisticalOperation(statisticalOperation);
        }
    }

    @Override
    public void setOnlyLastVersion(boolean onlyLastVersion) {
        onlyLastVersionFacet.setOnlyLastVersion(onlyLastVersion);
    }

    // IMPORTANT: This method must be inherited if you change the WebCriteria in T
    @Override
    public T getSearchCriteria() {
        VersionableStatisticalResourceWebCriteria searchCriteria = new VersionableStatisticalResourceWebCriteria();
        populateVersionableStatisticalResourceSearchCriteria(searchCriteria);
        return (T) searchCriteria;
    }

    protected void populateVersionableStatisticalResourceSearchCriteria(VersionableStatisticalResourceWebCriteria searchCriteria) {
        statisticalOperationFacet.populateCriteria(searchCriteria);
        populateVersionableSearchCriteria(searchCriteria);
    }

    @Override
    public List<FacetFilter> getFacets() {
        return Arrays.asList(statisticalOperationFacet, onlyLastVersionFacet, criteriaFacet);
    }

}
