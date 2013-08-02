package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetDatasourcesTabUiHandlers extends UiHandlers {

    void retrieveDatasourcesByDataset(String datasetUrn, int firstResult, int maxResults);
    void saveDatasource(DatasourceDto datasourceDto);
    void deleteDatasources(List<String> datasourcesUrns);

    // Importation

    void datasourcesImportationFailed(String errorMessage);
    void datasourcesImportationSucceed(String fileName);
}
