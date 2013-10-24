package org.siemac.metamac.statistical.resources.core.query.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QueryDto2DoMapper extends BaseDto2DoMapper {

    public QueryVersion queryVersionDtoToDo(QueryVersionDto queryVersionDto) throws MetamacException;

    public void checkOptimisticLocking(QueryVersionBaseDto queryVersionDto) throws MetamacException;

}
