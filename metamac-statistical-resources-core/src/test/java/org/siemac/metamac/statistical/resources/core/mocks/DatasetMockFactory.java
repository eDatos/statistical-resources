package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion03ForDataset03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion04ForDataset03AndLastVersion;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion05ForDataset04;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends StatisticalResourcesMockFactory<Dataset> {

    public static final String DATASET_01_BASIC_NAME                               = "DATASET_01_BASIC";
    private static Dataset     DATASET_01_BASIC;

    public static final String DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME        = "DATASET_02_BASIC_WITH_GENERATED_VERSION";
    private static Dataset     DATASET_02_BASIC_WITH_GENERATED_VERSION;

    public static final String DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME       = "DATASET_03_WITH_2_DATASET_VERSIONS";
    private static Dataset     DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;

    public static final String DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME = "DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME";
    private static Dataset     DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;

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

        // Needs urns
        dsV1.getSiemacMetadataStatisticalResource().setIsReplacedByVersion(StatisticalResourcesMockFactoryUtils.createRelatedResource(dsV2));
        dsV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesMockFactoryUtils.createRelatedResource(dsV1));
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

    private static Dataset createDataset() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetWithoutGeneratedDatasetVersions();
    }

}
