package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.springframework.stereotype.Component;

@Component
public class DatasourceMockFactory extends StatisticalResourcesMockFactory<Datasource> {

    public static final String DATASOURCE_01_BASIC_NAME                                 = "DATASOURCE_01_BASIC";
    private static Datasource  DATASOURCE_01_BASIC;

    public static final String DATASOURCE_02_BASIC_NAME                                 = "DATASOURCE_02_BASIC";
    private static Datasource  DATASOURCE_02_BASIC;

    public static final String DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03_NAME          = "DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03";
    private static Datasource  DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03;

    public static final String DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03_NAME          = "DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03";
    private static Datasource  DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03;

    public static final String DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04_NAME          = "DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04";
    private static Datasource  DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04;

    public static final String DATASOURCE_06_LINKED_TO_FILE_FOR_DATASET_VERSION_30_NAME = "DATASOURCE_06_LINKED_TO_FILE_FOR_DATASET_VERSION_30";
    private static Datasource  DATASOURCE_06_LINKED_TO_FILE_FOR_DATASET_VERSION_30;

    public static final String DATASOURCE_07_LINKED_TO_FILE_WITH_UNDERSCORE_NAME        = "DATASOURCE_07_LINKED_TO_FILE_WITH_UNDERSCORE";
    private static Datasource  DATASOURCE_07_LINKED_TO_FILE_WITH_UNDERSCORE;

    protected static Datasource getDatasource01Basic() {
        if (DATASOURCE_01_BASIC == null) {
            DATASOURCE_01_BASIC = createDatasource();
        }
        return DATASOURCE_01_BASIC;
    }

    protected static Datasource getDatasource02Basic() {
        if (DATASOURCE_02_BASIC == null) {
            DATASOURCE_02_BASIC = createDatasource();
        }
        return DATASOURCE_02_BASIC;
    }

    protected static Datasource getDatasorce03BasicForDatasetVersion03() {
        if (DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03 == null) {
            DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03 = createDatasource();
            // Dataset link set in datasetversion
        }
        return DATASOURCE_03_BASIC_FOR_DATASET_VERSION_03;
    }

    protected static Datasource getDatasorce04BasicForDatasetVersion03() {
        if (DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03 == null) {
            DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03 = createDatasource();
            // Dataset link set in datasetversion
        }
        return DATASOURCE_04_BASIC_FOR_DATASET_VERSION_03;
    }

    protected static Datasource getDatasorce05BasicForDatasetVersion04() {
        if (DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04 == null) {
            DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04 = createDatasource();
            // Dataset link set in datasetversion
        }
        return DATASOURCE_05_BASIC_FOR_DATASET_VERSION_04;
    }

    protected static Datasource getDatasorce06LinkedToFileForDatasetVersion30() {
        if (DATASOURCE_06_LINKED_TO_FILE_FOR_DATASET_VERSION_30 == null) {
            Datasource datasource = createDatasource();
            datasource.setFilename("datasource_06.px");
            datasource.getIdentifiableStatisticalResource().setCode(datasource.getFilename() + "_" + new DateTime().toString());
            DATASOURCE_06_LINKED_TO_FILE_FOR_DATASET_VERSION_30 = datasource;
            // Dataset link set in datasetversion
        }
        return DATASOURCE_06_LINKED_TO_FILE_FOR_DATASET_VERSION_30;
    }

    protected static Datasource getDatasorce07LinkedToFileWithUnderscore() {
        if (DATASOURCE_07_LINKED_TO_FILE_WITH_UNDERSCORE == null) {
            DATASOURCE_07_LINKED_TO_FILE_WITH_UNDERSCORE = createDatasource();
            DATASOURCE_07_LINKED_TO_FILE_WITH_UNDERSCORE.getIdentifiableStatisticalResource().setCode("datasource_underscore.px_" + new DateTime().toString());
            // Dataset link set in datasetversion
        }
        return DATASOURCE_07_LINKED_TO_FILE_WITH_UNDERSCORE;
    }

    public static Datasource generateSimpleDatasource() {
        return createDatasource();
    }

    private static Datasource createDatasource() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasourceWithGeneratedDatasetVersion();
    }

}
