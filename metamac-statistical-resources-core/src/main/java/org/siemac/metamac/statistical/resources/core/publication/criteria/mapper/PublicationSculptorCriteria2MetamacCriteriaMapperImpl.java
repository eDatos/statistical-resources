package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDo2DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationSculptorCriteria2MetamacCriteriaMapperImpl implements PublicationSculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private PublicationDo2DtoMapper              do2DtoMapper;


    @Override
    public MetamacCriteriaResult<RelatedResourceDto> pageResultToMetamacCriteriaResultPublicationRelatedResourceDto(PagedResult<PublicationVersion> source, Integer pageSize) throws MetamacException {
        MetamacCriteriaResult<RelatedResourceDto> target = new MetamacCriteriaResult<RelatedResourceDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<RelatedResourceDto>());
            for (PublicationVersion item : source.getValues()) {
                target.getResults().add(do2DtoMapper.publicationVersionDoToPublicationRelatedResourceDto(item));
            }
        }
        return target;
    }
}
