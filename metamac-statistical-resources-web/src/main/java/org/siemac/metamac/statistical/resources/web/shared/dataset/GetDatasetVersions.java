package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetVersions {

    @In(1)
    int                         firstResult;

    @In(2)
    int                         maxResults;

    @In(3)
    DatasetVersionWebCriteria   criteria;

    @Out(1)
    List<DatasetVersionBaseDto> datasetVersionBaseDtos;

    @Out(2)
    Integer                     firstResultOut;

    @Out(3)
    Integer                     totalResults;
}