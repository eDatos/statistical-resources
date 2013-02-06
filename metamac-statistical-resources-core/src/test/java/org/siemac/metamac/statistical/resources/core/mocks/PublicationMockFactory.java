package org.siemac.metamac.statistical.resources.core.mocks;

import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.springframework.stereotype.Component;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.*;

@Component
public class PublicationMockFactory extends StatisticalResourcesMockFactory<Publication> {

    public static final String PUBLICATION_01_BASIC_NAME                             = "PUBLICATION_01_BASIC";
    private static Publication PUBLICATION_01_BASIC;

    public static final String PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME      = "PUBLICATION_02_BASIC_WITH_GENERATED_VERSION";
    private static Publication PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;

    public static final String PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME = "PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS";
    private static Publication PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;

    protected static Publication getPublication01Basic() {
        if (PUBLICATION_01_BASIC == null) {
            PUBLICATION_01_BASIC = createPublication();
        }
        return PUBLICATION_01_BASIC;
    }

    protected static Publication getPublication02BasicWithGeneratedVersion() {
        if (PUBLICATION_02_BASIC_WITH_GENERATED_VERSION == null) {
            PUBLICATION_02_BASIC_WITH_GENERATED_VERSION = createPublicationWithGeneratedPublicationVersions();
        }
        return PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;
    }

    protected static Publication getPublication03BasicWith2PublicationVersions() {
        if (PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS == null) {
            Publication publication = createPublication();
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
        
        publicationV1.getSiemacMetadataStatisticalResource().setIsReplacedByVersion(StatisticalResourcesMockFactoryUtils.createRelatedResource(publicationV2));
        publicationV2.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesMockFactoryUtils.createRelatedResource(publicationV1));
    }

    private static Publication createPublication() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationWithoutGeneratedPublicationVersions();
    }

    private static Publication createPublicationWithGeneratedPublicationVersions() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationWithGeneratedPublicationVersions();
    }

}
