package org.siemac.metamac.statistical.resources.core.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;

public interface SculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<QueryDto> pageResultToMetamacCriteriaResultQuery(PagedResult<Query> source, Integer pageSize);

}
