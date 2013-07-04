package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QueryDo2DtoMapper {

    public QueryDto queryVersionDoToDto(QueryVersion source)  throws MetamacException;
    public List<QueryDto> queryVersionDoListToDtoList(List<QueryVersion> sources) throws MetamacException;

}
