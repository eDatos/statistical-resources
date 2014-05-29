package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchMultipleExternalItemPaginatedWindow;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public abstract class SearchMultiExternalItemSimpleItem extends ExternalItemListItem {

    private SearchMultipleExternalItemPaginatedWindow window;

    public SearchMultiExternalItemSimpleItem(String name, String title, int maxResults) {
        super(name, title, true);
        appendWindow(maxResults);
    }

    private void appendWindow(final int maxResults) {
        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                window = new SearchMultipleExternalItemPaginatedWindow(getConstants().resourceSelection(), maxResults, new SearchPaginatedAction<MetamacWebCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                        retrieveResources(firstResult, maxResults, webCriteria);
                    }

                });

                window.retrieveItems();

                window.setSelectedResources(getSelectedRelatedResources());

                window.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        setExternalItems(window.getSelectedResources());
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
