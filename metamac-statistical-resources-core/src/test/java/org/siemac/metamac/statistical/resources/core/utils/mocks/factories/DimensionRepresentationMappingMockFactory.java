package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;

@MockProvider
public class DimensionRepresentationMappingMockFactory extends StatisticalResourcesMockFactory<DimensionRepresentationMapping> {

    public static final String                               DIMENSION_REPRESENTATION_MAPPING_01_DATASET_01_NAME = "DIMENSION_REPRESENTATION_MAPPING_01_DATASET_01";

    private static DimensionRepresentationMappingMockFactory instance;

    private DimensionRepresentationMappingMockFactory() {
    }

    public static DimensionRepresentationMappingMockFactory getInstance() {
        if (instance == null) {
            instance = new DimensionRepresentationMappingMockFactory();
        }
        return instance;
    }

    protected DimensionRepresentationMapping getDimensionRepresentationMapping01Dataset01() {
        return createDimensionRepresentationMapping();
    }

    public static DimensionRepresentationMapping createDimensionRepresentationMapping() {
        Dataset dataset = getDatasetMock(DatasetMockFactory.DATASET_01_BASIC_NAME);
        return getStatisticalResourcesPersistedDoMocks().mockDimensionRepresentationMapping(dataset);
    }
}
