package org.siemac.metamac.statistical.resources.core.mocks;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasourceMockFactory extends StatisticalResourcesMockFactory<Datasource> {

    @Autowired
    StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    @Autowired
    DatasetVersionMockFactory            datasetVersionMockFactory;

    public static final String           DATASOURCE_01_BASIC_NAME                        = "DATASOURCE_01_BASIC";
    private static Datasource            DATASOURCE_01_BASIC;

    public static final String           DATASOURCE_02_BASIC_NAME                        = "DATASOURCE_02_BASIC";
    private static Datasource            DATASOURCE_02_BASIC;

    public static final String           DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03_NAME = "DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03";
    private static Datasource            DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03;

    public static final String           DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03_NAME = "DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03";
    private static Datasource            DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03;

    public static final String           DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04_NAME = "DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04";
    private static Datasource            DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04;

    public static Datasource getDatasource01Basic() {
        if (DATASOURCE_01_BASIC == null) {
            DATASOURCE_01_BASIC = createDatasource();
        }
        return DATASOURCE_01_BASIC;
    }

    public static Datasource getDatasource02Basic() {
        if (DATASOURCE_02_BASIC == null) {
            DATASOURCE_02_BASIC = createDatasource();
        }
        return DATASOURCE_02_BASIC;
    }

    public static Datasource getDatasorce03BasicForDatasetVersion03() {
        if (DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03 == null) {
            DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03 = createDatasource();
            // Dataset link set in datasetversion
        }
        return DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03;
    }

    public static Datasource getDatasorce04BasicForDatasetVersion03() {
        if (DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03 == null) {
            DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03 = createDatasource();
            // Dataset link set in datasetversion
        }
        return DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03;
    }

    public static Datasource getDatasorce05BasicForDatasetVersion04() {
        if (DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04 == null) {
            DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04 = createDatasource();
            // Dataset link set in datasetversion
        }
        return DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04;
    }

    private static Datasource createDatasource() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasourceWithGeneratedDatasetVersion();
    }

    private Datasource createDatasource(DatasetVersion datasetVersion) {
        return statisticalResourcesPersistedDoMocks.mockDatasource(datasetVersion);
    }

}
