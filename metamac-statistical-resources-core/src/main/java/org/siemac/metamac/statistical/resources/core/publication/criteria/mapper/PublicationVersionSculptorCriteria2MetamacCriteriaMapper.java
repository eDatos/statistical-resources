package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface PublicationVersionSculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<PublicationDto> pageResultToMetamacCriteriaResultPublicationVersion(PagedResult<PublicationVersion> source, Integer pageSize) throws MetamacException;

}
