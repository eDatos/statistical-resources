package org.siemac.metamac.statistical.resources.web.client.query.view.handlers;

import java.util.List;

import com.gwtplatform.mvp.client.UiHandlers;

public interface QueryListUiHandlers extends UiHandlers {

    void retrieveQueries(int firstResult, int maxResults);

    void deleteQueries(List<String> urns);

    void goToQuery(String urn);

    void goToNewQuery();
}
