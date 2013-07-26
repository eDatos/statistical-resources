package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.facets.StatisticalOperationFacetFilter;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleFilterForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;

public class StatisticalResourceFilterForm<T extends StatisticalResourceWebCriteria> extends SimpleFilterForm<T> {

    private StatisticalOperationFacetFilter statisticalOperationFacet;

    public StatisticalResourceFilterForm() {
        super();
        statisticalOperationFacet = new StatisticalOperationFacetFilter();
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
    @SuppressWarnings("unchecked")
    @Override
    public T getSearchCriteria() {
        StatisticalResourceWebCriteria searchCriteria = new StatisticalResourceWebCriteria();
        populateStatisticalResourceSearchCriteria(searchCriteria);
        return (T) searchCriteria;
    }

    protected void populateStatisticalResourceSearchCriteria(StatisticalResourceWebCriteria searchCriteria) {
        statisticalOperationFacet.populateCriteria(searchCriteria);
        populateMetamacWebCriteria(searchCriteria);
    }

    @Override
    public List<FacetFilter> getFacets() {
        return Arrays.asList(statisticalOperationFacet, criteriaFacet);
    }
}
