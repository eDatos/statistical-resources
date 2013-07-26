package org.siemac.metamac.statistical.resources.web.shared.query;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetQueries {

    @In(1)
    int                            firstResult;

    @In(2)
    int                            maxResults;

    @In(3)
    StatisticalResourceWebCriteria criteria;

    @Out(1)
    List<RelatedResourceDto>       queries;

    @Out(2)
    Integer                        firstResultOut;

    @Out(3)
    Integer                        totalResults;
}