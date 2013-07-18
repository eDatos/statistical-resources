package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleFilterForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;
import org.siemac.metamac.web.common.client.widgets.filters.facets.OnlyEnabledFacetFilter;

public class CommonConfigurationFilterForm extends SimpleFilterForm<CommonConfigurationWebCriteria> {

    private OnlyEnabledFacetFilter onlyEnabledFacet;

    public CommonConfigurationFilterForm() {
        super();
        onlyEnabledFacet = new OnlyEnabledFacetFilter();
        onlyEnabledFacet.setColSpan(2);
        criteriaFacet.setColSpan(2);
    }

    public void setShowOnlyEnabled(boolean onlyEnabled) {
        this.onlyEnabledFacet.setOnlyEnabled(onlyEnabled);
    }

    @Override
    public CommonConfigurationWebCriteria getSearchCriteria() {
        CommonConfigurationWebCriteria criteria = new CommonConfigurationWebCriteria();
        onlyEnabledFacet.populateCriteria(criteria);
        populateMetamacWebCriteria(criteria);
        return criteria;
    }

    @Override
    public List<FacetFilter> getFacets() {
        return Arrays.asList(onlyEnabledFacet, criteriaFacet);
    }

}
