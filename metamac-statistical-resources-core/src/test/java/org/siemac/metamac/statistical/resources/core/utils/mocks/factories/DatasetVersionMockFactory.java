package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce03BasicForDatasetVersion03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce04BasicForDatasetVersion03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce05BasicForDatasetVersion04;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends StatisticalResourcesMockFactory<DatasetVersion> {

    public static final String    DATASET_VERSION_01_BASIC_NAME                           = "DATASET_VERSION_01_BASIC";
    private static DatasetVersion DATASET_VERSION_01_BASIC;

    public static final String    DATASET_VERSION_02_BASIC_NAME                           = "DATASET_VERSION_02_BASIC";
    private static DatasetVersion DATASET_VERSION_02_BASIC;

    public static final String    DATASET_VERSION_03_FOR_DATASET_03_NAME                  = "DATASET_VERSION_03_FOR_DATASET_03";
    private static DatasetVersion DATASET_VERSION_03_FOR_DATASET_03;

    public static final String    DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME = "DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION";
    private static DatasetVersion DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;

    public static final String    DATASET_VERSION_05_FOR_DATASET_04_NAME                  = "DATASET_VERSION_05_FOR_DATASET_04";
    private static DatasetVersion DATASET_VERSION_05_FOR_DATASET_04;

    public static final String    DATASET_VERSION_06_FOR_QUERIES_NAME                     = "DATASET_VERSION_06_FOR_QUERIES";
    private static DatasetVersion DATASET_VERSION_06_FOR_QUERIES;

    public static final String    DATASET_VERSION_07_VALID_CODE_0001_NAME                 = "DATASET_VERSION_07_VALID_CODE_0001";
    private static DatasetVersion DATASET_VERSION_07_VALID_CODE_0001;

    public static final String    DATASET_VERSION_08_VALID_CODE_0002_NAME                 = "DATASET_VERSION_08_VALID_CODE_0002";
    private static DatasetVersion DATASET_VERSION_08_VALID_CODE_0002;

    public static final String    DATASET_VERSION_09_OPER_0001_CODE_0003_NAME             = "DATASET_VERSION_09_OPER_0001_CODE_0003";
    private static DatasetVersion DATASET_VERSION_09_OPER_0001_CODE_0003;

    public static final String    DATASET_VERSION_10_OPER_0002_CODE_0001_NAME             = "DATASET_VERSION_10_OPER_0002_CODE_0001";
    private static DatasetVersion DATASET_VERSION_10_OPER_0002_CODE_0001;

    public static final String    DATASET_VERSION_11_OPER_0002_CODE_0002_NAME             = "DATASET_VERSION_11_OPER_0002_CODE_0002";
    private static DatasetVersion DATASET_VERSION_11_OPER_0002_CODE_0002;

    public static final String    DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME              = "DATASET_VERSION_12_OPER_0002_MAX_CODE";
    private static DatasetVersion DATASET_VERSION_12_OPER_0002_MAX_CODE;

    public static final String    DATASET_VERSION_13_OPER_0002_CODE_0003_PROD_VAL_NAME    = "DATASET_VERSION_13_OPER_0002_CODE_0003_PROD_VAL";
    private static DatasetVersion DATASET_VERSION_13_OPER_0002_CODE_0003_PROD_VAL;

    public static final String    DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME       = "DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED";
    private static DatasetVersion DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED;

    private static final String   DATASET_VERSION_03_VERSION                              = "01.000";
    private static final String   DATASET_VERSION_04_VERSION                              = "02.000";
    private static final String   DATASET_VERSION_05_VERSION                              = "01.000";

    protected static DatasetVersion getDatasetVersion01Basic() {
        if (DATASET_VERSION_01_BASIC == null) {
            DATASET_VERSION_01_BASIC = createDatasetVersion(1);
        }
        return DATASET_VERSION_01_BASIC;
    }

    protected static DatasetVersion getDatasetVersion02Basic() {
        if (DATASET_VERSION_02_BASIC == null) {
            DATASET_VERSION_02_BASIC = createDatasetVersion(1);
        }
        return DATASET_VERSION_02_BASIC;
    }

    protected static DatasetVersion getDatasetVersion03ForDataset03() {
        if (DATASET_VERSION_03_FOR_DATASET_03 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(1);
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(DATASET_VERSION_03_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);

            // Relations
            DATASET_VERSION_03_FOR_DATASET_03 = datasetVersion;
            DATASET_VERSION_03_FOR_DATASET_03.setDataset(DatasetMockFactory.getDataset03With2DatasetVersions());
            setDatasetVersion03Datasources(datasetVersion);
        }
        return DATASET_VERSION_03_FOR_DATASET_03;
    }

    protected static DatasetVersion getDatasetVersion04ForDataset03AndLastVersion() {
        if (DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION == null) {
            DatasetVersion datasetVersion = createDatasetVersion(2);

            // Version 02.000
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(DATASET_VERSION_04_VERSION);

            // Is last version
            datasetVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

            // Relations
            DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION = datasetVersion;
            DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.setDataset(DatasetMockFactory.getDataset03With2DatasetVersions());
            setDatasetVersion04LastVersionForDataset03Datasources();
        }
        return DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;
    }

    protected static DatasetVersion getDatasetVersion05ForDataset04() {
        if (DATASET_VERSION_05_FOR_DATASET_04 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(1);

            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem());
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem());
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem());
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.addGeographicGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.addTemporalGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.addMeasure(StatisticalResourcesDoMocks.mockConceptExternalItem());
            datasetVersion.addMeasure(StatisticalResourcesDoMocks.mockConceptExternalItem());

            datasetVersion.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem());
            datasetVersion.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem());

            datasetVersion.setDateStart(new DateTime().minusYears(10));

            datasetVersion.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
            datasetVersion.setUpdateFrequency(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.setFormatExtentDimensions(3);
            datasetVersion.setFormatExtentObservations(1354);
            datasetVersion.setDateNextUpdate(new DateTime().plusMonths(1));
            datasetVersion.setBibliographicCitation(StatisticalResourcesDoMocks.mockInternationalString("es", "biblio"));

            // Version 01.000
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(DATASET_VERSION_05_VERSION);

            // Is last version
            datasetVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

            datasetVersion.getSiemacMetadataStatisticalResource().setCreatedBy(StatisticalResourcesDoMocks.mockString(10));
            datasetVersion.getSiemacMetadataStatisticalResource().setCreatedDate(StatisticalResourcesDoMocks.mockDateTime());
            datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdatedBy(StatisticalResourcesDoMocks.mockString(10));
            datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdated(StatisticalResourcesDoMocks.mockDateTime());

            // Relations
            DATASET_VERSION_05_FOR_DATASET_04 = datasetVersion;
            DATASET_VERSION_05_FOR_DATASET_04.setDataset(DatasetMockFactory.getDataset04With1DatasetVersions());
        }
        return DATASET_VERSION_05_FOR_DATASET_04;
    }

    protected static DatasetVersion getDatasetVersion06ForQueries() {
        if (DATASET_VERSION_06_FOR_QUERIES == null) {
            DATASET_VERSION_06_FOR_QUERIES = createDatasetVersion(1);
        }
        return DATASET_VERSION_06_FOR_QUERIES;
    }

    protected static DatasetVersion getDatasetVersion07ValidCode0001() {
        if (DATASET_VERSION_07_VALID_CODE_0001 == null) {
            DATASET_VERSION_07_VALID_CODE_0001 = createDatasetVersionInSpecificOperation("OPER_0001", 1);
        }
        return DATASET_VERSION_07_VALID_CODE_0001;
    }

    protected static DatasetVersion getDatasetVersion08ValidCode0002() {
        if (DATASET_VERSION_08_VALID_CODE_0002 == null) {
            DATASET_VERSION_08_VALID_CODE_0002 = createDatasetVersionInSpecificOperation("OPER_0001", 2);
        }
        return DATASET_VERSION_08_VALID_CODE_0002;
    }

    protected static DatasetVersion getDatasetVersion09Oper0001Code0003() {
        if (DATASET_VERSION_09_OPER_0001_CODE_0003 == null) {
            DATASET_VERSION_09_OPER_0001_CODE_0003 = createDatasetVersionInSpecificOperation("OPER_0001", 3);
        }
        return DATASET_VERSION_09_OPER_0001_CODE_0003;
    }

    protected static DatasetVersion getDatasetVersion10Oper0002Code0001() {
        if (DATASET_VERSION_10_OPER_0002_CODE_0001 == null) {
            DATASET_VERSION_10_OPER_0002_CODE_0001 = createDatasetVersionInSpecificOperation("OPER_0002", 1);
        }
        return DATASET_VERSION_10_OPER_0002_CODE_0001;
    }

    protected static DatasetVersion getDatasetVersion11Oper0002Code0002() {
        if (DATASET_VERSION_11_OPER_0002_CODE_0002 == null) {
            DATASET_VERSION_11_OPER_0002_CODE_0002 = createDatasetVersionInSpecificOperation("OPER_0002", 2);
        }
        return DATASET_VERSION_11_OPER_0002_CODE_0002;
    }

    protected static DatasetVersion getDatasetVersion12Oper0002MaxCode() {
        if (DATASET_VERSION_12_OPER_0002_MAX_CODE == null) {
            DATASET_VERSION_12_OPER_0002_MAX_CODE = createDatasetVersionInSpecificOperation("OPER_0002", 9999);
        }
        return DATASET_VERSION_12_OPER_0002_MAX_CODE;
    }

    protected static DatasetVersion getDatasetVersion13Oper0002Code0003ProdVal() {
        if (DATASET_VERSION_13_OPER_0002_CODE_0003_PROD_VAL == null) {
            DATASET_VERSION_13_OPER_0002_CODE_0003_PROD_VAL = createDatasetVersionInSpecificOperation("OPER_0002", 3);
            fillDatasetVersionInProductionValidation(DATASET_VERSION_13_OPER_0002_CODE_0003_PROD_VAL);
        }
        return DATASET_VERSION_13_OPER_0002_CODE_0003_PROD_VAL;
    }

    protected static DatasetVersion getDatasetVersion14Oper03Code01Published() {
        if (DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED == null) {
            DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED = createDatasetVersionInSpecificOperation("OPER_0003", 1);
            fillDatasetVersionInPublished(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED);
        }
        return DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED;
    }

    private static void setDatasetVersion03Datasources(DatasetVersion datasetVersion03) {
        datasetVersion03.addDatasource(getDatasorce03BasicForDatasetVersion03());
        datasetVersion03.addDatasource(getDatasorce04BasicForDatasetVersion03());
    }

    private static void setDatasetVersion04LastVersionForDataset03Datasources() {
        DatasetVersion datasetVersion04 = getDatasetVersion04ForDataset03AndLastVersion();
        datasetVersion04.addDatasource(getDatasorce05BasicForDatasetVersion04());
    }
    
    private static DatasetVersion createDatasetVersion(Integer sequentialId) {
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(null);
        ExternalItem operation = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation();
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(buildDatasetCode(operation.getCode(), sequentialId));
        return datasetVersion;
    }

    private static DatasetVersion createDatasetVersionInSpecificOperation(String operationCode, Integer sequentialId) {
        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationItem(operationCode);
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(null);
        datasetVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(operation);
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(buildDatasetCode(operationCode, sequentialId));
        return datasetVersion;
    }

    private static String buildDatasetCode(String operationCode, int sequentialId) {
        return operationCode + "_DATASET_" + String.format("%04d", sequentialId);
    }

    private static void fillDatasetVersionInProductionValidation(DatasetVersion datasetVersion) {
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);
    }

    private static void fillDatasetVersionInPublished(DatasetVersion datasetVersion) {
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);
    }

}
