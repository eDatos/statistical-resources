package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.springframework.stereotype.Component;

@Component
public class CubeMockFactory extends StatisticalResourcesMockFactory<Cube> {

    public static final String CUBE_01_BASIC_NAME = "CUBE_01_BASIC";
    private static Cube        CUBE_01_BASIC;

    public static final String CUBE_02_BASIC_NAME = "CUBE_02_BASIC";
    private static Cube        CUBE_02_BASIC;

    public static final String CUBE_03_BASIC_NAME = "CUBE_03_BASIC";
    private static Cube        CUBE_03_BASIC;
    
    public static final String CUBE_04_DATASET_NAME = "CUBE_04_DATASET";
    private static Cube CUBE_04_DATASET;
    
    public static final String CUBE_05_QUERY_NAME = "CUBE_05_QUERY";
    private static Cube CUBE_05_QUERY;
    
    public static final String CUBE_06_DATASET_WITH_PARENT_NAME = "CUBE_06_DATASET_WITH_PARENT";
    private static Cube CUBE_06_DATASET_WITH_PARENT;
    
    public static final String CUBE_07_QUERY_WITH_PARENT_NAME = "CUBE_07_QUERY_WITH_PARENT";
    private static Cube CUBE_07_QUERY_WITH_PARENT;

    protected static Cube getCube01Basic() {
        if (CUBE_01_BASIC == null) {
            CUBE_01_BASIC = createQueryCube();
        }
        return CUBE_01_BASIC;
    }

    protected static Cube getCube02Basic() {
        if (CUBE_02_BASIC == null) {
            CUBE_02_BASIC = createQueryCube();
        }
        return CUBE_02_BASIC;
    }

    protected static Cube getCube03Basic() {
        if (CUBE_03_BASIC == null) {
            CUBE_03_BASIC = createQueryCube();
        }
        return CUBE_03_BASIC;
    }
    
    
    protected static Cube getCube04Dataset() {
        if (CUBE_04_DATASET == null) {
            CUBE_04_DATASET = createDatasetCube();
        }
        return CUBE_04_DATASET;
    }
    
    
    protected static Cube getCube05Query() {
        if (CUBE_05_QUERY == null) {
            CUBE_05_QUERY = createQueryCube();
        }
        return CUBE_05_QUERY;
    }
    
    
    protected static Cube getCube06DatasetWithParent() {
        if (CUBE_06_DATASET_WITH_PARENT == null) {
            CUBE_06_DATASET_WITH_PARENT = createDatasetCubeWithParent();
        }
        return CUBE_06_DATASET_WITH_PARENT;
    }
    
    protected static Cube getCube07QueryWithParent() {
        if (CUBE_07_QUERY_WITH_PARENT == null) {
            CUBE_07_QUERY_WITH_PARENT = createQueryCubeWithParent();
        }
        return CUBE_07_QUERY_WITH_PARENT;
    }

    private static Cube createQueryCube() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryCube(getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedQueryVersions());
    }
    
    private static Cube createDatasetCube() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetCube(getStatisticalResourcesPersistedDoMocks().mockDatasetWithGeneratedDatasetVersions());
    }
    
    private static Cube createDatasetCubeWithParent() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetCubeWithParent(getStatisticalResourcesPersistedDoMocks().mockDatasetWithGeneratedDatasetVersions());
    }
    
    private static Cube createQueryCubeWithParent() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryCubeWithParent(getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedQueryVersions());
    }
}
