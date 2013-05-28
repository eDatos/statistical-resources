package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
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

public class DsdFilterForm extends FilterForm<DsdWebCriteria> {
    private static final String FIELD_STAT_OPER = "stat-oper-select";
    private static final String FIELD_LAST_VERSION = "last-version";
    private SelectItem statisticalOperationSelectItem;
    private String fixedDsdCode;
    private CheckboxItem onlyLastVersion;
    
    
    public DsdFilterForm() {
        statisticalOperationSelectItem = new SelectItem(FIELD_STAT_OPER, StatisticalResourcesWeb.getConstants().statisticalOperation());
        statisticalOperationSelectItem.setRequired(false);
        statisticalOperationSelectItem.setTitleOrientation(TitleOrientation.TOP);
        searchItem.setColSpan(2);
        fixedDsdCode = null;
        onlyLastVersion = new CheckboxItem(FIELD_LAST_VERSION, StatisticalResourcesWeb.getConstants().isLastVersion());
        onlyLastVersion.setValue(true);
        onlyLastVersion.setTitleOrientation(TitleOrientation.LEFT);
        onlyLastVersion.setColSpan(2);
        onlyLastVersion.setShowTitle(false);
        
    }
    
    public void setStatisticalOperations(List<ExternalItemDto> statisticalOperations) {
        statisticalOperationSelectItem.setValueMap(ExternalItemUtils.getExternalItemsHashMap(statisticalOperations));
    }
    
    
    public void setStatisticalOperation(ExternalItemDto statisticalOperation) {
        if  (statisticalOperation != null) {
            statisticalOperationSelectItem.setValue(statisticalOperation.getUrn());
        }
    }
    
    public void setFixedDsdCode(String fixedDsdCode) {
        this.fixedDsdCode = fixedDsdCode;
    }
    
    public void setOnlyLastVersion(boolean onlyLastVersion) {
        this.onlyLastVersion.setValue(onlyLastVersion);
    }
    

    @Override
    public DsdWebCriteria getSearchCriteria() {
        DsdWebCriteria criteria = new DsdWebCriteria();
        criteria.setCriteria(searchItem.getValueAsString());
        criteria.setStatisticalOperationUrn(statisticalOperationSelectItem.getValueAsString());
        criteria.setFixedDsdCode(fixedDsdCode);
        criteria.setOnlyLastVersion(onlyLastVersion.getValueAsBoolean());
        return criteria;
    }
    
    @Override
    public void setSearchAction(final SearchPaginatedAction<DsdWebCriteria> action, final int maxResults) {
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
    
    //This must be extended if we want to add more fields
    @Override
    public List<FormItem> getFormItems() {
        List<FormItem> formItems = new ArrayList<FormItem>();
        formItems.add(statisticalOperationSelectItem);
        formItems.add(searchItem);
        formItems.add(onlyLastVersion);
        return formItems;
    }
    
}
