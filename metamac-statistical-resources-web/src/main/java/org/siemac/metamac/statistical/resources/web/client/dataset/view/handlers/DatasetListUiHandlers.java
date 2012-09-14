package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetListUiHandlers extends UiHandlers {

    void goToDataSet(String code);
    void createDataSet(DataSetDto datasetDto);
    void deleteDataSets(List<String> urnsFromSelected);
    void retrieveDataSetsByStatisticalOperation(String operationUrn, int firstResult, int maxResults);

}
