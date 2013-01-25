package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationVersionMockFactory extends MockFactory<PublicationVersion> implements InitializingBean {

    @Autowired
    StatisticalResourcesPersistedDoMocks           statisticalResourcesPersistedDoMocks;

    @Autowired
    PublicationMockFactory                         publicationMockFactory;

    public static final String                     PUBLICATION_VERSION_01_BASIC_NAME                               = "PUBLICATION_VERSION_01_BASIC";
    public PublicationVersion                      PUBLICATION_VERSION_01_BASIC;

    public static final String                     PUBLICATION_VERSION_02_BASIC_NAME                               = "PUBLICATION_VERSION_02_BASIC";
    public PublicationVersion                      PUBLICATION_VERSION_02_BASIC;

    public static final String                     PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME                  = "PUBLICATION_VERSION_03_FOR_PUBLICATION_03";
    public PublicationVersion                      PUBLICATION_VERSION_03_FOR_PUBLICATION_03;

    public static final String                     PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME = "PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION";
    public PublicationVersion                      PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;

    private static Map<String, PublicationVersion> mocks;

    private static final String                    PUBLICATION_VERSION_03_VERSION                                  = "01.000";
    private static final String                    PUBLICATION_VERSION_04_VERSION                                  = "02.000";

    @Override
    public void afterPropertiesSet() throws Exception {
        PUBLICATION_VERSION_01_BASIC = createPublicationVersion();
        PUBLICATION_VERSION_02_BASIC = createPublicationVersion();
        PUBLICATION_VERSION_03_FOR_PUBLICATION_03 = createPublicationVersion03();
        PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION = createPublicationVersion04();

        mocks = new HashMap<String, PublicationVersion>();
        registerMocks(this, PublicationVersion.class, mocks);
    }

    @Override
    public PublicationVersion getMock(String id) {
        return mocks.get(id);
    }

    private PublicationVersion createPublicationVersion03() {
        // Relation with publication
        PublicationVersion publicationVersion = createPublicationVersion(publicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS);
        publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);
        publicationVersion.getSiemacMetadataStatisticalResource().setReplacedByVersion(PUBLICATION_VERSION_04_VERSION);

        return publicationVersion;
    }

    private PublicationVersion createPublicationVersion04() {
        // Relation with publication
        PublicationVersion publicationVersion = createPublicationVersion(publicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS);
        // Version 02.000
        publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(PUBLICATION_VERSION_04_VERSION);
        // ReplaceTo
        publicationVersion.getSiemacMetadataStatisticalResource().setReplaceToVersion(PUBLICATION_VERSION_03_VERSION);
        // Is last version
        publicationVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

        return publicationVersion;
    }

    private PublicationVersion createPublicationVersion() {
        return statisticalResourcesPersistedDoMocks.mockPublicationVersion();
    }

    private PublicationVersion createPublicationVersion(Publication publication) {
        return statisticalResourcesPersistedDoMocks.mockPublicationVersion(publication);
    }

}
