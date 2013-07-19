package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

public interface DatasetVersionSculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<DatasetVersionDto> pageResultToMetamacCriteriaResultDatasetVersion(PagedResult<DatasetVersion> source, Integer pageSize) throws MetamacException;

}
