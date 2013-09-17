package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_05_FOR_DATASET_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_73_V01_FOR_DATASET_11_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_74_V02_FOR_DATASET_11_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_75_V01_FOR_DATASET_12_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_76_V02_FOR_DATASET_12_NAME;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.DatasetMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.DatasetVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockDescriptor;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

@MockProvider
@SuppressWarnings("unused")
public class DatasetMockFactory extends StatisticalResourcesMockFactory<Dataset> {

    public static final String        DATASET_01_BASIC_NAME                                                                                                                    = "DATASET_01_BASIC";

    public static final String        DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME                                                                                             = "DATASET_02_BASIC_WITH_GENERATED_VERSION";

    public static final String        DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME                                                                                            = "DATASET_03_WITH_2_DATASET_VERSIONS";

    public static final String        DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME                                                                                      = "DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS";

    public static final String        DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                                                                                         = "DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";

    public static final String        DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME                                                                   = "DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";

    public static final String        DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME                                                                 = "DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE";

    public static final String        DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME                                                              = "DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE";

    public static final String        DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE_NAME                                                 = "DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE";

    public static final String        DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME = "DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION";

    public static final String        DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME       = "DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE";

    public static final String        DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME                                                                                  = "DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT";

    public static final String        DATASET_13_WITH_DRAFT_AND_PUBLISHED__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME                                                       = "DATASET_13_WITH_DRAFT_AND_PUBLISHED__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS";

    public static final String        DATASET_14_WITH_PUBLISHED_AND_NOT_VISIBLE_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME                                        = "DATASET_14_WITH_PUBLISHED_AND_NOT_VISIBLE_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS";

    public static final String        DATASET_15_WITH_TWO_PUBLISHED_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME                                                    = "DATASET_15_WITH_TWO_PUBLISHED_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS";

    public static final String        DATASET_16_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME                                                                                          = "DATASET_16_SIMPLE_LINKED_TO_PUB_VERSION_17";
    public static final String        DATASET_17_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME                                                                                          = "DATASET_17_SIMPLE_LINKED_TO_PUB_VERSION_17";

    private static DatasetMockFactory instance                                                                                                                                 = null;

    private DatasetMockFactory() {
    }

    public static DatasetMockFactory getInstance() {
        if (instance == null) {
            instance = new DatasetMockFactory();
        }
        return instance;
    }

    private static Dataset getDataset01Basic() {
        return generateDatasetWithGeneratedVersion();
    }

    private static Dataset getDataset02BasicWithGeneratedVersion() {
        return generateDatasetWithGeneratedVersion();
    }

