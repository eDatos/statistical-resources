package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.generateDatasetVersionInStatusWithGeneratedDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_21_FOR_QUERY_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06_NAME;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.QueryLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

@MockProvider
@SuppressWarnings("unused")
public class QueryMockFactory extends StatisticalResourcesMockFactory<Query> {

    public static final String      QUERY_01_SIMPLE_NAME                                                 = "QUERY_01_SIMPLE";

    public static final String      QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME                           = "QUERY_02_BASIC_WITH_GENERATED_VERSION";

    public static final String      QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME                            = "QUERY_03_WITH_2_QUERY_VERSIONS";

    public static final String      QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS_NAME                      = "QUERY_04_FULL_FILLED_WITH_1_QUERY_VERSIONS_NAME";

    public static final String      QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                       = "QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";

    public static final String      QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME = "QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";

    public static final String      QUERY_07_SIMPLE_MULTI_VERSION_NAME                                   = "QUERY_07_SIMPLE_MULTI_VERSION";

    public static final String      QUERY_08_SINGLE_VERSION_USED_IN_MULTIPLE_PUBLICATIONS_NAME           = "QUERY_08_SINGLE_VERSION_USED_IN_MULTIPLE_PUBLICATIONS";

    public static final String      QUERY_09_SINGLE_VERSION_USED_IN_PUB_VERSION_17_NAME                  = "QUERY_09_SINGLE_VERSION_USED_IN_PUB_17";

    private static QueryMockFactory instance                                                             = null;

    private QueryMockFactory() {
    }

    public static QueryMockFactory getInstance() {
        if (instance == null) {
            instance = new QueryMockFactory();
        }
        return instance;
    }

    private static Query getQuery01Simple() {
        return createQueryWithGeneratedVersion();
    }

    private static Query getQuery02BasicWithGeneratedVersion() {
        Query query = createQueryWithGeneratedVersion();
        query.getVersions().get(0).getLifeCycleStatisticalResource().setLastVersion(Boolean.TRUE);
        return query;
    }

    private static Query getQuery03With2QueryVersions() {
        Query query = createQueryWithoutGeneratedVersion();

        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();

        QueryVersion queryV01 = mockQuery03V01(query, datasetVersion);
        registerQueryVersionMock(QUERY_VERSION_21_FOR_QUERY_03_NAME, queryV01);

        QueryVersion queryV02 = mockQuery03V02(query, datasetVersion);
        registerQueryVersionMock(QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME, queryV02);

        queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV01));
        return query;
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

    private static Query getQuery04With1QueryVersions() {
        Query query = createQueryWithoutGeneratedVersion();
        QueryVersion queryVersion = mockQuery04V01(query, getStatisticalResourcesPersistedDoMocks().mockDatasetVersion());
        return query;
    }

    private static QueryVersion mockQuery04V01(Query query, DatasetVersion datasetVersion) {
        QueryVersionMock mock = buildSimpleMock(query, datasetVersion, INIT_VERSION);
        mock.getLifeCycleStatisticalResource().setCreatedBy(StatisticalResourcesDoMocks.mockString(10));
        mock.getLifeCycleStatisticalResource().setCreatedDate(StatisticalResourcesDoMocks.mockDateTime());
        mock.getLifeCycleStatisticalResource().setLastUpdatedBy(StatisticalResourcesDoMocks.mockString(10));
        mock.getLifeCycleStatisticalResource().setLastUpdated(StatisticalResourcesDoMocks.mockDateTime());
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
    }

    private static Query getQuery05WithMultiplePublishedVersions() {
        Query query = createQueryWithoutGeneratedVersion();

        DatasetVersion dataset = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();

        QueryVersion queryV01 = mockQuery05V01Published(query, dataset);
        QueryVersion queryV02 = mockQuery05V02Published(query, dataset);
        QueryVersion queryV03 = mockQuery05V03Published(query, dataset);
        registerQueryVersionMock(QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05_NAME, queryV03);

        queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV01));
        queryV03.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV02));

        return query;
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

    private static Query getQuery06WithMultiplePublishedVersionsAndLatestNoVisible() {
        Query query = createQueryWithoutGeneratedVersion();

        DatasetVersion dataset = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();

        QueryVersion queryV01 = mockQuery06V01(query, dataset);
        registerQueryVersionMock(QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06_NAME, queryV01);

        QueryVersion queryV02 = mockQuery06V02(query, dataset);
        registerQueryVersionMock(QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06_NAME, queryV02);

        queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToQueryVersion(queryV01));

        return query;
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

    private static Query getQuery07SimpleMultiVersion() {
        Query query = createQueryWithoutGeneratedVersion();

        DatasetVersion datasetVersion = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DRAFT);
        registerDatasetVersionMock(DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME, datasetVersion);

        QueryVersion queryV01 = mockQueryVersionSimple(query, datasetVersion, INIT_VERSION);
        QueryVersion queryV02 = mockQueryVersionSimple(query, datasetVersion, SECOND_VERSION);

        queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV01));

        return query;
    }

    private static QueryVersion mockQueryVersionSimple(Query query, DatasetVersion datasetVersion, String version) {
        QueryVersionMock mock = buildSimpleMock(query, datasetVersion, version);
        mock.setStatus(QueryStatusEnum.ACTIVE);
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersion(mock);
    }

    private static Query getQuery08SingleVersionUsedInMultiplePublications() {
        Query query = createQueryWithoutGeneratedVersion();

        DatasetVersion datasetVersion = DatasetMockFactory.generateDatasetWithGeneratedVersion().getVersions().get(0);

        QueryVersion queryV01 = mockQueryVersionSimple(query, datasetVersion, INIT_VERSION);
        QueryVersion queryV02 = mockQueryVersionSimple(query, datasetVersion, SECOND_VERSION);

        queryV02.getLifeCycleStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockQueryVersionRelated(queryV01));

        return query;
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