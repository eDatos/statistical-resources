package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockDescriptor;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockFactory;
import org.siemac.metamac.statistical.resources.core.common.domain.Translation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public abstract class StatisticalResourcesMockFactory<EntityMock> extends MockFactory<EntityMock> {

    public static final String OPERATION_01_CODE = "C00025A";
    public static final String OPERATION_02_CODE = "C00025B";
    public static final String OPERATION_03_CODE = "C00025C";

    public static final String INIT_VERSION      = "001.000";
    public static final String SECOND_VERSION    = "002.000";
    public static final String THIRD_VERSION     = "003.000";

    protected static StatisticalResourcesPersistedDoMocks getStatisticalResourcesPersistedDoMocks() {
        return StatisticalResourcesPersistedDoMocks.getInstance();
    }

    protected static Dataset getDatasetMock(String id) {
        return DatasetMockFactory.getInstance().getMock(id);
    }

    protected static Query getQueryMock(String id) {
        return QueryMockFactory.getInstance().getMock(id);
    }

    protected static MockDescriptor getDatasetMockDescriptor(String id) {
        return DatasetMockFactory.getInstance().getMockWithDependencies(id);
    }

    protected static MockDescriptor getPublicationMockDescriptor(String id) {
        return PublicationMockFactory.getInstance().getMockWithDependencies(id);
    }

    protected static MockDescriptor getQueryMockDescriptor(String id) {
        return QueryMockFactory.getInstance().getMockWithDependencies(id);
    }

    protected static DatasetVersion getDatasetVersionMock(String id) {
        return DatasetVersionMockFactory.getInstance().getMock(id);
    }

    protected static QueryVersion getQueryVersionMock(String id) {
        return QueryVersionMockFactory.getInstance().getMock(id);
    }

    protected static Datasource getDatasourceMock(String id) {
        return DatasourceMockFactory.getInstance().getMock(id);
    }

    protected static PublicationVersion getPublicationVersionMock(String id) {
        return PublicationVersionMockFactory.getInstance().getMock(id);
    }

    protected static void registerDatasetMock(String id, Dataset dataset) {
        DatasetMockFactory.getInstance().registerMock(id, dataset);
    }

    protected static void registerChapterMock(String id, Chapter chapter) {
        ChapterMockFactory.getInstance().registerMock(id, chapter);
    }

    protected static void registerCubeMock(String id, Cube cube) {
        CubeMockFactory.getInstance().registerMock(id, cube);
    }

    protected static void registerTranslationMock(String id, Translation translation) {
        TranslationMockFactory.getInstance().registerMock(id, translation);
    }

    protected static void registerQueryMock(String id, Query query) {
        QueryMockFactory.getInstance().registerMock(id, query);
    }

    protected static void registerDatasetVersionMock(String id, DatasetVersion datasetVersion) {
        DatasetVersionMockFactory.getInstance().registerMock(id, datasetVersion);
    }

    protected static void registerQueryVersionMock(String id, QueryVersion queryVersion) {
        QueryVersionMockFactory.getInstance().registerMock(id, queryVersion);
    }

    protected static void registerPublicationVersionMock(String id, PublicationVersion publicationVersion) {
        PublicationVersionMockFactory.getInstance().registerMock(id, publicationVersion);
    }

    protected static List<Object> buildObjectList(Object... objs) {
        return new ArrayList<Object>(Arrays.asList(objs));
    }

}
