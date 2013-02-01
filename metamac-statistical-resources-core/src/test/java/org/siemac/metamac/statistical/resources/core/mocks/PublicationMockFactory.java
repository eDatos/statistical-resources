package org.siemac.metamac.statistical.resources.core.mocks;

import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationMockFactory extends StatisticalResourcesMockFactory<Publication> {

    @Autowired
    PublicationVersionMockFactory publicationVersionMockFactory;

    public static final String    PUBLICATION_01_BASIC_NAME                             = "PUBLICATION_01_BASIC";
    private static Publication    PUBLICATION_01_BASIC;

    public static final String    PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME      = "PUBLICATION_02_BASIC_WITH_GENERATED_VERSION";
    private static Publication    PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;

    public static final String    PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME = "PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS";
    private static Publication    PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;

    public static Publication getPublication01Basic() {
        if (PUBLICATION_01_BASIC == null) {
            PUBLICATION_01_BASIC = createPublication();
        }
        return PUBLICATION_01_BASIC;
    }

    public static Publication getPublication02BasicWithGeneratedVersion() {
        if (PUBLICATION_02_BASIC_WITH_GENERATED_VERSION == null) {
            PUBLICATION_02_BASIC_WITH_GENERATED_VERSION = createPublicationWithGeneratedPublicationVersions();
        }
        return PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;
    }

    public static Publication getPublication03With2PublicationVersions() {
        if (PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS == null) {
            Publication publication = createPublication();
            // Versions linked in version mock factory
            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS = publication;
        }
        return PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;
    }

    private static Publication createPublication() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationWithoutGeneratedPublicationVersions();
    }

    private static Publication createPublicationWithGeneratedPublicationVersions() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationWithGeneratedPublicationVersions();
    }

}
