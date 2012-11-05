package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends MockFactory<Dataset> {

    public static final String          DATASET_BASIC_01_NAME = "DATASET_BASIC_01";
    public static final Dataset         DATASET_BASIC_01      = StatisticalResourcesDoMocks.mockDataset();

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
