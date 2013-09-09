package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils.prepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils.prepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils.prepareToVersioning;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce06LinkedToFileForDatasetVersion30;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.getDatasorce07LinkedToFileWithUnderscore;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.DatasetVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends StatisticalResourcesMockFactory<DatasetVersion> {

    private static final String   NOT_INITIAL_VERSION                                                                                    = "002.002";

    public static final String    DATASET_VERSION_01_BASIC_NAME                                                                          = "DATASET_VERSION_01_BASIC";
    private static DatasetVersion DATASET_VERSION_01_BASIC;

    public static final String    DATASET_VERSION_02_BASIC_NAME                                                                          = "DATASET_VERSION_02_BASIC";
    private static DatasetVersion DATASET_VERSION_02_BASIC;

    public static final String    DATASET_VERSION_03_FOR_DATASET_03_NAME                                                                 = "DATASET_VERSION_03_FOR_DATASET_03";
    private static DatasetVersion DATASET_VERSION_03_FOR_DATASET_03;

    public static final String    DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME                                                = "DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION";
    private static DatasetVersion DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;

    public static final String    DATASET_VERSION_05_FOR_DATASET_04_NAME                                                                 = "DATASET_VERSION_05_FOR_DATASET_04";
    private static DatasetVersion DATASET_VERSION_05_FOR_DATASET_04;

    public static final String    DATASET_VERSION_06_FOR_QUERIES_NAME                                                                    = "DATASET_VERSION_06_FOR_QUERIES";
    private static DatasetVersion DATASET_VERSION_06_FOR_QUERIES;

    public static final String    DATASET_VERSION_07_VALID_CODE_000001_NAME                                                              = "DATASET_VERSION_07_VALID_CODE_000001";
    private static DatasetVersion DATASET_VERSION_07_VALID_CODE_000001;

    public static final String    DATASET_VERSION_08_VALID_CODE_000002_NAME                                                              = "DATASET_VERSION_08_VALID_CODE_000002";
    private static DatasetVersion DATASET_VERSION_08_VALID_CODE_000002;

    public static final String    DATASET_VERSION_09_OPER_0001_CODE_000003_NAME                                                          = "DATASET_VERSION_09_OPER_0001_CODE_000003";
    private static DatasetVersion DATASET_VERSION_09_OPER_0001_CODE_000003;

    public static final String    DATASET_VERSION_10_OPER_0002_CODE_000001_NAME                                                          = "DATASET_VERSION_10_OPER_0002_CODE_000001";
    private static DatasetVersion DATASET_VERSION_10_OPER_0002_CODE_000001;

    public static final String    DATASET_VERSION_11_OPER_0002_CODE_000002_NAME                                                          = "DATASET_VERSION_11_OPER_0002_CODE_000002";
    private static DatasetVersion DATASET_VERSION_11_OPER_0002_CODE_000002;

    public static final String    DATASET_VERSION_12_OPER_0002_MAX_CODE_NAME                                                             = "DATASET_VERSION_12_OPER_0002_MAX_CODE";
    private static DatasetVersion DATASET_VERSION_12_OPER_0002_MAX_CODE;

    public static final String    DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL_NAME                                                 = "DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL";
    private static DatasetVersion DATASET_VERSION_13_OPER_0002_CODE_000003_PROD_VAL;

    public static final String    DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME                                                      = "DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED";
    private static DatasetVersion DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED;

    public static final String    DATASET_VERSION_15_DRAFT_NOT_READY_NAME                                                                = "DATASET_VERSION_15_DRAFT_NOT_READY";
    private static DatasetVersion DATASET_VERSION_15_DRAFT_NOT_READY;

    public static final String    DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME                                          = "DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION";
    private static DatasetVersion DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION;

    public static final String    DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA_NAME                                            = "DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA";
    private static DatasetVersion DATASET_VERSION_17_VERSION_RATIONALE_TYPE_MINOR_ERRATA;

    public static final String    DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED_NAME                                         = "DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED";
    private static DatasetVersion DATASET_VERSION_18_NEXT_VERSION_NOT_SCHEDULED_DATE_FILLED;

    public static final String    DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY_NAME                                                = "DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY";
    private static DatasetVersion DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY;

    public static final String    DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME                           = "DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION";
    private static DatasetVersion DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION;

    public static final String    DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME                            = "DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED";
    private static DatasetVersion DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED;

    public static final String    DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05_NAME                                                    = "DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05";
    private static DatasetVersion DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05;

    public static final String    DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05_NAME                                                    = "DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05";
    private static DatasetVersion DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05;

    public static final String    DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME                                                    = "DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05";
    private static DatasetVersion DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05;

    public static final String    DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06_NAME                                                    = "DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06";
    private static DatasetVersion DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06;

    public static final String    DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06_NAME                                         = "DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06";
    private static DatasetVersion DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06;

    public static final String    DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME                                                           = "DATASET_VERSION_27_WITH_COVERAGE_FILLED";
    private static DatasetVersion DATASET_VERSION_27_WITH_COVERAGE_FILLED;

    public static final String    DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA_NAME                                             = "DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA";
    private static DatasetVersion DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA;

    public static final String    DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME                                                            = "DATASET_VERSION_29_WITHOUT_DATASOURCES";
    private static DatasetVersion DATASET_VERSION_29_WITHOUT_DATASOURCES;

    public static final String    DATASET_VERSION_30_WITH_DATASOURCE_NAME                                                                = "DATASET_VERSION_30_WITH_DATASOURCE";
    private static DatasetVersion DATASET_VERSION_30_WITH_DATASOURCE;

    public static final String    DATASET_VERSION_32_WITH_MULTIPLE_DATASOURCES_LINKED_TO_FILE_NAME                                       = "DATASET_VERSION_32_WITH_MULTIPLE_DATASOURCES_LINKED_TO_FILE";
    private static DatasetVersion DATASET_VERSION_32_WITH_MULTIPLE_DATASOURCES_LINKED_TO_FILE;

    public static final String    DATASET_VERSION_33_WITH_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE_NAME                          = "DATASET_VERSION_33_WITH_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE";
    private static DatasetVersion DATASET_VERSION_33_WITH_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE;

    public static final String    DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001_NAME                                                   = "DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001";
    private static DatasetVersion DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001;

    public static final String    DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001_NAME                                                   = "DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001";
    private static DatasetVersion DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001;

    public static final String    DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002_NAME                                                   = "DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002";
    private static DatasetVersion DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002;

    public static final String    DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME                                       = "DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001";
    private static DatasetVersion DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001;

    public static final String    DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001_NAME                                       = "DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001";
    private static DatasetVersion DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001;

    public static final String    DATASET_VERSION_39_VERSION_RATIONALE_TYPE_MAJOR_NEW_RESOURCE_NAME                                      = "DATASET_VERSION_39_VERSION_RATIONALE_TYPE_MAJOR_NEW_RESOURCE";
    private static DatasetVersion DATASET_VERSION_39_VERSION_RATIONALE_TYPE_MAJOR_NEW_RESOURCE;

    public static final String    DATASET_VERSION_40_VERSION_RATIONALE_TYPE_MAJOR_ESTIMATORS_NAME                                        = "DATASET_VERSION_40_VERSION_RATIONALE_TYPE_MAJOR_ESTIMATORS";
    private static DatasetVersion DATASET_VERSION_40_VERSION_RATIONALE_TYPE_MAJOR_ESTIMATORS;

    public static final String    DATASET_VERSION_41_VERSION_RATIONALE_TYPE_MINOR_ERRATAS_NAME                                           = "DATASET_VERSION_41_VERSION_RATIONALE_TYPE_MINOR_ERRATAS";
    private static DatasetVersion DATASET_VERSION_41_VERSION_RATIONALE_TYPE_MINOR_ERRATAS;

    public static final String    DATASET_VERSION_42_VERSION_RATIONALE_TYPE_MINOR_ERRATAS_AND_MAJOR_ESTIMATORS_NAME                      = "DATASET_VERSION_42_VERSION_RATIONALE_TYPE_MINOR_ERRATAS_AND_MAJOR_ESTIMATORS";
    private static DatasetVersion DATASET_VERSION_42_VERSION_RATIONALE_TYPE_MINOR_ERRATAS_AND_MAJOR_ESTIMATORS;

    public static final String    DATASET_VERSION_43_NEXT_VERSION_NO_UPDATES_NAME                                                        = "DATASET_VERSION_43_NEXT_VERSION_NO_UPDATES";
    private static DatasetVersion DATASET_VERSION_43_NEXT_VERSION_NO_UPDATES;

    public static final String    DATASET_VERSION_44_NEXT_VERSION_NON_SCHEDULED_UPDATE_NAME                                              = "DATASET_VERSION_44_NEXT_VERSION_NON_SCHEDULED_UPDATE";
    private static DatasetVersion DATASET_VERSION_44_NEXT_VERSION_NON_SCHEDULED_UPDATE;

    public static final String    DATASET_VERSION_45_NEXT_VERSION_SCHEDULED_UPDATE_JANUARY_NAME                                          = "DATASET_VERSION_45_NEXT_VERSION_SCHEDULED_UPDATE_JANUARY";
    private static DatasetVersion DATASET_VERSION_45_NEXT_VERSION_SCHEDULED_UPDATE_JANUARY;

    public static final String    DATASET_VERSION_46_NEXT_VERSION_SCHEDULED_UPDATE_JULY_NAME                                             = "DATASET_VERSION_46_NEXT_VERSION_SCHEDULED_UPDATE_JULY";
    private static DatasetVersion DATASET_VERSION_46_NEXT_VERSION_SCHEDULED_UPDATE_JULY;

    public static final String    DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES_NAME                                               = "DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES";
    private static DatasetVersion DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES;

    public static final String    DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED_NAME                                                  = "DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED";
    private static DatasetVersion DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED;

    public static final String    DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME                          = "DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH";
    private static DatasetVersion DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH;

    public static final String    DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH_NAME                     = "DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH";
    private static DatasetVersion DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH;

    public static final String    DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE_NAME                                                       = "DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE";
    private static DatasetVersion DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE;

    public static final String    DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE_NAME                                       = "DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE";
    private static DatasetVersion DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE;

    public static final String    DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE_NAME                                        = "DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE";
    private static DatasetVersion DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE;

    public static final String    DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE_NAME                                         = "DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE";
    private static DatasetVersion DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE;

    public static final String    DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE_NAME                                                      = "DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE";
    private static DatasetVersion DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE;

    public static final String    DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES_NAME                                              = "DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES";
    private static DatasetVersion DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES;

    public static final String    DATASET_VERSION_57_DRAFT_INITIAL_VERSION_NAME                                                          = "DATASET_VERSION_57_DRAFT_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_57_DRAFT_INITIAL_VERSION;

    public static final String    DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION_NAME                                          = "DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION;

    public static final String    DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION_NAME                                           = "DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION;

    public static final String    DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION_NAME                                            = "DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION;

    public static final String    DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION_NAME                                                      = "DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION;

    public static final String    DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION_NAME                                                      = "DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION;

    public static final String    DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION_NAME                                      = "DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION;

    public static final String    DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION_NAME                                       = "DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION;

    public static final String    DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION_NAME                                        = "DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION;

    public static final String    DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION_NAME                                                  = "DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION";
    private static DatasetVersion DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION;

    public static final String    DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_NAME                                    = "DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED";
    private static DatasetVersion DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED;

    public static final String    DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE_NAME = "DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE";
    private static DatasetVersion DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE;

    public static final String    DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER_NAME                                                   = "DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER";
    private static DatasetVersion DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER;

    public static final String    DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME                                         = "DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL";
    private static DatasetVersion DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL;

    public static final String    DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED_NAME                                                  = "DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED";
    private static DatasetVersion DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED;

    public static final String    DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION_NAME                                      = "DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION";
    private static DatasetVersion DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION;

    public static final String    DATASET_VERSION_73_V01_FOR_DATASET_11_NAME                                                             = "DATASET_VERSION_73_V01_FOR_DATASET_11";
    private static DatasetVersion DATASET_VERSION_73_V01_FOR_DATASET_11;

    public static final String    DATASET_VERSION_74_V02_FOR_DATASET_11_NAME                                                             = "DATASET_VERSION_74_V02_FOR_DATASET_11";
    private static DatasetVersion DATASET_VERSION_74_V02_FOR_DATASET_11;

    public static final String    DATASET_VERSION_75_V01_FOR_DATASET_12_NAME                                                             = "DATASET_VERSION_75_V01_FOR_DATASET_12";
    private static DatasetVersion DATASET_VERSION_75_V01_FOR_DATASET_12;

    public static final String    DATASET_VERSION_76_V02_FOR_DATASET_12_NAME                                                             = "DATASET_VERSION_76_V02_FOR_DATASET_12";
    private static DatasetVersion DATASET_VERSION_76_V02_FOR_DATASET_12;

    protected static DatasetVersion getDatasetVersion01Basic() {
        if (DATASET_VERSION_01_BASIC == null) {
            DATASET_VERSION_01_BASIC = createDatasetVersionWithSequence(1);
            DATASET_VERSION_01_BASIC.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
        }
        return DATASET_VERSION_01_BASIC;
    }

    protected static DatasetVersion getDatasetVersion02Basic() {
        if (DATASET_VERSION_02_BASIC == null) {
            DATASET_VERSION_02_BASIC = createDatasetVersionWithSequence(1);
            DATASET_VERSION_02_BASIC.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
        }
        return DATASET_VERSION_02_BASIC;
    }

    protected static DatasetVersion getDatasetVersion03ForDataset03() {
        if (DATASET_VERSION_03_FOR_DATASET_03 == null) {
            Dataset dataset = DatasetMockFactory.getDataset03With2DatasetVersions();
            DATASET_VERSION_03_FOR_DATASET_03 = dataset.getVersions().get(0);
        }
        return DATASET_VERSION_03_FOR_DATASET_03;
    }

    protected static DatasetVersion getDatasetVersion04ForDataset03AndLastVersion() {
        if (DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION == null) {
            Dataset dataset = DatasetMockFactory.getDataset03With2DatasetVersions();
            DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION = dataset.getVersions().get(1);
        }
        return DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;
    }

    protected static DatasetVersion getDatasetVersion05ForDataset04() {
        if (DATASET_VERSION_05_FOR_DATASET_04 == null) {
            Dataset dataset = DatasetMockFactory.getDataset04FullFilledWith1DatasetVersions();
            DATASET_VERSION_05_FOR_DATASET_04 = dataset.getVersions().get(0);
        }
        return DATASET_VERSION_05_FOR_DATASET_04;
    }

    protected static DatasetVersion getDatasetVersion06ForQueries() {
        if (DATASET_VERSION_06_FOR_QUERIES == null) {
            DATASET_VERSION_06_FOR_QUERIES = createDatasetVersionWithSequence(2);
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
            prepareToVersioning(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED);
        }
        return DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED;
    }

    protected static DatasetVersion getDatasetVersion15DraftNotReady() {
        if (DATASET_VERSION_15_DRAFT_NOT_READY == null) {
            DATASET_VERSION_15_DRAFT_NOT_READY = createDatasetVersionWithSequence(1);
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
            DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05 = DatasetMockFactory.getDataset05WithMultiplePublishedVersions().getVersions().get(0);
        }
        return DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05;
    }

    protected static DatasetVersion getDatasetVersion23V2PublishedForDataset05() {
        if (DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05 == null) {
            DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05 = DatasetMockFactory.getDataset05WithMultiplePublishedVersions().getVersions().get(1);
        }
        return DATASET_VERSION_23_V2_PUBLISHED_FOR_DATASET_05;
    }

    protected static DatasetVersion getDatasetVersion24V3PublishedForDataset05() {
        if (DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05 == null) {
            DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05 = DatasetMockFactory.getDataset05WithMultiplePublishedVersions().getVersions().get(2);
        }
        return DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05;
    }

    protected static DatasetVersion getDatasetVersion25V1PublishedForDataset06() {
        if (DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06 == null) {
            DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06 = DatasetMockFactory.getDataset06WithMultiplePublishedVersionsAndLatestNoVisible().getVersions().get(0);
        }
        return DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06;
    }

    protected static DatasetVersion getDatasetVersion26V2PublishedNoVisibleForDataset06() {
        if (DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06 == null) {
            DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06 = DatasetMockFactory.getDataset06WithMultiplePublishedVersionsAndLatestNoVisible().getVersions().get(1);
        }
        return DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06;
    }

    protected static DatasetVersion getDatasetVersion27WithCoverageFilled() {
        if (DATASET_VERSION_27_WITH_COVERAGE_FILLED == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(2);

            prepareToProductionValidation(datasetVersion);

            datasetVersion.getDimensionsCoverage().clear();
            datasetVersion.addDimensionsCoverage(new CodeDimension("dim1", "code-d1-1"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("dim1", "code-d1-2"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("dim2", "code-d2-1"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("dim2", "code-d2-2"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("dim3", "code-d3-1"));

            DATASET_VERSION_27_WITH_COVERAGE_FILLED = datasetVersion;
        }
        return DATASET_VERSION_27_WITH_COVERAGE_FILLED;
    }

    protected static DatasetVersion getDatasetVersion28WithoutDatasourcesImportingData() {
        if (DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA == null) {
            DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA = createDatasetVersionWithSequence(2);
        }
        return DATASET_VERSION_28_WITHOUT_DATASOURCES_IMPORTING_DATA;

    }

    protected static DatasetVersion getDatasetVersion29WithoutDatasources() {
        if (DATASET_VERSION_29_WITHOUT_DATASOURCES == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);
            datasetVersion.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
            DATASET_VERSION_29_WITHOUT_DATASOURCES = datasetVersion;
        }
        return DATASET_VERSION_29_WITHOUT_DATASOURCES;
    }

    protected static DatasetVersion getDatasetVersion30WithDatasource() {
        if (DATASET_VERSION_30_WITH_DATASOURCE == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);
            DATASET_VERSION_30_WITH_DATASOURCE = datasetVersion;
            setDatasetVersion30Datasources();
        }
        return DATASET_VERSION_30_WITH_DATASOURCE;
    }

    private static void setDatasetVersion30Datasources() {
        DatasetVersion datasetVersion30 = getDatasetVersion30WithDatasource();
        datasetVersion30.addDatasource(getDatasorce06LinkedToFileForDatasetVersion30());
    }

    protected static DatasetVersion getDatasetVersion32WithMultipleDatasourcesLinkedToFile() {
        if (DATASET_VERSION_32_WITH_MULTIPLE_DATASOURCES_LINKED_TO_FILE == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);
            DATASET_VERSION_32_WITH_MULTIPLE_DATASOURCES_LINKED_TO_FILE = datasetVersion;
            setDatasetVersion32Datasources();
        }
        return DATASET_VERSION_32_WITH_MULTIPLE_DATASOURCES_LINKED_TO_FILE;
    }

    private static void setDatasetVersion32Datasources() {
        DatasetVersion datasetVersion32 = getDatasetVersion32WithMultipleDatasourcesLinkedToFile();
        datasetVersion32.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        datasetVersion32.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
    }

    protected static DatasetVersion getDatasetVersion33WithSingleDatasourceLinkedToFileWithUnderscore() {
        if (DATASET_VERSION_33_WITH_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);
            DATASET_VERSION_33_WITH_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE = datasetVersion;
            setDatasetVersion33Datasources();
        }
        return DATASET_VERSION_33_WITH_SINGLE_DATASOURCE_LINKED_TO_FILE_WITH_UNDERSCORE;
    }

    private static void setDatasetVersion33Datasources() {
        DatasetVersion datasetVersion33 = getDatasetVersion33WithSingleDatasourceLinkedToFileWithUnderscore();
        datasetVersion33.addDatasource(getDatasorce07LinkedToFileWithUnderscore());
    }

    protected static DatasetVersion getDatasetVersion34ForImportInOperation0001() {
        if (DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001 == null) {
            DatasetVersionMock template = new DatasetVersionMock();
            template.setSequentialId(1);
            template.setStatisticalOperationCode("OPER_0001");
            template.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());

            DatasetVersion datasetVersion = createDatasetVersionFromTemplate(template);

            DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001 = datasetVersion;
        }
        return DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001;
    }

    protected static DatasetVersion getDatasetVersion35ForImportInOperation0001() {
        if (DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001 == null) {
            DatasetVersion datasetVersion = createDatasetVersionInSpecificOperation("OPER_0001", 2);
            datasetVersion.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
            DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001 = datasetVersion;
        }
        return DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001;
    }

    protected static DatasetVersion getDatasetVersion36ForImportInOperation0002() {
        if (DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002 == null) {
            DatasetVersion datasetVersion = createDatasetVersionInSpecificOperation("OPER_0002", 1);
            datasetVersion.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
            DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002 = datasetVersion;
        }
        return DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002;
    }

    protected static DatasetVersion getDatasetVersion37WithSingleDatasourceInOperation0001() {
        if (DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001 == null) {
            DatasetVersion datasetVersion = createDatasetVersionInSpecificOperation("OPER_0001", 1);
            datasetVersion.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
            DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001 = datasetVersion;
            datasetVersion.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        }
        return DATASET_VERSION_37_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001;
    }

    protected static DatasetVersion getDatasetVersion38WithSingleDatasourceInOperation0001() {
        if (DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001 == null) {
            DatasetVersion datasetVersion = createDatasetVersionInSpecificOperation("OPER_0001", 1);
            datasetVersion.setRelatedDsd(StatisticalResourcesDoMocks.mockDsdExternalItem());
            DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001 = datasetVersion;
            datasetVersion.addDatasource(DatasourceMockFactory.generateSimpleDatasource());
        }
        return DATASET_VERSION_38_WITH_SINGLE_DATASOURCE_IN_OPERATION_0001;
    }

    protected static DatasetVersion getDatasetVersion39VersionRationaleTypeMajorNewResource() {
        if (DATASET_VERSION_39_VERSION_RATIONALE_TYPE_MAJOR_NEW_RESOURCE == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
            DATASET_VERSION_39_VERSION_RATIONALE_TYPE_MAJOR_NEW_RESOURCE = datasetVersion;
        }
        return DATASET_VERSION_39_VERSION_RATIONALE_TYPE_MAJOR_NEW_RESOURCE;
    }

    protected static DatasetVersion getDatasetVersion40VersionRationaleTypeMajorEstimators() {
        if (DATASET_VERSION_40_VERSION_RATIONALE_TYPE_MAJOR_ESTIMATORS == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_ESTIMATORS));
            DATASET_VERSION_40_VERSION_RATIONALE_TYPE_MAJOR_ESTIMATORS = datasetVersion;
        }
        return DATASET_VERSION_40_VERSION_RATIONALE_TYPE_MAJOR_ESTIMATORS;
    }

    protected static DatasetVersion getDatasetVersion41VersionRationaleTypeMinorErratas() {
        if (DATASET_VERSION_41_VERSION_RATIONALE_TYPE_MINOR_ERRATAS == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MINOR_ERRATA));
            DATASET_VERSION_41_VERSION_RATIONALE_TYPE_MINOR_ERRATAS = datasetVersion;
        }
        return DATASET_VERSION_41_VERSION_RATIONALE_TYPE_MINOR_ERRATAS;
    }

    protected static DatasetVersion getDatasetVersion42VersionRationaleTypeMinorErratasAndMajorEstimators() {
        if (DATASET_VERSION_42_VERSION_RATIONALE_TYPE_MINOR_ERRATAS_AND_MAJOR_ESTIMATORS == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MINOR_ERRATA));
            datasetVersion.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().add(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_ESTIMATORS));
            DATASET_VERSION_42_VERSION_RATIONALE_TYPE_MINOR_ERRATAS_AND_MAJOR_ESTIMATORS = datasetVersion;
        }
        return DATASET_VERSION_42_VERSION_RATIONALE_TYPE_MINOR_ERRATAS_AND_MAJOR_ESTIMATORS;
    }

    protected static DatasetVersion getDatasetVersion43NextVersionNoUpdates() {
        if (DATASET_VERSION_43_NEXT_VERSION_NO_UPDATES == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersion(NextVersionTypeEnum.NO_UPDATES);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersionDate(null);
            DATASET_VERSION_43_NEXT_VERSION_NO_UPDATES = datasetVersion;
        }
        return DATASET_VERSION_43_NEXT_VERSION_NO_UPDATES;
    }

    protected static DatasetVersion getDatasetVersion44NextVersionNonScheduledUpdate() {
        if (DATASET_VERSION_44_NEXT_VERSION_NON_SCHEDULED_UPDATE == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersionDate(null);
            DATASET_VERSION_44_NEXT_VERSION_NON_SCHEDULED_UPDATE = datasetVersion;
        }
        return DATASET_VERSION_44_NEXT_VERSION_NON_SCHEDULED_UPDATE;
    }

    protected static DatasetVersion getDatasetVersion45NextVersionScheduledUpdateJanuary() {
        if (DATASET_VERSION_45_NEXT_VERSION_SCHEDULED_UPDATE_JANUARY == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersion(NextVersionTypeEnum.SCHEDULED_UPDATE);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersionDate(new DateTime(2013, 1, 15, 12, 0, 0, 0));
            DATASET_VERSION_45_NEXT_VERSION_SCHEDULED_UPDATE_JANUARY = datasetVersion;
        }
        return DATASET_VERSION_45_NEXT_VERSION_SCHEDULED_UPDATE_JANUARY;
    }

    protected static DatasetVersion getDatasetVersion46NextVersionScheduledUpdateJuly() {
        if (DATASET_VERSION_46_NEXT_VERSION_SCHEDULED_UPDATE_JULY == null) {
            DatasetVersion datasetVersion = createDatasetVersionEmpty();
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersion(NextVersionTypeEnum.SCHEDULED_UPDATE);
            datasetVersion.getSiemacMetadataStatisticalResource().setNextVersionDate(new DateTime(2013, 7, 15, 12, 0, 0, 0));
            DATASET_VERSION_46_NEXT_VERSION_SCHEDULED_UPDATE_JULY = datasetVersion;
        }
        return DATASET_VERSION_46_NEXT_VERSION_SCHEDULED_UPDATE_JULY;
    }

    protected static DatasetVersion getDatasetVersion47WithCoverageFilledWithTitles() {
        if (DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(2);

            prepareToProductionValidation(datasetVersion);

            datasetVersion.getDimensionsCoverage().clear();
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2011", "2011"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010", "2010"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010-M02", "Febrero 2010"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010-M01", "Enero 2010"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES", "España"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES61", "Andalucia"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES70", "Canarias"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES45", "Cataluña"));

            DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES = datasetVersion;
        }
        return DATASET_VERSION_47_WITH_COVERAGE_FILLED_WITH_TITLES;
    }

    protected static DatasetVersion getDatasetVersion48WithTemporalCoverageFilled() {
        if (DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);

            prepareToProductionValidation(datasetVersion);

            DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED = datasetVersion;
        }
        return DATASET_VERSION_48_WITH_TEMPORAL_COVERAGE_FILLED;
    }

    protected static DatasetVersion getDatasetVersion49WithDatasourceFromPxWithNextUpdateInOneMonth() {
        if (DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);

            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2012", "2012"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2011", "2011"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010", "2010"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES", "España"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES61", "Andalucia"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES70", "Canarias"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES45", "Cataluña"));

            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2012", "2012"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2011", "2011"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2010", "2010"));

            DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH = datasetVersion;
            setDatasetVersion49Datasources(datasetVersion);
            datasetVersion.setDateNextUpdate(datasetVersion.getDatasources().get(0).getDateNextUpdate());
            datasetVersion.setUserModifiedDateNextUpdate(false);
        }
        return DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH;
    }

    private static void setDatasetVersion49Datasources(DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(DatasourceMockFactory.generatePxDatasource(new DateTime().plusMonths(1)));
    }

    protected static DatasetVersion getDatasetVersion50WithDatasourceFromPxWithUserNextUpdateInOneMonth() {
        if (DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);

            datasetVersion.setDateNextUpdate(new DateTime().plusMonths(1));
            datasetVersion.setUserModifiedDateNextUpdate(true);

            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2012", "2012"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2011", "2011"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010", "2010"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES", "España"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES61", "Andalucia"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES70", "Canarias"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES45", "Cataluña"));

            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2012", "2012"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2011", "2011"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2010", "2010"));

            DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH = datasetVersion;
            setDatasetVersion50Datasources(datasetVersion);
        }
        return DATASET_VERSION_50_WITH_DATASOURCE_FROM_PX_WITH_USER_NEXT_UPDATE_IN_ONE_MONTH;
    }

    private static void setDatasetVersion50Datasources(DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(DatasourceMockFactory.generatePxDatasource(new DateTime().plusMonths(1)));
    }

    protected static DatasetVersion getDatasetVersion51InDraftWithDatasource() {
        if (DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE == null) {
            DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DRAFT);
        }
        return DATASET_VERSION_51_IN_DRAFT_WITH_DATASOURCE;
    }

    protected static DatasetVersion getDatasetVersion52InProductionValidationWithDatasource() {
        if (DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE == null) {
            DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.PRODUCTION_VALIDATION);
        }
        return DATASET_VERSION_52_IN_PRODUCTION_VALIDATION_WITH_DATASOURCE;
    }

    protected static DatasetVersion getDatasetVersion53InDiffusionValidationWithDatasource() {
        if (DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE == null) {
            DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DIFFUSION_VALIDATION);
        }
        return DATASET_VERSION_53_IN_DIFFUSION_VALIDATION_WITH_DATASOURCE;
    }

    protected static DatasetVersion getDatasetVersion54InValidationRejectedWithDatasource() {
        if (DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE == null) {
            DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.VALIDATION_REJECTED);
        }
        return DATASET_VERSION_54_IN_VALIDATION_REJECTED_WITH_DATASOURCE;
    }

    protected static DatasetVersion getDatasetVersion55PublishedWithDatasource() {
        if (DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE == null) {
            DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.PUBLISHED);
        }
        return DATASET_VERSION_55_PUBLISHED_WITH_DATASOURCE;
    }

    protected static DatasetVersion getDatasetVersion56DraftWithDatasourceAndQueries() {
        if (DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES == null) {
            DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DRAFT);
        }
        return DATASET_VERSION_56_DRAFT_WITH_DATASOURCE_AND_QUERIES;
    }

    protected static DatasetVersion getDatasetVersion57DraftInitialVersion() {
        if (DATASET_VERSION_57_DRAFT_INITIAL_VERSION == null) {
            DATASET_VERSION_57_DRAFT_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DRAFT);
            DATASET_VERSION_57_DRAFT_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);
        }
        return DATASET_VERSION_57_DRAFT_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion58ProductionValidationInitialVersion() {
        if (DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION == null) {
            DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.PRODUCTION_VALIDATION);
            DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);
        }
        return DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion59DiffusionValidationInitialVersion() {
        if (DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION == null) {
            DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DIFFUSION_VALIDATION);
            DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);
        }
        return DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion60ValidationRejectedInitialVersion() {
        if (DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION == null) {
            DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.VALIDATION_REJECTED);
            DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);
        }
        return DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion61PublishedInitialVersion() {
        if (DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION == null) {
            DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.PUBLISHED);
            DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);
        }
        return DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion62DraftNotInitialVersion() {
        if (DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION == null) {
            DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DRAFT);
            DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(NOT_INITIAL_VERSION);
        }
        return DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion63ProductionValidationNotInitialVersion() {
        if (DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION == null) {
            DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.PRODUCTION_VALIDATION);
            DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(NOT_INITIAL_VERSION);
        }
        return DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion64DiffusionValidationNotInitialVersion() {
        if (DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION == null) {
            DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.DIFFUSION_VALIDATION);
            DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(NOT_INITIAL_VERSION);
        }
        return DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion65ValidationRejectedNotInitialVersion() {
        if (DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION == null) {
            DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.VALIDATION_REJECTED);
            DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(NOT_INITIAL_VERSION);
        }
        return DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion66PublishedNotInitialVersion() {
        if (DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION == null) {
            DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION = generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum.PUBLISHED);
            DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION.getSiemacMetadataStatisticalResource().setVersionLogic(NOT_INITIAL_VERSION);
        }
        return DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION;
    }

    protected static DatasetVersion getDatasetVersion67WithDatasourcesAndComputedFieldsFilled() {
        if (DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);

            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2012", "2012"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2011", "2011"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010", "2010"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES", "España"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES61", "Andalucia"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES70", "Canarias"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES45", "Cataluña"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C01", "Concept 01"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C02", "Concept 02"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C03", "Concept 03"));

            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2012", "2012"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2011", "2011"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2010", "2010"));

            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES"));
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES61"));
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES70"));
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES45"));

            datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C01"));
            datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C02"));
            datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C03"));

            datasetVersion.setDateStart(new DateTime(2010, 1, 31, 0, 0, 0, 0));
            datasetVersion.setDateEnd(new DateTime(2012, 12, 31, 23, 59, 59, 999));

            datasetVersion.setFormatExtentDimensions(3);
            datasetVersion.setFormatExtentObservations(36L);

            datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime().minusDays(1));

            datasetVersion.setDatasetRepositoryId(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

            DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED = datasetVersion;

            setDatasetVersion67Datasources(datasetVersion);
            datasetVersion.setDateNextUpdate(datasetVersion.getDatasources().get(0).getDateNextUpdate());
        }
        return DATASET_VERSION_67_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED;
    }

    private static void setDatasetVersion67Datasources(DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(DatasourceMockFactory.generatePxDatasource(new DateTime().plusMonths(1)));
    }

    protected static DatasetVersion getDatasetVersion68WithDatasourcesAndComputedFieldsFilledAndUserModifiedDateNextUpdate() {
        if (DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);

            datasetVersion.setDateNextUpdate(new DateTime().plusMonths(1));
            datasetVersion.setUserModifiedDateNextUpdate(true);

            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2012", "2012"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2011", "2011"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010", "2010"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES", "España"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES61", "Andalucia"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES70", "Canarias"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("GEO_DIM", "ES45", "Cataluña"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C01", "Concept 01"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C02", "Concept 02"));
            datasetVersion.addDimensionsCoverage(new CodeDimension("MEAS_DIM", "C03", "Concept 03"));

            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2012", "2012"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2011", "2011"));
            datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode("2010", "2010"));

            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES"));
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES61"));
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES70"));
            datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem("ES45"));

            datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C01"));
            datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C02"));
            datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem("C03"));

            datasetVersion.setDateStart(new DateTime(2010, 1, 31, 0, 0, 0, 0));
            datasetVersion.setDateEnd(new DateTime(2012, 12, 31, 23, 59, 59, 999));

            datasetVersion.setFormatExtentDimensions(3);
            datasetVersion.setFormatExtentObservations(36L);

            datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime().minusDays(1));

            DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE = datasetVersion;

            setDatasetVersion68Datasources(datasetVersion);
        }
        return DATASET_VERSION_68_WITH_DATASOURCES_AND_COMPUTED_FIELDS_FILLED_AND_USER_MODIFIED_DATE_NEXT_UPDATE;
    }

    private static void setDatasetVersion68Datasources(DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(DatasourceMockFactory.generatePxDatasource(new DateTime().plusMonths(1)));
    }

    private static DatasetVersion generateDatasetVersionInStatusWithGeneratedDatasource(ProcStatusEnum procStatus) {
        DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(procStatus);
        datasetVersion.addDatasource(DatasourceMockFactory.generateSimpleDatasource());

        switch (procStatus) {
            case DRAFT:
                prepareToProductionValidation(datasetVersion);
                break;
            case PRODUCTION_VALIDATION:
                prepareToDiffusionValidation(datasetVersion);
                break;
            case DIFFUSION_VALIDATION:
                prepareToPublished(datasetVersion);
                break;
            case VALIDATION_REJECTED:
                prepareToDiffusionValidation(datasetVersion);
                datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(procStatus);
                break;
            case PUBLISHED:
                datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().minusDays(2));
                prepareToVersioning(datasetVersion);
                break;
            case PUBLISHED_NOT_VISIBLE:
                datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(new DateTime().plusDays(2));
                prepareToVersioning(datasetVersion);
                break;
            default:
                break;
        }
        return datasetVersion;
    }

    protected static DatasetVersion getDatasetVersion69PublishedNoRootMaintainer() {
        if (DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER == null) {
            DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER = createDatasetVersionWithSequence(1);
            prepareToVersioning(DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER);
            DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER.getSiemacMetadataStatisticalResource().setMaintainer(StatisticalResourcesDoMocks.mockAgencyExternalItem("agency01", "SIEMAC.agency01"));
        }
        return DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER;
    }

    protected static DatasetVersion getDatasetVersion70PreparedToPublishExternalItemFull() {
        if (DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequence(1);
            prepareToPublished(datasetVersion);
            fillOptionalExternalItems(datasetVersion);
            DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL = datasetVersion;
        }
        return DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL;
    }

    protected static DatasetVersion getDatasetVersion71RelatedResourcesUnpublished() {
        if (DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequenceAndVersion(1, INIT_VERSION);
            prepareToPublished(datasetVersion);
            DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED = datasetVersion;

            fillOptionalRelatedResourcesNotPublished(datasetVersion);
        }
        return DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED;
    }

    protected static DatasetVersion getDatasetVersion72PreparedToPublishWithPreviousVersion() {
        if (DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION == null) {
            DatasetVersion datasetVersion = createDatasetVersionWithSequenceAndVersion(1, SECOND_VERSION);
            prepareToPublished(datasetVersion);

            DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION = datasetVersion;
            fillOptionalExternalItems(datasetVersion);
            fillOptionalRelatedResourcesPublished(datasetVersion);
        }
        return DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION;
    }

    protected static DatasetVersion getDatasetVersion73V01ForDataset11() {
        if (DATASET_VERSION_73_V01_FOR_DATASET_11 == null) {
            DATASET_VERSION_73_V01_FOR_DATASET_11 = DatasetMockFactory.getDataset11WithTwoVersionsWith3QueriesFirstVersionIsPublishedSecondVersionIsNotVisibleAllQueriesCompatible().getVersions()
                    .get(0);
        }
        return DATASET_VERSION_73_V01_FOR_DATASET_11;
    }

    protected static DatasetVersion getDatasetVersion74V02ForDataset11() {
        if (DATASET_VERSION_74_V02_FOR_DATASET_11 == null) {
            DATASET_VERSION_74_V02_FOR_DATASET_11 = DatasetMockFactory.getDataset11WithTwoVersionsWith3QueriesFirstVersionIsPublishedSecondVersionIsNotVisibleAllQueriesCompatible().getVersions()
                    .get(1);
        }
        return DATASET_VERSION_74_V02_FOR_DATASET_11;
    }

    protected static DatasetVersion getDatasetVersion75V01ForDataset12() {
        if (DATASET_VERSION_75_V01_FOR_DATASET_12 == null) {
            DATASET_VERSION_75_V01_FOR_DATASET_12 = DatasetMockFactory.getDataset12WithTwoVersionsWithQueriesInDraft().getVersions().get(0);
        }
        return DATASET_VERSION_75_V01_FOR_DATASET_12;
    }

    protected static DatasetVersion getDatasetVersion76V02ForDataset12() {
        if (DATASET_VERSION_76_V02_FOR_DATASET_12 == null) {
            DATASET_VERSION_76_V02_FOR_DATASET_12 = DatasetMockFactory.getDataset12WithTwoVersionsWithQueriesInDraft().getVersions().get(1);
        }
        return DATASET_VERSION_76_V02_FOR_DATASET_12;
    }

    // -----------------------------------------------------------------
    // PRIVATE UTILS
    // -----------------------------------------------------------------

    private static void fillDatasetVersionInProductionValidation(DatasetVersion datasetVersion) {
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
    }

    private static DatasetVersion createDatasetVersionWithSequence(Integer sequentialId) {
        return createDatasetVersionWithSequenceAndVersion(sequentialId, INIT_VERSION);
    }

    private static DatasetVersion createDatasetVersionWithSequenceAndVersion(Integer sequentialId, String version) {
        DatasetVersionMock template = new DatasetVersionMock();
        template.setSequentialId(sequentialId);
        template.setVersionLogic(version);
        return createDatasetVersionFromTemplate(template);
    }

    private static DatasetVersion createDatasetVersionEmpty() {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();
    }

    private static DatasetVersion createDatasetVersionInSpecificOperation(String operationCode, Integer sequentialId) {
        DatasetVersionMock template = new DatasetVersionMock();
        template.setSequentialId(sequentialId);
        template.setStatisticalOperationCode(operationCode);

        return createDatasetVersionFromTemplate(template);
    }

    private static DatasetVersion createDatasetVersionFromTemplate(DatasetVersionMock template) {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetVersion(template);
    }

    // -----------------------------------------------------------------
    // LIFE CYCLE PREPARATIONS
    // -----------------------------------------------------------------

    private static void fillOptionalRelatedResourcesNotPublished(DatasetVersion datasetVersion) {
        datasetVersion.getSiemacMetadataStatisticalResource().setReplaces(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(createDatasetVersionWithSequence(2)));
    }

    private static void fillOptionalRelatedResourcesPublished(DatasetVersion datasetVersion) {
        DateTime currentValidFrom = datasetVersion.getSiemacMetadataStatisticalResource().getValidFrom();

        DatasetVersionMock replacesVersionTemplate = new DatasetVersionMock();
        replacesVersionTemplate.setSequentialId(1);
        replacesVersionTemplate.setVersionLogic(INIT_VERSION);
        replacesVersionTemplate.setDataset(datasetVersion.getDataset());
        replacesVersionTemplate.getSiemacMetadataStatisticalResource().setValidFrom(currentValidFrom.minusDays(3));
        replacesVersionTemplate.getSiemacMetadataStatisticalResource().setPublicationDate(currentValidFrom.minusDays(3));

        DatasetVersion previousDatasetVersion = createDatasetVersionFromTemplate(replacesVersionTemplate);

        DatasetLifecycleTestUtils.prepareToVersioning(previousDatasetVersion);

        datasetVersion.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(previousDatasetVersion));

        DatasetVersionMock replacesTemplate = new DatasetVersionMock();
        replacesTemplate.setSequentialId(2);
        replacesVersionTemplate.setVersionLogic(INIT_VERSION);
        replacesTemplate.getSiemacMetadataStatisticalResource().setValidFrom(currentValidFrom.minusDays(3));
        replacesTemplate.getSiemacMetadataStatisticalResource().setPublicationDate(currentValidFrom.minusDays(3));
        DatasetVersion replacesDatasetVersion = createDatasetVersionFromTemplate(replacesTemplate);

        prepareToVersioning(replacesDatasetVersion);

        datasetVersion.getSiemacMetadataStatisticalResource().setReplaces(StatisticalResourcesPersistedDoMocks.mockDatasetVersionRelated(replacesDatasetVersion));
    }

    private static void fillOptionalExternalItems(DatasetVersion datasetVersion) {
        LifecycleTestUtils.fillOptionalExternalItemsSiemacResource(datasetVersion);

        datasetVersion.getStatisticalUnit().clear();
        datasetVersion.addStatisticalUnit(StatisticalResourcesPersistedDoMocks.mockConceptExternalItem());
        datasetVersion.addStatisticalUnit(StatisticalResourcesPersistedDoMocks.mockConceptExternalItem());
    }

}
