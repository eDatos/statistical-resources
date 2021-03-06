package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasetSculptorCriteria2MetamacCriteriaMapperImpl implements DatasetSculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private DatasetDo2DtoMapper              do2DtoMapper;


    @Override
    public MetamacCriteriaResult<RelatedResourceDto> pageResultToMetamacCriteriaResultDatasetRelatedResourceDto(PagedResult<DatasetVersion> source, Integer pageSize) throws MetamacException {
        MetamacCriteriaResult<RelatedResourceDto> target = new MetamacCriteriaResult<RelatedResourceDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<RelatedResourceDto>());
            for (DatasetVersion item : source.getValues()) {
                target.getResults().add(do2DtoMapper.datasetVersionDoToDatasetRelatedResourceDto(item));
            }
        }
        return target;
    }
}
