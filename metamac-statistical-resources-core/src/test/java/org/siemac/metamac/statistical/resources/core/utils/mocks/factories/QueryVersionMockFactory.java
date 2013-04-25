package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.springframework.stereotype.Component;

@Component
public class QueryVersionMockFactory extends StatisticalResourcesMockFactory<QueryVersion> {

    public static final String  QUERY_VERSION_01_WITH_SELECTION_NAME            = "QUERY_VERSION_01_WITH_SELECTION";
    private static QueryVersion QUERY_VERSION_01_WITH_SELECTION;

    public static final String  QUERY_VERSION_02_BASIC_ORDERED_01_NAME          = "QUERY_VERSION_02_BASIC_ORDERED_01";
    private static QueryVersion QUERY_VERSION_02_BASIC_ORDERED_01;

    public static final String  QUERY_VERSION_03_BASIC_ORDERED_02_NAME          = "QUERY_VERSION_03_BASIC_ORDERED_02";
    private static QueryVersion QUERY_VERSION_03_BASIC_ORDERED_02;

    public static final String  QUERY_VERSION_04_BASIC_ORDERED_03_NAME          = "QUERY_VERSION_04_BASIC_ORDERED_03";
    private static QueryVersion QUERY_VERSION_04_BASIC_ORDERED_03;

    public static final String  QUERY_VERSION_05_BASIC_NAME                     = "QUERY_VERSION_05_BASIC";
    private static QueryVersion QUERY_VERSION_05_BASIC;

    public static final String  QUERY_VERSION_06_BASIC_ACTIVE_NAME              = "QUERY_VERSION_06_BASIC_ACTIVE";
    private static QueryVersion QUERY_VERSION_06_BASIC_ACTIVE;

    public static final String  QUERY_VERSION_07_BASIC_ACTIVE_NAME              = "QUERY_VERSION_07_BASIC_ACTIVE";
    private static QueryVersion QUERY_VERSION_07_BASIC_ACTIVE;

    public static final String  QUERY_VERSION_08_BASIC_DISCONTINUED_NAME        = "QUERY_VERSION_08_BASIC_DISCONTINUED";
    private static QueryVersion QUERY_VERSION_08_BASIC_DISCONTINUED;

    public static final String  QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME      = "QUERY_VERSION_09_BASIC_PENDING_REVIEW";
    private static QueryVersion QUERY_VERSION_09_BASIC_PENDING_REVIEW;

    public static final String  QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME      = "QUERY_VERSION_10_ACTIVE_LATEST_DATA_5";
    private static QueryVersion QUERY_VERSION_10_ACTIVE_LATEST_DATA_5;

    public static final String  QUERY_VERSION_11_DRAFT_NAME                     = "QUERY_VERSION_11_DRAFT";
    private static QueryVersion QUERY_VERSION_11_DRAFT;

    public static final String  QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME     = "QUERY_VERSION_12_PRODUCTION_VALIDATION";
    private static QueryVersion QUERY_VERSION_12_PRODUCTION_VALIDATION;

    public static final String  QUERY_VERSION_13_DIFUSSION_VALIDATION_NAME      = "QUERY_VERSION_13_DIFUSSION_VALIDATION";
    private static QueryVersion QUERY_VERSION_13_DIFUSSION_VALIDATION;

    public static final String  QUERY_VERSION_14_VALIDATION_REJECTED_NAME       = "QUERY_VERSION_14_VALIDATION_REJECTED";
    private static QueryVersion QUERY_VERSION_14_VALIDATION_REJECTED;

    public static final String  QUERY_VERSION_15_PUBLISHED_NAME                 = "QUERY_VERSION_15_PUBLISHED";
    private static QueryVersion QUERY_VERSION_15_PUBLISHED;

    public static final String  QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME = "QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01";
    private static QueryVersion QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01;

    public static final String  QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME = "QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02";
    private static QueryVersion QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02;

