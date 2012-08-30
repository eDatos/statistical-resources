package org.siemac.metamac.statistical.resources.web.shared.operation;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;


@GenDispatch(isSecure=false)
public class GetOperationPaginatedList {

    @In(1)
    int firstResult;
    
    @In(2)
    int maxResult;
    
    @In(3)
    String operation;
    
    @Out(1)
    List<ExternalItemDto> operationsList;

    @Out(2)
    Integer          pageNumber;

    @Out(3)
    Integer          totalResults;
}
