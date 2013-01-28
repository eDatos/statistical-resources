package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
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

    public static final String                 DATASET_VERSION_05_FOR_DATASET_04_NAME                  = "DATASET_VERSION_03_FOR_DATASET_03";
    public DatasetVersion                      DATASET_VERSION_05_FOR_DATASET_04;

    private static Map<String, DatasetVersion> mocks;

    private static final String                DATASET_VERSION_03_VERSION                              = "01.000";
    private static final String                DATASET_VERSION_04_VERSION                              = "02.000";
    private static final String                DATASET_VERSION_05_VERSION                              = "01.000";

    @Override
    public void afterPropertiesSet() throws Exception {
        DATASET_VERSION_01_BASIC = createDatasetVersion();
        DATASET_VERSION_02_BASIC = createDatasetVersion();

        setDataset03AndVersionsRelations();

        setDataset04AndVersionsRelations();

        mocks = new HashMap<String, DatasetVersion>();
        registerMocks(this, DatasetVersion.class, mocks);
    }

    @Override
    public DatasetVersion getMock(String id) {
        return mocks.get(id);
    }

    private void setDataset03AndVersionsRelations() {
        Dataset dataset03 = datasetMockFactory.getDataset03With2DatasetVersions();

        DatasetVersion datasetVersion03 = getDatasetVersion03ForDataset03();
        datasetVersion03.addDatasource(datasourceMockFactory.getDatasorce03BasicForDatasetVersion03());
        datasetVersion03.addDatasource(datasourceMockFactory.getDatasorce04BasicForDatasetVersion03());

        DatasetVersion datasetVersion04 = getDatasetVersion04LastVersionForDataset03();
        datasetVersion04.addDatasource(datasourceMockFactory.getDatasorce05BasicForDatasetVersion04());

        datasetVersion03.getSiemacMetadataStatisticalResource().setIsReplacedByVersion(StatisticalResourcesMockFactoryUtils.createRelatedResource(datasetVersion04));
        datasetVersion04.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesMockFactoryUtils.createRelatedResource(DATASET_VERSION_03_FOR_DATASET_03));
        dataset03.addVersion(datasetVersion03);
        dataset03.addVersion(datasetVersion04);

    }

    private void setDataset04AndVersionsRelations() {
        Dataset dataset = datasetMockFactory.getDataset04With1DatasetVersions();
        DatasetVersion datasetVersion = getDatasetVersion05ForDataset04();

        dataset.addVersion(datasetVersion);
    }

    public DatasetVersion getDatasetVersion03ForDataset03() {
        if (DATASET_VERSION_03_FOR_DATASET_03 == null) {
            // Relation with dataset
            DatasetVersion datasetVersion = createDatasetVersion();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(DATASET_VERSION_03_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);

            DATASET_VERSION_03_FOR_DATASET_03 = datasetVersion;
        }
        return DATASET_VERSION_03_FOR_DATASET_03;
    }

    public DatasetVersion getDatasetVersion04LastVersionForDataset03() {
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
        }
        return DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;
    }

    public DatasetVersion getDatasetVersion05ForDataset04() {
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
    private DatasetVersion createDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = statisticalResourcesPersistedDoMocks.mockDatasetVersion(dataset);
        return datasetVersion;
    }

    private DatasetVersion createDatasetVersion() {
        return createDatasetVersion(null);
    }

}
