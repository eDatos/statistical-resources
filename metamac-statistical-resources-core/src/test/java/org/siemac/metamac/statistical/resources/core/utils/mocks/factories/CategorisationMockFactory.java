package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05_NAME;

import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CategorisationSequence;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

@SuppressWarnings("unused")
public class CategorisationMockFactory extends StatisticalResourcesMockFactory<Categorisation> {

    public static final String               CATEGORISATION_MAINTAINER_NAME                      = "CATEGORISATION_MAINTAINER";
    public static final String               CATEGORISATION_01_DATASET_VERSION_01_NAME           = "CATEGORISATION_01_DATASET_VERSION_01";
    public static final String               CATEGORISATION_02_DATASET_VERSION_01_NAME           = "CATEGORISATION_02_DATASET_VERSION_01";
    public static final String               CATEGORISATION_01_DATASET_VERSION_02_NAME           = "CATEGORISATION_01_DATASET_VERSION_02";
    public static final String               CATEGORISATION_02_DATASET_VERSION_02_NAME           = "CATEGORISATION_02_DATASET_VERSION_02";
    public static final String               CATEGORISATION_03_DATASET_VERSION_02_NAME           = "CATEGORISATION_03_DATASET_VERSION_02";
    public static final String               CATEGORISATION_01_DATASET_VERSION_03_PUBLISHED_NAME = "CATEGORISATION_01_DATASET_VERSION_03_PUBLISHED";
    public static final String               CATEGORISATION_SEQUENCE_NAME                        = "CATEGORISATION_SEQUENCE";

    private static CategorisationMockFactory instance                                            = null;

    private CategorisationMockFactory() {
    }

    public static CategorisationMockFactory getInstance() {
        if (instance == null) {
            instance = new CategorisationMockFactory();
        }
        return instance;
    }

    private static ExternalItem getCategorisationMaintainer() {
        return StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem("agency01", "ISTAC.agency01");
    }

    private static Categorisation getCategorisation01DatasetVersion01() {
        return createCategorisation("cat_data_1", "category01", DATASET_VERSION_01_BASIC_NAME);
    }

    private static Categorisation getCategorisation02DatasetVersion01() {
        return createCategorisation("cat_data_2", "category02", DATASET_VERSION_01_BASIC_NAME);
    }

    private static Categorisation getCategorisation01DatasetVersion02() {
        return createCategorisation("cat_data_3", "category01", DATASET_VERSION_02_BASIC_NAME);
    }

    private static Categorisation getCategorisation02DatasetVersion02() {
        return createCategorisation("cat_data_4", "category02", DATASET_VERSION_02_BASIC_NAME);
    }

    private static Categorisation getCategorisation03DatasetVersion02() {
        return createCategorisation("cat_data_5", "category03", DATASET_VERSION_02_BASIC_NAME);
    }

    private static Categorisation getCategorisation01DatasetVersion03Published() {
        return createCategorisation("cat_data_6", "category01", DATASET_VERSION_22_V1_PUBLISHED_FOR_DATASET_05_NAME);
    }

    public static Categorisation createCategorisation(String categorisationCode, String categoryCode, DatasetVersion datasetVersion) {
        return getStatisticalResourcesPersistedDoMocks().mockCategorisationWithGeneratedCategory(datasetVersion, getCategorisationMaintainer(), categoryCode, categorisationCode);
    }

    private static Categorisation createCategorisation(String categorisationCode, String categoryCode, String datasetId) {
        DatasetVersion datasetVersion = getDatasetVersionMock(datasetId);
        return createCategorisation(categorisationCode, categoryCode, datasetVersion);
    }

    private static CategorisationSequence getCategorisationSequence() {
        CategorisationSequence categorisationSequence = new CategorisationSequence();
        categorisationSequence.setActualSequence(Long.valueOf(100));
        categorisationSequence.setMaintainerUrn(getCategorisationMaintainer().getUrn());
        return categorisationSequence;
    }

}
