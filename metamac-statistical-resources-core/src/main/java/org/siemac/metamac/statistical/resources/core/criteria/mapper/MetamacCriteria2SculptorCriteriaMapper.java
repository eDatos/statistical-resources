package org.siemac.metamac.statistical.resources.core.criteria.mapper;

import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public interface MetamacCriteria2SculptorCriteriaMapper {

    public MetamacCriteria2SculptorCriteria<Query> getQueryCriteriaMapper();

}
