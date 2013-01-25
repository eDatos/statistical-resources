package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends MockFactory<Dataset> implements InitializingBean {

    @Autowired
    StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    @Autowired
    DatasetVersionMockFactory            datasetVersionMockFactory;

    public static final String           DATASET_01_BASIC_NAME                               = "DATASET_01_BASIC";
    public Dataset                       DATASET_01_BASIC;

    public static final String           DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME        = "DATASET_02_BASIC_WITH_GENERATED_VERSION";
    public Dataset                       DATASET_02_BASIC_WITH_GENERATED_VERSION;

    public static final String           DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME       = "DATASET_03_BASIC_WITH_2_DATASET_VERSIONS";
    public Dataset                       DATASET_03_BASIC_WITH_2_DATASET_VERSIONS;

    public static final String           DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME = "DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS_NAME";
    public Dataset                       DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS;

    private static Map<String, Dataset>  mocks;

    @Override
    public void afterPropertiesSet() throws Exception {
        DATASET_01_BASIC = createDataset();
        DATASET_02_BASIC_WITH_GENERATED_VERSION = createDataset();
        DATASET_03_BASIC_WITH_2_DATASET_VERSIONS = createDataset03With2DatasetVersions();
        DATASET_04_FULL_FILLED_WITH_1_DATASET_VERSIONS = createDataset04With1DatasetVersions();

        mocks = new HashMap<String, Dataset>();
        registerMocks(this, Dataset.class, mocks);
    }

    @Override
    public Dataset getMock(String id) {
        return mocks.get(id);
    }

    private Dataset createDataset03With2DatasetVersions() {
        Dataset dataset = createDataset();
        dataset.addVersion(datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03);
        dataset.addVersion(datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION);
        return dataset;
    }

    private Dataset createDataset04With1DatasetVersions() {
        Dataset dataset = createDataset();
        // TODO: add versions
        return dataset;
    }

    private Dataset createDataset() {
        return statisticalResourcesPersistedDoMocks.mockDatasetWithoutGeneratedDatasetVersions();
    }

}
