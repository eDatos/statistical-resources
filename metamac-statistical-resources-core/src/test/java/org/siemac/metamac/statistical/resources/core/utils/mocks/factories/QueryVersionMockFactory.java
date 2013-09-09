package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class QueryVersionMockFactory extends StatisticalResourcesMockFactory<QueryVersion> {

    public static final String  QUERY_VERSION_01_WITH_SELECTION_NAME                       = "QUERY_VERSION_01_WITH_SELECTION";
    private static QueryVersion QUERY_VERSION_01_WITH_SELECTION;

    public static final String  QUERY_VERSION_02_BASIC_ORDERED_01_NAME                     = "QUERY_VERSION_02_BASIC_ORDERED_01";
    private static QueryVersion QUERY_VERSION_02_BASIC_ORDERED_01;

    public static final String  QUERY_VERSION_03_BASIC_ORDERED_02_NAME                     = "QUERY_VERSION_03_BASIC_ORDERED_02";
    private static QueryVersion QUERY_VERSION_03_BASIC_ORDERED_02;

    public static final String  QUERY_VERSION_04_BASIC_ORDERED_03_NAME                     = "QUERY_VERSION_04_BASIC_ORDERED_03";
    private static QueryVersion QUERY_VERSION_04_BASIC_ORDERED_03;

    public static final String  QUERY_VERSION_05_BASIC_NAME                                = "QUERY_VERSION_05_BASIC";
    private static QueryVersion QUERY_VERSION_05_BASIC;

    public static final String  QUERY_VERSION_06_BASIC_ACTIVE_NAME                         = "QUERY_VERSION_06_BASIC_ACTIVE";
    private static QueryVersion QUERY_VERSION_06_BASIC_ACTIVE;

    public static final String  QUERY_VERSION_07_BASIC_ACTIVE_NAME                         = "QUERY_VERSION_07_BASIC_ACTIVE";
    private static QueryVersion QUERY_VERSION_07_BASIC_ACTIVE;

    public static final String  QUERY_VERSION_08_BASIC_DISCONTINUED_NAME                   = "QUERY_VERSION_08_BASIC_DISCONTINUED";
    private static QueryVersion QUERY_VERSION_08_BASIC_DISCONTINUED;

    public static final String  QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME                 = "QUERY_VERSION_09_BASIC_PENDING_REVIEW";
    private static QueryVersion QUERY_VERSION_09_BASIC_PENDING_REVIEW;

    public static final String  QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME                 = "QUERY_VERSION_10_ACTIVE_LATEST_DATA_5";
    private static QueryVersion QUERY_VERSION_10_ACTIVE_LATEST_DATA_5;

    public static final String  QUERY_VERSION_11_DRAFT_NAME                                = "QUERY_VERSION_11_DRAFT";
    private static QueryVersion QUERY_VERSION_11_DRAFT;

    public static final String  QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME                = "QUERY_VERSION_12_PRODUCTION_VALIDATION";
    private static QueryVersion QUERY_VERSION_12_PRODUCTION_VALIDATION;

    public static final String  QUERY_VERSION_13_DIFFUSION_VALIDATION_NAME                 = "QUERY_VERSION_13_DIFFUSION_VALIDATION";
    private static QueryVersion QUERY_VERSION_13_DIFFUSION_VALIDATION;

    public static final String  QUERY_VERSION_14_VALIDATION_REJECTED_NAME                  = "QUERY_VERSION_14_VALIDATION_REJECTED";
    private static QueryVersion QUERY_VERSION_14_VALIDATION_REJECTED;

    public static final String  QUERY_VERSION_15_PUBLISHED_NAME                            = "QUERY_VERSION_15_PUBLISHED";
    private static QueryVersion QUERY_VERSION_15_PUBLISHED;

    public static final String  QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME            = "QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01";
    private static QueryVersion QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01;

    public static final String  QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME            = "QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02";
    private static QueryVersion QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02;

    public static final String  QUERY_VERSION_21_FOR_QUERY_03_NAME                         = "QUERY_VERSION_21_FOR_QUERY_03";
    private static QueryVersion QUERY_VERSION_21_FOR_QUERY_03;

    public static final String  QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME        = "QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION";
    private static QueryVersion QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION;

    public static final String  QUERY_VERSION_23_FOR_QUERY_04_NAME                         = "QUERY_VERSION_23_FOR_QUERY_04";
    private static QueryVersion QUERY_VERSION_23_FOR_QUERY_04;

    public static final String  QUERY_VERSION_24_V1_PUBLISHED_FOR_QUERY_05_NAME            = "QUERY_VERSION_24_V1_PUBLISHED_FOR_QUERY_05";
    private static QueryVersion QUERY_VERSION_24_V1_PUBLISHED_FOR_QUERY_05;

    public static final String  QUERY_VERSION_25_V2_PUBLISHED_FOR_QUERY_05_NAME            = "QUERY_VERSION_25_V2_PUBLISHED_FOR_QUERY_05";
    private static QueryVersion QUERY_VERSION_25_V2_PUBLISHED_FOR_QUERY_05;

    public static final String  QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05_NAME            = "QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05";
    private static QueryVersion QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05;

    public static final String  QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06_NAME            = "QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06";
    private static QueryVersion QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06;

    public static final String  QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06_NAME = "QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06";
    private static QueryVersion QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06;

    public static final String  QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56_NAME       = "QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56";
    private static QueryVersion QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56;

    public static final String  QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56_NAME       = "QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56";
    private static QueryVersion QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56;

    protected static QueryVersion getQueryVersion01WithSelection() {
        if (QUERY_VERSION_01_WITH_SELECTION == null) {
            QUERY_VERSION_01_WITH_SELECTION = createQueryWithGeneratedDatasetVersion();
        }
        return QUERY_VERSION_01_WITH_SELECTION;
    }

    protected static QueryVersion getQueryVersion02BasicOrdered01() {
        if (QUERY_VERSION_02_BASIC_ORDERED_01 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();

            String code = "a";
            String[] maintainerAgencyId = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
            queryVersion.getLifeCycleStatisticalResource().setCode(code);
            queryVersion.getLifeCycleStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerAgencyId, code, INIT_VERSION));
            QUERY_VERSION_02_BASIC_ORDERED_01 = queryVersion;
        }
        return QUERY_VERSION_02_BASIC_ORDERED_01;
    }

    protected static QueryVersion getQueryVersion03BasicOrdered02() {
        if (QUERY_VERSION_03_BASIC_ORDERED_02 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();

            String code = "b";
            String[] maintainerAgencyId = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
            queryVersion.getLifeCycleStatisticalResource().setCode(code);
            queryVersion.getLifeCycleStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerAgencyId, code, INIT_VERSION));
            QUERY_VERSION_03_BASIC_ORDERED_02 = queryVersion;
        }
        return QUERY_VERSION_03_BASIC_ORDERED_02;
    }

    protected static QueryVersion getQueryVersion04BasicOrdered03() {
        if (QUERY_VERSION_04_BASIC_ORDERED_03 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();

            String code = "c";
            String[] maintainerAgencyId = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
            queryVersion.getLifeCycleStatisticalResource().setCode(code);
            queryVersion.getLifeCycleStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerAgencyId, code, INIT_VERSION));
            QUERY_VERSION_04_BASIC_ORDERED_03 = queryVersion;
        }
        return QUERY_VERSION_04_BASIC_ORDERED_03;
    }

    protected static QueryVersion getQueryVersion05Basic() {
        if (QUERY_VERSION_05_BASIC == null) {
            QUERY_VERSION_05_BASIC = createQueryWithGeneratedDatasetVersion();
        }
        return QUERY_VERSION_05_BASIC;
    }

    protected static QueryVersion getQueryVersion06BasicActive() {
        if (QUERY_VERSION_06_BASIC_ACTIVE == null) {
            QUERY_VERSION_06_BASIC_ACTIVE = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_06_BASIC_ACTIVE.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_VERSION_06_BASIC_ACTIVE;
    }

    protected static QueryVersion getQueryVersion07BasicActive() {
        if (QUERY_VERSION_07_BASIC_ACTIVE == null) {
            QUERY_VERSION_07_BASIC_ACTIVE = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion04ForDataset03AndLastVersion(), true);
            QUERY_VERSION_07_BASIC_ACTIVE.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_VERSION_07_BASIC_ACTIVE;
    }

    protected static QueryVersion getQueryVersion08BasicDiscontinued() {
        if (QUERY_VERSION_08_BASIC_DISCONTINUED == null) {
            QUERY_VERSION_08_BASIC_DISCONTINUED = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion03ForDataset03(), false);
            QUERY_VERSION_08_BASIC_DISCONTINUED.setStatus(QueryStatusEnum.DISCONTINUED);
        }
        return QUERY_VERSION_08_BASIC_DISCONTINUED;
    }

    protected static QueryVersion getQueryVersion09BasicPendingReview() {
        if (QUERY_VERSION_09_BASIC_PENDING_REVIEW == null) {
            QUERY_VERSION_09_BASIC_PENDING_REVIEW = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion03ForDataset03(), true);
            QUERY_VERSION_09_BASIC_PENDING_REVIEW.setStatus(QueryStatusEnum.PENDING_REVIEW);
        }
        return QUERY_VERSION_09_BASIC_PENDING_REVIEW;
    }

    protected static QueryVersion getQueryVersion10ActiveLatestData5() {
        if (QUERY_VERSION_10_ACTIVE_LATEST_DATA_5 == null) {
            QUERY_VERSION_10_ACTIVE_LATEST_DATA_5 = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion04ForDataset03AndLastVersion(), true);
            QUERY_VERSION_10_ACTIVE_LATEST_DATA_5.setType(QueryTypeEnum.LATEST_DATA);
            QUERY_VERSION_10_ACTIVE_LATEST_DATA_5.setLatestDataNumber(Integer.valueOf(5));
        }
        return QUERY_VERSION_10_ACTIVE_LATEST_DATA_5;
    }

    protected static QueryVersion getQueryVersion11Draft() {
        if (QUERY_VERSION_11_DRAFT == null) {
            QUERY_VERSION_11_DRAFT = createQueryWithGeneratedDatasetVersion();
            prepareToProductionValidation(QUERY_VERSION_11_DRAFT);
        }
        return QUERY_VERSION_11_DRAFT;
    }

    protected static QueryVersion getQueryVersion12ProductionValidation() {
        if (QUERY_VERSION_12_PRODUCTION_VALIDATION == null) {
            QUERY_VERSION_12_PRODUCTION_VALIDATION = createQueryWithGeneratedDatasetVersion();
            prepareToDiffusionValidation(QUERY_VERSION_12_PRODUCTION_VALIDATION);
        }
        return QUERY_VERSION_12_PRODUCTION_VALIDATION;
    }

    protected static QueryVersion getQueryVersion13DiffusionValidation() {
        if (QUERY_VERSION_13_DIFFUSION_VALIDATION == null) {
            QUERY_VERSION_13_DIFFUSION_VALIDATION = createQueryWithGeneratedDatasetVersion();
            prepareToValidationRejected(QUERY_VERSION_13_DIFFUSION_VALIDATION);
            QUERY_VERSION_13_DIFFUSION_VALIDATION.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        }
        return QUERY_VERSION_13_DIFFUSION_VALIDATION;
    }

    protected static QueryVersion getQueryVersion14ValidationRejected() {
        if (QUERY_VERSION_14_VALIDATION_REJECTED == null) {
            QUERY_VERSION_14_VALIDATION_REJECTED = createQueryWithGeneratedDatasetVersion();
            prepareToDiffusionValidation(QUERY_VERSION_14_VALIDATION_REJECTED);
            QUERY_VERSION_14_VALIDATION_REJECTED.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.VALIDATION_REJECTED);
        }
        return QUERY_VERSION_14_VALIDATION_REJECTED;
    }

    protected static QueryVersion getQueryVersion15Published() {
        if (QUERY_VERSION_15_PUBLISHED == null) {
            QUERY_VERSION_15_PUBLISHED = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_15_PUBLISHED.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
            QUERY_VERSION_15_PUBLISHED.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(2));
        }
        return QUERY_VERSION_15_PUBLISHED;
    }

    protected static QueryVersion getQueryVersion19WithCodeAndUrnQuery01() {
        if (QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01 == null) {
            String queryCode = "QUERY01";
            QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01 = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01.getLifeCycleStatisticalResource().setCode(queryCode);
            String[] maintainer = new String[]{QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
            QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01.getLifeCycleStatisticalResource()
                    .setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainer, queryCode, INIT_VERSION));
        }
        return QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01;
    }

    protected static QueryVersion getQueryVersion20WithCodeAndUrnQuery02() {
        if (QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02 == null) {
            String queryCode = "QUERY02";
            QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02 = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02.getLifeCycleStatisticalResource().setCode(queryCode);
            String[] maintainer = new String[]{QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
            QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02.getLifeCycleStatisticalResource()
                    .setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainer, queryCode, INIT_VERSION));
        }
        return QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02;
    }

    protected static QueryVersion getQueryVersion20ForQuery03() {
        if (QUERY_VERSION_21_FOR_QUERY_03 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(INIT_VERSION);
            queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // not last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(2));
            queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(2));

            // Relations
            QUERY_VERSION_21_FOR_QUERY_03 = queryVersion;
            QUERY_VERSION_21_FOR_QUERY_03.setQuery(QueryMockFactory.getQuery03With2QueryVersions());
        }
        return QUERY_VERSION_21_FOR_QUERY_03;
    }

    protected static QueryVersion getQueryVersion21ForQuery03() {
        if (QUERY_VERSION_21_FOR_QUERY_03 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(INIT_VERSION);
            queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // not last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(2));
            queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(2));
            queryVersion.getLifeCycleStatisticalResource().setLastVersion(Boolean.FALSE);

            // Relations
            QUERY_VERSION_21_FOR_QUERY_03 = queryVersion;
            QUERY_VERSION_21_FOR_QUERY_03.setQuery(QueryMockFactory.getQuery03With2QueryVersions());
        }
        return QUERY_VERSION_21_FOR_QUERY_03;
    }

    protected static QueryVersion getQueryVersion22ForQuery03AndLastVersion() {
        if (QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();

            // Version 002.000
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(SECOND_VERSION);

            // Is last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            queryVersion.getLifeCycleStatisticalResource().setLastVersion(Boolean.TRUE);

            // Relations
            QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION = queryVersion;
            QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION.setQuery(QueryMockFactory.getQuery03With2QueryVersions());
        }
        return QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION;
    }

    protected static QueryVersion getQueryVersion23ForQuery04() {
        if (QUERY_VERSION_23_FOR_QUERY_04 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();

            // Version 001.000
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(INIT_VERSION);

            queryVersion.getLifeCycleStatisticalResource().setCreatedBy(StatisticalResourcesDoMocks.mockString(10));
            queryVersion.getLifeCycleStatisticalResource().setCreatedDate(StatisticalResourcesDoMocks.mockDateTime());
            queryVersion.getLifeCycleStatisticalResource().setLastUpdatedBy(StatisticalResourcesDoMocks.mockString(10));
            queryVersion.getLifeCycleStatisticalResource().setLastUpdated(StatisticalResourcesDoMocks.mockDateTime());

            // Relations
            QUERY_VERSION_23_FOR_QUERY_04 = queryVersion;
            QUERY_VERSION_23_FOR_QUERY_04.setQuery(QueryMockFactory.getQuery04With1QueryVersions());
        }
        return QUERY_VERSION_23_FOR_QUERY_04;
    }

    protected static QueryVersion getQueryVersion24V1PublishedForQuery05() {
        if (QUERY_VERSION_24_V1_PUBLISHED_FOR_QUERY_05 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(INIT_VERSION);
            queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // not last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(3));
            queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(3));

            // Relations
            QUERY_VERSION_24_V1_PUBLISHED_FOR_QUERY_05 = queryVersion;
            QUERY_VERSION_24_V1_PUBLISHED_FOR_QUERY_05.setQuery(QueryMockFactory.getQuery05WithMultiplePublishedVersions());
        }
        return QUERY_VERSION_24_V1_PUBLISHED_FOR_QUERY_05;
    }

    protected static QueryVersion getQueryVersion25V2PublishedForQuery05() {
        if (QUERY_VERSION_25_V2_PUBLISHED_FOR_QUERY_05 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(SECOND_VERSION);
            queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // not last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(2));
            queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(2));

            // Relations
            QUERY_VERSION_25_V2_PUBLISHED_FOR_QUERY_05 = queryVersion;
            QUERY_VERSION_25_V2_PUBLISHED_FOR_QUERY_05.setQuery(QueryMockFactory.getQuery05WithMultiplePublishedVersions());
        }
        return QUERY_VERSION_25_V2_PUBLISHED_FOR_QUERY_05;
    }

    protected static QueryVersion getQueryVersion26V3PublishedForQuery05() {
        if (QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(THIRD_VERSION);
            queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(1));

            // Relations
            QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05 = queryVersion;
            QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05.setQuery(QueryMockFactory.getQuery05WithMultiplePublishedVersions());
        }
        return QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05;
    }

    protected static QueryVersion getQueryVersion27V1PublishedForQuery06() {
        if (QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(INIT_VERSION);
            queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(1));

            // Relations
            QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06 = queryVersion;
            QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06.setQuery(QueryMockFactory.getQuery06WithMultiplePublishedVersionsAndLatestNoVisible());
        }
        return QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06;
    }

    protected static QueryVersion getQueryVersion28V2PublishedNoVisibleForQuery06() {
        if (QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06 == null) {
            QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
            queryVersion.getLifeCycleStatisticalResource().setVersionLogic(SECOND_VERSION);
            queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            // last version
            queryVersion.getLifeCycleStatisticalResource().setCreationDate(new DateTime());
            queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().plusDays(1));

            // Relations
            QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06 = queryVersion;
            QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06.setQuery(QueryMockFactory.getQuery06WithMultiplePublishedVersionsAndLatestNoVisible());
        }
        return QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06;

    }

    protected static QueryVersion getQueryVersion29SimpleForQuery07Dataset56() {
        if (QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56 == null) {
            QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56 = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion56DraftWithDatasourceAndQueries(), true);
            QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_VERSION_29_SIMPLE_FOR_QUERY_07_DATASET_56;
    }

    protected static QueryVersion getQueryVersion30SimpleForQuery07Dataset56() {
        if (QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56 == null) {
            QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56 = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion56DraftWithDatasourceAndQueries(), true);
            QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_VERSION_30_SIMPLE_FOR_QUERY_07_DATASET_56;
    }

    private static QueryVersion createQueryWithDatasetVersion(DatasetVersion datasetVersion, boolean isLastDatasetVersion) {
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersionWithDatasetVersion(datasetVersion, isLastDatasetVersion);
    }

    private static QueryVersion createQueryWithGeneratedDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersionWithGeneratedDatasetVersion();
    }

    // -----------------------------------------------------------------
    // LIFE CYCLE PREPARATIONS
    // -----------------------------------------------------------------

    private static void prepareToProductionValidation(QueryVersion queryVersion) {
        LifecycleTestUtils.prepareToProductionValidation(queryVersion);
        queryVersion.setType(QueryTypeEnum.FIXED);
        queryVersion.setStatus(QueryStatusEnum.ACTIVE);
    }

    private static void prepareToDiffusionValidation(QueryVersion queryVersion) {
        prepareToProductionValidation(queryVersion);
        LifecycleTestUtils.prepareToDiffusionValidation(queryVersion);
    }

    private static void prepareToValidationRejected(QueryVersion queryVersion) {
        prepareToProductionValidation(queryVersion);
        LifecycleTestUtils.prepareToValidationRejected(queryVersion);
    }

}