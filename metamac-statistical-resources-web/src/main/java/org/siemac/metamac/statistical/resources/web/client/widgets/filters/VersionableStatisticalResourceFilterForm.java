package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterForm;

import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class VersionableStatisticalResourceFilterForm extends FilterForm<VersionableStatisticalResourceWebCriteria> {

    private static final String FIELD_STAT_OPER    = "stat-oper-select";
    private static final String FIELD_LAST_VERSION = "last-version";
    private SelectItem          statisticalOperationSelectItem;
    private CheckboxItem        onlyLastVersion;

    public VersionableStatisticalResourceFilterForm() {
        statisticalOperationSelectItem = new SelectItem(FIELD_STAT_OPER, StatisticalResourcesWeb.getConstants().statisticalOperation());
        statisticalOperationSelectItem.setRequired(false);
        statisticalOperationSelectItem.setTitleOrientation(TitleOrientation.TOP);
        searchItem.setColSpan(2);
        onlyLastVersion = new CheckboxItem(FIELD_LAST_VERSION, StatisticalResourcesWeb.getConstants().isLastVersion());
        onlyLastVersion.setValue(true);
        onlyLastVersion.setTitleOrientation(TitleOrientation.LEFT);
        onlyLastVersion.setColSpan(2);
        onlyLastVersion.setShowTitle(false);
    }

    public void setStatisticalOperations(List<ExternalItemDto> statisticalOperations) {
        statisticalOperationSelectItem.setValueMap(ExternalItemUtils.getExternalItemsHashMap(statisticalOperations));
    }

    public void setSelectedStatisticalOperation(ExternalItemDto statisticalOperation) {
        if (statisticalOperation != null) {
            statisticalOperationSelectItem.setValue(statisticalOperation.getUrn());
        }
    }

    public void setOnlyLastVersion(boolean onlyLastVersion) {
        this.onlyLastVersion.setValue(onlyLastVersion);
    }

    @Override
    public VersionableStatisticalResourceWebCriteria getSearchCriteria() {
        VersionableStatisticalResourceWebCriteria searchCriteria = new VersionableStatisticalResourceWebCriteria();
        searchCriteria.setCriteria(searchItem.getValueAsString());
        searchCriteria.setStatisticalOperationUrn(statisticalOperationSelectItem.getValueAsString());
        searchCriteria.setOnlyLastVersion(onlyLastVersion.getValueAsBoolean());
        return searchCriteria;
    }

    @Override
    public List<FormItem> getFormItems() {
        return Arrays.asList(statisticalOperationSelectItem, searchItem, onlyLastVersion);
    }
    
    @Override
    public void setSearchAction(final SearchPaginatedAction<VersionableStatisticalResourceWebCriteria> action, final int maxResults) {
        searchIcon.addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                action.retrieveResultSet(0, maxResults, getSearchCriteria());
            }
        });

        statisticalOperationSelectItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                action.retrieveResultSet(0, maxResults, getSearchCriteria());
            }
        });

        onlyLastVersion.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                action.retrieveResultSet(0, maxResults, getSearchCriteria());
            }
        });
    }
}
