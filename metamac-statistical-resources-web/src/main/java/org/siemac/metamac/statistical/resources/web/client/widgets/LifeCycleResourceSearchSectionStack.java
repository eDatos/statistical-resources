package org.siemac.metamac.statistical.resources.web.client.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.LifeCycleStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.BaseAdvancedSearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCheckboxItem;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public abstract class LifeCycleResourceSearchSectionStack extends BaseAdvancedSearchSectionStack {

    public LifeCycleResourceSearchSectionStack() {
    }

    @Override
    protected void clearAdvancedSearchSection() {
        super.clearAdvancedSearchSection();
        // Search last versions by default
        ((CustomCheckboxItem) advancedSearchForm.getItem(LifeCycleResourceDS.LAST_VERSION)).setValue(true);
    }

    @Override
    protected void createAdvancedSearchForm() {
        advancedSearchForm = new GroupDynamicForm(StringUtils.EMPTY);
        advancedSearchForm.setPadding(5);
        advancedSearchForm.setMargin(5);
        advancedSearchForm.setVisible(false);
        // TODO add statistical operation
        TextItem code = new TextItem(LifeCycleResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
        TextItem name = new TextItem(LifeCycleResourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        TextItem urn = new TextItem(LifeCycleResourceDS.URN, getConstants().identifiableStatisticalResourceURN());
        TextItem description = new TextItem(LifeCycleResourceDS.DESCRIPTION, getConstants().nameableStatisticalResourceDescription());
        SelectItem procStatus = new SelectItem(LifeCycleResourceDS.PROC_STATUS, getConstants().lifeCycleStatisticalResourceProcStatus());
        procStatus.setValueMap(CommonUtils.getProcStatusHashMap());
        // TODO add the rest of the fields
        CustomCheckboxItem isLastVersion = new CustomCheckboxItem(LifeCycleResourceDS.LAST_VERSION, MetamacWebCommon.getConstants().isLastVersion());
        isLastVersion.setValue(true);
        CustomButtonItem searchItem = new CustomButtonItem(ADVANCED_SEARCH_ITEM_NAME, MetamacWebCommon.getConstants().search());
        searchItem.setColSpan(4);
        searchItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                retrieveResources();
            }
        });
        FormItem[] advancedSearchFormItems = new FormItem[]{code, name, urn, description, procStatus, isLastVersion, searchItem};
        setFormItemsInAdvancedSearchForm(advancedSearchFormItems);
    }

    public LifeCycleStatisticalResourceWebCriteria getLifeCycleResourceWebCriteria(LifeCycleStatisticalResourceWebCriteria lifecycleStatisticalResourceWebCriteria) {
        // TODO add statistical operation
        lifecycleStatisticalResourceWebCriteria.setCriteria(searchForm.getValueAsString(SEARCH_ITEM_NAME));
        lifecycleStatisticalResourceWebCriteria.setCode(advancedSearchForm.getValueAsString(LifeCycleResourceDS.CODE));
        lifecycleStatisticalResourceWebCriteria.setTitle(advancedSearchForm.getValueAsString(LifeCycleResourceDS.TITLE));
        lifecycleStatisticalResourceWebCriteria.setUrn(advancedSearchForm.getValueAsString(LifeCycleResourceDS.URN));
        lifecycleStatisticalResourceWebCriteria.setDescription(advancedSearchForm.getValueAsString(LifeCycleResourceDS.DESCRIPTION));
        lifecycleStatisticalResourceWebCriteria.setProcStatus(CommonUtils.getProcStatusEnum(advancedSearchForm.getValueAsString(LifeCycleResourceDS.PROC_STATUS)));
        // TODO add the rest of the fields
        lifecycleStatisticalResourceWebCriteria.setOnlyLastVersion(((CustomCheckboxItem) advancedSearchForm.getItem(LifeCycleResourceDS.LAST_VERSION)).getValueAsBoolean());
        return lifecycleStatisticalResourceWebCriteria;
    }
}
