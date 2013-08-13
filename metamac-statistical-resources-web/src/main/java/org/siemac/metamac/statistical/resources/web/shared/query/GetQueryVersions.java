package org.siemac.metamac.statistical.resources.web.shared.query;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.QueryVersionWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetQueryVersions {

    @In(1)
    int                     firstResult;

    @In(2)
    int                     maxResults;

    @In(3)
    QueryVersionWebCriteria criteria;

    @Out(1)
    List<QueryVersionDto>   queriesList;

    @Out(2)
    Integer                 pageNumber;

    @Out(3)
    Integer                 totalResults;
}