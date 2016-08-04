package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.facets.StatisticalOperationFacetFilter;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.filters.base.SimpleFilterBaseForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;

public class StatisticalResourceFilterForm extends SimpleFilterBaseForm<StatisticalResourceWebCriteria> {

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

    @Override
    public StatisticalResourceWebCriteria getSearchCriteria() {
        StatisticalResourceWebCriteria searchCriteria = super.getSearchCriteria();

        statisticalOperationFacet.populateCriteria(searchCriteria);

        return searchCriteria;
    }

    @Override
    protected StatisticalResourceWebCriteria buildEmptySearchCriteria() {
        return new StatisticalResourceWebCriteria();
    }

    @Override
    public List<FacetFilter> getFacets() {
        return Arrays.asList(statisticalOperationFacet, criteriaFacet);
    }
}
