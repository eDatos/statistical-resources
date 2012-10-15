package org.siemac.metamac.statistical.resources.core.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SculptorCriteria2MetamacCriteriaMapperImpl implements SculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private Do2DtoMapper              do2DtoMapper;


    @Override
    public MetamacCriteriaResult<QueryDto> pageResultToMetamacCriteriaResultQuery(PagedResult<Query> source, Integer pageSize) {
        MetamacCriteriaResult<QueryDto> target = new MetamacCriteriaResult<QueryDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<QueryDto>());
            for (Query item : source.getValues()) {
                target.getResults().add(do2DtoMapper.queryDoToDto(item));
            }
        }
        return target;
    }
}
