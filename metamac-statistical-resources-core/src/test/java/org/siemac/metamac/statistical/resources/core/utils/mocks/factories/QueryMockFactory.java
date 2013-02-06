package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends StatisticalResourcesMockFactory<Query> {

    public static final String QUERY_01_BASIC_NAME                = "QUERY_01_BASIC";
    private static Query       QUERY_01_BASIC;

    public static final String QUERY_02_BASIC_ORDERED_01_NAME     = "QUERY_02_BASIC_ORDERED_01";
    private static Query       QUERY_02_BASIC_ORDERED_01;

    public static final String QUERY_03_BASIC_ORDERED_02_NAME     = "QUERY_03_BASIC_ORDERED_02";
    private static Query       QUERY_03_BASIC_ORDERED_02;

    public static final String QUERY_04_BASIC_ORDERED_03_NAME     = "QUERY_04_BASIC_ORDERED_03";
    private static Query       QUERY_04_BASIC_ORDERED_03;

    public static final String QUERY_05_WITH_DATASET_VERSION_NAME = "QUERY_05_WITH_DATASET_VERSION";
    private static Query       QUERY_05_WITH_DATASET_VERSION;

    protected static Query getQuery01Basic() {
        if (QUERY_01_BASIC == null) {
            QUERY_01_BASIC = createQuery();
        }
        return QUERY_01_BASIC;
    }

    protected static Query getQuery02BasicOrdered01() {
        if (QUERY_02_BASIC_ORDERED_01 == null) {
            QUERY_02_BASIC_ORDERED_01 = createQuery();
            QUERY_02_BASIC_ORDERED_01.getLifeCycleStatisticalResource().setCode("a");
        }
        return QUERY_02_BASIC_ORDERED_01;
    }

    protected static Query getQuery03BasicOrdered02() {
        if (QUERY_03_BASIC_ORDERED_02 == null) {
            QUERY_03_BASIC_ORDERED_02 = createQuery();
            QUERY_03_BASIC_ORDERED_02.getLifeCycleStatisticalResource().setCode("b");
        }
        return QUERY_03_BASIC_ORDERED_02;
    }

    protected static Query getQuery04BasicOrdered03() {
        if (QUERY_04_BASIC_ORDERED_03 == null) {
            QUERY_04_BASIC_ORDERED_03 = createQuery();
            QUERY_04_BASIC_ORDERED_03.getLifeCycleStatisticalResource().setCode("c");
        }
        return QUERY_04_BASIC_ORDERED_03;
    }

    protected static Query getQuery05WithDatasetVersion() {
        if (QUERY_05_WITH_DATASET_VERSION == null) {
            QUERY_05_WITH_DATASET_VERSION = createQueryWithDatasetVersion();
        }
        return QUERY_05_WITH_DATASET_VERSION;
    }

    private static Query createQuery() {
        return getStatisticalResourcesPersistedDoMocks().mockQuery();
    }

    private static Query createQueryWithDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithDatasetVersion();
    }


}