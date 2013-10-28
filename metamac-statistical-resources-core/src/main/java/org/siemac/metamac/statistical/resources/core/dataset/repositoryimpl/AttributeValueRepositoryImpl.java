package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for AttributeValue
 */
@Repository("attributeValueRepository")
public class AttributeValueRepositoryImpl extends AttributeValueRepositoryBase {

    public AttributeValueRepositoryImpl() {
    }

    @Override
    public List<AttributeValue> findValuesForDatasetVersionByAttributeId(long datasetVersionId, String attributeId) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("attributeId", attributeId);
        parameters.put("datasetVersionId", datasetVersionId);

        //@formatter:off
        return findByQuery(
                "from AttributeValue value " + 
                "where value.dsdComponentId = :attributeId " + 
                "and value.datasetVersion.id = :datasetVersionId", parameters);
        //@formatter:on

    }
}
