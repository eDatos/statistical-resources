package org.siemac.metamac.statistical.resources.core.query.mapper;

import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

@org.springframework.stereotype.Component("queryDo2DtoMapper")
public class QueryDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements QueryDo2DtoMapper {


    @Override
    public QueryDto queryDoToDto(Query source) {
        if (source == null) {
            return null;
        }
        QueryDto target = new QueryDto();
        queryDoToDto(source, target);
        return target;
    }

    private QueryDto queryDoToDto(Query source, QueryDto target) {
        if (source == null) {
            return null;
        }

        nameableStatisticalResourceDoToDto(source.getNameableStatisticalResource(), target);

        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());

        return target;
    }
}
