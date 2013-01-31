package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.ArrayList;
import java.util.List;

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
    
    @Override
    public List<QueryDto> queryDoListToDtoList(List<Query> sources) {
        List<QueryDto> targets = new ArrayList<QueryDto>();
        for (Query source : sources) {
            targets.add(queryDoToDto(source));
        }
        return targets;
    }

    private QueryDto queryDoToDto(Query source, QueryDto target) {
        if (source == null) {
            return null;
        }

        lifeCycleStatisticalResourceDoToDto(source.getLifeCycleStatisticalResource(), target);

        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());

        return target;
    }

}
