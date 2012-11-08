package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasourceMockFactory extends MockFactory<Datasource> {

    public static final String             DATASOURCE_BASIC_01_NAME = "DATASOURCE_BASIC_01";
    public static final Datasource         DATASOURCE_BASIC_01      = StatisticalResourcesDoMocks.mockDatasource();

    public static final String             DATASOURCE_BASIC_02_NAME = "DATASOURCE_BASIC_02";
    public static final Datasource         DATASOURCE_BASIC_02      = StatisticalResourcesDoMocks.mockDatasource();

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
