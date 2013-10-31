package org.siemac.metamac.statistical.resources.web.client.widgets.filters.facets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasSchemeCriteria;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterAction;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleHiddenVersionableFilterForm;
import org.siemac.metamac.web.common.client.widgets.filters.facets.FacetFilter;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.windows.search.SearchRelatedResourceBasePaginatedWindow;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class ItemSchemeCriteriaFacetFilter implements FacetFilter {

    protected SearchExternalItemLinkItem     schemeFilter;
    protected SearchSrmSchemePaginatedWindow schemeWindow;
    protected FilterAction                   filterAction;

    public ItemSchemeCriteriaFacetFilter(final int maxResults, final SearchPaginatedAction<MetamacVersionableWebCriteria> action) {
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
        ExternalItemDto selectedResource = StatisticalResourcesFormUtils.getExternalItemValue(schemeFilter);
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
        StatisticalResourcesFormUtils.setExternalItemValue(schemeFilter, selected);
        filterAction.applyFilter();
    }

    private SearchExternalItemLinkItem createSchemeFilterItem(final SearchPaginatedAction<MetamacVersionableWebCriteria> action) {
        SearchExternalItemLinkItem item = new SearchExternalItemLinkItem("SCHEME", StatisticalResourcesWeb.getConstants().itemScheme());

        item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                schemeWindow = new SearchSrmSchemePaginatedWindow(StatisticalResourcesWeb.getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, action);
                action.retrieveResultSet(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, new MetamacVersionableWebCriteria());
                schemeWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        setSelectedConceptScheme(schemeWindow.getSelectedResource());
                        schemeWindow.markForDestroy();
                    }
                });
            }
        });

        item.getClearIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                setSelectedConceptScheme(null);
            }
        });

        return item;
    }

    private class SearchSrmSchemePaginatedWindow extends SearchRelatedResourceBasePaginatedWindow<ExternalItemDto, MetamacVersionableWebCriteria> {

        public SearchSrmSchemePaginatedWindow(String title, int maxResults, SearchPaginatedAction<MetamacVersionableWebCriteria> action) {
            super(title, maxResults, new SimpleHiddenVersionableFilterForm<MetamacVersionableWebCriteria>(), action);
        }
    }
}
