package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion03ForDataset03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion04ForDataset03AndLastVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion05ForDataset04;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion22V1PublishedForDataset05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion23V2PublishedForDataset05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion24V3PublishedForDataset05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion25V1PublishedForDataset06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.getDatasetVersion26V2PublishedNoVisibleForDataset06;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends StatisticalResourcesMockFactory<Dataset> {

    public static final String DATASET_01_BASIC_NAME                                                  = "DATASET_01_BASIC";
    private static Dataset     DATASET_01_BASIC;

    public static final String DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME                           = "DATASET_02_BASIC_WITH_GENERATED_VERSION";
    private static Dataset     DATASET_02_BASIC_WITH_GENERATED_VERSION;

    public static final String DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME                          = "DATASET_03_WITH_2_DATASET_VERSIONS";
    private static Dataset     DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;

    public static final String DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME                    = "DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME";
    private static Dataset     DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;

    public static final String DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                       = "DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";
    private static Dataset     DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;

    public static final String DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME = "DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";
    private static Dataset     DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;

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

    protected static Dataset getDataset04With1DatasetVersions() {
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
    
    
    private static Dataset createDataset() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetWithoutGeneratedDatasetVersions();
    }

}
