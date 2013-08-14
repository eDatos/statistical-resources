package org.siemac.metamac.statistical.resources.web.client.query.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface QueryUiHandlers extends BaseUiHandlers {

    void goToQueries();

    void saveQuery(QueryVersionDto query);

    void retrieveDatasetsForQuery(int firstResult, int maxResults, DatasetVersionWebCriteria criteria);
    void retrieveStatisticalOperationsForDatasetSelection();

    void retrieveDimensionsForDataset(String urn);

    void retrieveDimensionCodesForDataset(String urn, String dimensionId, MetamacWebCriteria webCriteria);

    void retrieveAgencySchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria);
    void retrieveAgencies(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria);

    // Life cycle

    void sendToProductionValidation(QueryVersionDto queryVersionDto);
    void sendToDiffusionValidation(QueryVersionDto queryVersionDto);
    void rejectValidation(QueryVersionDto queryVersionDto);
    void programPublication(QueryVersionDto queryVersionDto);
    void cancelProgrammedPublication(QueryVersionDto queryVersionDto);
    void publish(QueryVersionDto queryVersionDto);
    void version(QueryVersionDto queryVersionDto, VersionTypeEnum versionType);
}
