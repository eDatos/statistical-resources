package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.PublicationLifecycleTestUtils.prepareToVersioning;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_27_V1_PUBLISHED_FOR_PUBLICATION_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_28_V2_PUBLISHED_FOR_PUBLICATION_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_30_V1_PUBLISHED_FOR_PUBLICATION_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.createChapterElementLevel;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.createDatasetCubeElementLevel;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.createQueryCubeElementLevel;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.PublicationLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.stereotype.Component;

@MockProvider
@SuppressWarnings("unused")
public class PublicationMockFactory extends StatisticalResourcesMockFactory<Publication> {

    public static final String            PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME                           = "PUBLICATION_02_BASIC_WITH_GENERATED_VERSION";

    public static final String            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME                      = "PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS";

    public static final String            PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS_NAME                 = "PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS";

    public static final String            PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                       = "PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";

    public static final String            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME = "PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";

    private static PublicationMockFactory instance                                                                   = null;

    private PublicationMockFactory() {
    }

    public static PublicationMockFactory getInstance() {
        if (instance == null) {
            instance = new PublicationMockFactory();
        }
        return instance;
    }

    private static Publication getPublication02BasicWithGeneratedVersion() {
        return createPublicationWithGeneratedPublicationVersions();
    }

    private static Publication getPublication03BasicWith2PublicationVersions() {
        PublicationMock publication = createPublicationToAddVersions(1);

        PublicationVersion version01 = createPublicationVersionPublishedPreviousVersion(publication, INIT_VERSION, new DateTime().minusDays(2), null);
        registerPublicationVersionMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME, version01);

