package org.siemac.metamac.statistical.resources.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public interface SculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<QueryDto> pageResultToMetamacCriteriaResultQuery(PagedResult<Query> source, Integer pageSize);

}
