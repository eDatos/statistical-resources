package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion03ForDataset03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion04ForDataset03AndLastVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion05ForDataset04;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion22V1PublishedForDataset05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion23V2PublishedForDataset05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion24V3PublishedForDataset05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion25V1PublishedForDataset06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion26V2PublishedNoVisibleForDataset06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion31WithSingleDatasourceLinkedToFile;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion32WithMultipleDatasourcesLinkedToFile;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion33WithSingleDatasourceLinkedToFileWithUnderscore;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends StatisticalResourcesMockFactory<Dataset> {

    public static final String DATASET_01_BASIC_NAME                                                    = "DATASET_01_BASIC";
    private static Dataset     DATASET_01_BASIC;

    public static final String DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME                             = "DATASET_02_BASIC_WITH_GENERATED_VERSION";
    private static Dataset     DATASET_02_BASIC_WITH_GENERATED_VERSION;

    public static final String DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME                            = "DATASET_03_WITH_2_DATASET_VERSIONS";
    private static Dataset     DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;

    public static final String DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME                      = "DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS";
    private static Dataset     DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;

    public static final String DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                         = "DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";
    private static Dataset     DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;

    public static final String DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME   = "DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";
    private static Dataset     DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;

    public static final String DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_NAME = "DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE";
    private static Dataset     DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE;
    
    public static final String DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME = "DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE";
    private static Dataset     DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE;
    
    public static final String DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE_NAME = "DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE";
    private static Dataset     DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE;
    
    protected static Dataset getDataset01Basic() {
        if (DATASET_01_BASIC == null) {
            DATASET_01_BASIC = createDataset();
        }
        return DATASET_01_BASIC;
    }

    protected static Dataset getDataset02BasicWithGeneratedVersion() {
        if (DATASET_02_BASIC_WITH_GENERATED_VERSION == null) {
            DATASET_02_BASIC_WITH_GENERATED_VERSION = createDataset();
        }
        return DATASET_02_BASIC_WITH_GENERATED_VERSION;
    }

    protected static Dataset getDataset03With2DatasetVersions() {
        if (DATASET_03_BASIC_WITH_2_DATASET_VERSIONS == null) {
            Dataset dataset = createDataset();
            DATASET_03_BASIC_WITH_2_DATASET_VERSIONS = dataset;
            setDataset03Versions(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);
        }
        return DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;
    }

    protected static void setDataset03Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion03ForDataset03();
        DatasetVersion dsV2 = getDatasetVersion04ForDataset03AndLastVersion();

        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);

        dataset.addVersion(dsV2);
        dsV2.setDataset(dataset);

        dsV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(dsV1));
    }

    protected static Dataset getDataset04FullFilledWith1DatasetVersions() {
        if (DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS == null) {
            Dataset dataset = createDataset();
            // Dataset version relation is set in version factory
            DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS = dataset;
            setDataset04Versions(dataset);
        }
        return DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;
    }

    private static void setDataset04Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion05ForDataset04();
        dsV1.getSiemacMetadataStatisticalResource().setLastVersion(Boolean.TRUE);

        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);
    }

    protected static Dataset getDataset05WithMultiplePublishedVersions() {
        if (DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS == null) {
            Dataset dataset = createDataset();
            DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS = dataset;
            setDataset05Versions(DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS);
        }
        return DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;
    }

    private static void setDataset05Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion22V1PublishedForDataset05();
        DatasetVersion dsV2 = getDatasetVersion23V2PublishedForDataset05();
        DatasetVersion dsV3 = getDatasetVersion24V3PublishedForDataset05();

        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);

        dataset.addVersion(dsV2);
        dsV2.setDataset(dataset);

        dataset.addVersion(dsV3);
        dsV3.setDataset(dataset);

        dsV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(dsV1));
        dsV3.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(dsV2));
    }

    protected static Dataset getDataset06WithMultiplePublishedVersionsAndLatestNoVisible() {
        if (DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE == null) {
            Dataset dataset = createDataset();
            DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE = dataset;
            setDataset06Versions(DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE);
        }
        return DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;
    }

    private static void setDataset06Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion25V1PublishedForDataset06();
        DatasetVersion dsV2 = getDatasetVersion26V2PublishedNoVisibleForDataset06();

        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);

        dataset.addVersion(dsV2);
        dsV2.setDataset(dataset);

        dsV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToDatasetVersion(dsV1));
    }

    protected static Dataset getDataset07WithSingleVersionAndSingleDatasourceLinkedToFile() {
        if (DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE == null) {
            Dataset dataset = createDataset();
            DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE = dataset;
            setDataset07Versions(DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE);
        }
        return DATASET_07_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE;
    }

    private static void setDataset07Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion31WithSingleDatasourceLinkedToFile();

        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);
    }
    
    protected static Dataset getDataset08WithSingleVersionAndMultipleDatasourcesLinkedToFile() {
            Dataset dataset = createDataset();
            if (DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE == null) {
            DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE = dataset;
            setDataset08Versions(DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE);
        }
        return DATASET_08_WITH_SINGLE_VERSION_AND_MULTIPLE_DATASOURCES_LINKED_TO_FILE;
    }
    
    protected static Dataset getDataset09WithSingleVersionAndSingleDatasourceLinkedToFileWithUnderscore() {
        if (DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE == null) {
            Dataset dataset = createDataset();
            DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE = dataset;
            setDataset09Versions(DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE);
        }
        return DATASET_09_WITH_SINGLE_VERSION_AND_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE;
    }

    private static void setDataset09Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion33WithSingleDatasourceLinkedToFileWithUnderscore();

        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);
    }
    
    private static void setDataset08Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion32WithMultipleDatasourcesLinkedToFile();
        
        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);
    }

    private static Dataset createDataset() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetWithoutGeneratedDatasetVersions();
    }

}
