package org.siemac.metamac.statistical.resources.web.client.widgets.filters.facets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasSchemeCriteria;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterAction;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleFilterForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class ItemSchemeCriteriaFacetFilter implements FacetFilter {

    protected SearchExternalItemLinkItem     schemeFilter;
    protected SearchSrmSchemePaginatedWindow schemeWindow;
    protected FilterAction                   filterAction;

    public ItemSchemeCriteriaFacetFilter(final int maxResults, final SearchPaginatedAction<MetamacWebCriteria> action) {
        super();
        schemeFilter = createSchemeFilterItem(action);
        schemeFilter.setShowTitle(true);
    }

    @Override
    public void setFilterAction(FilterAction action) {
        filterAction = action;
    }

    @Override
    public FormItem getFormItem() {
        return schemeFilter;
    }

    @Override
    public void setColSpan(int cols) {
        schemeFilter.setColSpan(cols);
    }

    public void populateCriteria(HasSchemeCriteria criteria) {
        ExternalItemDto selectedResource = schemeWindow != null ? schemeWindow.getSelectedResource() : null;
        criteria.setSchemeUrn(selectedResource != null ? selectedResource.getUrn() : null);
    }

    public void setFilterResources(List<ExternalItemDto> resources) {
        if (schemeWindow != null) {
            schemeWindow.setResources(resources);
        }
    }

    public void refreshFilterSourcePaginationInfo(int firstResult, int elementsInPage, int totalResults) {
        if (schemeWindow != null) {
            schemeWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    public void setSelectedConceptScheme(ExternalItemDto selected) {
        schemeFilter.setValue(ExternalItemUtils.getExternalItemName(schemeWindow.getSelectedResource()));
        filterAction.applyFilter();
    }
    // FIXME: GENERALIZE, CHANGE CONCEPT SCHEME BY SCHEME
    private SearchExternalItemLinkItem createSchemeFilterItem(final SearchPaginatedAction<MetamacWebCriteria> action) {
        SearchExternalItemLinkItem item = new SearchExternalItemLinkItem("CONCEPT_SCHEME", StatisticalResourcesWeb.getConstants().conceptScheme());

        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                schemeWindow = new SearchSrmSchemePaginatedWindow(StatisticalResourcesWeb.getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, action);
                action.retrieveResultSet(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, new MetamacWebCriteria());
                schemeWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        setSelectedConceptScheme(schemeWindow.getSelectedResource());
                        schemeWindow.markForDestroy();
                    }
                });
            }
        });

        return item;
    }

    private class SearchSrmSchemePaginatedWindow extends SearchRelatedResourceBasePaginatedWindow<ExternalItemDto, MetamacWebCriteria> {

        public SearchSrmSchemePaginatedWindow(String title, int maxResults, SearchPaginatedAction<MetamacWebCriteria> action) {
            super(title, maxResults, new SimpleFilterForm(), action);
        }
    }
}