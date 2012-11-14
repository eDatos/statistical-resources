package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks.*;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.springframework.stereotype.Component;

@Component
public class PublicationVersionMockFactory extends MockFactory<PublicationVersion> {

    public static final String                     PUBLICATION_VERSION_01_BASIC_NAME                                           = "PUBLICATION_VERSION_01_BASIC";
    public static final PublicationVersion         PUBLICATION_VERSION_01_BASIC                                                = mockPersistedPublicationVersion();

    public static final String                     PUBLICATION_VERSION_02_BASIC_NAME                                           = "PUBLICATION_VERSION_02_BASIC";
    public static final PublicationVersion         PUBLICATION_VERSION_02_BASIC                                                = mockPersistedPublicationVersion();

    public static final String                     PUBLICATION_VERSION_03_ASSOCIATED_WITH_PUBLICATION_03_NAME                  = "PUBLICATION_VERSION_03_ASSOCIATED_WITH_PUBLICATION_03";
    public static final PublicationVersion         PUBLICATION_VERSION_03_ASSOCIATED_WITH_PUBLICATION_03                       = mockPersistedPublicationVersion(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS);

    public static final String                     PUBLICATION_VERSION_04_ASSOCIATED_WITH_PUBLICATION_03_AND_LAST_VERSION_NAME = "PUBLICATION_VERSION_04_ASSOCIATED_WITH_PUBLICATION_03_AND_LAST_VERSION";
    public static final PublicationVersion         PUBLICATION_VERSION_04_ASSOCIATED_WITH_PUBLICATION_03_AND_LAST_VERSION      = createPublicationVersion04();

    private static Map<String, PublicationVersion> mocks;

    static {
        mocks = new HashMap<String, PublicationVersion>();
        registerMocks(PublicationVersionMockFactory.class, PublicationVersion.class, mocks);
    }

    @Override
    public PublicationVersion getMock(String id) {
        return mocks.get(id);
    }


    private static PublicationVersion createPublicationVersion04() {
        // Relation with publication
        PublicationVersion publicationVersion = mockPersistedPublicationVersion(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS);
        // Version 02.000
        publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic("02.000");
        // Is last version
        publicationVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

        return publicationVersion;
    }

}
