package org.siemac.metamac.statistical.resources.web.client.operation.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface OperationResourcesUiHandlers extends UiHandlers {

    void goToDataset(DatasetVersionBaseDto datasetVersionBaseDto);
    void goToPublication(PublicationVersionBaseDto publicationVersionBaseDto);
    void goToQuery(QueryVersionBaseDto queryVersionBaseDto);
    void goToMultidataset(MultidatasetVersionBaseDto multidatasetVersionBaseDto);
}
