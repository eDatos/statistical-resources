package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetListUiHandlers extends UiHandlers {

    void goToDataset(String code);
    void createDataset(DatasetDto datasetDto);
    void deleteDatasets(List<String> urnsFromSelected);
    void retrieveDatasetsByStatisticalOperation(String operationUrn, int firstResult, int maxResults);
    
    //Dsd related actions
    void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria);
    void retrieveStatisticalOperationsForDsdSelection();

}
