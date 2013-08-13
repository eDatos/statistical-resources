package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.LifeCycleResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.shared.criteria.QueryVersionWebCriteria;

public class QueryVersionSearchSectionStack extends LifeCycleResourceSearchSectionStack {

    private QueryListUiHandlers uiHandlers;

    public QueryVersionSearchSectionStack() {
    }

    public QueryVersionWebCriteria getQueryVersionWebCriteria() {
        return (QueryVersionWebCriteria) getLifeCycleResourceWebCriteria(new QueryVersionWebCriteria());
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
