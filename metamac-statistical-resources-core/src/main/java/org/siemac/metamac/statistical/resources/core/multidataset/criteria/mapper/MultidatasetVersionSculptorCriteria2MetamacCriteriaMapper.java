package org.siemac.metamac.statistical.resources.core.multidataset.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public interface MultidatasetVersionSculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<MultidatasetVersionBaseDto> pageResultToMetamacCriteriaResultMultidatasetVersion(PagedResult<MultidatasetVersion> source, Integer pageSize) throws MetamacException;

}
