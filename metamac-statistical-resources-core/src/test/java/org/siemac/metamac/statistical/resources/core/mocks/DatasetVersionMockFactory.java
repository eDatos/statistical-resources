package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends MockFactory<DatasetVersion> implements InitializingBean {

    @Autowired
    StatisticalResourcesPersistedDoMocks       statisticalResourcesPersistedDoMocks;

    @Autowired
    DatasetMockFactory                         datasetMockFactory;

    @Autowired
    DatasourceMockFactory                      datasourceMockFactory;

    public static final String                 DATASET_VERSION_01_BASIC_NAME                           = "DATASET_VERSION_01_BASIC";
    public DatasetVersion                      DATASET_VERSION_01_BASIC;

    public static final String                 DATASET_VERSION_02_BASIC_NAME                           = "DATASET_VERSION_02_BASIC";
    public DatasetVersion                      DATASET_VERSION_02_BASIC;

    public static final String                 DATASET_VERSION_03_FOR_DATASET_03_NAME                  = "DATASET_VERSION_03_FOR_DATASET_03";
    public DatasetVersion                      DATASET_VERSION_03_FOR_DATASET_03;

    public static final String                 DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME = "DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION";
    public DatasetVersion                      DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;

    private static Map<String, DatasetVersion> mocks;

    private static final String                DATASET_VERSION_03_VERSION                              = "01.000";
    private static final String                DATASET_VERSION_04_VERSION                              = "02.000";

    @Override
    public void afterPropertiesSet() throws Exception {
        DATASET_VERSION_01_BASIC = createDatasetVersion();
        DATASET_VERSION_02_BASIC = createDatasetVersion();
        DATASET_VERSION_03_FOR_DATASET_03 = createDatasetVersion03();
        DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION = createDatasetVersion04();

        mocks = new HashMap<String, DatasetVersion>();
        registerMocks(this, DatasetVersion.class, mocks);
    }

    @Override
    public DatasetVersion getMock(String id) {
        return mocks.get(id);
    }

    private DatasetVersion createDatasetVersion03() {
        // Relation with dataset
        DatasetVersion datasetVersion = createDatasetVersion(datasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);
        datasetVersion.getSiemacMetadataStatisticalResource().setReplacedBy(DATASET_VERSION_04_VERSION);

        // Have two datasources
        datasetVersion.addDatasource(datasourceMockFactory.DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03);
        datasetVersion.addDatasource(datasourceMockFactory.DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03);

        return datasetVersion;
    }

    private DatasetVersion createDatasetVersion04() {
        // Relation with dataset
        DatasetVersion datasetVersion = createDatasetVersion(datasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS);

        // Version 02.000
        datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(DATASET_VERSION_04_VERSION);

        // ReplaceTo
        datasetVersion.getSiemacMetadataStatisticalResource().setReplaceTo(DATASET_VERSION_03_VERSION);

        // Is last version
        datasetVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

        // Have one datasource
        datasetVersion.addDatasource(datasourceMockFactory.DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04);

        return datasetVersion;
    }

    private DatasetVersion createDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = statisticalResourcesPersistedDoMocks.mockDatasetVersion(dataset);
        return datasetVersion;
    }

    private DatasetVersion createDatasetVersion() {
        return createDatasetVersion(null);
    }

}
