package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion03ForPublication03;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion04ForPublication03AndLastVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion17WithStructureForPublicationVersion04;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion18WithStructureForPublicationVersion04AndLastVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion27V1PublishedForPublication05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion28V2PublishedForPublication05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion29V3PublishedForPublication05;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion30V1PublishedForPublication06;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.getPublicationVersion31V2PublishedNoVisibleForPublication06;

import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.stereotype.Component;

@Component
public class PublicationMockFactory extends StatisticalResourcesMockFactory<Publication> {

    public static final String PUBLICATION_01_BASIC_NAME                                                  = "PUBLICATION_01_BASIC";
    private static Publication PUBLICATION_01_BASIC;

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

    protected static Publication getPublication01Basic() {
        if (PUBLICATION_01_BASIC == null) {
            PUBLICATION_01_BASIC = createPublication();
        }
        return PUBLICATION_01_BASIC;
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
            Publication publication = createPublication();

            // Relations
            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS = publication;
            setPublication03Versions(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS);
        }
        return PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;
    }

    private static void setPublication03Versions(Publication publication) {
        PublicationVersion publicationV1 = getPublicationVersion03ForPublication03();
        PublicationVersion publicationV2 = getPublicationVersion04ForPublication03AndLastVersion();

        publication.addVersion(publicationV1);
        publicationV1.setPublication(publication);

        publication.addVersion(publicationV2);
        publicationV2.setPublication(publication);
    }
    
    protected static Publication getPublication04StructuredWith2PublicationVersions() {
        if (PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS == null) {
            Publication publication = createPublication();

            // Relations
            PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS = publication;
            setPublication04Versions(PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS);
        }
        return PUBLICATION_04_STRUCTURED_WITH_2_PUBLICATION_VERSIONS;
    }

    private static void setPublication04Versions(Publication publication) {
        PublicationVersion publicationV1 = getPublicationVersion17WithStructureForPublicationVersion04();
        PublicationVersion publicationV2 = getPublicationVersion18WithStructureForPublicationVersion04AndLastVersion();

        publication.addVersion(publicationV1);
        publicationV1.setPublication(publication);

        publication.addVersion(publicationV2);
        publicationV2.setPublication(publication);
    }
    
    protected static Publication getPublication05WithMultiplePublishedVersions() {
        if (PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS == null) {
            Publication publication = createPublication();
            PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS = publication;
            setPublication05Versions(PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS);
        }
        return PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS;
    }

    private static void setPublication05Versions(Publication publication) {
        PublicationVersion dsV1 = getPublicationVersion27V1PublishedForPublication05();
        PublicationVersion dsV2 = getPublicationVersion28V2PublishedForPublication05();
        PublicationVersion dsV3 = getPublicationVersion29V3PublishedForPublication05();

        publication.addVersion(dsV1);
        dsV1.setPublication(publication);

        publication.addVersion(dsV2);
        dsV2.setPublication(publication);

        publication.addVersion(dsV3);
        dsV3.setPublication(publication);

        dsV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToPublicationVersion(dsV1));
        dsV3.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToPublicationVersion(dsV2));
    }

    
    protected static Publication getPublication06WithMultiplePublishedVersionsAndLatestNoVisible() {
        if (PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE == null) {
            Publication publication = createPublication();
            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE = publication;
            setPublication06Versions(PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE);
        }
        return PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE;
    }
    
    private static void setPublication06Versions(Publication publication) {
        PublicationVersion dsV1 = getPublicationVersion30V1PublishedForPublication06();
        PublicationVersion dsV2 = getPublicationVersion31V2PublishedNoVisibleForPublication06();

        publication.addVersion(dsV1);
        dsV1.setPublication(publication);

        publication.addVersion(dsV2);
        dsV2.setPublication(publication);

        dsV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToPublicationVersion(dsV1));
    }

    private static Publication createPublication() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationWithoutGeneratedPublicationVersion();
    }

    private static Publication createPublicationWithGeneratedPublicationVersions() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationWithGeneratedPublicationVersion();
    }

}
