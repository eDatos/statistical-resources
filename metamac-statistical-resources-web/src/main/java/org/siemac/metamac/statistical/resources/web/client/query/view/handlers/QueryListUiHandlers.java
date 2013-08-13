package org.siemac.metamac.statistical.resources.web.client.query.view.handlers;

import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.QueryVersionWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

public interface QueryListUiHandlers extends BaseUiHandlers {

    void retrieveQueries(int firstResult, int maxResults, QueryVersionWebCriteria criteria);
    void deleteQueries(List<String> urns);

    void goToQuery(String urn);
    void goToNewQuery();

    // LifeCycle

    void sendToProductionValidation(List<QueryVersionDto> queryVersionDtos);
    void sendToDiffusionValidation(List<QueryVersionDto> queryVersionDtos);
    void rejectValidation(List<QueryVersionDto> queryVersionDtos);
    void publish(List<QueryVersionDto> queryVersionDtos);
    void programPublication(List<QueryVersionDto> queryVersionDtos);
    void version(List<QueryVersionDto> queryVersionDtos, VersionTypeEnum versionType);
}
