package org.siemac.metamac.statistical.resources.web.shared.external;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetStatisticalOperationInstancesPaginatedList {

    @In(1)
    String                operationCode;

    @In(2)
    int                   firstResult;

    @In(3)
    int                   maxResults;

    @In(4)
    MetamacWebCriteria    criteria;

    @Out(1)
    List<ExternalItemDto> operationInstancesList;

    @Out(2)
    Integer               firstResultOut;

    @Out(3)
    Integer               totalResults;

}
