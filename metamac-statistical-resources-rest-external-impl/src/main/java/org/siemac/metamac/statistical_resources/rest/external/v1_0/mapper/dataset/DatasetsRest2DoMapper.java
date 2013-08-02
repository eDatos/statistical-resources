package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface DatasetsRest2DoMapper {

    public RestCriteria2SculptorCriteria<DatasetVersion> getDatasetCriteriaMapper();
}
