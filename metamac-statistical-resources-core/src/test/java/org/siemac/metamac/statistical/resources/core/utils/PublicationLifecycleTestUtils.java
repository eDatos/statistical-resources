package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class PublicationLifecycleTestUtils {

    // *****************************************************
    // PRODUCTION VALIDATION
    // *****************************************************

    public static void prepareToProductionValidation(PublicationVersion publicationVersion) {
        LifecycleTestUtils.prepareToProductionValidationSiemac(publicationVersion);
        prepareToLifecycleCommonPublicationVersion(publicationVersion);
    }

    public static void fillAsProductionValidation(PublicationVersion publicationVersion) {
        prepareToProductionValidation(publicationVersion);
        LifecycleTestUtils.fillAsProductionValidationSiemac(publicationVersion);
    }

    // *****************************************************
    // DIFFUSION VALIDATION
    // *****************************************************

    public static void prepareToDiffusionValidation(PublicationVersion publicationVersion) {
        fillAsProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToDiffusionValidationSiemac(publicationVersion);
    }

    public static void fillAsDiffusionValidation(PublicationVersion publicationVersion) {
        prepareToDiffusionValidation(publicationVersion);
        LifecycleTestUtils.fillAsDiffusionValidationSiemac(publicationVersion);
    }

    // *****************************************************
    // VALIDATION REJECTED
    // *****************************************************

    public static void prepareToValidationRejected(PublicationVersion publicationVersion) {
        fillAsProductionValidation(publicationVersion);
        LifecycleTestUtils.prepareToValidationRejectedFromProductionValidationSiemac(publicationVersion);
    }

    public static void fillAsValidationRejected(PublicationVersion publicationVersion) {
        prepareToValidationRejected(publicationVersion);
        LifecycleTestUtils.fillAsValidationRejectedFromProductionValidationSiemac(publicationVersion);
    }

    // *****************************************************
    // PUBLISHING
    // *****************************************************

    public static void prepareToPublished(PublicationVersion publicationVersion) {
        fillAsDiffusionValidation(publicationVersion);
        LifecycleTestUtils.prepareToPublishingSiemac(publicationVersion);
        fillStructure(publicationVersion);
    }

    public static void fillAsPublished(PublicationVersion publicationVersion) {
        prepareToPublished(publicationVersion);
        LifecycleTestUtils.fillAsPublishedSiemac(publicationVersion);
        fillPublicationAsPublished(publicationVersion);
    }

    private static void fillPublicationAsPublished(PublicationVersion publicationVersion) {
        if (publicationVersion.getFormatExtentResources() == null) {
            publicationVersion.setFormatExtentResources(computeFormatExtent(publicationVersion));
        }
    }

    private static Integer computeFormatExtent(PublicationVersion publicationVersion) {
        Integer count = 0;
        for (ElementLevel level : publicationVersion.getChildrenAllLevels()) {
            if (level.getCube() != null) {
                count++;
            }
        }
        return count;
    }

    // *****************************************************
    // VERSIONING
    // *****************************************************

    public static void prepareToVersioning(PublicationVersion publicationVersion) {
        fillAsPublished(publicationVersion);
        LifecycleTestUtils.prepareToVersioningSiemac(publicationVersion);
    }

    public static void fillAsVersioned(PublicationVersion publicationVersion) {
        prepareToVersioning(publicationVersion);
        LifecycleTestUtils.fillAsVersionedSiemac(publicationVersion);
    }

    private static void prepareToLifecycleCommonPublicationVersion(PublicationVersion publicationVersion) {
        // Inherited fields that need customization based on Resource's type

    }

    private static void fillStructure(PublicationVersion publicationVersion) {
        if (publicationVersion.getChildrenAllLevels().isEmpty()) {
            Dataset dataset = DatasetMockFactory.createTwoPublishedVersionsForDataset(50);
            ElementLevel level01 = PublicationVersionMockFactory.createDatasetCubeElementLevel(publicationVersion, dataset);
            level01.setOrderInLevel(1L);

            Query query = QueryMockFactory.createPublishedQueryLinkedToDataset(StatisticalResourcesPersistedDoMocks.mockString(10), dataset, new DateTime());
            ElementLevel level02 = PublicationVersionMockFactory.createQueryCubeElementLevel(publicationVersion, query);
            level02.setOrderInLevel(2L);
        }
    }

}
