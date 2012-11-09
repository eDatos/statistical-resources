package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.*;
import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasourceMockFactory extends MockFactory<Datasource> {

    public static final String             DATASOURCE_01_BASIC_NAME                        = "DATASOURCE_01_BASIC";
    public static final Datasource         DATASOURCE_01_BASIC                             = StatisticalResourcesDoMocks.mockDatasource();

    public static final String             DATASOURCE_02_BASIC_NAME                        = "DATASOURCE_02_BASIC";
    public static final Datasource         DATASOURCE_02_BASIC                             = StatisticalResourcesDoMocks.mockDatasource();

    public static final String             DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03_NAME = "DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03";
    public static final Datasource         DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03      = StatisticalResourcesDoMocks.mockDatasource(DATASET_VERSION_03_FOR_DATASET_03);

    public static final String             DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03_NAME = "DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03";
    public static final Datasource         DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03      = StatisticalResourcesDoMocks.mockDatasource(DATASET_VERSION_03_FOR_DATASET_03);

    public static final String             DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04_NAME = "DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04";
    public static final Datasource         DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04      = StatisticalResourcesDoMocks.mockDatasource(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION);

    private static Map<String, Datasource> mocks;

    static {
        mocks = new HashMap<String, Datasource>();
        registerMocks(DatasourceMockFactory.class, Datasource.class, mocks);
    }

    @Override
    public Datasource getMock(String id) {
        return mocks.get(id);
    }
}
