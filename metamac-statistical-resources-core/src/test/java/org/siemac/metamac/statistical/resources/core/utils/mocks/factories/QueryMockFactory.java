package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends StatisticalResourcesMockFactory<Query> {

    public static final String QUERY_01_WITH_SELECTION_NAME         = "QUERY_01_WITH_SELECTION";
    private static Query       QUERY_01_WITH_SELECTION;

    public static final String QUERY_02_BASIC_ORDERED_01_NAME       = "QUERY_02_BASIC_ORDERED_01";
    private static Query       QUERY_02_BASIC_ORDERED_01;

    public static final String QUERY_03_BASIC_ORDERED_02_NAME       = "QUERY_03_BASIC_ORDERED_02";
    private static Query       QUERY_03_BASIC_ORDERED_02;

    public static final String QUERY_04_BASIC_ORDERED_03_NAME       = "QUERY_04_BASIC_ORDERED_03";
    private static Query       QUERY_04_BASIC_ORDERED_03;

    public static final String QUERY_05_BASIC_NAME                  = "QUERY_05_BASIC";
    private static Query       QUERY_05_BASIC;

    public static final String QUERY_06_BASIC_ACTIVE_NAME           = "QUERY_06_BASIC_ACTIVE";
    private static Query       QUERY_06_BASIC_ACTIVE;

    public static final String QUERY_07_BASIC_ACTIVE_NAME           = "QUERY_07_BASIC_ACTIVE";
    private static Query       QUERY_07_BASIC_ACTIVE;

    public static final String QUERY_08_BASIC_DISCONTINUED_NAME     = "QUERY_08_BASIC_DISCONTINUED";
    private static Query       QUERY_08_BASIC_DISCONTINUED;

    public static final String QUERY_09_BASIC_PENDING_REVIEW_NAME   = "QUERY_09_BASIC_PENDING_REVIEW";
    private static Query       QUERY_09_BASIC_PENDING_REVIEW;

    public static final String QUERY_10_ACTIVE_LATEST_DATA_5_NAME   = "QUERY_10_ACTIVE_LATEST_DATA_5";
    private static Query       QUERY_10_ACTIVE_LATEST_DATA_5;

    public static final String QUERY_11_DRAFT_NAME                  = "QUERY_11_DRAFT";
    private static Query       QUERY_11_DRAFT;

    public static final String QUERY_12_PRODUCTION_VALIDATION_NAME  = "QUERY_12_PRODUCTION_VALIDATION";
    private static Query       QUERY_12_PRODUCTION_VALIDATION;

    public static final String QUERY_13_DIFUSSION_VALIDATION_NAME   = "QUERY_13_DIFUSSION_VALIDATION";
    private static Query       QUERY_13_DIFUSSION_VALIDATION;

    public static final String QUERY_14_VALIDATION_REJECTED_NAME    = "QUERY_14_VALIDATION_REJECTED";
    private static Query       QUERY_14_VALIDATION_REJECTED;

    public static final String QUERY_15_PUBLICATION_PROGRAMMED_NAME = "QUERY_15_PUBLICATION_PROGRAMMED";
    private static Query       QUERY_15_PUBLICATION_PROGRAMMED;

    public static final String QUERY_16_PUBLICATION_PENDING_NAME    = "QUERY_16_PUBLICATION_PENDING";
    private static Query       QUERY_16_PUBLICATION_PENDING;

    public static final String QUERY_17_PUBLICATION_FAILED_NAME     = "QUERY_17_PUBLICATION_FAILED";
    private static Query       QUERY_17_PUBLICATION_FAILED;

    public static final String QUERY_18_PUBLISHED_NAME              = "QUERY_18_PUBLISHED";
    private static Query       QUERY_18_PUBLISHED;

    public static final String QUERY_19_ARCHIVED_NAME               = "QUERY_19_ARCHIVED";
    private static Query       QUERY_19_ARCHIVED;

    protected static Query getQuery01WithSelection() {
        if (QUERY_01_WITH_SELECTION == null) {
            QUERY_01_WITH_SELECTION = createQueryWithSelectionAndGeneratedDatasetVersion();
        }
        return QUERY_01_WITH_SELECTION;
    }

    protected static Query getQuery02BasicOrdered01() {
        if (QUERY_02_BASIC_ORDERED_01 == null) {
            QUERY_02_BASIC_ORDERED_01 = createQueryWithGeneratedDatasetVersion();
            QUERY_02_BASIC_ORDERED_01.getLifeCycleStatisticalResource().setCode("a");
        }
        return QUERY_02_BASIC_ORDERED_01;
    }

    protected static Query getQuery03BasicOrdered02() {
        if (QUERY_03_BASIC_ORDERED_02 == null) {
            QUERY_03_BASIC_ORDERED_02 = createQueryWithGeneratedDatasetVersion();
            QUERY_03_BASIC_ORDERED_02.getLifeCycleStatisticalResource().setCode("b");
        }
        return QUERY_03_BASIC_ORDERED_02;
    }

    protected static Query getQuery04BasicOrdered03() {
        if (QUERY_04_BASIC_ORDERED_03 == null) {
            QUERY_04_BASIC_ORDERED_03 = createQueryWithGeneratedDatasetVersion();
            QUERY_04_BASIC_ORDERED_03.getLifeCycleStatisticalResource().setCode("c");
        }
        return QUERY_04_BASIC_ORDERED_03;
    }

    protected static Query getQuery05Basic() {
        if (QUERY_05_BASIC == null) {
            QUERY_05_BASIC = createQueryWithGeneratedDatasetVersion();
        }
        return QUERY_05_BASIC;
    }

    protected static Query getQuery06BasicActive() {
        if (QUERY_06_BASIC_ACTIVE == null) {
            QUERY_06_BASIC_ACTIVE = createQueryWithGeneratedDatasetVersion();
            QUERY_06_BASIC_ACTIVE.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_06_BASIC_ACTIVE;
    }

    protected static Query getQuery07BasicActive() {
        if (QUERY_07_BASIC_ACTIVE == null) {
            QUERY_07_BASIC_ACTIVE = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion04ForDataset03AndLastVersion());
            QUERY_07_BASIC_ACTIVE.setStatus(QueryStatusEnum.ACTIVE);
        }
        return QUERY_07_BASIC_ACTIVE;
    }

    protected static Query getQuery08BasicDiscontinued() {
        if (QUERY_08_BASIC_DISCONTINUED == null) {
            QUERY_08_BASIC_DISCONTINUED = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion03ForDataset03());
            QUERY_08_BASIC_DISCONTINUED.setStatus(QueryStatusEnum.DISCONTINUED);
        }
        return QUERY_08_BASIC_DISCONTINUED;
    }

    protected static Query getQuery09BasicPendingReview() {
        if (QUERY_09_BASIC_PENDING_REVIEW == null) {
            QUERY_09_BASIC_PENDING_REVIEW = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion03ForDataset03());
            QUERY_09_BASIC_PENDING_REVIEW.setStatus(QueryStatusEnum.PENDING_REVIEW);
        }
        return QUERY_09_BASIC_PENDING_REVIEW;
    }

    public static Query getQuery10ActiveLatestData5() {
        if (QUERY_10_ACTIVE_LATEST_DATA_5 == null) {
            QUERY_10_ACTIVE_LATEST_DATA_5 = createQueryWithDatasetVersion(DatasetVersionMockFactory.getDatasetVersion04ForDataset03AndLastVersion());
            QUERY_10_ACTIVE_LATEST_DATA_5.setType(QueryTypeEnum.LATEST_DATA);
            QUERY_10_ACTIVE_LATEST_DATA_5.setLatestDataNumber(Integer.valueOf(5));
        }
        return QUERY_10_ACTIVE_LATEST_DATA_5;
    }

    protected static Query getQuery11Draft() {
        if (QUERY_11_DRAFT == null) {
            QUERY_11_DRAFT = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_11_DRAFT.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        }
        return QUERY_11_DRAFT;
    }

    protected static Query getQuery12ProductionValidation() {
        if (QUERY_12_PRODUCTION_VALIDATION == null) {
            QUERY_12_PRODUCTION_VALIDATION = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_12_PRODUCTION_VALIDATION.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);
        }
        return QUERY_12_PRODUCTION_VALIDATION;
    }

    protected static Query getQuery13DifussionValidation() {
        if (QUERY_13_DIFUSSION_VALIDATION == null) {
            QUERY_13_DIFUSSION_VALIDATION = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_13_DIFUSSION_VALIDATION.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
        }
        return QUERY_13_DIFUSSION_VALIDATION;
    }

    protected static Query getQuery14ValidationRejected() {
        if (QUERY_14_VALIDATION_REJECTED == null) {
            QUERY_14_VALIDATION_REJECTED = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_14_VALIDATION_REJECTED.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);
        }
        return QUERY_14_VALIDATION_REJECTED;
    }

    protected static Query getQuery15PublicationProgrammed() {
        if (QUERY_15_PUBLICATION_PROGRAMMED == null) {
            QUERY_15_PUBLICATION_PROGRAMMED = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_15_PUBLICATION_PROGRAMMED.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED);
        }
        return QUERY_15_PUBLICATION_PROGRAMMED;
    }

    protected static Query getQuery16PublicationPending() {
        if (QUERY_16_PUBLICATION_PENDING == null) {
            QUERY_16_PUBLICATION_PENDING = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_16_PUBLICATION_PENDING.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLICATION_PENDING);
        }
        return QUERY_16_PUBLICATION_PENDING;
    }

    protected static Query getQuery17PublicationFailed() {
        if (QUERY_17_PUBLICATION_FAILED == null) {
            QUERY_17_PUBLICATION_FAILED = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_17_PUBLICATION_FAILED.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLICATION_FAILED);
        }
        return QUERY_17_PUBLICATION_FAILED;
    }

    protected static Query getQuery18Published() {
        if (QUERY_18_PUBLISHED == null) {
            QUERY_18_PUBLISHED = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_18_PUBLISHED.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);

        }
        return QUERY_18_PUBLISHED;
    }

    protected static Query getQuery19Archived() {
        if (QUERY_19_ARCHIVED == null) {
            QUERY_19_ARCHIVED = createQueryWithSelectionAndGeneratedDatasetVersion();
            QUERY_19_ARCHIVED.getLifeCycleStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.ARCHIVED);
        }
        return QUERY_19_ARCHIVED;
    }

    
    private static Query createQueryWithDatasetVersion(DatasetVersion datasetVersion) {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithDatasetVersion(datasetVersion);
    }
    
    private static Query createQueryWithGeneratedDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithGeneratedDatasetVersion();
    }

    private static Query createQueryWithSelectionAndGeneratedDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithSelectionAndGeneratedDatasetVersion();
    }
}