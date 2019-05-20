package org.siemac.metamac.statistical.resources.core.multidataset.criteria.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.multidataset.mapper.MultidatasetDo2DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultidatasetSculptorCriteria2MetamacCriteriaMapperImpl implements MultidatasetSculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private MultidatasetDo2DtoMapper do2DtoMapper;

    @Override
    public MetamacCriteriaResult<RelatedResourceDto> pageResultToMetamacCriteriaResultMultidatasetRelatedResourceDto(PagedResult<MultidatasetVersion> source, Integer pageSize)
            throws MetamacException {
        MetamacCriteriaResult<RelatedResourceDto> target = new MetamacCriteriaResult<>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<RelatedResourceDto>());
            for (MultidatasetVersion item : source.getValues()) {
                target.getResults().add(do2DtoMapper.multidatasetVersionDoToMultidatasetRelatedResourceDto(item));
            }
        }
        return target;
    }
}
