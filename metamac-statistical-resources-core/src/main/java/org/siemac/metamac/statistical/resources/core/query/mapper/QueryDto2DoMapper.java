package org.siemac.metamac.statistical.resources.core.query.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QueryDto2DoMapper {

    public QueryVersion queryVersionDtoToDo(QueryDto queryDto) throws MetamacException;

}
