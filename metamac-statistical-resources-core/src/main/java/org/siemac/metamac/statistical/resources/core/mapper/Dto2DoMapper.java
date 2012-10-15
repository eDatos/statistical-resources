package org.siemac.metamac.statistical.resources.core.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;

public interface Dto2DoMapper {

    public Query queryDtoToDo(QueryDto queryDto) throws MetamacException;

}
