package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends MockFactory<Dataset> {

    public static final String          DATASET_01_BASIC_NAME                         = "DATASET_01_BASIC";
    public static final Dataset         DATASET_01_BASIC                              = StatisticalResourcesDoMocks.mockDataset();

    public static final String          DATASET_02_BASIC_NAME                         = "DATASET_02_BASIC";
    public static final Dataset         DATASET_02_BASIC                              = StatisticalResourcesDoMocks.mockDataset();

    public static final String          DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME = "DATASET_03_BASIC_WITH_2_DATASET_VERSIONS";
    public static final Dataset         DATASET_03_BASIC_WITH_2_DATASET_VERSIONS      = StatisticalResourcesDoMocks.mockDataset();

    private static Map<String, Dataset> mocks;

    static {
        mocks = new HashMap<String, Dataset>();
        registerMocks(DatasetMockFactory.class, Dataset.class, mocks);
    }

    @Override
    public Dataset getMock(String id) {
        return mocks.get(id);
    }
}
