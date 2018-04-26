package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;

@SuppressWarnings("unused")
@MockProvider
public class MultidatasetCubeMockFactory extends StatisticalResourcesMockFactory<MultidatasetCube> {

    public static final String                 MULTIDATASET_CUBE_01_BASIC_NAME                            = "MULTIDATASET_CUBE_01_BASIC";

    public static final String                 MULTIDATASET_CUBE_02_BASIC_NAME                            = "MULTIDATASET_CUBE_02_BASIC";

    public static final String                 MULTIDATASET_CUBE_03_BASIC_NAME                            = "MULTIDATASET_CUBE_03_BASIC";

    public static final String                 MULTIDATASET_CUBE_04_DATASET_NAME                          = "MULTIDATASET_CUBE_04_DATASET";

    public static final String                 MULTIDATASET_CUBE_05_QUERY_NAME                            = "MULTIDATASET_CUBE_05_QUERY";

    public static final String                 MULTIDATASET_CUBE_06_EMPTY_IN_MULTIDATASET_VERSION_90_NAME = "MULTIDATASET_CUBE_06_EMPTY_IN_MULTIDATASET_VERSION_90";

    private static MultidatasetCubeMockFactory instance                                                   = null;

    private MultidatasetCubeMockFactory() {
    }

    public static MultidatasetCubeMockFactory getInstance() {
        if (instance == null) {
            instance = new MultidatasetCubeMockFactory();
        }
        return instance;
    }

    private static MultidatasetCube getMultidatasetCube01Basic() {
        return createQueryCube();
    }

    private static MultidatasetCube getMultidatasetCube02Basic() {
        return createQueryCube();
    }

    private static MultidatasetCube getMultidatasetCube03Basic() {
        return createQueryCube();
    }

    private static MultidatasetCube getMultidatasetCube04Dataset() {
        return createDatasetCube();
    }

    private static MultidatasetCube getMultidatasetCube05Query() {
        return createQueryCube();
    }

    // private static MultidatasetCube getMultidatasetCube06EmptyInMultidatasetVersion90() {
    // return createQueryCube();
    // }

    private static MultidatasetCube createQueryCube() {
        return getStatisticalResourcesPersistedDoMocks().mockMultidatasetCube(getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedQueryVersion());
    }

    private static MultidatasetCube createDatasetCube() {
        return getStatisticalResourcesPersistedDoMocks().mockMultidatasetCube(getStatisticalResourcesPersistedDoMocks().mockDatasetWithGeneratedDatasetVersion());
    }

}
