package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.PublicationLifecycleTestUtils.prepareToVersioning;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.getDataset01Basic;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.getDataset03With2DatasetVersions;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.createChapterElementLevel;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.createDatasetCubeElementLevel;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.createQueryCubeElementLevel;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion30V1PublishedForPublication06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion31V2PublishedNoVisibleForPublication06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.getQuery01Simple;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class PublicationMockFactory extends StatisticalResourcesMockFactory<Publication> {

    public static final String PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME                           = "PUBLICATION_02_BASIC_WITH_GENERATED_VERSION";
    private static Publication PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;

    public static final String PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME                      = "PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS";
    private static Publication PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;

    public static final String PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS_NAME                 = "PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS";
    private static Publication PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS;

    public static final String PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME                       = "PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS";
    private static Publication PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;

    public static final String PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME = "PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE";
    private static Publication PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;

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

            Publication publication = createPublicationToAddVersions(1);

            PublicationVersionMock version01Mock = buildPublishedPublicationVersion(publication, INIT_VERSION, new DateTime().minusDays(2), null);
            version01Mock.getSiemacMetadataStatisticalResource().setLastVersion(false);
            PublicationVersion publicationVersionV01 = getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(version01Mock);
            prepareToVersioning(publicationVersionV01);

            PublicationVersionMock version02Mock = new PublicationVersionMock();
            version02Mock.setPublication(publication);
            version02Mock.setVersionLogic(SECOND_VERSION);
            version02Mock.getSiemacMetadataStatisticalResource().setLastVersion(Boolean.TRUE);
            version02Mock.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            PublicationVersion publicationVersionV02 = getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(version02Mock);

            // Relations
            publicationVersionV02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersionV01));

            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS = publication;
        }
        return PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;
    }

    protected static Publication getPublication04StructuredWith2PublicationVersions() {
        if (PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS == null) {

            PublicationMock publication = createPublicationToAddVersions(1);

            PublicationVersion version01 = buildPublication04Version01(publication, new DateTime().minusDays(2));

            PublicationVersion version02 = buildPublication04Version02(publication);

            version02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(version01));

            PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS = publication;
        }
        return PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS;
    }

    private static PublicationVersion buildPublication04Version01(PublicationMock publication, DateTime validFrom) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, INIT_VERSION, validFrom, null);

        // Structure
        // Chapter 01
        ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
        elementLevel01.setOrderInLevel(Long.valueOf(1));
        // --> Chapter 01.01
        ElementLevel elementLevel01_01 = createChapterElementLevel(publicationVersion, elementLevel01);
        elementLevel01_01.setOrderInLevel(Long.valueOf(1));
        // ----> Cube 01.01.01
        ElementLevel elementLevel01_01_01 = createDatasetCubeElementLevel(publicationVersion, getDataset03With2DatasetVersions(), elementLevel01_01);
        elementLevel01_01_01.setOrderInLevel(Long.valueOf(1));
        // --> Chapter 01.02
        ElementLevel elementLevel01_02 = createChapterElementLevel(publicationVersion, elementLevel01);
        elementLevel01_02.setOrderInLevel(Long.valueOf(2));
        // ----> Cube 01.02.01
        ElementLevel elementLevel01_02_01 = createQueryCubeElementLevel(publicationVersion, getQuery01Simple(), elementLevel01_02);
        elementLevel01_02_01.setOrderInLevel(Long.valueOf(1));
        // --> Cube 01.03
        ElementLevel elementLevel01_03 = createDatasetCubeElementLevel(publicationVersion, getDataset01Basic(), elementLevel01);
        elementLevel01_03.setOrderInLevel(Long.valueOf(3));
        // Chapter 02
        ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
        elementLevel02.setOrderInLevel(Long.valueOf(2));
        // --> Cube 02.01
        ElementLevel elementLevel02_01 = createQueryCubeElementLevel(publicationVersion, getQuery01Simple(), elementLevel02);
        elementLevel02_01.setOrderInLevel(Long.valueOf(1));
        // Cube 03
        ElementLevel elementLevel03 = createQueryCubeElementLevel(publicationVersion, getQuery01Simple());
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

            PublicationVersion publicationVersion01 = buildPublication05Version01(publication, new DateTime().minusDays(3), secondVersionPublishTime);
            PublicationVersion publicationVersion02 = buildPublication05Version02(publication, new DateTime().minusDays(2), thirdVersionPublishTime);
            PublicationVersion publicationVersion03 = buildPublication05Version03(publication, new DateTime().minusDays(1));

            publicationVersion03.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion02));
            publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));

            PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS = publication;
        }
        return PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;
    }

    private static PublicationVersion buildPublication05Version01(PublicationMock publication, DateTime validFrom, DateTime validTo) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, INIT_VERSION, validFrom, validTo);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(false);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        prepareToVersioning(publicationVersion);
        return publicationVersion;
    }

    private static PublicationVersion buildPublication05Version02(PublicationMock publication, DateTime validFrom, DateTime validTo) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, SECOND_VERSION, validFrom, validTo);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(false);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        prepareToVersioning(publicationVersion);
        return publicationVersion;
    }

    private static PublicationVersion buildPublication05Version03(PublicationMock publication, DateTime validFrom) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, THIRD_VERSION, validFrom, null);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(true);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        prepareToVersioning(publicationVersion);
        return publicationVersion;
    }

    protected static Publication getPublication06WithMultiplePublishedVersionsAndLatestNoVisible() {
        if (PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE == null) {
            PublicationMock publication = createPublicationToAddVersions(1);

            DateTime secondVersionPublishTime = new DateTime().plusDays(1);

            PublicationVersion publicationVersion01 = buildPublication06Version01Published(publication, new DateTime().minusDays(1), secondVersionPublishTime);

            PublicationVersion publicationVersion02 = buildPublication06Version02Published(publication, secondVersionPublishTime);

            publicationVersion02.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesPersistedDoMocks.mockPublicationVersionRelated(publicationVersion01));
            
            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE = publication;
        }
        return PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;
    }

    private static PublicationVersion buildPublication06Version01Published(Publication publication, DateTime validFrom, DateTime validTo) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, INIT_VERSION, validFrom, validTo);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(false);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        prepareToVersioning(publicationVersion);
        return publicationVersion;
    }

    private static PublicationVersion buildPublication06Version02Published(Publication publication, DateTime validFrom) {
        PublicationVersionMock publicationVersion = buildPublishedPublicationVersion(publication, SECOND_VERSION, validFrom, null);
        publicationVersion.getSiemacMetadataStatisticalResource().setLastVersion(true);
        getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publicationVersion);
        prepareToVersioning(publicationVersion);
        return publicationVersion;
    }

    private static PublicationVersionMock buildPublishedPublicationVersion(Publication publication, String version, DateTime validFrom, DateTime validTo) {
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

    private static PublicationMock createPublicationToAddVersions(Integer sequential) {
        PublicationMock publication = new PublicationMock();
        publication.setSequentialId(1);
        getStatisticalResourcesPersistedDoMocks().mockPublication(publication);
        return publication;
    }

}
