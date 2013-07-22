package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce03BasicForDatasetVersion03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce04BasicForDatasetVersion03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce05BasicForDatasetVersion04;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends StatisticalResourcesMockFactory<DatasetVersion> {

    public static final String    DATASET_VERSION_01_BASIC_NAME                                                = "DATASET_VERSION_01_BASIC";
    private static DatasetVersion DATASET_VERSION_01_BASIC;

    public static final String    DATASET_VERSION_02_BASIC_NAME                                                = "DATASET_VERSION_02_BASIC";
    private static DatasetVersion DATASET_VERSION_02_BASIC;

    public static final String    DATASET_VERSION_03_FOR_DATASET_03_NAME                                       = "DATASET_VERSION_03_FOR_DATASET_03";
    private static DatasetVersion DATASET_VERSION_03_FOR_DATASET_03;

    public static final String    DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME                      = "DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION";
    private static DatasetVersion DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;

    public static final String    DATASET_VERSION_05_FOR_DATASET_04_NAME                                       = "DATASET_VERSION_05_FOR_DATASET_04";
    private static DatasetVersion DATASET_VERSION_05_FOR_DATASET_04;

    public static final String    DATASET_VERSION_06_FOR_QUERIES_NAME                                          = "DATASET_VERSION_06_FOR_QUERIES";
    private static DatasetVersion DATASET_VERSION_06_FOR_QUERIES;

    public static final String    DATASET_VERSION_07_VALID_CODE_000001_NAME                                    = "DATASET_VERSION_07_VALID_CODE_000001";
    private static DatasetVersion DATASET_VERSION_07_VALID_CODE_000001;

    public static final String    DATASET_VERSION_08_VALID_CODE_000002_NAME                                    = "DATASET_VERSION_08_VALID_CODE_000002";
    private static DatasetVersion DATASET_VERSION_08_VALID_CODE_000002;

    public static final String    DATASET_VERSION_09_OPER_0001_CODE_000003_NAME                                = "DATASET_VERSION_09_OPER_0001_CODE_000003";
    private static DatasetVersion DATASET_VERSION_09_OPER_0001_CODE_000003;

    public static final String    DATASET_VERSION_10_OPER_0002_CODE_000001_NAME                                = "DATASET_VERSION_10_OPER_0002_CODE_000001";
    private static DatasetVersion DATASET_VERSION_10_OPER_0002_CODE_000001;

    public static final String    DATASET_VERSION_11_OPER_0002_CODE_000002_NAME                                = "DATASET_VERSION_11_OPER_0002_CODE_000002";
    private static DatasetVersion DATASET_VERSION_11_OPER_0002_CODE_000002;

    public static final String    DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME                                   = "DATASET_VERSION_12_OPER_0002_MAX_CODE";
    private static DatasetVersion DATASET_VERSION_12_OPER_0002_MAX_CODE;

    public static final String    DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME                       = "DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL";
    private static DatasetVersion DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL;

    public static final String    DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME                            = "DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED";
    private static DatasetVersion DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED;

    public static final String    DATASET_VERSION_15_DRAFT_NOT_READY_NAME                                      = "DATASET_VERSION_15_DRAFT_NOT_READY";
    private static DatasetVersion DATASET_VERSION_15_DRAFT_NOT_READY;

    public static final String    DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME                = "DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION";
    private static DatasetVersion DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION;

    public static final String    DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA_NAME                  = "DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA";
    private static DatasetVersion DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA;

    public static final String    DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED_NAME               = "DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED";
    private static DatasetVersion DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED;

    public static final String    DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY_NAME                      = "DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY";
    private static DatasetVersion DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY;

    public static final String    DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME = "DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION";
    private static DatasetVersion DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION;

    public static final String    DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME  = "DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED";
    private static DatasetVersion DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED;

    public static final String    DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05_NAME                          = "DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05";
    private static DatasetVersion DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05;

    public static final String    DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05_NAME                          = "DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05";
    private static DatasetVersion DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05;

    public static final String    DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME                          = "DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05";
    private static DatasetVersion DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05;

    public static final String    DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06_NAME                          = "DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06";
    private static DatasetVersion DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06;

    public static final String    DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06_NAME               = "DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06";
    private static DatasetVersion DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06;

    public static final String    DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME                                 = "DATASET_VERSION_27_WITH_COVERAGE_FILLED";
    private static DatasetVersion DATASET_VERSION_27_WITH_COVERAGE_FILLED;
    
    private static final String   INIT_VERSION                                                                 = "001.000";
    private static final String   SECOND_VERSION                                                               = "002.000";
    private static final String   THIRD_VERSION                                                                = "003.000";

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
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // not last version
            datasetVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(2));
            datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(2));

            // Relations
            DATASET_VERSION_03_FOR_DATASET_03 = datasetVersion;
            DATASET_VERSION_03_FOR_DATASET_03.setDataset(DatasetMockFactory.getDataset03With2DatasetVersions());
            setDatasetVersion03Datasources(datasetVersion);
        }
        return DATASET_VERSION_03_FOR_DATASET_03;
    }

    private static void setDatasetVersion03Datasources(DatasetVersion datasetVersion03) {
        datasetVersion03.addDatasource(getDatasorce03BasicForDatasetVersion03());
        datasetVersion03.addDatasource(getDatasorce04BasicForDatasetVersion03());
    }

    protected static DatasetVersion getDatasetVersion04ForDataset03AndLastVersion() {
        if (DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION == null) {
            DatasetVersion datasetVersion = createDatasetVersion(2);

            // Version 002.000
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);

            // Is last version
            datasetVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            datasetVersion.getSiemacMetadataStatisticalResource().setLastVersion(true);

            // Relations
            DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION = datasetVersion;
            DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.setDataset(DatasetMockFactory.getDataset03With2DatasetVersions());
            setDatasetVersion04LastVersionForDataset03Datasources();
        }
        return DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;
    }

    private static void setDatasetVersion04LastVersionForDataset03Datasources() {
        DatasetVersion datasetVersion04 = getDatasetVersion04ForDataset03AndLastVersion();
        datasetVersion04.addDatasource(getDatasorce05BasicForDatasetVersion04());
    }

    protected static DatasetVersion getDatasetVersion05ForDataset04() {
        if (DATASET_VERSION_05_FOR_DATASET_04 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(1);

            datasetVersion.getGeographicCoverage().clear();
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.getTemporalCoverage().clear();
            datasetVersion.getTemporalCoverage().add(StatisticalResourcesDoMocks.mockTemporalCode());

            datasetVersion.getMeasureCoverage().clear();
            datasetVersion.getMeasureCoverage().add(StatisticalResourcesDoMocks.mockConceptExternalItem());

            datasetVersion.addGeographicGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.addTemporalGranularity(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem());
            datasetVersion.addStatisticalUnit(StatisticalResourcesDoMocks.mockConceptExternalItem());

            datasetVersion.setDateStart(new DateTime().minusYears(10));

            datasetVersion.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
            datasetVersion.setUpdateFrequency(StatisticalResourcesDoMocks.mockCodeExternalItem());

            datasetVersion.setFormatExtentDimensions(3);
            datasetVersion.setFormatExtentObservations(1354);
            datasetVersion.setDateNextUpdate(new DateTime().plusMonths(1));
            datasetVersion.setBibliographicCitation(StatisticalResourcesDoMocks.mockInternationalString("es", "biblio"));

            // Version 001.000
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);

            datasetVersion.getSiemacMetadataStatisticalResource().setCreatedBy(StatisticalResourcesDoMocks.mockString(10));
            datasetVersion.getSiemacMetadataStatisticalResource().setCreatedDate(StatisticalResourcesDoMocks.mockDateTime());
            datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdatedBy(StatisticalResourcesDoMocks.mockString(10));
            datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdated(StatisticalResourcesDoMocks.mockDateTime());

            // Relations
            DATASET_VERSION_05_FOR_DATASET_04 = datasetVersion;
            DATASET_VERSION_05_FOR_DATASET_04.setDataset(DatasetMockFactory.getDataset04FullFilledWith1DatasetVersions());
        }
        return DATASET_VERSION_05_FOR_DATASET_04;
    }

    protected static DatasetVersion getDatasetVersion06ForQueries() {
        if (DATASET_VERSION_06_FOR_QUERIES == null) {
            DATASET_VERSION_06_FOR_QUERIES = createDatasetVersion(1);
        }
        return DATASET_VERSION_06_FOR_QUERIES;
    }

    protected static DatasetVersion getDatasetVersion07ValidCode000001() {
        if (DATASET_VERSION_07_VALID_CODE_000001 == null) {
            DATASET_VERSION_07_VALID_CODE_000001 = createDatasetVersionInSpecificOperation(OPERATION_01_CODE, 1);
        }
        return DATASET_VERSION_07_VALID_CODE_000001;
    }

    protected static DatasetVersion getDatasetVersion08ValidCode000002() {
        if (DATASET_VERSION_08_VALID_CODE_000002 == null) {
            DATASET_VERSION_08_VALID_CODE_000002 = createDatasetVersionInSpecificOperation(OPERATION_01_CODE, 2);
        }
        return DATASET_VERSION_08_VALID_CODE_000002;
    }

    protected static DatasetVersion getDatasetVersion09Oper0001Code000003() {
        if (DATASET_VERSION_09_OPER_0001_CODE_000003 == null) {
            DATASET_VERSION_09_OPER_0001_CODE_000003 = createDatasetVersionInSpecificOperation(OPERATION_01_CODE, 3);
        }
        return DATASET_VERSION_09_OPER_0001_CODE_000003;
    }

    protected static DatasetVersion getDatasetVersion10Oper0002Code000001() {
        if (DATASET_VERSION_10_OPER_0002_CODE_000001 == null) {
            DATASET_VERSION_10_OPER_0002_CODE_000001 = createDatasetVersionInSpecificOperation(OPERATION_02_CODE, 1);
        }
        return DATASET_VERSION_10_OPER_0002_CODE_000001;
    }

    protected static DatasetVersion getDatasetVersion11Oper0002Code000002() {
        if (DATASET_VERSION_11_OPER_0002_CODE_000002 == null) {
            DATASET_VERSION_11_OPER_0002_CODE_000002 = createDatasetVersionInSpecificOperation(OPERATION_02_CODE, 2);
        }
        return DATASET_VERSION_11_OPER_0002_CODE_000002;
    }

    protected static DatasetVersion getDatasetVersion12Oper0002MaxCode() {
        if (DATASET_VERSION_12_OPER_0002_MAX_CODE == null) {
            DATASET_VERSION_12_OPER_0002_MAX_CODE = createDatasetVersionInSpecificOperation(OPERATION_02_CODE, 999999);
        }
        return DATASET_VERSION_12_OPER_0002_MAX_CODE;
    }

    protected static DatasetVersion getDatasetVersion13Oper0002Code000003ProdVal() {
        if (DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL == null) {
            DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL = createDatasetVersionInSpecificOperation(OPERATION_02_CODE, 3);
            fillDatasetVersionInProductionValidation(DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL);
        }
        return DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL;
    }

    protected static DatasetVersion getDatasetVersion14Oper03Code01Published() {
        if (DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED == null) {
            DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED = createDatasetVersionInSpecificOperation(OPERATION_03_CODE, 1);
            fillDatasetVersionInPublished(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED);
        }
        return DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED;
    }

    protected static DatasetVersion getDatasetVersion15DraftNotReady() {
        if (DATASET_VERSION_15_DRAFT_NOT_READY == null) {
            DATASET_VERSION_15_DRAFT_NOT_READY = createDatasetVersion(1);
            DATASET_VERSION_15_DRAFT_NOT_READY.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        }
        return DATASET_VERSION_15_DRAFT_NOT_READY;
    }

    protected static DatasetVersion getDatasetVersion16DraftReadyForProductionValidation() {
        if (DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION == null) {
            DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION = createDatasetVersionEmpty();
            prepareToProductionValidation(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION);
        }
        return DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION;
    }

    protected static DatasetVersion getDatasetVersion17VersionRationaleTypeMinorErrata() {
        if (DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA = datasetVersion;
            prepareToProductionValidation(datasetVersion);
            datasetVersion.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MINOR_ERRATA));
        }
        return DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA;
    }

    protected static DatasetVersion getDatasetVersion18NextVersionNotScheduledDateFilled() {
        if (DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED = datasetVersion;
            prepareToProductionValidation(datasetVersion);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersionDate(new DateTime().plusDays(10));
        }
        return DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED;
    }

    protected static DatasetVersion getDatasetVersion19ProductionValidationNotReady() {
        if (DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY == null) {
            DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY = createDatasetVersionEmpty();
            prepareToDiffusionValidation(DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY);
        }
        return DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY;
    }

    protected static DatasetVersion getDatasetVersion20ProductionValidationReadyForDiffusionValidation() {
        if (DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION == null) {
            DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION = createDatasetVersionEmpty();
            prepareToDiffusionValidation(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION);
        }
        return DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION;
    }

    protected static DatasetVersion getDatasetVersion21ProductionValidationReadyForValidationRejected() {
        if (DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED == null) {
            DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED = createDatasetVersionEmpty();
            prepareToValidationRejected(DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED);
        }
        return DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED;
    }

    protected static DatasetVersion getDatasetVersion22V1PublishedForDataset05() {
        if (DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(1);
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // not last version
            datasetVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(3));
            datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(3));

            // Relations
            DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05 = datasetVersion;
            DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05.setDataset(DatasetMockFactory.getDataset05WithMultiplePublishedVersions());
        }
        return DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05;
    }

    protected static DatasetVersion getDatasetVersion23V2PublishedForDataset05() {
        if (DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(2);
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // not last version
            datasetVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(2));
            datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(2));

            // Relations
            DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05 = datasetVersion;
            DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05.setDataset(DatasetMockFactory.getDataset05WithMultiplePublishedVersions());
        }
        return DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05;
    }

    protected static DatasetVersion getDatasetVersion24V3PublishedForDataset05() {
        if (DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(3);
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(THIRD_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // last version
            datasetVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(1));

            // Relations
            DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05 = datasetVersion;
            DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05.setDataset(DatasetMockFactory.getDataset05WithMultiplePublishedVersions());
        }
        return DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05;
    }

    protected static DatasetVersion getDatasetVersion25V1PublishedForDataset06() {
        if (DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(1);
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // last version
            datasetVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(1));

            // Relations
            DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06 = datasetVersion;
            DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06.setDataset(DatasetMockFactory.getDataset06WithMultiplePublishedVersionsAndLatestNoVisible());
        }
        return DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06;
    }

    protected static DatasetVersion getDatasetVersion26V2PublishedNoVisibleForDataset06() {
        if (DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06 == null) {
            DatasetVersion datasetVersion = createDatasetVersion(2);
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // last version
            datasetVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime());
            datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().plusDays(1));

            // Relations
            DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06 = datasetVersion;
            DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06.setDataset(DatasetMockFactory.getDataset06WithMultiplePublishedVersionsAndLatestNoVisible());
        }
        return DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06;

    }
    
    protected static DatasetVersion getDatasetVersion27WithCoverageFilled() {
        if (DATASET_VERSION_27_WITH_COVERAGE_FILLED == null) {
            DatasetVersion datasetVersion = createDatasetVersion(2);
            
            prepareToProductionValidation(datasetVersion);
            
            datasetVersion.getCoverages().add(new CodeDimension("dim1","code-d1-1"));
            datasetVersion.getCoverages().add(new CodeDimension("dim1","code-d1-2"));
            datasetVersion.getCoverages().add(new CodeDimension("dim2","code-d2-1"));
            datasetVersion.getCoverages().add(new CodeDimension("dim2","code-d2-2"));
            datasetVersion.getCoverages().add(new CodeDimension("dim3","code-d3-1"));
            
            DATASET_VERSION_27_WITH_COVERAGE_FILLED = datasetVersion;
            // Relations
            for (CodeDimension code : datasetVersion.getCoverages()) {
                code.setDatasetVersion(DATASET_VERSION_27_WITH_COVERAGE_FILLED);
            }
        }
        return DATASET_VERSION_27_WITH_COVERAGE_FILLED;
        
    }

    private static void fillDatasetVersionInProductionValidation(DatasetVersion datasetVersion) {
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
    }

    private static void fillDatasetVersionInPublished(DatasetVersion datasetVersion) {
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
    }

    private static DatasetVersion createDatasetVersion(Integer sequentialId) {
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(null);
        ExternalItem operation = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation();
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(buildDatasetCode(operation.getCode(), sequentialId));
        return datasetVersion;
    }

    private static DatasetVersion createDatasetVersionEmpty() {
        Dataset ds = getStatisticalResourcesPersistedDoMocks().mockDatasetWithoutGeneratedDatasetVersions();
        DatasetVersion datasetVersion = new DatasetVersion();
        datasetVersion.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());

        datasetVersion.setDataset(ds);
        return datasetVersion;
    }

    private static DatasetVersion createDatasetVersionInSpecificOperation(String operationCode, Integer sequentialId) {
        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(null);
        datasetVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(operation);
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(buildDatasetCode(operationCode, sequentialId));
        return datasetVersion;
    }

    private static String buildDatasetVersionUrn(String code, String version) {
        return GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(new String[]{"maitainer"}, code, version);
    }

    private static String buildDatasetCode(String operationCode, int sequentialId) {
        return operationCode + String.format("%06d", sequentialId);
    }

    // -----------------------------------------------------------------
    // LIFE CYCLE PREPARATIONS
    // -----------------------------------------------------------------

    private static void prepareToProductionValidation(DatasetVersion datasetVersion) {
        LifecycleTestUtils.prepareToProductionValidation(datasetVersion);
        prepareToLifecycleCommonDatasetVersion(datasetVersion);
    }

    private static void prepareToDiffusionValidation(DatasetVersion datasetVersion) {
        prepareToProductionValidation(datasetVersion);
        LifecycleTestUtils.prepareToDiffusionValidation(datasetVersion);
    }

    private static void prepareToValidationRejected(DatasetVersion datasetVersion) {
        prepareToProductionValidation(datasetVersion);
        LifecycleTestUtils.prepareToValidationRejected(datasetVersion);
    }

    private static void prepareToLifecycleCommonDatasetVersion(DatasetVersion datasetVersion) {
        ExternalItem geoGranularity = StatisticalResourcesPersistedDoMocks.mockCodeExternalItem();
        datasetVersion.addGeographicGranularity(geoGranularity);

        ExternalItem timeGranularity = StatisticalResourcesPersistedDoMocks.mockCodeExternalItem();
        datasetVersion.addTemporalGranularity(timeGranularity);

        ExternalItem dsd = StatisticalResourcesPersistedDoMocks.mockDsdExternalItem();
        datasetVersion.setRelatedDsd(dsd);

        datasetVersion.setDateNextUpdate(new DateTime().plusDays(10));
        
        ExternalItem codeUpdateFreq = StatisticalResourcesPersistedDoMocks.mockCodeExternalItem();
        datasetVersion.setUpdateFrequency(codeUpdateFreq);

        StatisticOfficiality officiality = getStatisticalResourcesPersistedDoMocks().mockStatisticOfficiality("officiality");
        datasetVersion.setStatisticOfficiality(officiality);

        // Inherited fields that need customization based on Resource's type
        String code = buildDatasetCode(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), 1);
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(code);
        datasetVersion.getSiemacMetadataStatisticalResource().setUrn(buildDatasetVersionUrn(code, datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        datasetVersion.getSiemacMetadataStatisticalResource().setType(StatisticalResourceTypeEnum.DATASET);

        if (datasetVersion.getDatasources().isEmpty()) {
            Datasource datasource = DatasourceMockFactory.generateSimpleDatasource();
            datasource.setDatasetVersion(datasetVersion);
            datasetVersion.addDatasource(datasource);
        }
    }
}
