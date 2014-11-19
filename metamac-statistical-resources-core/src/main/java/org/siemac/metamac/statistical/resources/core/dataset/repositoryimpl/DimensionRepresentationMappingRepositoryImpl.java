package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
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
    public DimensionRepresentationMapping findByDatasetAndDatasourceFilename(String datasetVersionUrn, String datasourceFilename) {
        List<ConditionalCriteria> condition = criteriaFor(DimensionRepresentationMapping.class).withProperty(DimensionRepresentationMappingProperties.datasourceFilename()).eq(datasourceFilename)
                .and().withProperty(DimensionRepresentationMappingProperties.datasetVersion().siemacMetadataStatisticalResource().urn()).eq(datasetVersionUrn).distinctRoot().build();

        List<DimensionRepresentationMapping> result = findByCondition(condition);

        if (result.size() == 0) {
            return null;
        }

        return result.get(0);
    }

    @Override
    public List<DimensionRepresentationMapping> findByDatasetAndDatasourceFilenames(String datasetVersionUrn, List<String> datasourceFilenames) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(DimensionRepresentationMapping.class)
                .withProperty(DimensionRepresentationMappingProperties.datasetVersion().siemacMetadataStatisticalResource().urn()).eq(datasetVersionUrn).and()
                .withProperty(DimensionRepresentationMappingProperties.datasourceFilename()).in(datasourceFilenames).distinctRoot().build();

        return findByCondition(condition);
    }
}
