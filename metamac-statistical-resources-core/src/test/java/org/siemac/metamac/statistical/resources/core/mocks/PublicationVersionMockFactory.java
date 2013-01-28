package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
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
        PUBLICATION_VERSION_03_FOR_PUBLICATION_03 = getPublicationVersion03();
        PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION = getPublicationVersion04();

        setPublication03AndVersionsRelations();

        mocks = new HashMap<String, PublicationVersion>();
        registerMocks(this, PublicationVersion.class, mocks);
    }

    @Override
    public PublicationVersion getMock(String id) {
        return mocks.get(id);
    }

    private void setPublication03AndVersionsRelations() {
        Publication pub = publicationMockFactory.getPublication03With2PublicationVersions();

        PublicationVersion pubVersion03 = getPublicationVersion03();
        PublicationVersion pubVersion04 = getPublicationVersion04();

        pub.addVersion(pubVersion03);
        pub.addVersion(pubVersion04);

        pubVersion03.getSiemacMetadataStatisticalResource().setIsReplacedBy(StatisticalResourcesMockFactoryUtils.createRelatedResource(pubVersion04));
        pubVersion04.getSiemacMetadataStatisticalResource().setReplacesVersion(StatisticalResourcesMockFactoryUtils.createRelatedResource(pubVersion03));
    }

    private PublicationVersion getPublicationVersion03() {
        if (PUBLICATION_VERSION_03_FOR_PUBLICATION_03 == null) {

            // Relation with publication
            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(PUBLICATION_VERSION_03_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);

            PUBLICATION_VERSION_03_FOR_PUBLICATION_03 = publicationVersion;
        }
        return PUBLICATION_VERSION_03_FOR_PUBLICATION_03;
    }

    private PublicationVersion getPublicationVersion04() {
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

    private PublicationVersion createPublicationVersion() {
        return statisticalResourcesPersistedDoMocks.mockPublicationVersion();
    }

    private PublicationVersion createPublicationVersion(Publication publication) {
        return statisticalResourcesPersistedDoMocks.mockPublicationVersion(publication);
    }

    private RelatedResource createRelatedResourcePublicationVersion(PublicationVersion publicationVersion) {
        RelatedResource relatedResource = StatisticalResourcesDoMocks.mockDatasetVersionRelated();
        relatedResource.setCode(publicationVersion.getSiemacMetadataStatisticalResource().getCode());
        relatedResource.setTitle(publicationVersion.getSiemacMetadataStatisticalResource().getTitle());
        relatedResource.setUrn(publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        relatedResource.setUri(publicationVersion.getSiemacMetadataStatisticalResource().getUri());
        return relatedResource;
    }
}
