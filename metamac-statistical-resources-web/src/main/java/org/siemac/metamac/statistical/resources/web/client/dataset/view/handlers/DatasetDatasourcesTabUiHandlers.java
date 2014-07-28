package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import java.util.List;

import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetDatasourcesTabUiHandlers extends UiHandlers {

    void deleteDatasources(List<String> datasourcesUrns);

    // Importation

    void datasourcesImportationFailed(String errorMessage);
    void datasourcesImportationSucceed(String fileName);
    void retrieveAlternativeCodelistsForVariable(String dimensionId, String variableUrn, int firstResult, int maxResults, SrmExternalResourceRestCriteria criteria);
    void retrieveDimensionVariablesForDataset(String urn);
    
    void showWaitPopup();
}
