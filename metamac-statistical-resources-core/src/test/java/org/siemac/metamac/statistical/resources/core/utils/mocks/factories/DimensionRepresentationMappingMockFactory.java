package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;

@MockProvider
public class DimensionRepresentationMappingMockFactory extends StatisticalResourcesMockFactory<DimensionRepresentationMapping> {

    public static final String                               DIMENSION_REPRESENTATION_MAPPING_01_DATASOURCE_01_NAME = "DIMENSION_REPRESENTATION_MAPPING_01_DATASOURCE_01";

    private static DimensionRepresentationMappingMockFactory instance;

    private DimensionRepresentationMappingMockFactory() {
    }

    public static DimensionRepresentationMappingMockFactory getInstance() {
        if (instance == null) {
            instance = new DimensionRepresentationMappingMockFactory();
        }
        return instance;
    }

    protected DimensionRepresentationMapping getDimensionRepresentationMapping01Datasource01() {
        return createDimensionRepresentationMapping(DATASOURCE_01_BASIC_NAME);
    }

    public static DimensionRepresentationMapping createDimensionRepresentationMapping(String datasourceUrn) {
        return getStatisticalResourcesPersistedDoMocks().mockDimensionRepresentationMapping(datasourceUrn);
    }
}
