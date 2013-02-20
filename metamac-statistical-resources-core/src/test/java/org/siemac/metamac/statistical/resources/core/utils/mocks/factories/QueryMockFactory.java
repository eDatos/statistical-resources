package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends StatisticalResourcesMockFactory<Query> {

    public static final String QUERY_01_WITH_SELECTION_NAME       = "QUERY_01_WITH_SELECTION";
    private static Query       QUERY_01_WITH_SELECTION;

    public static final String QUERY_02_BASIC_ORDERED_01_NAME     = "QUERY_02_BASIC_ORDERED_01";
    private static Query       QUERY_02_BASIC_ORDERED_01;

    public static final String QUERY_03_BASIC_ORDERED_02_NAME     = "QUERY_03_BASIC_ORDERED_02";
    private static Query       QUERY_03_BASIC_ORDERED_02;

    public static final String QUERY_04_BASIC_ORDERED_03_NAME     = "QUERY_04_BASIC_ORDERED_03";
    private static Query       QUERY_04_BASIC_ORDERED_03;

    public static final String QUERY_05_BASIC_NAME                = "QUERY_05_BASIC";
    private static Query       QUERY_05_BASIC;

    public static final String QUERY_06_BASIC_ACTIVE_NAME         = "QUERY_06_BASIC_ACTIVE";
    private static Query       QUERY_06_BASIC_ACTIVE;

    public static final String QUERY_07_BASIC_ACTIVE_NAME         = "QUERY_07_BASIC_ACTIVE";
    private static Query       QUERY_07_BASIC_ACTIVE;

    public static final String QUERY_08_BASIC_DISCONTINUED_NAME   = "QUERY_08_BASIC_DISCONTINUED";
    private static Query       QUERY_08_BASIC_DISCONTINUED;

    public static final String QUERY_09_BASIC_PENDING_REVIEW_NAME = "QUERY_09_BASIC_PENDING_REVIEW";
    private static Query       QUERY_09_BASIC_PENDING_REVIEW;

    public static final String QUERY_10_ACTIVE_LATEST_DATA_5_NAME = "QUERY_10_ACTIVE_LATEST_DATA_5";
    private static Query       QUERY_10_ACTIVE_LATEST_DATA_5;

    protected static Query getQuery01WithSelection() {
        if (QUERY_01_WITH_SELECTION == null) {
            QUERY_01_WITH_SELECTION = createQueryWithSelectionAndGeneratedDatasetVersion();
        }
        return QUERY_01_WITH_SELECTION;
    }

    protected static Query getQuery02BasicOrdered01() {
        if (QUERY_02_BASIC_ORDERED_01 == null) {
            QUERY_02_BASIC_ORDERED_01 = createQueryWithGeneratedDatasetVersion();
            QUERY_02_BASIC_ORDERED_01.getLifeCycleStatisticalResource().setCode("a");
        }
        return QUERY_02_BASIC_ORDERED_01;
    }

    protected static Query getQuery03BasicOrdered02() {
        if (QUERY_03_BASIC_ORDERED_02 == null) {
            QUERY_03_BASIC_ORDERED_02 = createQueryWithGeneratedDatasetVersion();
            QUERY_03_BASIC_ORDERED_02.getLifeCycleStatisticalResource().setCode("b");
        }
        return QUERY_03_BASIC_ORDERED_02;
    }

    protected static Query getQuery04BasicOrdered03() {
        if (QUERY_04_BASIC_ORDERED_03 == null) {
            QUERY_04_BASIC_ORDERED_03 = createQueryWithGeneratedDatasetVersion();
            QUERY_04_BASIC_ORDERED_03.getLifeCycleStatisticalResource().setCode("c");
        }
        return QUERY_04_BASIC_ORDERED_03;
    }

    protected static Query getQuery05Basic() {
        if (QUERY_05_BASIC == null) {
            QUERY_05_BASIC = createQueryWithGeneratedDatasetVersion();
        }
        return QUERY_05_BASIC;
    }

    protected static Query getQuery06BasicActive() {
        if (QUERY_06_BASIC_ACTIVE == null) {
            QUERY_06_BASIC_ACTIVE = createQueryWithGeneratedDatasetVersion();
            QUERY_06_BASIC_ACTIVE.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_06_BASIC_ACTIVE;
    }

    protected static Query getQuery07BasicActive() {
        if (QUERY_07_BASIC_ACTIVE == null) {
            QUERY_07_BASIC_ACTIVE = createQueryWithGeneratedDatasetVersion();
            QUERY_07_BASIC_ACTIVE.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_07_BASIC_ACTIVE;
    }

    protected static Query getQuery08BasicDiscontinued() {
        if (QUERY_08_BASIC_DISCONTINUED == null) {
            QUERY_08_BASIC_DISCONTINUED = createQueryWithGeneratedDatasetVersion();
            QUERY_08_BASIC_DISCONTINUED.setStatus(QueryStatusEnum.DISCONTINUED);
        }
        return QUERY_08_BASIC_DISCONTINUED;
    }

    protected static Query getQuery09BasicPendingReview() {
        if (QUERY_09_BASIC_PENDING_REVIEW == null) {
            QUERY_09_BASIC_PENDING_REVIEW = createQueryWithGeneratedDatasetVersion();
            QUERY_09_BASIC_PENDING_REVIEW.setStatus(QueryStatusEnum.PENDING_REVIEW);
        }
        return QUERY_09_BASIC_PENDING_REVIEW;
    }

    public static Query getQuery10ActiveLatestData5() {
        if (QUERY_10_ACTIVE_LATEST_DATA_5 == null) {
            QUERY_10_ACTIVE_LATEST_DATA_5 = createQueryWithGeneratedDatasetVersion();
            QUERY_10_ACTIVE_LATEST_DATA_5.setType(QueryTypeEnum.LATEST_DATA);
            QUERY_10_ACTIVE_LATEST_DATA_5.setLatestDataNumber(Integer.valueOf(5));
        }
        return QUERY_10_ACTIVE_LATEST_DATA_5;
    }

    private static Query createQueryWithGeneratedDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedDatasetVersion();
    }

    private static Query createQueryWithSelectionAndGeneratedDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithSelectionAndGeneratedDatasetVersion();
    }
}