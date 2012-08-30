package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetPaginatedList {

    @In(1)
    int              firstResult;

    @In(2)
    int              maxResults;

    @In(3)
    String           dataset;

    @Out(1)
    List<DatasetDto> datasetList;

    @Out(2)
    Integer          pageNumber;

    @Out(3)
    Integer          totalResults;

}