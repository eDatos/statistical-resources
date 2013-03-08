package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeAcronymEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
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

    public static final String        PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001_NAME            = "PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001";
    private static PublicationVersion PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001;

    public static final String        PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002_NAME            = "PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002";
    private static PublicationVersion PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002;

    public static final String        PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003_NAME            = "PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003";
    private static PublicationVersion PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003;

    public static final String        PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001_NAME            = "PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001";
    private static PublicationVersion PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001;

    public static final String        PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002_NAME            = "PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002";
    private static PublicationVersion PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002;

    public static final String        PUBLICATION_VERSION_10_OPERATION_0002_CODE_0003_NAME            = "PUBLICATION_VERSION_10_OPERATION_0002_CODE_0003";
    private static PublicationVersion PUBLICATION_VERSION_10_OPERATION_0002_CODE_0003;

    public static final String        PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX_NAME             = "PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX";
    private static PublicationVersion PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX;

    private static final String       OPERATION_01_CODE                                               = "C00025A";
    private static final String       OPERATION_02_CODE                                               = "C00022A";

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

            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(PUBLICATION_VERSION_03_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);

            // Relations
            PUBLICATION_VERSION_03_FOR_PUBLICATION_03 = publicationVersion;
            PUBLICATION_VERSION_03_FOR_PUBLICATION_03.setPublication(PublicationMockFactory.getPublication03BasicWith2PublicationVersions());
        }
        return PUBLICATION_VERSION_03_FOR_PUBLICATION_03;
    }

    protected static PublicationVersion getPublicationVersion04ForPublication03AndLastVersion() {
        if (PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION == null) {
            PublicationVersion publicationVersion = createPublicationVersion();

            // Version 02.000
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(PUBLICATION_VERSION_04_VERSION);

            // Last version
            publicationVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);

            // Relations
            PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION = publicationVersion;
            PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION.setPublication(PublicationMockFactory.getPublication03BasicWith2PublicationVersions());
        }
        return PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;
    }

    public static PublicationVersion getPublicationVersion05Operation0001Code0001() {
        if (PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001 == null) {
            PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001 = createPublicationVersionInOperation(OPERATION_01_CODE, 1);
        }
        return PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001;
    }

    public static PublicationVersion getPublicationVersion06Operation0001Code0002() {
        if (PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002 == null) {
            PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002 = createPublicationVersionInOperation(OPERATION_01_CODE, 2);
        }
        return PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002;
    }

    public static PublicationVersion getPublicationVersion07Operation0001Code0003() {
        if (PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003 == null) {
            PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003 = createPublicationVersionInOperation(OPERATION_01_CODE, 3);
        }
        return PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003;
    }

    public static PublicationVersion getPublicationVersion08Operation0002Code0001() {
        if (PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001 == null) {
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001 = createPublicationVersionInOperation(OPERATION_02_CODE, 1);
        }
        return PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001;
    }

    public static PublicationVersion getPublicationVersion09Operation0002Code0002() {
        if (PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002 == null) {
            PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002 = createPublicationVersionInOperation(OPERATION_02_CODE, 2);
        }
        return PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002;
    }

    public static PublicationVersion getPublicationVersion10Operation0002Code0003() {
        if (PUBLICATION_VERSION_10_OPERATION_0002_CODE_0003 == null) {
            PUBLICATION_VERSION_10_OPERATION_0002_CODE_0003 = createPublicationVersionInOperation(OPERATION_02_CODE, 3);
        }
        return PUBLICATION_VERSION_10_OPERATION_0002_CODE_0003;
    }

    public static PublicationVersion getPublicationVersion11Operation0002CodeMax() {
        if (PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX == null) {
            PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX = createPublicationVersionInOperation(OPERATION_01_CODE, 9999);
        }
        return PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX;
    }

    private static RelatedResource createRelatedResourcePublicationVersion(PublicationVersion publicationVersion) {
        RelatedResource relatedResource = StatisticalResourcesDoMocks.mockDatasetVersionRelated();
        relatedResource.setCode(publicationVersion.getSiemacMetadataStatisticalResource().getCode());
        relatedResource.setTitle(publicationVersion.getSiemacMetadataStatisticalResource().getTitle());
        relatedResource.setUrn(publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        relatedResource.setUri(publicationVersion.getSiemacMetadataStatisticalResource().getUri());
        return relatedResource;
    }

    private static PublicationVersion createPublicationVersion(Publication publication) {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationVersion(publication);
    }

    private static PublicationVersion createPublicationVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationVersion();
    }

    private static PublicationVersion createPublicationVersionInOperation(String operationCode, int sequentialId) {
        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationItem(operationCode);
        PublicationVersion publicationVersion = getStatisticalResourcesPersistedDoMocks().mockPublicationVersion();
        publicationVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(operation);
        publicationVersion.getSiemacMetadataStatisticalResource().setCode(buildPublicationCode(operationCode, sequentialId));
        return publicationVersion;
    }

    private static String buildPublicationCode(String operationCode, int sequentialId) {
        return operationCode + "_" + StatisticalResourceTypeAcronymEnum.PDD + "_" + String.format("%04d", sequentialId);
    }
}
