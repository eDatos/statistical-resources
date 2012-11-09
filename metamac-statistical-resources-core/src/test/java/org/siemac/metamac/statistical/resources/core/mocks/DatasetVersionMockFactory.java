package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.*;
import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends MockFactory<DatasetVersion> {

    public static final String                 DATASET_VERSION_01_BASIC_NAME                           = "DATASET_VERSION_01_BASIC";
    public static final DatasetVersion         DATASET_VERSION_01_BASIC                                = StatisticalResourcesDoMocks.mockDatasetVersion();

    public static final String                 DATASET_VERSION_02_BASIC_NAME                           = "DATASET_VERSION_02_BASIC";
    public static final DatasetVersion         DATASET_VERSION_02_BASIC                                = StatisticalResourcesDoMocks.mockDatasetVersion();

    public static final String                 DATASET_VERSION_03_FOR_DATASET_03_NAME                  = "DATASET_VERSION_03_FOR_DATASET_03";
    public static final DatasetVersion         DATASET_VERSION_03_FOR_DATASET_03                       = createDatasetVersion03();

    public static final String                 DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME = "DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION";
    public static final DatasetVersion         DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION      = createDatasetVersion04();

    private static Map<String, DatasetVersion> mocks;

    static {
        mocks = new HashMap<String, DatasetVersion>();
        registerMocks(DatasetVersionMockFactory.class, DatasetVersion.class, mocks);
    }

    @Override
    public DatasetVersion getMock(String id) {
        return mocks.get(id);
    }

    private static DatasetVersion createDatasetVersion03() {
        // Relation with dataset
        DatasetVersion datasetVersion = StatisticalResourcesDoMocks.mockDatasetVersion(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);

        // Have two datasources
        datasetVersion.addDatasource(DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03);
        datasetVersion.addDatasource(DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03);

        return datasetVersion;
    }

    private static DatasetVersion createDatasetVersion04() {
        // Relation with dataset
        DatasetVersion datasetVersion = StatisticalResourcesDoMocks.mockDatasetVersion(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);

        // Version 02.000
        datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic("02.000");

        // Is last version
        datasetVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

        // Have one datasource
        datasetVersion.addDatasource(DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04);

        return datasetVersion;
    }

}
