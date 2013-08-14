package org.siemac.metamac.statistical.resources.web.client.operation.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface OperationResourcesUiHandlers extends UiHandlers {

    void goToDataset(DatasetVersionDto datasetVersionDto);
    void goToPublication(PublicationVersionDto publicationVersionDto);
    void goToQuery(QueryVersionDto queryVersionDto);
}
