package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

@SuppressWarnings("unused")
public class DatasourceMockFactory extends StatisticalResourcesMockFactory<Datasource> {

    public static final String DATASOURCE_01_BASIC_NAME = "DATASOURCE_01_BASIC";

    public static final String DATASOURCE_02_BASIC_NAME = "DATASOURCE_02_BASIC";

    private static DatasourceMockFactory instance = null;

    private DatasourceMockFactory() {
    }

    public static DatasourceMockFactory getInstance() {
        if (instance == null) {
            instance = new DatasourceMockFactory();
        }
        return instance;
    }

    private static Datasource getDatasource01Basic() {
        Datasource datasource = createDatasource();
        datasource.getDatasetVersion().setDatasetRepositoryId(StatisticalResourcesPersistedDoMocks.mockString(10));
        return datasource;
    }

    private static Datasource getDatasource02Basic() {
        return createDatasource();
    }

    public static Datasource generateSimpleDatasource() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasource(new Datasource());
    }

    public static Datasource generateDatasource(String sourceName) {
        Datasource template = new Datasource();
        template.setSourceName(sourceName);
        return getStatisticalResourcesPersistedDoMocks().mockDatasource(template);
    }

    public static Datasource generatePxDatasource(DateTime dateNextUpdate) {
        Datasource datasource = createDatasource();
        datasource.getIdentifiableStatisticalResource().setCode("pxFile.px_" + new DateTime().toString());
        datasource.setDateNextUpdate(dateNextUpdate);
        return datasource;
    }

    private static Datasource createDatasource() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasourceWithGeneratedDatasetVersion();
    }

}
