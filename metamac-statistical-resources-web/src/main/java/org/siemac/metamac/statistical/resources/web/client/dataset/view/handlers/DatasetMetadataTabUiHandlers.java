package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface DatasetMetadataTabUiHandlers extends BaseUiHandlers, StatisticalResourceUiHandlers {

    // Lifecycle
    void sendToProductionValidation(DatasetDto dataset);
    void sendToDiffusionValidation(DatasetDto dataset);
    void rejectValidation(DatasetDto dataset);
    // void sendToPendingPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    // void programPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    // void cancelProgrammedPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void publish(DatasetDto dataset);
    // void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void version(DatasetDto dataset, VersionTypeEnum versionType);

    void retrieveDataset(String datasetIdentifier);
    void saveDataset(DatasetDto datasetDto);

    // DSD
    void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria);
    void retrieveStatisticalOperationsForDsdSelection();

    // Geo granularities
    void retrieveCodesForGeographicalGranularities(int firstResult, int maxResults, MetamacWebCriteria criteria);
    // time granularities
    void retrieveCodesForTemporalGranularities(int firstResult, int maxResults, MetamacWebCriteria webCriteria);

    // RELATED DATASETS
    void retrieveDatasetsForReplaces(int firstResult, int maxResults, String criteria);
    void retrieveDatasetsForIsReplacedBy(int firstResult, int maxResults, String criteria);

    // Concept schemes, concepts
    void retrieveConceptSchemesForStatisticalUnit(int firstResult, int maxResults, MetamacWebCriteria webCriteria);
    void retrieveConceptsForStatisticalUnit(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria);
}
