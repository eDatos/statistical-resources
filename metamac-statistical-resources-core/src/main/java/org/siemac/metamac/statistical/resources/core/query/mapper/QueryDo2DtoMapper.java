package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QueryDo2DtoMapper {

    public QueryVersionDto queryVersionDoToDto(QueryVersion source)  throws MetamacException;
    public List<QueryVersionDto> queryVersionDoListToDtoList(List<QueryVersion> sources) throws MetamacException;

}
