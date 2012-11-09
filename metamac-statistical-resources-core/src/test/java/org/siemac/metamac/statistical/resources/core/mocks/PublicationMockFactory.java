package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.*;
import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class PublicationMockFactory extends MockFactory<Publication> {

    public static final String              PUBLICATION_01_BASIC_NAME                             = "PUBLICATION_01_BASIC";
    public static final Publication         PUBLICATION_01_BASIC                                  = StatisticalResourcesDoMocks.mockPublication();

    public static final String              PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME      = "PUBLICATION_02_BASIC_WITH_GENERATED_VERSION";
    public static final Publication         PUBLICATION_02_BASIC_WITH_GENERATED_VERSION           = StatisticalResourcesDoMocks.mockPublicationWithGeneratedPublicationVersions();

    public static final String              PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME = "PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS";
    public static final Publication         PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS      = createPublication03With2PublicationVersions();

    private static Map<String, Publication> mocks;

    static {
        mocks = new HashMap<String, Publication>();
        registerMocks(PublicationMockFactory.class, Publication.class, mocks);
    }

    @Override
    public Publication getMock(String id) {
        return mocks.get(id);
    }

    private static Publication createPublication03With2PublicationVersions() {
        Publication publication = StatisticalResourcesDoMocks.mockPublication();
        publication.addVersion(PUBLICATION_VERSION_03_ASSOCIATED_WITH_PUBLICATION_03);
        publication.addVersion(PUBLICATION_VERSION_04_ASSOCIATED_WITH_PUBLICATION_03_AND_LAST_VERSION);
        return publication;
    }

}
