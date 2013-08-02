package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection;

import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface CollectionsRest2DoMapper {

    public RestCriteria2SculptorCriteria<PublicationVersion> getCollectionCriteriaMapper();
}