        PublicationVersion version02 = createPublicationVersionLastVersionInStatus(publication, SECOND_VERSION, ProcStatusEnum.DRAFT);
        registerPublicationVersionMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME, version02);

        // Relations
        version02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(version01));
        return publication;
    }

    private static Publication getPublication04StructuredWith2PublicationVersions() {

        PublicationMock publication = createPublicationToAddVersions(1);

        PublicationVersion version01 = createPublication04Version01(publication, new DateTime().minusDays(2));
        registerPublicationVersionMock(PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME, version01);

        PublicationVersion version02 = createPublication04Version02(publication);
        registerPublicationVersionMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME, version02);

        version02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(version01));
        return publication;
    }

    private static PublicationVersion createPublication04Version01(PublicationMock publication, DateTime validFrom) {
        PublicationVersion publicationVersion = createPublicationVersionPublishedPreviousVersion(publication, INIT_VERSION, validFrom, null);

        Query query01 = QueryMockFactory.generateQueryWithGeneratedVersion();
        registerQueryMock(QueryMockFactory.QUERY_09_SINGLE_VERSION_USED_IN_PUB_VERSION_17_NAME, query01);

        // Structure
        // Chapter 01
        ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
        elementLevel01.setOrderInLevel(Long.valueOf(1));
        // --> Chapter 01.01
        ElementLevel elementLevel01_01 = createChapterElementLevel(publicationVersion, elementLevel01);
        elementLevel01_01.setOrderInLevel(Long.valueOf(1));
        // ----> Cube 01.01.01

        Dataset dataset01 = DatasetMockFactory.generateDatasetWithGeneratedVersion();
        registerDatasetMock(DatasetMockFactory.DATASET_22_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME, dataset01);

        ElementLevel elementLevel01_01_01 = createDatasetCubeElementLevel(publicationVersion, dataset01, elementLevel01_01);
        elementLevel01_01_01.setOrderInLevel(Long.valueOf(1));
        // --> Chapter 01.02
        ElementLevel elementLevel01_02 = createChapterElementLevel(publicationVersion, elementLevel01);
        elementLevel01_02.setOrderInLevel(Long.valueOf(2));
        // ----> Cube 01.02.01
        ElementLevel elementLevel01_02_01 = createQueryCubeElementLevel(publicationVersion, query01, elementLevel01_02);
        elementLevel01_02_01.setOrderInLevel(Long.valueOf(1));
        // --> Cube 01.03

        Dataset dataset02 = DatasetMockFactory.generateDatasetWithGeneratedVersion();
        registerDatasetMock(DatasetMockFactory.DATASET_23_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME, dataset02);
        ElementLevel elementLevel01_03 = createDatasetCubeElementLevel(publicationVersion, dataset02, elementLevel01);
        elementLevel01_03.setOrderInLevel(Long.valueOf(3));
        // Chapter 02
        ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
        elementLevel02.setOrderInLevel(Long.valueOf(2));
        // --> Cube 02.01
        ElementLevel elementLevel02_01 = createQueryCubeElementLevel(publicationVersion, query01, elementLevel02);
        elementLevel02_01.setOrderInLevel(Long.valueOf(1));
        // Cube 03
        ElementLevel elementLevel03 = createQueryCubeElementLevel(publicationVersion, query01);
        elementLevel03.setOrderInLevel(Long.valueOf(3));

        return publicationVersion;
    }

    private static PublicationVersion createPublication04Version02(PublicationMock publication) {
        // General metadata
        PublicationVersionMock publicationVersion = PublicationVersionMock.buildSimpleVersion(publication, SECOND_VERSION);
        publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));

        // Structure
        // Chapter 01
        ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
        elementLevel01.setOrderInLevel(Long.valueOf(1));
        // Chapter 02
        ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
        elementLevel02.setOrderInLevel(Long.valueOf(2));
        // --> Chapter 02.01
        ElementLevel elementLevel02_01 = createChapterElementLevel(publicationVersion, elementLevel02);
        elementLevel02_01.setOrderInLevel(Long.valueOf(1));

        return createPublicationVersionInStatus(publicationVersion, ProcStatusEnum.DRAFT);
    }

    private static Publication getPublication05WithMultiplePublishedVersions() {
        PublicationMock publication = createPublicationToAddVersions(1);

        DateTime secondVersionPublishTime = new DateTime().minusDays(2);
        DateTime thirdVersionPublishTime = new DateTime().minusDays(1);

        PublicationVersion publicationVersion01 = createPublicationVersionPublishedPreviousVersion(publication, INIT_VERSION, new DateTime().minusDays(3), secondVersionPublishTime);
        registerPublicationVersionMock(PUBLICATION_VERSION_27_V1_PUBLISHED_FOR_PUBLICATION_05_NAME, publicationVersion01);

        PublicationVersion publicationVersion02 = createPublicationVersionPublishedPreviousVersion(publication, SECOND_VERSION, new DateTime().minusDays(2), thirdVersionPublishTime);
        registerPublicationVersionMock(PUBLICATION_VERSION_28_V2_PUBLISHED_FOR_PUBLICATION_05_NAME, publicationVersion02);

        PublicationVersion publicationVersion03 = createPublicationVersionPublishedLastVersion(publication, THIRD_VERSION, new DateTime().minusDays(1));
        registerPublicationVersionMock(PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME, publicationVersion03);

        publicationVersion03.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion02));
        publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));
        return publication;
    }

    private static Publication getPublication06WithMultiplePublishedVersionsAndLatestNoVisible() {
        PublicationMock publication = createPublicationToAddVersions(1);

        DateTime secondVersionPublishTime = new DateTime().plusDays(1);

        PublicationVersion publicationVersion01 = createPublicationVersionPublishedPreviousVersion(publication, INIT_VERSION, new DateTime().minusDays(1), secondVersionPublishTime);
        registerPublicationVersionMock(PUBLICATION_VERSION_30_V1_PUBLISHED_FOR_PUBLICATION_06_NAME, publicationVersion01);

        PublicationVersion publicationVersion02 = createPublicationVersionPublishedLastVersion(publication, SECOND_VERSION, secondVersionPublishTime);
        registerPublicationVersionMock(PublicationVersionMockFactory.PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME, publicationVersion02);

        publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));
        return publication;
    }

    // Public builders

    public static Publication buildPublicationWithTwoVersionsPublished(int sequentialId) {
        PublicationMock publication = createPublicationToAddVersions(sequentialId);

        DateTime secondVersionPublishTime = new DateTime().minusDays(1);

        PublicationVersion publicationVersion01 = createPublicationVersionPublishedPreviousVersion(publication, INIT_VERSION, new DateTime().minusDays(2), secondVersionPublishTime);

        PublicationVersion publicationVersion02 = createPublicationVersionPublishedLastVersion(publication, SECOND_VERSION, secondVersionPublishTime);

        publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));

        return publication;
    }

    public static Publication buildPublicationWithTwoVersionsOnePublishedLastNotVisible(int sequentialId) {
        PublicationMock publication = createPublicationToAddVersions(sequentialId);

        DateTime secondVersionPublishTime = new DateTime().plusDays(1);

        PublicationVersion publicationVersion01 = createPublicationVersionPublishedPreviousVersion(publication, INIT_VERSION, new DateTime().minusDays(2), secondVersionPublishTime);

        PublicationVersion publicationVersion02 = createPublicationVersionPublishedLastVersion(publication, SECOND_VERSION, secondVersionPublishTime);

        publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));

        return publication;
    }

    public static Publication buildPublicationWithTwoVersionsOnePublishedLastDraft(int sequentialId) {
        PublicationMock publication = createPublicationToAddVersions(sequentialId);

        PublicationVersion publicationVersion01 = createPublicationVersionPublishedPreviousVersion(publication, INIT_VERSION, new DateTime().minusDays(2), null);

        PublicationVersion publicationVersion02 = createPublicationVersionLastVersionInStatus(publication, SECOND_VERSION, ProcStatusEnum.DRAFT);

        publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));

        return publication;
    }

    public static Publication buildPublicationWithSingleVersionPublished(int sequentialId) {
        PublicationMock publication = createPublicationToAddVersions(sequentialId);

        PublicationVersion publicationVersion01 = createPublicationVersionPublishedLastVersion(publication, INIT_VERSION, new DateTime().minusDays(2));

        return publication;
    }

    public static Publication buildPublicationWithSingleVersionPublishedNotVisible(int sequentialId) {
        PublicationMock publication = createPublicationToAddVersions(sequentialId);

        PublicationVersion publicationVersion01 = createPublicationVersionPublishedLastVersion(publication, INIT_VERSION, new DateTime().plusDays(2));

        return publication;
    }

    public static Publication buildPublicationWithSingleVersionDraft(int sequentialId) {
        PublicationMock publication = createPublicationToAddVersions(sequentialId);

        PublicationVersion publicationVersion01 = createPublicationVersionLastVersionInStatus(publication, INIT_VERSION, ProcStatusEnum.DRAFT);

        return publication;
    }

    // INTERNAL BUILDERS

    private static PublicationVersion createPublicationVersionLastVersionInStatus(Publication publication, String version, ProcStatusEnum status) {
        PublicationVersionMock publicationVersionMock = buildPublicationVersionSimple(publication, version, true);
        return createPublicationVersionInStatus(publicationVersionMock, status);
    }

    private static PublicationVersionMock buildPublicationVersionSimple(Publication publication, String version, boolean isLastVersion) {
        PublicationVersionMock publicationVersion = new PublicationVersionMock();
        publicationVersion.setPublication(publication);
        publicationVersion.setVersionLogic(version);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(isLastVersion);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        return publicationVersion;
    }

    private static PublicationVersion createPublicationVersionPublishedPreviousVersion(PublicationMock publication, String version, DateTime validFrom, DateTime validTo) {
        PublicationVersionMock publicationVersion = buildPublishedReadyPublicationVersion(publication, version, validFrom, validTo, false);
        return createPublicationVersionInStatus(publicationVersion, ProcStatusEnum.PUBLISHED);
    }

    private static PublicationVersion createPublicationVersionPublishedLastVersion(PublicationMock publication, String version, DateTime validFrom) {
        PublicationVersionMock publicationVersion = buildPublishedReadyPublicationVersion(publication, version, validFrom, null, true);
        return createPublicationVersionInStatus(publicationVersion, ProcStatusEnum.PUBLISHED);
    }

    private static PublicationVersionMock buildPublishedReadyPublicationVersion(PublicationMock publication, String version, DateTime validFrom, DateTime validTo, boolean lastVersion) {
        PublicationVersionMock publicationVersion = new PublicationVersionMock();
        publicationVersion.setPublication(publication);
        publicationVersion.setVersionLogic(version);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(lastVersion);

        if (validFrom.isAfterNow()) {
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime());
        } else {
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(validFrom.minusHours(1));
        }
        publicationVersion.getSiemacMetadataStatisticalResource().setValidFrom(validFrom);
        publicationVersion.getSiemacMetadataStatisticalResource().setValidTo(validTo);

        return publicationVersion;
    }

    private static PublicationVersion createPublicationVersionInStatus(PublicationVersionMock publicationVersionMock, ProcStatusEnum status) {
        PublicationVersion publicationVersion = getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersionMock);

        switch (status) {
            case PRODUCTION_VALIDATION:
                PublicationLifecycleTestUtils.fillAsProductionValidation(publicationVersion);
                break;
            case DIFFUSION_VALIDATION:
                PublicationLifecycleTestUtils.fillAsDiffusionValidation(publicationVersion);
                break;
            case VALIDATION_REJECTED:
                PublicationLifecycleTestUtils.fillAsValidationRejected(publicationVersion);
                break;
            case PUBLISHED:
                PublicationLifecycleTestUtils.fillAsPublished(publicationVersion);
                break;
            case PUBLISHED_NOT_VISIBLE:
                throw new IllegalArgumentException("Unsupported status not visible, set first the ValidFrom to the future and use status PUBLISHED");
            case DRAFT:
                break;
            default:
                throw new IllegalArgumentException("Unsupported status " + status);
        }
        return publicationVersion;
    }

    // Creations
    private static Publication createPublicationWithGeneratedPublicationVersions() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationWithGeneratedPublicationVersion();
    }

    protected static PublicationMock createPublicationToAddVersions(Integer sequential) {
        PublicationMock publication = new PublicationMock();
        publication.setSequentialId(sequential);
        getStatisticalResourcesPersistedDoMocks().mockPublication(publication);
        return publication;
    }

}