    protected static QueryVersion getQueryVersion01WithSelection() {
        if (QUERY_VERSION_01_WITH_SELECTION == null) {
            QUERY_VERSION_01_WITH_SELECTION = createQueryWithGeneratedDatasetVersion();
        }
        return QUERY_VERSION_01_WITH_SELECTION;
    }

    protected static QueryVersion getQueryVersion02BasicOrdered01() {
        if (QUERY_VERSION_02_BASIC_ORDERED_01 == null) {
            QUERY_VERSION_02_BASIC_ORDERED_01 = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_02_BASIC_ORDERED_01.getLifeCycleStatisticalResource().setCode("a");
        }
        return QUERY_VERSION_02_BASIC_ORDERED_01;
    }

    protected static QueryVersion getQueryVersion03BasicOrdered02() {
        if (QUERY_VERSION_03_BASIC_ORDERED_02 == null) {
            QUERY_VERSION_03_BASIC_ORDERED_02 = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_03_BASIC_ORDERED_02.getLifeCycleStatisticalResource().setCode("b");
        }
        return QUERY_VERSION_03_BASIC_ORDERED_02;
    }

    protected static QueryVersion getQueryVersion04BasicOrdered03() {
        if (QUERY_VERSION_04_BASIC_ORDERED_03 == null) {
            QUERY_VERSION_04_BASIC_ORDERED_03 = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_04_BASIC_ORDERED_03.getLifeCycleStatisticalResource().setCode("c");
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

    public static QueryVersion getQueryVersion10ActiveLatestData5() {
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
            QUERY_VERSION_11_DRAFT.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        }
        return QUERY_VERSION_11_DRAFT;
    }

    protected static QueryVersion getQueryVersion12ProductionValidation() {
        if (QUERY_VERSION_12_PRODUCTION_VALIDATION == null) {
            QUERY_VERSION_12_PRODUCTION_VALIDATION = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_12_PRODUCTION_VALIDATION.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        }
        return QUERY_VERSION_12_PRODUCTION_VALIDATION;
    }

    protected static QueryVersion getQueryVersion13DifussionValidation() {
        if (QUERY_VERSION_13_DIFUSSION_VALIDATION == null) {
            QUERY_VERSION_13_DIFUSSION_VALIDATION = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_13_DIFUSSION_VALIDATION.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        }
        return QUERY_VERSION_13_DIFUSSION_VALIDATION;
    }

    protected static QueryVersion getQueryVersion14ValidationRejected() {
        if (QUERY_VERSION_14_VALIDATION_REJECTED == null) {
            QUERY_VERSION_14_VALIDATION_REJECTED = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_14_VALIDATION_REJECTED.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.VALIDATION_REJECTED);
        }
        return QUERY_VERSION_14_VALIDATION_REJECTED;
    }

    protected static QueryVersion getQueryVersion15Published() {
        if (QUERY_VERSION_15_PUBLISHED == null) {
            QUERY_VERSION_15_PUBLISHED = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_15_PUBLISHED.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

        }
        return QUERY_VERSION_15_PUBLISHED;
    }

    public static QueryVersion getQueryVersion19WithCodeAndUrnQuery01() {
        if (QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01 == null) {
            String queryCode = "QUERY01";
            QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01 = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01.getLifeCycleStatisticalResource().setCode(queryCode);
            QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01.getLifeCycleStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(queryCode));
        }
        return QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01;
    }

    public static QueryVersion getQueryVersion20WithCodeAndUrnQuery02() {
        if (QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02 == null) {
            String queryCode = "QUERY02";
            QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02 = createQueryWithGeneratedDatasetVersion();
            QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02.getLifeCycleStatisticalResource().setCode(queryCode);
            QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02.getLifeCycleStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(queryCode));
        }
        return QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02;
    }

    private static QueryVersion createQueryWithDatasetVersion(DatasetVersion datasetVersion, boolean isLastDatasetVersion) {
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersionWithDatasetVersion(datasetVersion, isLastDatasetVersion);
    }

    private static QueryVersion createQueryWithGeneratedDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersionWithGeneratedDatasetVersion();
    }

}