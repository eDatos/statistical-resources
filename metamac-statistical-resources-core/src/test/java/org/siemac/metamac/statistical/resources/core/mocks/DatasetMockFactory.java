package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.*;
import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends MockFactory<Dataset> {

    public static final String          DATASET_01_BASIC_NAME                         = "DATASET_01_BASIC";
    public static final Dataset         DATASET_01_BASIC                              = StatisticalResourcesDoMocks.mockDataset();

    public static final String          DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME  = "DATASET_02_BASIC_WITH_GENERATED_VERSION";
    public static final Dataset         DATASET_02_BASIC_WITH_GENERATED_VERSION       = StatisticalResourcesDoMocks.mockDatasetWithGeneratedDatasetVersions();

    public static final String          DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME = "DATASET_03_BASIC_WITH_2_DATASET_VERSIONS";
    public static final Dataset         DATASET_03_BASIC_WITH_2_DATASET_VERSIONS      = createDataset03With2DatasetVersions();

    private static Map<String, Dataset> mocks;

    static {
        mocks = new HashMap<String, Dataset>();
        registerMocks(DatasetMockFactory.class, Dataset.class, mocks);
    }

    @Override
    public Dataset getMock(String id) {
        return mocks.get(id);
    }

    private static Dataset createDataset03With2DatasetVersions() {
        Dataset dataset = StatisticalResourcesDoMocks.mockDataset();
        dataset.addVersion(DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03);
        dataset.addVersion(DATASET_VERSION_04_ASSOCIATED_WITH_DATASET_03_AND_LAST_VERSION);
        return dataset;
    }

}
