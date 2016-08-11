package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface PublicationSculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<RelatedResourceDto> pageResultToMetamacCriteriaResultPublicationRelatedResourceDto(PagedResult<PublicationVersion> source, Integer pageSize) throws MetamacException;

}
