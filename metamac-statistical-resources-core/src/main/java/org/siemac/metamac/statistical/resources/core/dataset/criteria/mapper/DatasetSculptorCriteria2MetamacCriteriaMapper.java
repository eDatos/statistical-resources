package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;

public interface DatasetSculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<RelatedResourceDto> pageResultToMetamacCriteriaResultDatasetRelatedResourceDto(PagedResult<DatasetVersion> source, Integer pageSize) throws MetamacException;

}
