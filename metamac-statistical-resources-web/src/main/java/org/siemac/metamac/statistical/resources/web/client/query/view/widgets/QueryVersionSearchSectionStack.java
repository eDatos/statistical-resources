package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.LifeCycleResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.shared.criteria.QueryVersionWebCriteria;

import com.smartgwt.client.widgets.form.fields.SelectItem;

public class QueryVersionSearchSectionStack extends LifeCycleResourceSearchSectionStack {

    private QueryListUiHandlers uiHandlers;

    public QueryVersionSearchSectionStack() {
    }

    public QueryVersionWebCriteria getQueryVersionWebCriteria() {
        QueryVersionWebCriteria criteria = (QueryVersionWebCriteria) getLifeCycleResourceWebCriteria(new QueryVersionWebCriteria());
        criteria.setQueryStatus(CommonUtils.getQueryStatusEnum(advancedSearchForm.getValueAsString(QueryDS.STATUS)));
        criteria.setQueryType(CommonUtils.getQueryTypeEnum(advancedSearchForm.getValueAsString(QueryDS.TYPE)));
        return criteria;
    }

    @Override
    protected void createAdvancedSearchForm() {
        super.createAdvancedSearchForm();
        // TODO Related dataset version

        SelectItem status = new SelectItem(QueryDS.STATUS, getConstants().queryStatus());
        status.setValueMap(CommonUtils.getQueryStatusHashMap());

        SelectItem type = new SelectItem(QueryDS.TYPE, getConstants().queryType());
        type.setValueMap(CommonUtils.getQueryTypeHashMap());

        advancedSearchForm.addFieldsInThePenultimePosition(status, type);
    }

    @Override
    public void retrieveResources() {
        getUiHandlers().retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getQueryVersionWebCriteria());
    }

    public void setUiHandlers(QueryListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public QueryListUiHandlers getUiHandlers() {
        return uiHandlers;
    }
}
