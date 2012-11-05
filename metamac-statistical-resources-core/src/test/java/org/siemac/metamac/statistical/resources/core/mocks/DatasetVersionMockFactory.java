package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends MockFactory<DatasetVersion> {

    public static final String                 DATASET_VERSION_BASIC_01_NAME = "DATASET_VERSION_BASIC_01";
    public static final DatasetVersion         DATASET_VERSION_BASIC_01      = StatisticalResourcesDoMocks.mockDatasetVersion();

    private static Map<String, DatasetVersion> mocks;

    static {
        mocks = new HashMap<String, DatasetVersion>();
        registerMocks(DatasetVersionMockFactory.class, DatasetVersion.class, mocks);
    }

    @Override
    public DatasetVersion getMock(String id) {
        return mocks.get(id);
    }

}
