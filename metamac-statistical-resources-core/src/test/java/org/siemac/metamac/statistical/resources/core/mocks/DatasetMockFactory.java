package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion03ForDataset03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion04LastVersionForDataset03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion05ForDataset04;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends StatisticalResourcesMockFactory<Dataset> {

    @Autowired
    StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    @Autowired
    DatasetVersionMockFactory            datasetVersionMockFactory;

    public static final String           DATASET_01_BASIC_NAME                               = "DATASET_01_BASIC";
    private static Dataset               DATASET_01_BASIC;

    public static final String           DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME        = "DATASET_02_BASIC_WITH_GENERATED_VERSION";
    private static Dataset               DATASET_02_BASIC_WITH_GENERATED_VERSION;

    public static final String           DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME       = "DATASET_03_BASIC_WITH_2_DATASET_VERSIONS";
    private static Dataset               DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;

    public static final String           DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME = "DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME";
    private static Dataset               DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;

    public static Dataset getDataset01Basic() {
        if (DATASET_01_BASIC == null) {
            DATASET_01_BASIC = createDataset();
        }
        return DATASET_01_BASIC;
    }

    public static Dataset getDataset02BasicWithGeneratedVersion() {
        if (DATASET_02_BASIC_WITH_GENERATED_VERSION == null) {
            DATASET_02_BASIC_WITH_GENERATED_VERSION = createDataset();
        }
        return DATASET_02_BASIC_WITH_GENERATED_VERSION;
    }

    public static Dataset getDataset03With2DatasetVersions() {
        if (DATASET_03_BASIC_WITH_2_DATASET_VERSIONS == null) {
            Dataset dataset = createDataset();
            DATASET_03_BASIC_WITH_2_DATASET_VERSIONS = dataset;
            setDataset03Versions(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);
        }
        return DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;
    }

    private static void setDataset03Versions(Dataset dataset) {
        DatasetVersion dsV1 = getDatasetVersion03ForDataset03();
        DatasetVersion dsV2 = getDatasetVersion04LastVersionForDataset03();

        dataset.addVersion(dsV1);
        dsV1.setDataset(dataset);

        dataset.addVersion(dsV2);
        dsV2.setDataset(dataset);
    }

    public static Dataset getDataset04With1DatasetVersions() {
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
