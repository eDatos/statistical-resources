package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.QueryLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockDescriptor;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

@MockProvider
@SuppressWarnings("unused")
public class QueryVersionMockFactory extends StatisticalResourcesMockFactory<QueryVersion> {

    public static final String             QUERY_VERSION_01_WITH_SELECTION_NAME                                         = "QUERY_VERSION_01_WITH_SELECTION";

    public static final String             QUERY_VERSION_02_BASIC_ORDERED_01_NAME                                       = "QUERY_VERSION_02_BASIC_ORDERED_01";

    public static final String             QUERY_VERSION_03_BASIC_ORDERED_02_NAME                                       = "QUERY_VERSION_03_BASIC_ORDERED_02";

    public static final String             QUERY_VERSION_04_BASIC_ORDERED_03_NAME                                       = "QUERY_VERSION_04_BASIC_ORDERED_03";

    public static final String             QUERY_VERSION_05_BASIC_NAME                                                  = "QUERY_VERSION_05_BASIC";

    public static final String             QUERY_VERSION_06_BASIC_ACTIVE_NAME                                           = "QUERY_VERSION_06_BASIC_ACTIVE";

    public static final String             QUERY_VERSION_07_BASIC_ACTIVE_NAME                                           = "QUERY_VERSION_07_BASIC_ACTIVE";

    public static final String             QUERY_VERSION_08_BASIC_DISCONTINUED_NAME                                     = "QUERY_VERSION_08_BASIC_DISCONTINUED";

    public static final String             QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME                                   = "QUERY_VERSION_09_BASIC_PENDING_REVIEW";

    public static final String             QUERY_VERSION_10_ACTIVE_LATEST_DATA_5_NAME                                   = "QUERY_VERSION_10_ACTIVE_LATEST_DATA_5";

    public static final String             QUERY_VERSION_11_DRAFT_NAME                                                  = "QUERY_VERSION_11_DRAFT";

    public static final String             QUERY_VERSION_12_PRODUCTION_VALIDATION_NAME                                  = "QUERY_VERSION_12_PRODUCTION_VALIDATION";

    public static final String             QUERY_VERSION_13_DIFFUSION_VALIDATION_NAME                                   = "QUERY_VERSION_13_DIFFUSION_VALIDATION";

    public static final String             QUERY_VERSION_14_VALIDATION_REJECTED_NAME                                    = "QUERY_VERSION_14_VALIDATION_REJECTED";

    public static final String             QUERY_VERSION_15_PUBLISHED_NAME                                              = "QUERY_VERSION_15_PUBLISHED";

    public static final String             QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01_NAME                              = "QUERY_VERSION_19_WITH_CODE_AND_URN_QUERY01";

    public static final String             QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02_NAME                              = "QUERY_VERSION_20_WITH_CODE_AND_URN_QUERY02";

    public static final String             QUERY_VERSION_21_FOR_QUERY_03_NAME                                           = "QUERY_VERSION_21_FOR_QUERY_03";

    public static final String             QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME                          = "QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION";

    public static final String             QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05_NAME                              = "QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05";

    public static final String             QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06_NAME                              = "QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06";

    public static final String             QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06_NAME                   = "QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06";

    public static final String             QUERY_VERSION_29_CHECK_COMPAT_DATASET_86_OK_NAME                             = "QUERY_VERSION_29_CHECK_COMPAT_DATASET_86_OK";

    public static final String             QUERY_VERSION_30_CHECK_COMPAT_DATASET_86_LESS_DIMENSIONS_NAME                = "QUERY_VERSION_30_CHECK_COMPAT_DATASET_86_LESS_DIMENSIONS";

    public static final String             QUERY_VERSION_31_CHECK_COMPAT_DATASET_86_MORE_DIMENSIONS_NAME                = "QUERY_VERSION_31_CHECK_COMPAT_DATASET_86_MORE_DIMENSIONS";

    public static final String             QUERY_VERSION_32_CHECK_COMPAT_DATASET_86_MORE_CODES_NAME                     = "QUERY_VERSION_32_CHECK_COMPAT_DATASET_86_MORE_CODES";

    public static final String             QUERY_VERSION_33_CHECK_COMPAT_DATASET_86_INVALID_LATEST_TEMPORAL_CODE_NAME   = "QUERY_VERSION_33_CHECK_COMPAT_DATASET_86_INVALID_LATEST_TEMPORAL_CODE";

