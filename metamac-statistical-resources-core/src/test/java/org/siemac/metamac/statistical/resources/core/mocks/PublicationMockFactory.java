package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationMockFactory extends MockFactory<Publication> implements InitializingBean {

    @Autowired
    StatisticalResourcesPersistedDoMocks    statisticalResourcesPersistedDoMocks;

    @Autowired
    PublicationVersionMockFactory           publicationVersionMockFactory;

    public static final String              PUBLICATION_01_BASIC_NAME                             = "PUBLICATION_01_BASIC";
    public Publication                      PUBLICATION_01_BASIC;

    public static final String              PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME      = "PUBLICATION_02_BASIC_WITH_GENERATED_VERSION";
    public Publication                      PUBLICATION_02_BASIC_WITH_GENERATED_VERSION;

    public static final String              PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME = "PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS";
    public Publication                      PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;

    private static Map<String, Publication> mocks;

    @Override
    public void afterPropertiesSet() throws Exception {
        PUBLICATION_01_BASIC = createPublication();
        PUBLICATION_02_BASIC_WITH_GENERATED_VERSION = createPublicationWithGeneratedPublicationVersions();
        PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS = getPublication03With2PublicationVersions();

        mocks = new HashMap<String, Publication>();
        registerMocks(this, Publication.class, mocks);
    }

    @Override
    public Publication getMock(String id) {
        return mocks.get(id);
    }

    public Publication getPublication03With2PublicationVersions() {
        if (PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS == null) {
            Publication publication = createPublication();
            // Versions linked in version mock factory
            PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS = publication;
        }
        return PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS;
    }

    private Publication createPublication() {
        return statisticalResourcesPersistedDoMocks.mockPublicationWithoutGeneratedPublicationVersions();
    }

    private Publication createPublicationWithGeneratedPublicationVersions() {
        return statisticalResourcesPersistedDoMocks.mockPublicationWithGeneratedPublicationVersions();
    }

}
