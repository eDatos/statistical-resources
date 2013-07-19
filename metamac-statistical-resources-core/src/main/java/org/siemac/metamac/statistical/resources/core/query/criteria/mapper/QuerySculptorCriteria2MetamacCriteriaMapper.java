package org.siemac.metamac.statistical.resources.core.query.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QuerySculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<QueryVersionDto> pageResultToMetamacCriteriaResultQuery(PagedResult<QueryVersion> source, Integer pageSize) throws MetamacException;

}