    public static final String             QUERY_VERSION_34_CHECK_COMPAT_DATASET_87_INVALID_QUERY_TYPE_AUTOINC_NAME     = "QUERY_VERSION_34_CHECK_COMPAT_DATASET_87_INVALID_QUERY_TYPE_AUTOINC";

    public static final String             QUERY_VERSION_35_CHECK_COMPAT_DATASET_87_INVALID_QUERY_TYPE_LATEST_DATA_NAME = "QUERY_VERSION_35_CHECK_COMPAT_DATASET_87_INVALID_QUERY_TYPE_LATEST_DATA";

    public static final String             QUERY_VERSION_36_LINKED_TO_DATASET_NAME                                      = "QUERY_VERSION_36_LINKED_TO_DATASET";

    private static QueryVersionMockFactory instance                                                                     = null;

    private QueryVersionMockFactory() {
    }

    public static QueryVersionMockFactory getInstance() {
        if (instance == null) {
            instance = new QueryVersionMockFactory();
        }
        return instance;
    }

    private static QueryVersion getQueryVersion01WithSelection() {
        return createQueryWithGeneratedDatasetVersion();
    }

    private static QueryVersion getQueryVersion02BasicOrdered01() {
        return createQueryWithGeneratedDatasetVersion("a");
    }

    private static QueryVersion getQueryVersion03BasicOrdered02() {
        return createQueryWithGeneratedDatasetVersion("b");
    }

    private static QueryVersion getQueryVersion04BasicOrdered03() {
        return createQueryWithGeneratedDatasetVersion("c");
    }

    private static QueryVersion getQueryVersion05Basic() {
        return createQueryWithGeneratedDatasetVersion();
    }

    private static QueryVersion getQueryVersion06BasicActive() {
        return createQueryWithGeneratedDatasetVersion();
    }

    private static QueryVersion getQueryVersion07BasicActive() {
        DatasetVersion datasetVersion04 = getDatasetVersionMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        QueryVersion queryVersion = createQueryWithDatasetVersion(datasetVersion04, true);
        return queryVersion;
    }

    private static QueryVersion getQueryVersion08BasicDiscontinued() {
        DatasetVersion datasetVersion03 = getDatasetVersionMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        QueryVersion queryVersion = createQueryWithDatasetVersion(datasetVersion03, false);
        queryVersion.setStatus(QueryStatusEnum.DISCONTINUED);
        return queryVersion;
    }

    private static QueryVersion getQueryVersion09BasicPendingReview() {
        DatasetVersion datasetVersion03 = getDatasetVersionMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        QueryVersion queryVersion = createQueryWithDatasetVersion(datasetVersion03, true);
        queryVersion.setStatus(QueryStatusEnum.PENDING_REVIEW);
        return queryVersion;
    }

    private static QueryVersion getQueryVersion10ActiveLatestData5() {
        DatasetVersion datasetVersion04 = getDatasetVersionMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        QueryVersion queryVersion = createQueryWithDatasetVersion(datasetVersion04, true);
        queryVersion.setType(QueryTypeEnum.LATEST_DATA);
        queryVersion.setLatestDataNumber(Integer.valueOf(5));
        queryVersion.getSelection().clear();
        queryVersion.addSelection(buildSelectionItemWithDimensionAndCodes("DIM01", "C01", "C02"));
        queryVersion.addSelection(buildSelectionItemWithDimensionAndCodes("TIME_PERIOD"));
        return queryVersion;
    }

    private static QueryVersion getQueryVersion11Draft() {
        QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
        prepareToProductionValidation(queryVersion);
        return queryVersion;
    }

    private static QueryVersion getQueryVersion12ProductionValidation() {
        QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
        prepareToDiffusionValidation(queryVersion);
        return queryVersion;
    }

    private static QueryVersion getQueryVersion13DiffusionValidation() {
        QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
        prepareToValidationRejected(queryVersion);
        queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        return queryVersion;
    }

