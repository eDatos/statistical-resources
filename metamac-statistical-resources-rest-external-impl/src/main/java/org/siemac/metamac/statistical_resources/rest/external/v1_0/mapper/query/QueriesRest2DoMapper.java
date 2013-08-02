package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query;

import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QueriesRest2DoMapper {

    public RestCriteria2SculptorCriteria<QueryVersion> getQueryCriteriaMapper();
}
