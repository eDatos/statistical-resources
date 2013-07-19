package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface DatasetMetadataTabUiHandlers extends BaseUiHandlers, StatisticalResourceUiHandlers {

    // Lifecycle
    void sendToProductionValidation(DatasetVersionDto dataset);
    void sendToDiffusionValidation(DatasetVersionDto dataset);
    void rejectValidation(DatasetVersionDto dataset);
    // void sendToPendingPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    // void programPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    // void cancelProgrammedPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void publish(DatasetVersionDto dataset);
    // void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void version(DatasetVersionDto dataset, VersionTypeEnum versionType);

    void retrieveDataset(String datasetIdentifier);
    void saveDataset(DatasetVersionDto datasetDto);

    // DSD
    void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria);
    void retrieveStatisticalOperationsForDsdSelection();

    // Geo granularities
    void retrieveCodesForGeographicalGranularities(int firstResult, int maxResults, MetamacWebCriteria criteria);
    // time codes
    void retrieveTemporalCodesForField(int firstResult, int maxResults, MetamacWebCriteria webCriteria, DatasetMetadataExternalField updateFrequency);

    // RELATED DATASETS
    void retrieveDatasetsForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria);

    // Concept schemes, concepts
    void retrieveConceptSchemesForStatisticalUnit(int firstResult, int maxResults, MetamacWebCriteria webCriteria);
    void retrieveConceptsForStatisticalUnit(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria);
}
