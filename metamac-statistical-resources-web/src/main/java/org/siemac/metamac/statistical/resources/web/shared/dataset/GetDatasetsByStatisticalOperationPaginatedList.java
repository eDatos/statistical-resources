package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetsByStatisticalOperationPaginatedList {

    @In(1)
    String           operationUrn;

    @In(2)
    int              firstResult;

    @In(3)
    int              maxResults;

    @Out(1)
    List<DatasetDto> datasetsList;

    @Out(2)
    Integer          pageNumber;

    @Out(3)
    Integer          totalResults;

    @Out(4)
    ExternalItemDto  operationDto;

}