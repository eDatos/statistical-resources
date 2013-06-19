package org.siemac.metamac.statistical.resources.web.client.query.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;


public interface QueryUiHandlers extends BaseUiHandlers {

    void saveQuery(QueryDto query);
    
    void retrieveDatasetsForQuery(int firstResult, int maxResults, VersionableStatisticalResourceWebCriteria criteria);
    void retrieveStatisticalOperationsForDatasetSelection();
}