    private static Dataset getDataset03With2DatasetVersions() {
        DatasetMock dataset = new DatasetMock();
        dataset.setSequentialId(1);
        getStatisticalResourcesPersistedDoMocks().mockDataset(dataset);

        DatasetVersionMock templateV1 = new DatasetVersionMock();
        templateV1.setDataset(dataset);
        templateV1.setVersionLogic(INIT_VERSION);

        // not last version
        templateV1.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(4));
        templateV1.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(3));
        templateV1.getSiemacMetadataStatisticalResource().setLastVersion(false);
        // datasources
        templateV1.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        templateV1.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        templateV1.addDatasource(DatasourceMockFactory.generateSimpleDatasource());

        DatasetVersion datasetVersionV1 = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(templateV1);
        registerDatasetVersionMock(DATASET_VERSION_03_FOR_DATASET_03_NAME, datasetVersionV1);

        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionV1);

        DatasetVersionMock templateV2 = new DatasetVersionMock();
        templateV2.setDataset(dataset);
        templateV2.setVersionLogic(SECOND_VERSION);

        // last version
        templateV2.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
        templateV2.getSiemacMetadataStatisticalResource().setLastVersion(true);

        // datasources
        templateV2.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        DatasetVersion datasetVersionV2 = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(templateV2);
        registerDatasetVersionMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME, datasetVersionV2);

        datasetVersionV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionV1));

        return dataset;
    }

    private static Dataset getDataset04FullFilledWith1DatasetVersions() {
        DatasetVersionMock template = new DatasetVersionMock();
        template.setSequentialId(1);
        template.setVersionLogic(INIT_VERSION);
        template.getSiemacMetadataStatisticalResource().setLastVersion(true);
        template.setStatisticalOperationCode("statOper02");

        template.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem());

        template.getTemporalCoverage().add(StatisticalResourcesDoMocks.mockTemporalCode());

        template.getMeasureCoverage().add(StatisticalResourcesDoMocks.mockConceptExternalItem());
        template.addGeographicGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem());

        template.addTemporalGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem());

        template.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem());
        template.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem());

        template.setDateStart(new DateTime().minusYears(10));

        template.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
        template.setUpdateFrequency(StatisticalResourcesDoMocks.mockCodeExternalItem());

        template.setFormatExtentDimensions(3);
        template.setFormatExtentObservations(1354L);
        template.setDateNextUpdate(new DateTime().plusMonths(1));
        template.setBibliographicCitation(StatisticalResourcesDoMocks.mockInternationalString("es", "biblio"));

        template.getSiemacMetadataStatisticalResource().setCreatedBy(StatisticalResourcesDoMocks.mockString(10));
        template.getSiemacMetadataStatisticalResource().setCreatedDate(StatisticalResourcesDoMocks.mockDateTime());
        template.getSiemacMetadataStatisticalResource().setLastUpdatedBy(StatisticalResourcesDoMocks.mockString(10));
        template.getSiemacMetadataStatisticalResource().setLastUpdated(StatisticalResourcesDoMocks.mockDateTime());

        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
        registerDatasetVersionMock(DATASET_VERSION_05_FOR_DATASET_04_NAME, datasetVersion);

        return datasetVersion.getDataset();
    }

    private static Dataset getDataset05WithMultiplePublishedVersions() {
        DatasetMock dataset = new DatasetMock();
        dataset.setStatisticalOperationCode("oper01");
        dataset.setSequentialId(1);
        getStatisticalResourcesPersistedDoMocks().mockDataset(dataset);

        // v1
        DatasetVersionMock datasetVersionv1 = new DatasetVersionMock();
        datasetVersionv1.setDataset(dataset);
        datasetVersionv1.setVersionLogic(INIT_VERSION);
        // not last version
        datasetVersionv1.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(3));
        datasetVersionv1.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(3));
        datasetVersionv1.getSiemacMetadataStatisticalResource().setLastVersion(false);
        getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionv1);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionv1);
        registerDatasetVersionMock(DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05_NAME, datasetVersionv1);

        // v2
        DatasetVersionMock datasetVersionv2 = new DatasetVersionMock();
        datasetVersionv2.setDataset(dataset);
        datasetVersionv2.setVersionLogic(SECOND_VERSION);
        // not last version
        datasetVersionv2.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(2));
        datasetVersionv2.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(2));
        datasetVersionv2.getSiemacMetadataStatisticalResource().setLastVersion(false);
        getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionv2);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionv2);
        registerDatasetVersionMock(DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05_NAME, datasetVersionv2);

        // v3
        DatasetVersionMock datasetVersionv3 = new DatasetVersionMock();
        datasetVersionv3.setDataset(dataset);
        datasetVersionv3.setVersionLogic(THIRD_VERSION);
        // last version
        datasetVersionv3.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
        datasetVersionv3.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(1));
        datasetVersionv3.getSiemacMetadataStatisticalResource().setLastVersion(true);
        getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionv3);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionv3);
        registerDatasetVersionMock(DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME, datasetVersionv3);

        // Relations
        datasetVersionv2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionv1));
        datasetVersionv3.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionv2));

        return dataset;
    }

    private static Dataset getDataset06WithMultiplePublishedVersionsAndLatestNoVisible() {
        DatasetMock dataset = new DatasetMock();
        dataset.setStatisticalOperationCode("oper01");
        dataset.setSequentialId(1);
        getStatisticalResourcesPersistedDoMocks().mockDataset(dataset);

        // v1
        DatasetVersionMock datasetVersionv1 = new DatasetVersionMock();
        datasetVersionv1.setDataset(dataset);
        datasetVersionv1.setVersionLogic(INIT_VERSION);
        // last version
        datasetVersionv1.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
        datasetVersionv1.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(1));
        datasetVersionv1.getSiemacMetadataStatisticalResource().setLastVersion(false);
        getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionv1);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionv1);
        registerDatasetVersionMock(DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06_NAME, datasetVersionv1);

        // v2
        DatasetVersionMock datasetVersionv2 = new DatasetVersionMock();
        datasetVersionv2.setDataset(dataset);
        datasetVersionv2.setVersionLogic(SECOND_VERSION);
        // last version
        datasetVersionv2.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime());
        datasetVersionv2.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().plusDays(1));
        datasetVersionv2.getSiemacMetadataStatisticalResource().setLastVersion(true);
        getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionv2);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionv2);
        registerDatasetVersionMock(DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06_NAME, datasetVersionv2);

        datasetVersionv2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionv1));

        return dataset;
    }

    private static Dataset getDataset07WithSingleVersionAndSingleDatasourceLinkedToFile() {

        DatasetVersionMock datasetVersionMock = new DatasetVersionMock();
        datasetVersionMock.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionMock);

        return datasetVersion.getDataset();
    }

    private static Dataset getDataset08WithSingleVersionAndMultipleDatasourcesLinkedToFile() {
        DatasetVersionMock datasetVersionMock = new DatasetVersionMock();
        datasetVersionMock.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        datasetVersionMock.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionMock);

        return datasetVersion.getDataset();
    }

    private static Dataset getDataset09WithSingleVersionAndSingleDatasourceLinkedToFileWithUnderscore() {
        Datasource datasource = new Datasource();
        datasource.setFilename("datasource_underscore.px");
        datasource = getStatisticalResourcesPersistedDoMocks().mockDatasource(datasource);

        DatasetVersionMock datasetVersionMock = new DatasetVersionMock();
        datasetVersionMock.addDatasource(datasource);
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionMock);

        return datasetVersion.getDataset();
    }

    private static MockDescriptor getDataset10WithTwoVersionsWithQueriesFirstVersionIsRequiredByTwoQueryVersionsSecondIsRequiredByOneQueryVersion() {
        DatasetVersionMock previousTemplate = new DatasetVersionMock();
        previousTemplate.setVersionLogic(INIT_VERSION);
        previousTemplate.getSiemacMetadataStatisticalResource().setLastVersion(false);
        previousTemplate.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(5));
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(previousTemplate);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersion);

        DatasetVersionMock template = new DatasetVersionMock();
        template.setDataset(datasetVersion.getDataset());
        template.setVersionLogic(SECOND_VERSION);
        template.getSiemacMetadataStatisticalResource().setLastVersion(true);
        template.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(datasetVersion));
        template.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(2));
        DatasetVersion datasetVersionLatestPublished = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionLatestPublished);

        Query query01 = createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q01", datasetVersion, datasetVersionLatestPublished);
        Query query02 = createQueryWithOneVersionDiscontinued("Q02", datasetVersion);
        Query query03 = createQueryWithOneVersionDiscontinued("Q03", datasetVersion);

        return new MockDescriptor(datasetVersion.getDataset(), query01, query02, query03);
    }

    private static MockDescriptor getDataset11WithTwoVersionsWith3QueriesFirstVersionIsPublishedSecondVersionIsNotVisibleAllQueriesCompatible() {
        DateTime publishingTimeV2 = new DateTime().plusDays(2);
        DatasetVersionMock previousTemplate = new DatasetVersionMock();
        previousTemplate.setVersionLogic(INIT_VERSION);
        previousTemplate.getSiemacMetadataStatisticalResource().setLastVersion(false);
        previousTemplate.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(5));
        previousTemplate.getSiemacMetadataStatisticalResource().setValidTo(publishingTimeV2);
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(previousTemplate);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersion);
        registerDatasetVersionMock(DATASET_VERSION_73_V01_FOR_DATASET_11_NAME, datasetVersion);

        DatasetVersionMock template = new DatasetVersionMock();
        template.setDataset(datasetVersion.getDataset());
        template.setVersionLogic(SECOND_VERSION);
        template.getSiemacMetadataStatisticalResource().setLastVersion(true);
        template.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(datasetVersion));
        template.getSiemacMetadataStatisticalResource().setValidFrom(publishingTimeV2);
        DatasetVersion datasetVersionLatestPublished = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionLatestPublished);
        registerDatasetVersionMock(DATASET_VERSION_74_V02_FOR_DATASET_11_NAME, datasetVersionLatestPublished);

        Query query01 = createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q01", datasetVersion, datasetVersionLatestPublished);
        Query query02 = createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q02", datasetVersion, datasetVersionLatestPublished);
        Query query03 = createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q03", datasetVersion, datasetVersionLatestPublished);

        return new MockDescriptor(datasetVersion.getDataset(), query01, query02, query03);
    }

    private static MockDescriptor getDataset12WithTwoVersionsWithQueriesInDraft() {
        DatasetVersionMock previousTemplate = new DatasetVersionMock();
        previousTemplate.setVersionLogic(INIT_VERSION);
        previousTemplate.getSiemacMetadataStatisticalResource().setLastVersion(false);
        previousTemplate.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(5));
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(previousTemplate);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersion);
        registerDatasetVersionMock(DATASET_VERSION_75_V01_FOR_DATASET_12_NAME, datasetVersion);

        DatasetVersionMock template = new DatasetVersionMock();
        template.setDataset(datasetVersion.getDataset());
        template.setVersionLogic(SECOND_VERSION);
        template.getSiemacMetadataStatisticalResource().setLastVersion(true);
        template.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(datasetVersion));
        DatasetVersion datasetVersionLatestVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
        DatasetLifecycleTestUtils.prepareToPublished(datasetVersionLatestVersion);
        registerDatasetVersionMock(DATASET_VERSION_76_V02_FOR_DATASET_12_NAME, datasetVersionLatestVersion);

        // Query 1
        QueryVersionMock queryV1Template = new QueryVersionMock();
        queryV1Template.getLifeCycleStatisticalResource().setCode("Q01");
        queryV1Template.setMaintainerCode("agency01");
        queryV1Template.setStatisticalOperationCode("OPER01");
        queryV1Template.setStatus(QueryStatusEnum.ACTIVE);
        queryV1Template.setDatasetVersion(datasetVersion);
        queryV1Template.setLastVersion(true);
        queryV1Template.setVersionLogic(INIT_VERSION);
        queryV1Template.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        QueryVersion query01 = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(queryV1Template);

        QueryVersion query02 = createQueryWithOneVersionInDraft("Q02", datasetVersionLatestVersion);

        return new MockDescriptor(datasetVersion.getDataset(), query01, query02);
    }

    private static MockDescriptor getDataset13WithDraftAndPublishedIsPartOfPublicationsDifferentStatus() {
        DatasetVersion datasetVersion = mockDatasetVersionPublished(null, INIT_VERSION, new DateTime().minusDays(5), null);

        Dataset dataset = datasetVersion.getDataset();

        DatasetVersionMock template = new DatasetVersionMock();
        template.setDataset(datasetVersion.getDataset());
        template.setVersionLogic(SECOND_VERSION);
        template.getSiemacMetadataStatisticalResource().setLastVersion(true);
        DatasetVersion datasetVersionLatestVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);

        datasetVersionLatestVersion.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(datasetVersion));

        Publication pub01 = createPublicationWithTwoVersionsPublishedBothLinkedToDataset(1, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_46_PUBLISHED_V01_FOR_PUBLICATION_07_NAME, pub01.getVersions().get(0));
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_47_PUBLISHED_V02_FOR_PUBLICATION_07_NAME, pub01.getVersions().get(1));

        Publication pub02 = createPublicationWithTwoVersionsOnePublishedLastNotVisibleBothLinkedToDataset(2, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_48_PUBLISHED_V01_FOR_PUBLICATION_08_NAME, pub02.getVersions().get(0));
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_49_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_08_NAME, pub02.getVersions().get(1));

        Publication pub03 = createPublicationWithSingleVersionDraftLinkedToDataset(3, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_50_DRAFT_V01_FOR_PUBLICATION_09_NAME, pub03.getVersions().get(0));

        return new MockDescriptor(dataset, pub01, pub02, pub03);
    }

    private static MockDescriptor getDataset14WithPublishedAndNotVisibleVersionsIsPartOfPublicationsDifferentStatus() {
        DateTime secondVersionValidFrom = new DateTime().plusDays(1);

        DatasetVersion datasetVersion = mockDatasetVersionPublished(null, INIT_VERSION, new DateTime().minusDays(5), secondVersionValidFrom);

        Dataset dataset = datasetVersion.getDataset();

        DatasetVersion datasetVersionLatestVersion = mockDatasetVersionPublishedLastVersion(datasetVersion.getDataset(), SECOND_VERSION, secondVersionValidFrom);

        datasetVersionLatestVersion.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(datasetVersion));

        Publication pub01 = createPublicationWithTwoVersionsPublishedBothLinkedToDataset(1, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_51_PUBLISHED_V01_FOR_PUBLICATION_10_NAME, pub01.getVersions().get(0));
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_52_PUBLISHED_V02_FOR_PUBLICATION_10_NAME, pub01.getVersions().get(1));

        Publication pub02 = createPublicationWithTwoVersionsOnePublishedLastNotVisibleBothLinkedToDataset(2, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_53_PUBLISHED_V01_FOR_PUBLICATION_11_NAME, pub02.getVersions().get(0));
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_54_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_11_NAME, pub02.getVersions().get(1));

        Publication pub03 = createPublicationWithSingleVersionDraftLinkedToDataset(3, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_55_DRAFT_V01_FOR_PUBLICATION_12_NAME, pub03.getVersions().get(0));

        return new MockDescriptor(dataset, pub01, pub02, pub03);
    }

    private static MockDescriptor getDataset15WithTwoPublishedVersionsIsPartOfPublicationsDifferentStatus() {
        DateTime secondVersionValidFrom = new DateTime().minusDays(1);

        DatasetVersion datasetVersion = mockDatasetVersionPublished(null, INIT_VERSION, new DateTime().minusDays(5), secondVersionValidFrom);
        Dataset dataset = datasetVersion.getDataset();

        DatasetVersion datasetVersionLatestVersion = mockDatasetVersionPublishedLastVersion(datasetVersion.getDataset(), SECOND_VERSION, secondVersionValidFrom);

        datasetVersionLatestVersion.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(datasetVersion));

        Publication pub01 = createPublicationWithTwoVersionsPublishedBothLinkedToDataset(1, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_56_PUBLISHED_V01_FOR_PUBLICATION_13_NAME, pub01.getVersions().get(0));
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_57_PUBLISHED_V02_FOR_PUBLICATION_13_NAME, pub01.getVersions().get(1));

        Publication pub02 = createPublicationWithTwoVersionsOnePublishedLastNotVisibleBothLinkedToDataset(2, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_58_PUBLISHED_V01_FOR_PUBLICATION_14_NAME, pub02.getVersions().get(0));
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_59_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_14_NAME, pub02.getVersions().get(1));

        Publication pub03 = createPublicationWithSingleVersionDraftLinkedToDataset(3, dataset);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_60_DRAFT_V01_FOR_PUBLICATION_15_NAME, pub03.getVersions().get(0));

        return new MockDescriptor(dataset, pub01, pub02, pub03);
    }

    private static Publication createPublicationWithTwoVersionsPublishedBothLinkedToDataset(int sequentialId, Dataset dataset) {
        Publication pub = PublicationMockFactory.buildPublicationWithTwoVersionsPublished(sequentialId);
        linkDatasetInPublicationVersionFirstLevel(dataset, pub.getVersions().get(0));
        linkDatasetInPublicationVersionFirstLevel(dataset, pub.getVersions().get(1));
        return pub;
    }

    private static Publication createPublicationWithTwoVersionsOnePublishedLastNotVisibleBothLinkedToDataset(int sequentialId, Dataset dataset) {
        Publication pub = PublicationMockFactory.buildPublicationWithTwoVersionsOnePublishedLastNotVisible(sequentialId);
        linkDatasetInPublicationVersionFirstLevel(dataset, pub.getVersions().get(0));
        linkDatasetInPublicationVersionFirstLevel(dataset, pub.getVersions().get(1));
        return pub;
    }

    private static Publication createPublicationWithSingleVersionDraftLinkedToDataset(int sequentialId, Dataset dataset) {
        Publication pub = PublicationMockFactory.buildPublicationWithSingleVersionDraft(sequentialId);
        linkDatasetInPublicationVersionFirstLevel(dataset, pub.getVersions().get(0));
        return pub;
    }

    private static void linkDatasetInPublicationVersionFirstLevel(Dataset dataset, PublicationVersion publicationVersion) {
        ElementLevel elementLevelV01 = PublicationVersionMockFactory.createDatasetCubeElementLevel(publicationVersion, dataset);
        elementLevelV01.setOrderInLevel(Long.valueOf(publicationVersion.getChildrenFirstLevel().size() + 1));
    }

    private static DatasetVersion mockDatasetVersionPublished(Dataset dataset, String version, DateTime validFrom, DateTime validTo) {
        return mockDatasetVersionPublished(dataset, version, validFrom, validTo, false);
    }

    private static DatasetVersion mockDatasetVersionPublishedLastVersion(Dataset dataset, String version, DateTime validFrom) {
        return mockDatasetVersionPublished(dataset, version, validFrom, null, true);
    }

    private static DatasetVersion mockDatasetVersionPublished(Dataset dataset, String version, DateTime validFrom, DateTime validTo, boolean isLastVersion) {
        DatasetVersionMock template = new DatasetVersionMock();
        template.setDataset(dataset);
        template.setVersionLogic(version);
        template.getSiemacMetadataStatisticalResource().setLastVersion(isLastVersion);
        template.getSiemacMetadataStatisticalResource().setValidFrom(validFrom);
        template.getSiemacMetadataStatisticalResource().setValidTo(validTo);
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
        DatasetLifecycleTestUtils.prepareToVersioning(datasetVersion);
        return datasetVersion;
    }

    protected static Query createQueryWithOneVersionDiscontinued(String identifier, DatasetVersion datasetVersion) {

        QueryVersionMock queryV1Template = new QueryVersionMock();
        queryV1Template.getLifeCycleStatisticalResource().setCode(identifier);
        queryV1Template.setStatus(QueryStatusEnum.DISCONTINUED);
        queryV1Template.setDatasetVersion(datasetVersion);
        queryV1Template.setLastVersion(true);
        queryV1Template.setVersionLogic(INIT_VERSION);
        queryV1Template.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        queryV1Template.getLifeCycleStatisticalResource().setValidFrom(datasetVersion.getSiemacMetadataStatisticalResource().getValidFrom());
        QueryVersion query02Discontinued = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(queryV1Template);
        return query02Discontinued.getQuery();
    }

    protected static QueryVersion createQueryWithOneVersionInDraft(String identifier, DatasetVersion datasetVersion) {

        QueryVersionMock queryV1Template = new QueryVersionMock();
        queryV1Template.getLifeCycleStatisticalResource().setCode(identifier);
        queryV1Template.setStatus(QueryStatusEnum.ACTIVE);
        queryV1Template.setDatasetVersion(datasetVersion);
        queryV1Template.setLastVersion(true);
        queryV1Template.setVersionLogic(INIT_VERSION);
        queryV1Template.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        QueryVersion query02Discontinued = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(queryV1Template);
        return query02Discontinued;
    }

    protected static Query createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion(String identifier, DatasetVersion datasetVersion, DatasetVersion datasetVersionLatestPublished) {
        DateTime latestQueryVersionPublishingTime = datasetVersionLatestPublished.getSiemacMetadataStatisticalResource().getValidFrom();
        QueryVersionMock queryV1Template = new QueryVersionMock();
        queryV1Template.setStatus(QueryStatusEnum.ACTIVE);
        queryV1Template.setDatasetVersion(datasetVersion);
        queryV1Template.setLastVersion(false);
        queryV1Template.setVersionLogic(INIT_VERSION);
        queryV1Template.getLifeCycleStatisticalResource().setCode(identifier);
        queryV1Template.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        queryV1Template.getLifeCycleStatisticalResource().setValidFrom(datasetVersion.getSiemacMetadataStatisticalResource().getValidFrom());
        queryV1Template.getLifeCycleStatisticalResource().setValidTo(latestQueryVersionPublishingTime);
        QueryVersion query01V1 = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(queryV1Template);

        QueryVersionMock queryV2Template = new QueryVersionMock();
        queryV2Template.setQuery(query01V1.getQuery());
        queryV2Template.setStatus(QueryStatusEnum.ACTIVE);
        queryV2Template.setDatasetVersion(datasetVersionLatestPublished);
        queryV2Template.setLastVersion(true);
        queryV2Template.setVersionLogic(SECOND_VERSION);
        queryV2Template.getLifeCycleStatisticalResource().setCode(identifier);
        queryV2Template.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        queryV2Template.getLifeCycleStatisticalResource().setValidFrom(datasetVersionLatestPublished.getSiemacMetadataStatisticalResource().getValidFrom());
        QueryVersion query01V2 = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(queryV2Template);
        return query01V1.getQuery();
    }

    protected static Dataset generateDatasetWithGeneratedVersion() {
        DatasetMock template = new DatasetMock();
        return getStatisticalResourcesPersistedDoMocks().mockDatasetWithGeneratedDatasetVersion(template);
    }

}
