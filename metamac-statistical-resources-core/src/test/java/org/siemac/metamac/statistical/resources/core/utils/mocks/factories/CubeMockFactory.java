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

    protected static Cube getCube01Basic() {
        if (CUBE_01_BASIC == null) {
            CUBE_01_BASIC = createCube();
        }
        return CUBE_01_BASIC;
    }

    protected static Cube getCube02Basic() {
        if (CUBE_02_BASIC == null) {
            CUBE_02_BASIC = createCube();
        }
        return CUBE_02_BASIC;
    }

    protected static Cube getCube03Basic() {
        if (CUBE_03_BASIC == null) {
            CUBE_03_BASIC = createCube();
        }
        return CUBE_03_BASIC;
    }

    private static Cube createCube() {
        return getStatisticalResourcesPersistedDoMocks().mockCube();
    }
}
