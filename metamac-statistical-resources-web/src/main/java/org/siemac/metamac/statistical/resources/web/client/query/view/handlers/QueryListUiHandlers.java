package org.siemac.metamac.statistical.resources.web.client.query.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface QueryListUiHandlers extends UiHandlers {

    void retrieveQueries(int firstResult, int maxResults, StatisticalResourceWebCriteria criteria);

    void deleteQueries(List<String> urns);

    void goToQuery(String urn);

    void goToNewQuery();
}
