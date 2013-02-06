package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationVersionMockFactory extends StatisticalResourcesMockFactory<PublicationVersion> {

    @Autowired
    PublicationMockFactory            publicationMockFactory;

    public static final String        PUBLICATION_VERSION_01_BASIC_NAME                               = "PUBLICATION_VERSION_01_BASIC";
    private static PublicationVersion PUBLICATION_VERSION_01_BASIC;

    public static final String        PUBLICATION_VERSION_02_BASIC_NAME                               = "PUBLICATION_VERSION_02_BASIC";
    private static PublicationVersion PUBLICATION_VERSION_02_BASIC;

    public static final String        PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME                  = "PUBLICATION_VERSION_03_FOR_PUBLICATION_03";
    private static PublicationVersion PUBLICATION_VERSION_03_FOR_PUBLICATION_03;

    public static final String        PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME = "PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION";
    private static PublicationVersion PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;

    private static final String       PUBLICATION_VERSION_03_VERSION                                  = "01.000";
    private static final String       PUBLICATION_VERSION_04_VERSION                                  = "02.000";

    protected static PublicationVersion getPublicationVersion01Basic() {
        if (PUBLICATION_VERSION_01_BASIC == null) {
            PUBLICATION_VERSION_01_BASIC = createPublicationVersion();
        }
        return PUBLICATION_VERSION_01_BASIC;
    }
    
    
    protected static PublicationVersion getPublicationVersion02Basic() {
        if (PUBLICATION_VERSION_02_BASIC == null) {
            PUBLICATION_VERSION_02_BASIC = createPublicationVersion();
        }
        return PUBLICATION_VERSION_02_BASIC;
    }

    protected static PublicationVersion getPublicationVersion03ForPublication03() {
        if (PUBLICATION_VERSION_03_FOR_PUBLICATION_03 == null) {

            // Relation with publication
            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(PUBLICATION_VERSION_03_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);

            PUBLICATION_VERSION_03_FOR_PUBLICATION_03 = publicationVersion;
        }
        return PUBLICATION_VERSION_03_FOR_PUBLICATION_03;
    }

    protected static PublicationVersion getPublicationVersion04ForPublication03AndLastVersion() {
        if (PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION == null) {
            // Relation with publication
            PublicationVersion publicationVersion = createPublicationVersion();
            // Version 02.000
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(PUBLICATION_VERSION_04_VERSION);

            publicationVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);
            PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION = publicationVersion;
        }
        return PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;
    }

    private static RelatedResource createRelatedResourcePublicationVersion(PublicationVersion publicationVersion) {
        RelatedResource relatedResource = StatisticalResourcesDoMocks.mockDatasetVersionRelated();
        relatedResource.setCode(publicationVersion.getSiemacMetadataStatisticalResource().getCode());
        relatedResource.setTitle(publicationVersion.getSiemacMetadataStatisticalResource().getTitle());
        relatedResource.setUrn(publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        relatedResource.setUri(publicationVersion.getSiemacMetadataStatisticalResource().getUri());
        return relatedResource;
    }

    private static PublicationVersion createPublicationVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationVersion();
    }

    private static PublicationVersion createPublicationVersion(Publication publication) {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publication);
    }
}
