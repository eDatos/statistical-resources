package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;

public interface DatasetListUiHandlers extends NewStatisticalResourceUiHandlers {

    void goToDataset(String code);
    void createDataset(DatasetVersionDto datasetDto);
    void deleteDatasets(List<String> urnsFromSelected);
    void retrieveDatasetsByStatisticalOperation(String operationUrn, int firstResult, int maxResults);

    // Dsd related actions
    void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria);
    void retrieveStatisticalOperationsForDsdSelection();
}
