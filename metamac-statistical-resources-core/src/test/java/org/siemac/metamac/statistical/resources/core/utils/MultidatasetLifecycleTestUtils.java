package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class MultidatasetLifecycleTestUtils {

    // *****************************************************
    // PRODUCTION VALIDATION
    // *****************************************************

    public static void prepareToProductionValidation(MultidatasetVersion multidatasetVersion) {
        LifecycleTestUtils.prepareToProductionValidationSiemac(multidatasetVersion);
        prepareToLifecycleCommonMultidatasetVersion(multidatasetVersion);
    }

    public static void fillAsProductionValidation(MultidatasetVersion multidatasetVersion) {
        prepareToProductionValidation(multidatasetVersion);
        LifecycleTestUtils.fillAsProductionValidationSiemac(multidatasetVersion);
    }

    // *****************************************************
    // DIFFUSION VALIDATION
    // *****************************************************

    public static void prepareToDiffusionValidation(MultidatasetVersion multidatasetVersion) {
        fillAsProductionValidation(multidatasetVersion);
        LifecycleTestUtils.prepareToDiffusionValidationSiemac(multidatasetVersion);
    }

    public static void fillAsDiffusionValidation(MultidatasetVersion multidatasetVersion) {
        prepareToDiffusionValidation(multidatasetVersion);
        LifecycleTestUtils.fillAsDiffusionValidationSiemac(multidatasetVersion);
    }

    // *****************************************************
    // VALIDATION REJECTED
    // *****************************************************

    public static void prepareToValidationRejected(MultidatasetVersion multidatasetVersion) {
        fillAsProductionValidation(multidatasetVersion);
        LifecycleTestUtils.prepareToValidationRejectedFromProductionValidationSiemac(multidatasetVersion);
    }

    public static void fillAsValidationRejected(MultidatasetVersion multidatasetVersion) {
        prepareToValidationRejected(multidatasetVersion);
        LifecycleTestUtils.fillAsValidationRejectedFromProductionValidationSiemac(multidatasetVersion);
    }

    // *****************************************************
    // PUBLISHING
    // *****************************************************

    public static void prepareToPublished(MultidatasetVersion multidatasetVersion) {
        fillAsDiffusionValidation(multidatasetVersion);
        LifecycleTestUtils.prepareToPublishingSiemac(multidatasetVersion);
        fillStructure(multidatasetVersion);
    }

    public static void fillAsPublished(MultidatasetVersion multidatasetVersion) {
        prepareToPublished(multidatasetVersion);
        LifecycleTestUtils.fillAsPublishedSiemac(multidatasetVersion);
        fillMultidatasetAsPublished(multidatasetVersion);
    }

    private static void fillMultidatasetAsPublished(MultidatasetVersion multidatasetVersion) {
        if (multidatasetVersion.getFormatExtentResources() == null) {
            multidatasetVersion.setFormatExtentResources(computeFormatExtent(multidatasetVersion));
        }
    }

    private static Integer computeFormatExtent(MultidatasetVersion multidatasetVersion) {
        return multidatasetVersion.getCubes().size();
    }

    // *****************************************************
    // VERSIONING
    // *****************************************************

    public static void prepareToVersioning(MultidatasetVersion multidatasetVersion) {
        fillAsPublished(multidatasetVersion);
        LifecycleTestUtils.prepareToVersioningSiemac(multidatasetVersion);
    }

    public static void fillAsVersioned(MultidatasetVersion multidatasetVersion) {
        prepareToVersioning(multidatasetVersion);
        LifecycleTestUtils.fillAsVersionedSiemac(multidatasetVersion);
    }

    // Inherited fields that need customization based on Resource's type
    private static void prepareToLifecycleCommonMultidatasetVersion(MultidatasetVersion multidatasetVersion) {
        multidatasetVersion.setFilteringDimension(StatisticalResourcesPersistedDoMocks.mockInternationalString());
    }

    private static void fillStructure(MultidatasetVersion multidatasetVersion) {
        if (multidatasetVersion.getCubes().isEmpty()) {
            Dataset dataset = DatasetMockFactory.createTwoPublishedVersionsForDataset(50);
            MultidatasetCube cube01 = MultidatasetVersionMockFactory.createDatasetMultidatasetCube(multidatasetVersion, dataset);
            cube01.setOrderInMultidataset(1L);

            Query query = QueryMockFactory.createPublishedQueryLinkedToDataset(StatisticalResourcesPersistedDoMocks.mockString(10), dataset, new DateTime());
            MultidatasetCube cube02 = MultidatasetVersionMockFactory.createQueryMultidatasetCube(multidatasetVersion, query);
            cube02.setOrderInMultidataset(2L);
        }
    }

}
