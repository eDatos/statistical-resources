package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasourceMockFactory extends MockFactory<Datasource> implements InitializingBean {

    @Autowired
    StatisticalResourcesPersistedDoMocks   statisticalResourcesPersistedDoMocks;

    @Autowired
    DatasetVersionMockFactory              datasetVersionMockFactory;

    public static final String             DATASOURCE_01_BASIC_NAME                        = "DATASOURCE_01_BASIC";
    public Datasource                      DATASOURCE_01_BASIC;

    public static final String             DATASOURCE_02_BASIC_NAME                        = "DATASOURCE_02_BASIC";
    public Datasource                      DATASOURCE_02_BASIC;

    public static final String             DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03_NAME = "DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03";
    public Datasource                      DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03;

    public static final String             DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03_NAME = "DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03";
    public Datasource                      DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03;

    public static final String             DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04_NAME = "DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04";
    public Datasource                      DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04;

    private static Map<String, Datasource> mocks;

    @Override
    public void afterPropertiesSet() throws Exception {
        DATASOURCE_01_BASIC = createDatasource();
        DATASOURCE_02_BASIC = createDatasource();

        getDatasorce03BasicForDatasetVersion03();
        getDatasorce04BasicForDatasetVersion03();
        getDatasorce05BasicForDatasetVersion04();

        mocks = new HashMap<String, Datasource>();
        registerMocks(this, Datasource.class, mocks);
    }

    @Override
    public Datasource getMock(String id) {
        return mocks.get(id);
    }

    public Datasource getDatasorce03BasicForDatasetVersion03() {
        if (DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03 == null) {
            DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03 = createDatasource(datasetVersionMockFactory.getDatasetVersion03ForDataset03());
        }
        return DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03;
    }

    public Datasource getDatasorce04BasicForDatasetVersion03() {
        if (DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03 == null) {
            DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03 = createDatasource(datasetVersionMockFactory.getDatasetVersion03ForDataset03());
        }
        return DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03;
    }

    public Datasource getDatasorce05BasicForDatasetVersion04() {
        if (DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04 == null) {
            DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04 = createDatasource(datasetVersionMockFactory.getDatasetVersion04LastVersionForDataset03());
        }
        return DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04;
    }

    private Datasource createDatasource() {
        return statisticalResourcesPersistedDoMocks.mockDatasourceWithGeneratedDatasetVersion();
    }

    private Datasource createDatasource(DatasetVersion datasetVersion) {
        return statisticalResourcesPersistedDoMocks.mockDatasource(datasetVersion);
    }

}
