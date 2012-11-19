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
        DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03 = createDatasource(datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03);
        DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03 = createDatasource(datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03);
        DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04 = createDatasource(datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION);

        mocks = new HashMap<String, Datasource>();
        registerMocks(this, Datasource.class, mocks);
    }

    @Override
    public Datasource getMock(String id) {
        return mocks.get(id);
    }

    private Datasource createDatasource() {
        return statisticalResourcesPersistedDoMocks.mockDatasourceWithGeneratedDatasetVersion();
    }

    private Datasource createDatasource(DatasetVersion datasetVersion) {
        return statisticalResourcesPersistedDoMocks.mockDatasource(datasetVersion);
    }

}
