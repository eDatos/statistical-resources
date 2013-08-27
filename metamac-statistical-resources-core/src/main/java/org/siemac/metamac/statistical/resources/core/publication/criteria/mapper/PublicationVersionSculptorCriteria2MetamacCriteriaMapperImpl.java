package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDo2DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationVersionSculptorCriteria2MetamacCriteriaMapperImpl implements PublicationVersionSculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private PublicationDo2DtoMapper              do2DtoMapper;


    @Override
    public MetamacCriteriaResult<PublicationVersionBaseDto> pageResultToMetamacCriteriaResultPublicationVersion(PagedResult<PublicationVersion> source, Integer pageSize) throws MetamacException {
        MetamacCriteriaResult<PublicationVersionBaseDto> target = new MetamacCriteriaResult<PublicationVersionBaseDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<PublicationVersionBaseDto>());
            for (PublicationVersion item : source.getValues()) {
                target.getResults().add(do2DtoMapper.publicationVersionDoToBaseDto(item));
            }
        }
        return target;
    }
}
