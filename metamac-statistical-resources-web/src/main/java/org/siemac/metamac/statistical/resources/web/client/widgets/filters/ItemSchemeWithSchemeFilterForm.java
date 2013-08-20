package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.filters.facets.ItemSchemeCriteriaFacetFilter;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleVersionableFilterForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;

public class ItemSchemeWithSchemeFilterForm extends SimpleVersionableFilterForm<ItemSchemeWebCriteria> {

    protected ItemSchemeCriteriaFacetFilter itemSchemeFilterFacet;

    public ItemSchemeWithSchemeFilterForm(final int maxResults, final SearchPaginatedAction<MetamacVersionableWebCriteria> action) {
        super();
        itemSchemeFilterFacet = new ItemSchemeCriteriaFacetFilter(maxResults, action);
        criteriaFacet.setColSpan(2);
    }

    public void setFilterResources(List<ExternalItemDto> resources) {
        if (itemSchemeFilterFacet != null) {
            itemSchemeFilterFacet.setFilterResources(resources);
        }
    }

    public void refreshFilterSourcePaginationInfo(int firstResult, int elementsInPage, int totalResults) {
        if (itemSchemeFilterFacet != null) {
            itemSchemeFilterFacet.refreshFilterSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    @Override
    public List<FacetFilter> getFacets() {
        return Arrays.asList(onlyLastVersionFacet, itemSchemeFilterFacet, criteriaFacet);
    }

    @Override
    public ItemSchemeWebCriteria getSearchCriteria() {
        ItemSchemeWebCriteria criteria = new ItemSchemeWebCriteria();
        itemSchemeFilterFacet.populateCriteria(criteria);
        populateVersionableSearchCriteria(criteria);
        return criteria;
    }
}
