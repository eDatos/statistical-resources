package org.siemac.metamac.statistical.resources.web.client.query.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface QueryListUiHandlers extends UiHandlers {

    void retrieveQueriesByStatisticalOperation(int firstResult, int maxResults, StatisticalResourceWebCriteria criteria);
    void deleteQueries(List<String> urns);

    void goToQuery(String urn);
    void goToNewQuery();

    // LifeCycle

    void sendToProductionValidation(List<QueryVersionDto> queryVersionDtos);
    void sendToDiffusionValidation(List<QueryVersionDto> queryVersionDtos);
    void rejectValidation(List<QueryVersionDto> queryVersionDtos);
    void publish(List<QueryVersionDto> queryVersionDtos);
    void programPublication(List<QueryVersionDto> queryVersionDtos);
}
