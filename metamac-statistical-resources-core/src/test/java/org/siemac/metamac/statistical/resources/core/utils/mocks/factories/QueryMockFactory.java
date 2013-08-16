package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion21ForQuery03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion22ForQuery03AndLastVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion23ForQuery04;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion24V1PublishedForQuery05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion25V2PublishedForQuery05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion26V3PublishedForQuery05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion27V1PublishedForQuery06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion28V2PublishedNoVisibleForQuery06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion29SimpleForQuery07Dataset56;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.getQueryVersion30SimpleForQuery07Dataset56;

import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends StatisticalResourcesMockFactory<Query> {

    public static final String QUERY_01_SIMPLE_NAME                                                 = "QUERY_01_SIMPLE";
    private static Query       QUERY_01_SIMPLE;

    public static final String QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME                           = "QUERY_02_BASIC_WITH_GENERATED_VERSION";
    private static Query       QUERY_02_BASIC_WITH_GENERATED_VERSION;

    public static final String QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME                            = "QUERY_03_WITH_2_QUERY_VERSIONS";
    private static Query       QUERY_03_BASIC_WITH_2_QUERY_VERSIONS;

    public static final String QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS_NAME                      = "QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS_NAME";
    private static Query       QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS;

    public static final String QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                       = "QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";
    private static Query       QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;

    public static final String QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME = "QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";
    private static Query       QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;

    public static final String QUERY_07_SIMPLE_MULTI_VERSION_NAME                                   = "QUERY_07_SIMPLE_MULTI_VERSION";
    private static Query       QUERY_07_SIMPLE_MULTI_VERSION;

    protected static Query getQuery01Simple() {
        if (QUERY_01_SIMPLE == null) {
            QUERY_01_SIMPLE = createQueryWithGeneratedVersion();
        }
        return QUERY_01_SIMPLE;
    }

    protected static Query getQuery02BasicWithGeneratedVersion() {
        if (QUERY_02_BASIC_WITH_GENERATED_VERSION == null) {
            Query query = createQueryWithGeneratedVersion();
            query.getVersions().get(0).getLifeCycleStatisticalResource().setLastVersion(Boolean.TRUE);
            QUERY_02_BASIC_WITH_GENERATED_VERSION = query;
        }
        return QUERY_02_BASIC_WITH_GENERATED_VERSION;
    }

    protected static Query getQuery03With2QueryVersions() {
        if (QUERY_03_BASIC_WITH_2_QUERY_VERSIONS == null) {
            Query query = createQueryWithoutGeneratedVersion();
            QUERY_03_BASIC_WITH_2_QUERY_VERSIONS = query;
            setQuery03Versions(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS);
        }
        return QUERY_03_BASIC_WITH_2_QUERY_VERSIONS;
    }

    protected static void setQuery03Versions(Query query) {
        QueryVersion dsV1 = getQueryVersion21ForQuery03();
        QueryVersion dsV2 = getQueryVersion22ForQuery03AndLastVersion();

        query.addVersion(dsV1);
        dsV1.setQuery(query);

        query.addVersion(dsV2);
        dsV2.setQuery(query);

        dsV2.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToQueryVersion(dsV1));
    }

    protected static Query getQuery04With1QueryVersions() {
        if (QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS == null) {
            Query query = createQueryWithoutGeneratedVersion();
            // Query version relation is set in version factory
            QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS = query;
            setQuery04Versions(query);
        }
        return QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS;
    }

    private static void setQuery04Versions(Query query) {
        QueryVersion dsV1 = getQueryVersion23ForQuery04();

        query.addVersion(dsV1);
        dsV1.setQuery(query);
    }

    protected static Query getQuery05WithMultiplePublishedVersions() {
        if (QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS == null) {
            Query query = createQueryWithoutGeneratedVersion();
            QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS = query;
            setQuery05Versions(QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS);
        }
        return QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;
    }

    private static void setQuery05Versions(Query query) {
        QueryVersion dsV1 = getQueryVersion24V1PublishedForQuery05();
        QueryVersion dsV2 = getQueryVersion25V2PublishedForQuery05();
        QueryVersion dsV3 = getQueryVersion26V3PublishedForQuery05();

        query.addVersion(dsV1);
        dsV1.setQuery(query);

        query.addVersion(dsV2);
        dsV2.setQuery(query);

        query.addVersion(dsV3);
        dsV3.setQuery(query);

        dsV2.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToQueryVersion(dsV1));
        dsV3.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToQueryVersion(dsV2));
    }

    protected static Query getQuery06WithMultiplePublishedVersionsAndLatestNoVisible() {
        if (QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE == null) {
            Query query = createQueryWithoutGeneratedVersion();
            QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE = query;
            setQuery06Versions(QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE);
        }
        return QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;
    }

    private static void setQuery06Versions(Query query) {
        QueryVersion dsV1 = getQueryVersion27V1PublishedForQuery06();
        QueryVersion dsV2 = getQueryVersion28V2PublishedNoVisibleForQuery06();

        query.addVersion(dsV1);
        dsV1.setQuery(query);

        query.addVersion(dsV2);
        dsV2.setQuery(query);

        dsV2.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToQueryVersion(dsV1));
    }

    protected static Query getQuery07MultiVersion() {
        if (QUERY_07_SIMPLE_MULTI_VERSION == null) {
            Query query = createQueryWithoutGeneratedVersion();
            QUERY_07_SIMPLE_MULTI_VERSION = query;
            setQuery07Versions(QUERY_07_SIMPLE_MULTI_VERSION);
        }
        return QUERY_07_SIMPLE_MULTI_VERSION;
    }

    protected static void setQuery07Versions(Query query) {
        QueryVersion dsV1 = getQueryVersion29SimpleForQuery07Dataset56();
        QueryVersion dsV2 = getQueryVersion30SimpleForQuery07Dataset56();

        query.addVersion(dsV1);
        dsV1.setQuery(query);

        query.addVersion(dsV2);
        dsV2.setQuery(query);

        dsV2.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToQueryVersion(dsV1));
    }

    private static Query createQueryWithGeneratedVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedQueryVersions();
    }

    private static Query createQueryWithoutGeneratedVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithoutGeneratedQueryVersions();
    }
}