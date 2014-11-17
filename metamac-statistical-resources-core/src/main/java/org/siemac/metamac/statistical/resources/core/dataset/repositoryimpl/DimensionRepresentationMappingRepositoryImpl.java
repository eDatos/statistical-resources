package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMappingProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for DimensionRepresentationMapping
 */
@Repository("dimensionRepresentationMappingRepository")
public class DimensionRepresentationMappingRepositoryImpl extends DimensionRepresentationMappingRepositoryBase {

    public DimensionRepresentationMappingRepositoryImpl() {
    }

    @Override
    public DimensionRepresentationMapping findByDatasourceFilename(String datasourceFilename) {
        List<ConditionalCriteria> condition = criteriaFor(DimensionRepresentationMapping.class).withProperty(DimensionRepresentationMappingProperties.datasourceFilename()).eq(datasourceFilename)
                .distinctRoot().build();

        List<DimensionRepresentationMapping> result = findByCondition(condition);

        if (result.size() == 0) {
            return null;
        }

        return result.get(0);
    }
}
