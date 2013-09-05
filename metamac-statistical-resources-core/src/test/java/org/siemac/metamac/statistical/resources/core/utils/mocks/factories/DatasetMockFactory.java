package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.DatasetMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.DatasetVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends StatisticalResourcesMockFactory<Dataset> {

    public static final String  DATASET_01_BASIC_NAME                                                                                                                    = "DATASET_01_BASIC";
    private static Dataset      DATASET_01_BASIC;

    public static final String  DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME                                                                                             = "DATASET_02_BASIC_WITH_GENERATED_VERSION";
    private static Dataset      DATASET_02_BASIC_WITH_GENERATED_VERSION;

    public static final String  DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME                                                                                            = "DATASET_03_WITH_2_DATASET_VERSIONS";
    private static Dataset      DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;

    public static final String  DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME                                                                                      = "DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS";
    private static Dataset      DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;

    public static final String  DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                                                                                         = "DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";
    private static Dataset      DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;

    public static final String  DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME                                                                   = "DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";
    private static Dataset      DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;

    public static final String  DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME                                                                 = "DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE";
    private static Dataset      DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE;

    public static final String  DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME                                                              = "DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE";
    private static Dataset      DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE;

    public static final String  DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE_NAME                                                 = "DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE";
    private static Dataset      DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE;

    public static final String  DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME = "DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION";
    private static Dataset      DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION;
    private static List<Object> dataset10Dependencies                                                                                                                    = new ArrayList<Object>();

    public static final String  DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME       = "DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE";
    private static Dataset      DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE;
    private static List<Object> dataset11Dependencies                                                                                                                    = new ArrayList<Object>();

    public static final String  DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME                                                                                  = "DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT";
    private static Dataset      DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT;
    private static List<Object> dataset12Dependencies                                                                                                                    = new ArrayList<Object>();

    protected static Dataset getDataset01Basic() {
        if (DATASET_01_BASIC == null) {
            DATASET_01_BASIC = createDatasetWithGeneratedVersion();
        }
        return DATASET_01_BASIC;
    }

    protected static Dataset getDataset02BasicWithGeneratedVersion() {
        if (DATASET_02_BASIC_WITH_GENERATED_VERSION == null) {
            DATASET_02_BASIC_WITH_GENERATED_VERSION = createDatasetWithGeneratedVersion();
        }
        return DATASET_02_BASIC_WITH_GENERATED_VERSION;
    }

    protected static Dataset getDataset03With2DatasetVersions() {
        if (DATASET_03_BASIC_WITH_2_DATASET_VERSIONS == null) {

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

            datasetVersionV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionV1));

            DATASET_03_BASIC_WITH_2_DATASET_VERSIONS = dataset;
        }
        return DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;
    }

    protected static Dataset getDataset04FullFilledWith1DatasetVersions() {
        if (DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS == null) {
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

            DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS = datasetVersion.getDataset();
        }
        return DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;
    }

    protected static Dataset getDataset05WithMultiplePublishedVersions() {
        if (DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS == null) {

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

            // Relations
            datasetVersionv2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionv1));
            datasetVersionv3.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionv2));

            DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS = dataset;
        }
        return DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;
    }

    protected static Dataset getDataset06WithMultiplePublishedVersionsAndLatestNoVisible() {
        if (DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE == null) {
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

            datasetVersionv2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(datasetVersionv1));

            DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE = dataset;
        }
        return DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;
    }

    protected static Dataset getDataset07WithSingleVersionAndSingleDatasourceLinkedToFile() {
        if (DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE == null) {

            DatasetVersionMock datasetVersionMock = new DatasetVersionMock();
            datasetVersionMock.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
            DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionMock);

            DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE = datasetVersion.getDataset();
        }
        return DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE;
    }

    protected static Dataset getDataset08WithSingleVersionAndMultipleDatasourcesLinkedToFile() {
        if (DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE == null) {

            DatasetVersionMock datasetVersionMock = new DatasetVersionMock();
            datasetVersionMock.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
            datasetVersionMock.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
            DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionMock);

            DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE = datasetVersion.getDataset();
        }
        return DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE;
    }

    protected static Dataset getDataset09WithSingleVersionAndSingleDatasourceLinkedToFileWithUnderscore() {
        if (DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE == null) {

            Datasource datasource = new Datasource();
            datasource.setFilename("datasource_underscore.px");
            datasource = getStatisticalResourcesPersistedDoMocks().mockDatasource(datasource);

            DatasetVersionMock datasetVersionMock = new DatasetVersionMock();
            datasetVersionMock.addDatasource(datasource);
            DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(datasetVersionMock);

            DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE = datasetVersion.getDataset();
        }
        return DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE;
    }

    protected static Dataset getDataset10WithTwoVersionsWithQueriesFirstVersionIsRequiredByTwoQueryVersionsSecondIsRequiredByOneQueryVersion() {
        if (DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION == null) {
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

            dataset10Dependencies.addAll(createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q01", datasetVersion, datasetVersionLatestPublished));

            dataset10Dependencies.add(createQueryWithOneVersionDiscontinued("Q02", datasetVersion));

            dataset10Dependencies.add(createQueryWithOneVersionDiscontinued("Q03", datasetVersion));

            DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION = datasetVersion.getDataset();
        }
        registerDependencies(dataset10Dependencies);
        return DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION;
    }

    protected static Dataset getDataset11WithTwoVersionsWith3QueriesFirstVersionIsPublishedSecondVersionIsNotVisibleAllQueriesCompatible() {
        if (DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE == null) {
            DateTime publishingTimeV2 = new DateTime().plusDays(2);
            DatasetVersionMock previousTemplate = new DatasetVersionMock();
            previousTemplate.setVersionLogic(INIT_VERSION);
            previousTemplate.getSiemacMetadataStatisticalResource().setLastVersion(false);
            previousTemplate.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(5));
            previousTemplate.getSiemacMetadataStatisticalResource().setValidTo(publishingTimeV2);
            DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(previousTemplate);
            DatasetLifecycleTestUtils.prepareToVersioning(datasetVersion);

            DatasetVersionMock template = new DatasetVersionMock();
            template.setDataset(datasetVersion.getDataset());
            template.setVersionLogic(SECOND_VERSION);
            template.getSiemacMetadataStatisticalResource().setLastVersion(true);
            template.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(datasetVersion));
            template.getSiemacMetadataStatisticalResource().setValidFrom(publishingTimeV2);
            DatasetVersion datasetVersionLatestPublished = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
            DatasetLifecycleTestUtils.prepareToVersioning(datasetVersionLatestPublished);

            dataset11Dependencies.addAll(createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q01", datasetVersion, datasetVersionLatestPublished));

            dataset11Dependencies.addAll(createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q02", datasetVersion, datasetVersionLatestPublished));

            dataset11Dependencies.addAll(createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion("Q03", datasetVersion, datasetVersionLatestPublished));

            DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE = datasetVersion.getDataset();
        }
        registerDependencies(dataset11Dependencies);
        return DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE;
    }
    
    
    protected static Dataset getDataset12WithTwoVersionsWithQueriesInDraft() {
        if (DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT == null) {
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
            DatasetVersion datasetVersionLatestVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
            DatasetLifecycleTestUtils.prepareToPublished(datasetVersionLatestVersion);

            //Query 1
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
            
            dataset12Dependencies.add(query01);
            
            dataset12Dependencies.add(createQueryWithOneVersionInDraft("Q02", datasetVersionLatestVersion));
            
            DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT = datasetVersion.getDataset();
        }
        registerDependencies(dataset12Dependencies);
        return DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT;
    }

    protected static QueryVersion createQueryWithOneVersionDiscontinued(String identifier, DatasetVersion datasetVersion) {

        QueryVersionMock queryV1Template = new QueryVersionMock();
        queryV1Template.getLifeCycleStatisticalResource().setCode(identifier);
        queryV1Template.setStatus(QueryStatusEnum.DISCONTINUED);
        queryV1Template.setDatasetVersion(datasetVersion);
        queryV1Template.setLastVersion(true);
        queryV1Template.setVersionLogic(INIT_VERSION);
        queryV1Template.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        queryV1Template.getLifeCycleStatisticalResource().setValidFrom(datasetVersion.getSiemacMetadataStatisticalResource().getValidFrom());
        QueryVersion query02Discontinued = getStatisticalResourcesPersistedDoMocks().mockQueryVersion(queryV1Template);
        return query02Discontinued;
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

    protected static List<QueryVersion> createQueryWithTwoVersionsLinkedEachOneToADifferentDatasetVersion(String identifier, DatasetVersion datasetVersion, DatasetVersion datasetVersionLatestPublished) {
        List<QueryVersion> mocks = new ArrayList<QueryVersion>();

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
        mocks.add(query01V1);

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
        mocks.add(query01V2);
        return mocks;
    }

    private static Dataset createDatasetWithGeneratedVersion() {
        DatasetMock template = new DatasetMock();
        return getStatisticalResourcesPersistedDoMocks().mockDatasetWithGeneratedDatasetVersion(template);
    }

}
