package org.siemac.metamac.statistical.resources.core.query.criteria.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDo2DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuerySculptorCriteria2MetamacCriteriaMapperImpl implements QuerySculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private QueryDo2DtoMapper              do2DtoMapper;


    @Override
    public MetamacCriteriaResult<QueryDto> pageResultToMetamacCriteriaResultQuery(PagedResult<QueryVersion> source, Integer pageSize) throws MetamacException {
        MetamacCriteriaResult<QueryDto> target = new MetamacCriteriaResult<QueryDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<QueryDto>());
            for (QueryVersion item : source.getValues()) {
                target.getResults().add(do2DtoMapper.queryVersionDoToDto(item));
            }
        }
        return target;
    }
}
