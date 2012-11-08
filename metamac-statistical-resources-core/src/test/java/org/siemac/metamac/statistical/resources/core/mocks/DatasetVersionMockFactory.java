package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends MockFactory<DatasetVersion> {

    public static final String                 DATASET_VERSION_01_BASIC_NAME                                       = "DATASET_VERSION_01_BASIC";
    public static final DatasetVersion         DATASET_VERSION_01_BASIC                                            = StatisticalResourcesDoMocks.mockDatasetVersion();

    public static final String                 DATASET_VERSION_02_BASIC_NAME                                       = "DATASET_VERSION_02_BASIC";
    public static final DatasetVersion         DATASET_VERSION_02_BASIC                                            = StatisticalResourcesDoMocks.mockDatasetVersion();

    public static final String                 DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03_NAME                  = "DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03";
    public static final DatasetVersion         DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03                       = StatisticalResourcesDoMocks
                                                                                                                           .mockDatasetVersion(DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);

    public static final String                 DATASET_VERSION_04_ASSOCIATED_WITH_DATASET_03_AND_LAST_VERSION_NAME = "DATASET_VERSION_04_ASSOCIATED_WITH_DATASET_03_AND_LAST_VERSION";
    public static final DatasetVersion         DATASET_VERSION_04_ASSOCIATED_WITH_DATASET_03_AND_LAST_VERSION      = createDatasetVersion04AssociatedWithDataset03AndLastVersion(); 

    
    
    private static Map<String, DatasetVersion> mocks;

    static {
        mocks = new HashMap<String, DatasetVersion>();
        registerMocks(DatasetVersionMockFactory.class, DatasetVersion.class, mocks);
    }

    @Override
    public DatasetVersion getMock(String id) {
        return mocks.get(id);
    }

    private static DatasetVersion createDatasetVersion04AssociatedWithDataset03AndLastVersion() {
        DatasetVersion datasetVersion = StatisticalResourcesDoMocks.mockDatasetVersion(DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);
        datasetVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);
        return datasetVersion;
    }

}
