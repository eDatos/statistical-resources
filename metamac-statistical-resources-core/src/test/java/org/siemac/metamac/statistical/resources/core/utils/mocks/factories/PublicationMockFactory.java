package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.PublicationLifecycleTestUtils.prepareToVersioning;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME;
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
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class PublicationMockFactory extends StatisticalResourcesMockFactory<Publication> {

    public static final String            PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME                           = "PUBLICATION_02_BASIC_WITH_GENERATED_VERSION";
    private static Publication            PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;

    public static final String            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME                      = "PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS";
    private static Publication            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;

    public static final String            PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS_NAME                 = "PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS";
    private static Publication            PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS;

    public static final String            PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                       = "PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";
    private static Publication            PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;

    public static final String            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME = "PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";
    private static Publication            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;

    private static PublicationMockFactory instance                                                                   = null;

    private PublicationMockFactory() {
    }

    public static PublicationMockFactory getInstance() {
        if (instance == null) {
            instance = new PublicationMockFactory();
        }
        return instance;
    }

    protected static Publication getPublication02BasicWithGeneratedVersion() {
        if (PUBLICATION_02_BASIC_WITH_GENERATED_VERSION == null) {
            Publication publication = createPublicationWithGeneratedPublicationVersions();
            publication.getVersions().get(0).getSiemacMetadataStatisticalResource().setLastVersion(Boolean.TRUE);
            PUBLICATION_02_BASIC_WITH_GENERATED_VERSION = publication;
        }
        return PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;
    }

    protected static Publication getPublication03BasicWith2PublicationVersions() {
        if (PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS == null) {

            PublicationMock publication = createPublicationToAddVersions(1);

            PublicationVersion version01 = buildPublicationVersionPublished(publication, INIT_VERSION, new DateTime().minusDays(2), null);

            PublicationVersion version02 = buildPublicationVersionSimpleLastVersion(publication, SECOND_VERSION);

            // Relations
            version02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(version01));

            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS = publication;
        }
        return PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;
    }

    protected static Publication getPublication04StructuredWith2PublicationVersions() {
        if (PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS == null) {

            PublicationMock publication = createPublicationToAddVersions(1);

            PublicationVersion version01 = buildPublication04Version01(publication, new DateTime().minusDays(2));
            registerPublicationVersionMock(PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME, version01);

            PublicationVersion version02 = buildPublication04Version02(publication);
            registerPublicationVersionMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME, version02);

            version02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(version01));

            PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS = publication;
        }
        return PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS;
    }

    private static PublicationVersion buildPublication04Version01(PublicationMock publication, DateTime validFrom) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, INIT_VERSION, validFrom, null);

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
        registerDatasetMock(DatasetMockFactory.DATASET_16_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME, dataset01);

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
        registerDatasetMock(DatasetMockFactory.DATASET_17_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME, dataset02);
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

        prepareToVersioning(publicationVersion);

        return getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
    }
    private static PublicationVersion buildPublication04Version02(PublicationMock publication) {
        // General metadata
        PublicationVersionMock publicationVersion = new PublicationVersionMock();
        publicationVersion.setPublication(publication);
        publicationVersion.setVersionLogic(SECOND_VERSION);
        publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
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

        return getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
    }

    protected static Publication getPublication05WithMultiplePublishedVersions() {
        if (PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS == null) {
            PublicationMock publication = createPublicationToAddVersions(1);

            DateTime secondVersionPublishTime = new DateTime().minusDays(2);
            DateTime thirdVersionPublishTime = new DateTime().minusDays(1);

            PublicationVersion publicationVersion01 = buildPublicationVersionPublished(publication, INIT_VERSION, new DateTime().minusDays(3), secondVersionPublishTime);
            PublicationVersion publicationVersion02 = buildPublicationVersionPublished(publication, SECOND_VERSION, new DateTime().minusDays(2), thirdVersionPublishTime);
            PublicationVersion publicationVersion03 = buildPublicationVersionPublishedLastVersion(publication, THIRD_VERSION, new DateTime().minusDays(1));

            publicationVersion03.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion02));
            publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));

            PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS = publication;
        }
        return PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;
    }

    protected static Publication getPublication06WithMultiplePublishedVersionsAndLatestNoVisible() {
        if (PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE == null) {
            PublicationMock publication = createPublicationToAddVersions(1);

            DateTime secondVersionPublishTime = new DateTime().plusDays(1);

            PublicationVersion publicationVersion01 = buildPublicationVersionPublished(publication, INIT_VERSION, new DateTime().minusDays(1), secondVersionPublishTime);

            PublicationVersion publicationVersion02 = buildPublicationVersionPublishedLastVersion(publication, SECOND_VERSION, secondVersionPublishTime);

            publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));

            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE = publication;
        }
        return PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;
    }

    // Builders

    protected static Publication buildPublicationWithTwoVersionsPublishedLinkedToDataset(Dataset dataset) {
        PublicationMock publication = createPublicationToAddVersions(1);

        DateTime secondVersionPublishTime = new DateTime().minusDays(1);

        PublicationVersion publicationVersion01 = buildPublicationVersionPublished(publication, INIT_VERSION, new DateTime().minusDays(2), secondVersionPublishTime);
        {
            ElementLevel elementLevel01 = createDatasetCubeElementLevel(publicationVersion01, dataset);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
        }

        PublicationVersion publicationVersion02 = buildPublicationVersionPublishedLastVersion(publication, SECOND_VERSION, secondVersionPublishTime);
        {
            ElementLevel elementLevel01 = createDatasetCubeElementLevel(publicationVersion02, dataset);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
        }

        publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));

        return publication;
    }

    protected static Publication buildPublicationWithTwoVersionsPublishedOneNotVisibleLinkedToDataset(Dataset dataset) {
        PublicationMock publication = createPublicationToAddVersions(1);

        DateTime secondVersionPublishTime = new DateTime().plusDays(1);

        PublicationVersion publicationVersion01 = buildPublicationVersionPublished(publication, INIT_VERSION, new DateTime().minusDays(1), secondVersionPublishTime);
        {
            ElementLevel elementLevel01 = createDatasetCubeElementLevel(publicationVersion01, dataset);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
        }

        PublicationVersion publicationVersion02 = buildPublicationVersionPublishedLastVersion(publication, SECOND_VERSION, secondVersionPublishTime);
        {
            ElementLevel elementLevel01 = createDatasetCubeElementLevel(publicationVersion02, dataset);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
        }

        publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));
        return publication;
    }

    protected static Publication buildPublicationWithDraftVersionLinkedToDataset(Dataset dataset) {
        PublicationMock publication = createPublicationToAddVersions(1);

        PublicationVersion publicationVersion01 = buildPublicationVersionSimpleLastVersion(publication, INIT_VERSION);
        {
            ElementLevel elementLevel01 = createDatasetCubeElementLevel(publicationVersion01, dataset);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
        }

        return publication;
    }

    private static PublicationVersion buildPublicationVersionSimpleLastVersion(Publication publication, String version) {
        return buildPublicationVersionSimple(publication, version, true);
    }

    private static PublicationVersion buildPublicationVersionSimple(Publication publication, String version, boolean isLastVersion) {
        PublicationVersionMock publicationVersion = new PublicationVersionMock();
        publicationVersion.setPublication(publication);
        publicationVersion.setVersionLogic(version);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(isLastVersion);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        return publicationVersion;
    }

    protected static PublicationVersion buildPublicationVersionPublished(PublicationMock publication, String version, DateTime validFrom, DateTime validTo) {
        return buildPublicationVersionPublished(publication, version, validFrom, validTo, false);
    }

    protected static PublicationVersion buildPublicationVersionPublishedLastVersion(PublicationMock publication, String version, DateTime validFrom) {
        return buildPublicationVersionPublished(publication, version, validFrom, null, true);
    }

    private static PublicationVersion buildPublicationVersionPublished(PublicationMock publication, String version, DateTime validFrom, DateTime validTo, boolean lastVersion) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, version, validFrom, validTo);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(lastVersion);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        prepareToVersioning(publicationVersion);
        return publicationVersion;
    }

    protected static PublicationVersionMock buildPublishedPublicationVersion(PublicationMock publication, String version, DateTime validFrom, DateTime validTo) {
        PublicationVersionMock publicationVersion = new PublicationVersionMock();
        publicationVersion.setPublication(publication);
        publicationVersion.setVersionLogic(version);

        // not last version
        if (validFrom.isAfterNow()) {
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime());
        } else {
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(validFrom.minusHours(1));
        }
        publicationVersion.getSiemacMetadataStatisticalResource().setValidFrom(validFrom);
        publicationVersion.getSiemacMetadataStatisticalResource().setValidTo(validTo);

        return publicationVersion;
    }

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
