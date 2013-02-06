package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.getDatasorce03BasicForDatasetVersion03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.getDatasorce04BasicForDatasetVersion03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.getDatasorce05BasicForDatasetVersion04;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
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

    public static final String    DATASET_VERSION_05_FOR_DATASET_04_NAME                  = "DATASET_VERSION_03_FOR_DATASET_03";
    private static DatasetVersion DATASET_VERSION_05_FOR_DATASET_04;

    public static final String    DATASET_VERSION_06_FOR_QUERIES_NAME                     = "DATASET_VERSION_06_FOR_QUERIES";
    private static DatasetVersion DATASET_VERSION_06_FOR_QUERIES;

    public static final String    DATASET_VERSION_07_VALID_CODE_0001_NAME                 = "DATASET_VERSION_07_VALID_CODE_0001";
    private static DatasetVersion DATASET_VERSION_07_VALID_CODE_0001;
    
    public static final String    DATASET_VERSION_08_VALID_CODE_0002_NAME                 = "DATASET_VERSION_08_VALID_CODE_0002";
    private static DatasetVersion DATASET_VERSION_08_VALID_CODE_0002;
    
    private static ExternalItem   STATISTICAL_OPERATION_0001;

    private static final String   DATASET_VERSION_03_VERSION                              = "01.000";
    private static final String   DATASET_VERSION_04_VERSION                              = "02.000";
    private static final String   DATASET_VERSION_05_VERSION                              = "01.000";


    protected static DatasetVersion getDatasetVersion01Basic() {
        if (DATASET_VERSION_01_BASIC == null) {
            DATASET_VERSION_01_BASIC = createDatasetVersion();
        }
        return DATASET_VERSION_01_BASIC;
    }

    protected static DatasetVersion getDatasetVersion02Basic() {
        if (DATASET_VERSION_02_BASIC == null) {
            DATASET_VERSION_02_BASIC = createDatasetVersion();
        }
        return DATASET_VERSION_02_BASIC;
    }

    protected static DatasetVersion getDatasetVersion03ForDataset03() {
        if (DATASET_VERSION_03_FOR_DATASET_03 == null) {
            // Relation with dataset
            DatasetVersion datasetVersion = createDatasetVersion();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(DATASET_VERSION_03_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);

            DATASET_VERSION_03_FOR_DATASET_03 = datasetVersion;
            setDatasetVersion03Datasources(datasetVersion);
        }
        return DATASET_VERSION_03_FOR_DATASET_03;
    }

    protected static DatasetVersion getDatasetVersion04ForDataset03AndLastVersion() {
        if (DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION == null) {
            // Relation with dataset
            DatasetVersion datasetVersion = createDatasetVersion();

            // Version 02.000
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(DATASET_VERSION_04_VERSION);

            // ReplaceTo
            // datasetVersion.getSiemacMetadataStatisticalResource().setReplacesVersion(createRelatedResourceDatasetVersion(DATASET_VERSION_03_FOR_DATASET_03));

            // Is last version
            datasetVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

            DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION = datasetVersion;
            setDatasetVersion04LastVersionForDataset03Datasources();
        }
        return DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;
    }


    protected static DatasetVersion getDatasetVersion05ForDataset04() {
        if (DATASET_VERSION_05_FOR_DATASET_04 == null) {
            // Relation with dataset
            DatasetVersion datasetVersion = createDatasetVersion();

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
            datasetVersion.setDateStart(new DateTime().plusYears(10));

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

            DATASET_VERSION_05_FOR_DATASET_04 = datasetVersion;
        }
        return DATASET_VERSION_05_FOR_DATASET_04;
    }

    protected static DatasetVersion getDatasetVersion06ForQueries() {
        if (DATASET_VERSION_06_FOR_QUERIES == null) {
            DATASET_VERSION_06_FOR_QUERIES = createDatasetVersion();
        }
        return DATASET_VERSION_06_FOR_QUERIES;
    }
    
    protected static DatasetVersion getDatasetVersion07ValidCode0001() {
        if (DATASET_VERSION_07_VALID_CODE_0001 == null) {
            DatasetVersion dsVersion = createDatasetVersion();
            DATASET_VERSION_07_VALID_CODE_0001 = dsVersion;

            dsVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(getStatisticalOperation0001());
            dsVersion.getSiemacMetadataStatisticalResource().setCode("OPER_0001_DSC_0002");
        }
        return DATASET_VERSION_07_VALID_CODE_0001;
    }

    protected static DatasetVersion getDatasetVersion08ValidCode0002() {
        if (DATASET_VERSION_08_VALID_CODE_0002 == null) {
            DatasetVersion dsVersion = createDatasetVersion();
            DATASET_VERSION_08_VALID_CODE_0002 = dsVersion;

            dsVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(getStatisticalOperation0001());
            dsVersion.getSiemacMetadataStatisticalResource().setCode("OPER_0001_DSC_0002");
        }
        return DATASET_VERSION_08_VALID_CODE_0002;
    }


    private static void setDatasetVersion03Datasources(DatasetVersion datasetVersion03) {
        datasetVersion03.addDatasource(getDatasorce03BasicForDatasetVersion03());
        datasetVersion03.addDatasource(getDatasorce04BasicForDatasetVersion03());
    }
    
    private static void setDatasetVersion04LastVersionForDataset03Datasources() {
        DatasetVersion datasetVersion04 = getDatasetVersion04ForDataset03AndLastVersion();
        datasetVersion04.addDatasource(getDatasorce05BasicForDatasetVersion04());
    }
    
    private static ExternalItem getStatisticalOperation0001() {
        if (STATISTICAL_OPERATION_0001 == null) {
            STATISTICAL_OPERATION_0001 = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationItem("OPER_0001");
        }
        return STATISTICAL_OPERATION_0001;
    }

    
    private static DatasetVersion createDatasetVersion() {
        return createDatasetVersion(null);
    }

    private static DatasetVersion createDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(dataset);
        return datasetVersion;
    }

}
