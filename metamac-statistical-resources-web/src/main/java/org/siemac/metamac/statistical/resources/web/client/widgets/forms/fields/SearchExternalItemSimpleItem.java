package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleExternalItemPaginatedWindow;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.SimpleFilterForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public abstract class SearchExternalItemSimpleItem extends SearchExternalItemLinkItem {

    private SearchSingleExternalItemPaginatedWindow<MetamacWebCriteria> window;

    public SearchExternalItemSimpleItem(String name, String title, int maxResults) {
        super(name, title);
        appendWindow(maxResults);
    }

    private void appendWindow(final int maxResults) {
        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                SimpleFilterForm<MetamacWebCriteria> simpleFilter = new SimpleFilterForm<MetamacWebCriteria>();
                window = new SearchSingleExternalItemPaginatedWindow<MetamacWebCriteria>(getConstants().resourceSelection(), maxResults, simpleFilter, new SearchPaginatedAction<MetamacWebCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                        retrieveResources(firstResult, maxResults, webCriteria);
                    }

                });
                retrieveResources(0, maxResults, new MetamacWebCriteria());

                window.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        setExternalItem(window.getSelectedResource());
                        window.markForDestroy();
                    }
                });
            }
        });

    }

    public void setResources(List<ExternalItemDto> items, int firstResult, int totalResults) {
        if (window != null) {
            window.setResources(items);
            window.refreshSourcePaginationInfo(firstResult, items.size(), totalResults);
        }
    }

    protected abstract void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria);
}
