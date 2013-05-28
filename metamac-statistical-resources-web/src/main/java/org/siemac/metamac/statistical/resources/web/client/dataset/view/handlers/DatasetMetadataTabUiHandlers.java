package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.view.handlers.LifeCycleUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

public interface DatasetMetadataTabUiHandlers extends LifeCycleUiHandlers, BaseUiHandlers {

    void retrieveAgencies(int firstResult, int maxResults, String queryText);
    void retrieveDataset(String datasetIdentifier);
    void saveDataset(DatasetDto datasetDto);


    // DSD
    void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria);
    void retrieveStatisticalOperationsForDsdSelection();
    
    // COMMON METADATA
    void retrieveCommonConfigurations(CommonConfigurationWebCriteria criteria);
    
    // RELATED DATASETS
    void retrieveDatasetsForReplaces(int firstResult, int maxResults, String criteria);
    void retrieveDatasetsForIsReplacedBy(int firstResult, int maxResults, String criteria);
}
