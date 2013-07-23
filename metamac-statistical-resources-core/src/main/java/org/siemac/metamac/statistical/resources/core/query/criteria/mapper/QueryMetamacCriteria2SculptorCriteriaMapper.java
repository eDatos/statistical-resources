package org.siemac.metamac.statistical.resources.core.query.criteria.mapper;

import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;


public interface QueryMetamacCriteria2SculptorCriteriaMapper {
    
    public MetamacCriteria2SculptorCriteria<QueryVersion> getQueryCriteriaMapper();
}
