package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.QueryLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
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

            DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();

            QueryVersion queryV01 = mockQuery03V01(query, datasetVersion);
            QueryVersion queryV02 = mockQuery03V02(query, datasetVersion);

            queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV01));

            QUERY_03_BASIC_WITH_2_QUERY_VERSIONS = query;
        }
        return QUERY_03_BASIC_WITH_2_QUERY_VERSIONS;
    }

    private static QueryVersion mockQuery03V01(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildSimpleMock(query, datasetVersion, INIT_VERSION);

        // not last version
        mock.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(2));
        mock.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(2));
        mock.getLifeCycleStatisticalResource().setLastVersion(Boolean.FALSE);

        QueryVersion queryVersion = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
        QueryLifecycleTestUtils.prepareToVersioning(queryVersion);
        return queryVersion;
    }

    private static QueryVersion mockQuery03V02(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildSimpleMock(query, datasetVersion, SECOND_VERSION);

        // Is last version
        mock.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(1));
        mock.getLifeCycleStatisticalResource().setLastVersion(Boolean.TRUE);

        return getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
    }

    protected static Query getQuery04With1QueryVersions() {
        if (QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS == null) {
            Query query = createQueryWithoutGeneratedVersion();

            mockQuery04V01(query, getStatisticalResourcesPersistedDoMocks().mockDatasetVersion());

            QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS = query;
        }
        return QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS;
    }

    private static QueryVersion mockQuery04V01(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildSimpleMock(query, datasetVersion, INIT_VERSION);
        mock.getLifeCycleStatisticalResource().setCreatedBy(StatisticalResourcesDoMocks.mockString(10));
        mock.getLifeCycleStatisticalResource().setCreatedDate(StatisticalResourcesDoMocks.mockDateTime());
        mock.getLifeCycleStatisticalResource().setLastUpdatedBy(StatisticalResourcesDoMocks.mockString(10));
        mock.getLifeCycleStatisticalResource().setLastUpdated(StatisticalResourcesDoMocks.mockDateTime());
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
    }

    protected static Query getQuery05WithMultiplePublishedVersions() {
        if (QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS == null) {
            Query query = createQueryWithoutGeneratedVersion();

            DatasetVersion dataset = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();

            QueryVersion queryV01 = mockQuery05V01Published(query, dataset);
            QueryVersion queryV02 = mockQuery05V02Published(query, dataset);
            QueryVersion queryV03 = mockQuery05V03Published(query, dataset);

            queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV01));
            queryV03.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV02));

            QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS = query;
        }
        return QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;
    }

    private static QueryVersion mockQuery05V01Published(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildPublishedQueryVersionMock(query, datasetVersion, INIT_VERSION, new DateTime().minusDays(3));
        mock.getLifeCycleStatisticalResource().setLastVersion(false);

        QueryVersion queryVersion = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
        QueryLifecycleTestUtils.prepareToVersioning(queryVersion);
        return queryVersion;
    }

    private static QueryVersion mockQuery05V02Published(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildPublishedQueryVersionMock(query, datasetVersion, SECOND_VERSION, new DateTime().minusDays(2));
        mock.getLifeCycleStatisticalResource().setLastVersion(false);

        QueryVersion queryVersion = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
        QueryLifecycleTestUtils.prepareToVersioning(queryVersion);
        return queryVersion;
    }

    private static QueryVersion mockQuery05V03Published(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildPublishedQueryVersionMock(query, datasetVersion, THIRD_VERSION, new DateTime().minusDays(1));
        mock.getLifeCycleStatisticalResource().setLastVersion(false);

        QueryVersion queryVersion = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
        QueryLifecycleTestUtils.prepareToVersioning(queryVersion);
        return queryVersion;
    }

    protected static Query getQuery06WithMultiplePublishedVersionsAndLatestNoVisible() {
        if (QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE == null) {
            Query query = createQueryWithoutGeneratedVersion();

            DatasetVersion dataset = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();

            QueryVersion queryV01 = mockQuery06V01(query, dataset);

            QueryVersion queryV02 = mockQuery06V02(query, dataset);

            queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToQueryVersion(queryV01));

            QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE = query;
        }
        return QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;
    }

    private static QueryVersion mockQuery06V01(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildPublishedQueryVersionMock(query, datasetVersion, INIT_VERSION, new DateTime().minusDays(1));
        // last version
        mock.getLifeCycleStatisticalResource().setLastUpdated(new DateTime().minusHours(22));
        mock.getLifeCycleStatisticalResource().setLastVersion(false);

        QueryVersion queryVersion = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
        QueryLifecycleTestUtils.prepareToVersioning(queryVersion);
        return queryVersion;
    }

    private static QueryVersion mockQuery06V02(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildPublishedQueryVersionMock(query, datasetVersion, SECOND_VERSION, new DateTime().plusDays(1));
        // last version
        mock.getLifeCycleStatisticalResource().setLastUpdated(new DateTime().minusHours(1));
        mock.getLifeCycleStatisticalResource().setLastVersion(true);

        QueryVersion queryVersion = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
        QueryLifecycleTestUtils.prepareToVersioning(queryVersion);
        return queryVersion;
    }

    protected static Query getQuery07SimpleMultiVersion() {
        if (QUERY_07_SIMPLE_MULTI_VERSION == null) {
            Query query = createQueryWithoutGeneratedVersion();

            DatasetVersion datasetVersion = DatasetVersionMockFactory.getDatasetVersion56DraftWithDatasourceAndQueries();

            QueryVersion queryV01 = mockQuery07V01(query, datasetVersion);
            QueryVersion queryV02 = mockQuery07V02(query, datasetVersion);

            queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV01));

            QUERY_07_SIMPLE_MULTI_VERSION = query;
        }
        return QUERY_07_SIMPLE_MULTI_VERSION;
    }

    private static QueryVersion mockQuery07V01(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildSimpleMock(query, datasetVersion, INIT_VERSION);
        mock.setStatus(QueryStatusEnum.ACTIVE);
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
    }

    private static QueryVersion mockQuery07V02(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildSimpleMock(query, datasetVersion, SECOND_VERSION);
        mock.setStatus(QueryStatusEnum.ACTIVE);
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
    }

    public static Query generateQueryWithGeneratedVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedQueryVersion();
    }

    private static Query createQueryWithGeneratedVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedQueryVersion();
    }

    private static Query createQueryWithoutGeneratedVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQuery(new QueryMock());
    }

    private static QueryVersionMock buildSimpleMock(Query query, DatasetVersion datasetVersion, String versionLogic) {
        QueryVersionMock mock = new QueryVersionMock();
        mock.setQuery(query);
        mock.setDatasetVersion(datasetVersion);
        mock.setVersionLogic(versionLogic);
        return mock;
    }

    private static QueryVersionMock buildPublishedQueryVersionMock(Query query, DatasetVersion datasetVersion, String versionLogic, DateTime validFrom) {
        QueryVersionMock mock = new QueryVersionMock();
        mock.setQuery(query);
        mock.setDatasetVersion(datasetVersion);
        mock.setVersionLogic(versionLogic);
        if (validFrom.isAfterNow()) {
            mock.getLifeCycleStatisticalResource().setCreationDate(new DateTime());
        } else {
            mock.getLifeCycleStatisticalResource().setCreationDate(validFrom.minusHours(1));
        }
        mock.getLifeCycleStatisticalResource().setValidFrom(validFrom);
        return mock;
    }
}