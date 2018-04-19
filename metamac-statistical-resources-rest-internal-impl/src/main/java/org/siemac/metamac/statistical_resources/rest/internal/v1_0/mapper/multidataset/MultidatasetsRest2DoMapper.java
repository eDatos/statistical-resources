package org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.multidataset;

import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public interface MultidatasetsRest2DoMapper {

    public RestCriteria2SculptorCriteria<MultidatasetVersion> getMultidatasetCriteriaMapper();
}
