package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public interface QueryDo2DtoMapper {

    public QueryDto queryDoToDto(Query query);
    public List<QueryDto> queryDoListToDtoList(List<Query> sources);

}
