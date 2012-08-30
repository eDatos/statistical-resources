package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetListUiHandlers extends UiHandlers {

    void goToDataset(String code);
    void createDataset(DatasetDto datasetDto);
    void deleteDatasets(List<Long> idsFromSelected);
    void retrieveDatasets(int firstResult, int maxResults, String conceptScheme);

}
