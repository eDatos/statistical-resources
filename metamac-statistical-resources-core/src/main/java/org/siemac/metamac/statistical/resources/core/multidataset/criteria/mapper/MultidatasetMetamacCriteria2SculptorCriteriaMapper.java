package org.siemac.metamac.statistical.resources.core.multidataset.criteria.mapper;

import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public interface MultidatasetMetamacCriteria2SculptorCriteriaMapper {

    public MetamacCriteria2SculptorCriteria<MultidatasetVersion> getMultidatasetCriteriaMapper();

}
