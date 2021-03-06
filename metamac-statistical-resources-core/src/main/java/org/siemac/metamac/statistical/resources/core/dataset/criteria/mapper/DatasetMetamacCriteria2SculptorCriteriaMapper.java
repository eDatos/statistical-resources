package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;


public interface DatasetMetamacCriteria2SculptorCriteriaMapper {
    
    public MetamacCriteria2SculptorCriteria<DatasetVersion> getDatasetCriteriaMapper();
}
