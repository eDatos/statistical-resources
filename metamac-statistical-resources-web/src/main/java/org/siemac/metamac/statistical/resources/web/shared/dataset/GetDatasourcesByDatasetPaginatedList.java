package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasourcesByDatasetPaginatedList {

    @In(1)
    String              datasetUrn;

    @In(2)
    int                 firstResult;

    @In(3)
    int                 maxResults;

    @Out(1)
    List<DatasourceDto> datasourcesList;

    @Out(2)
    Integer             pageNumber;

    @Out(3)
    Integer             totalResults;
}
