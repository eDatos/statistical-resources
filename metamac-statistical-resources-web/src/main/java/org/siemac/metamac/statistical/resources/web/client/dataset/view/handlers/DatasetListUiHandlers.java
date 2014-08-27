package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface DatasetListUiHandlers extends NewStatisticalResourceUiHandlers {

    void goToDataset(String code);
    void createDataset(DatasetVersionDto datasetDto);
    void deleteDatasets(List<String> urnsFromSelected);
    void retrieveDatasets(int firstResult, int maxResults, DatasetVersionWebCriteria criteria);

    // LifeCycle

    void sendToProductionValidation(List<DatasetVersionBaseDto> datasetVersionBaseDtos);
    void sendToDiffusionValidation(List<DatasetVersionBaseDto> datasetVersionBaseDtos);
    void rejectValidation(List<DatasetVersionBaseDto> datasetVersionBaseDtos, String reasonOfRejection);
    void publish(List<DatasetVersionBaseDto> datasetVersionBaseDtos);
    void programPublication(List<DatasetVersionBaseDto> datasetVersionBaseDtos, Date validFrom);
    void cancelProgrammedPublication(List<DatasetVersionBaseDto> datasetVersionBaseDtos);
    void version(List<DatasetVersionBaseDto> datasetVersionBaseDtos, VersionTypeEnum versionType);

    // DSD related actions

    void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria);
    void retrieveStatisticalOperationsForDsdSelection();

    // Importation

    void datasourcesImportationFailed(String errorMessage);
    void datasourcesImportationSucceed(String fileName);

    // Related resources

    void retrieveStatisticalOperationsForSearchSection(int firstResult, int maxResults, MetamacWebCriteria criteria);
    void retrieveGeographicGranularitiesForSearchSection(int firstResult, int maxResults, MetamacWebCriteria criteria);
    void retrieveTemporalGranularitiesForSearchSection(int firstResult, int maxResults, MetamacWebCriteria criteria);
    void retrieveStatisticalOperationsForDsdSelectionInSearchSection();
    void retrieveDsdsForSearchSection(int firstResult, int maxResults, DsdWebCriteria criteria);
}
