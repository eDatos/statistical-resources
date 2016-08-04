package org.siemac.metamac.statistical.resources.web.client.widgets.filters.base;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.facets.StatisticalOperationFacetFilter;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.filters.base.SimpleVersionableFilterBaseForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;
import org.siemac.metamac.web.common.client.widgets.filters.facets.OnlyLastVersionFacetFilter;

public abstract class VersionableStatisticalResourceFilterBaseForm<T extends VersionableStatisticalResourceWebCriteria> extends SimpleVersionableFilterBaseForm<T> {

    private StatisticalOperationFacetFilter statisticalOperationFacet;

    public VersionableStatisticalResourceFilterBaseForm() {
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

    // IMPORTANT: This method must be inherited if you change the WebCriteria in T
    @Override
    public T getSearchCriteria() {
        T searchCriteria = super.getSearchCriteria();

        statisticalOperationFacet.populateCriteria(searchCriteria);

        return searchCriteria;
    }

    @Override
    public List<FacetFilter> getFacets() {
        return Arrays.asList(statisticalOperationFacet, onlyLastVersionFacet, criteriaFacet);
    }
}
