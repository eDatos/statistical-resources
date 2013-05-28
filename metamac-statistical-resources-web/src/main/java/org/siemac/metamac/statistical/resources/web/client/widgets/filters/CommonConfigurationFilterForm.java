package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.filters.FilterForm;

import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class CommonConfigurationFilterForm extends FilterForm<CommonConfigurationWebCriteria> {
    private static final String FIELD_ONLY_ENABLED = "only-enabled";
    private CheckboxItem onlyEnabled;
    
    
    public CommonConfigurationFilterForm() {
        searchItem.setColSpan(2);
        onlyEnabled = new CheckboxItem(FIELD_ONLY_ENABLED, StatisticalResourcesWeb.getConstants().isEnabled());
        onlyEnabled.setValue(true);
        onlyEnabled.setTitleOrientation(TitleOrientation.LEFT);
        onlyEnabled.setColSpan(2);
        onlyEnabled.setShowTitle(false);
        
    }
    
    public void setShowOnlyEnabled(boolean onlyEnabled) {
        this.onlyEnabled.setValue(onlyEnabled);
    }
    

    @Override
    public CommonConfigurationWebCriteria getSearchCriteria() {
        CommonConfigurationWebCriteria criteria = new CommonConfigurationWebCriteria();
        criteria.setCriteria(searchItem.getValueAsString());
        criteria.setOnlyEnabled(onlyEnabled.getValueAsBoolean());
        return criteria;
    }
    
    @Override
    public void setSearchAction(final SearchPaginatedAction<CommonConfigurationWebCriteria> action, final int maxResults) {
        searchIcon.addFormItemClickHandler(new FormItemClickHandler() {
            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                action.retrieveResultSet(0, maxResults, getSearchCriteria());
            }
        });
        
        onlyEnabled.addChangedHandler(new ChangedHandler() {
            
            @Override
            public void onChanged(ChangedEvent event) {
                action.retrieveResultSet(0, maxResults, getSearchCriteria());
            }
        });
    }
    
    //This must be extended if we want to add more fields
    @Override
    public List<FormItem> getFormItems() {
        List<FormItem> formItems = new ArrayList<FormItem>();
        formItems.add(searchItem);
        formItems.add(onlyEnabled);
        return formItems;
    }
    
}
