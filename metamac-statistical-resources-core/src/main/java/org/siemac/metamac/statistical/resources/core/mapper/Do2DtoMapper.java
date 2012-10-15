package org.siemac.metamac.statistical.resources.core.mapper;

import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;

public interface Do2DtoMapper {

    public QueryDto queryDoToDto(Query query);

}