    private static QueryVersion getQueryVersion14ValidationRejected() {
        QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
        prepareToDiffusionValidation(queryVersion);
        queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.VALIDATION_REJECTED);
        return queryVersion;
    }

    private static QueryVersion getQueryVersion15Published() {
        QueryVersion queryVersion = createQueryWithGeneratedDatasetVersion();
        queryVersion.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        queryVersion.getLifeCycleStatisticalResource().setValidFrom(new DateTime().minusDays(2));
        return queryVersion;
    }

    private static QueryVersion getQueryVersion19WithCodeAndUrnQuery01() {
        QueryVersionMock queryMock = new QueryVersionMock();
        queryMock.getLifeCycleStatisticalResource().setCode("QUERY01");
        queryMock.getLifeCycleStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem("agency01", "agency01"));
        return createQueryWithGeneratedDatasetVersion(queryMock);
    }

    private static QueryVersion getQueryVersion20WithCodeAndUrnQuery02() {
        QueryVersionMock queryMock = new QueryVersionMock();
        queryMock.getLifeCycleStatisticalResource().setCode("QUERY02");
        queryMock.getLifeCycleStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem("agency01", "agency01"));
        return createQueryWithGeneratedDatasetVersion(queryMock);
    }

    private static MockDescriptor getQueryVersion28V2PublishedNoVisibleForQuery06() {
        MockDescriptor query = getQueryMockDescriptor(QueryMockFactory.QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME);
        return new MockDescriptor(getQueryVersionMock(QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06_NAME), query);

    }

    // Public builders
    public static QueryVersionMock buildQueryVersionMockSimple(String code) {
        QueryVersionMock template = new QueryVersionMock();
        template.getLifeCycleStatisticalResource().setCode(code);
        template.setFixedDatasetVersion(getStatisticalResourcesPersistedDoMocks().mockDatasetVersion());
        template.setStatus(QueryStatusEnum.ACTIVE);
        return template;
    }

    public static QuerySelectionItem buildSelectionItemWithDimensionAndCodes(String dimensionId, String... codes) {
        QuerySelectionItem item = new QuerySelectionItem();
        item.setDimension(dimensionId);
        for (String code : codes) {
            CodeItem codeItem = new CodeItem();
            codeItem.setCode(code);
            codeItem.setTitle(code);
            item.addCode(codeItem);
        }
        return item;
    }

    private static QueryVersion createQueryWithDatasetVersion(DatasetVersion datasetVersion, boolean isLastDatasetVersion) {
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersionWithDatasetVersion(datasetVersion, isLastDatasetVersion);
    }

    private static QueryVersion createQueryWithGeneratedDatasetVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersionWithGeneratedDatasetVersion();
    }

    private static QueryVersion createQueryWithGeneratedDatasetVersion(String code) {
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();
        StatisticalResourcesPersistedDoMocks.mockDatasetVersionCoveragesAndRelated(datasetVersion);

        QueryVersionMock template = new QueryVersionMock();
        template.setFixedDatasetVersion(datasetVersion);
        template.setStatus(QueryStatusEnum.ACTIVE);
        template.getLifeCycleStatisticalResource().setCode(code);
        return createQueryVersionFromTemplate(template);
    }

    private static QueryVersion createQueryWithGeneratedDatasetVersion(QueryVersionMock template) {
        DatasetVersion datasetVersion = getStatisticalResourcesPersistedDoMocks().mockDatasetVersion();
        StatisticalResourcesPersistedDoMocks.mockDatasetVersionCoveragesAndRelated(datasetVersion);
        template.setFixedDatasetVersion(datasetVersion);
        template.setStatus(QueryStatusEnum.ACTIVE);
        return createQueryVersionFromTemplate(template);
    }

    public static QueryVersion createQueryVersionFromTemplate(QueryVersionMock template) {
        return getStatisticalResourcesPersistedDoMocks().mockQueryVersion(template);
    }

    // -----------------------------------------------------------------
    // LIFE CYCLE PREPARATIONS
    // -----------------------------------------------------------------

    private static void prepareToProductionValidation(QueryVersion queryVersion) {
        QueryLifecycleTestUtils.prepareToProductionValidation(queryVersion);
    }

    private static void prepareToDiffusionValidation(QueryVersion queryVersion) {
        QueryLifecycleTestUtils.prepareToDiffusionValidation(queryVersion);
    }

    private static void prepareToValidationRejected(QueryVersion queryVersion) {
        QueryLifecycleTestUtils.prepareToValidationRejected(queryVersion);
    }

}