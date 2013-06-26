package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.getDataset01Basic;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.getDataset03With2DatasetVersions;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.getQuery01Simple;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationVersionMockFactory extends StatisticalResourcesMockFactory<PublicationVersion> {

    @Autowired
    PublicationMockFactory            publicationMockFactory;

    public static final String        PUBLICATION_VERSION_01_BASIC_NAME                                                      = "PUBLICATION_VERSION_01_BASIC";
    private static PublicationVersion PUBLICATION_VERSION_01_BASIC;

    public static final String        PUBLICATION_VERSION_02_BASIC_NAME                                                      = "PUBLICATION_VERSION_02_BASIC";
    private static PublicationVersion PUBLICATION_VERSION_02_BASIC;

    public static final String        PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME                                         = "PUBLICATION_VERSION_03_FOR_PUBLICATION_03";
    private static PublicationVersion PUBLICATION_VERSION_03_FOR_PUBLICATION_03;

    public static final String        PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME                        = "PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION";
    private static PublicationVersion PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;

    public static final String        PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME                                 = "PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001";
    private static PublicationVersion PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001;

    public static final String        PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME                                 = "PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002";
    private static PublicationVersion PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002;

    public static final String        PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME                                 = "PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003";
    private static PublicationVersion PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003;

    public static final String        PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001_NAME                                 = "PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001";
    private static PublicationVersion PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001;

    public static final String        PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002_NAME                                 = "PUBLICATION_VERSION_09_OPERATION_0002_CODE_000002";
    private static PublicationVersion PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002;

    public static final String        PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003_NAME                                 = "PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003";
    private static PublicationVersion PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003;

    public static final String        PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX_NAME                                    = "PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX";
    private static PublicationVersion PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX;

    public static final String        PUBLICATION_VERSION_12_DRAFT_NAME                                                      = "PUBLICATION_VERSION_12_DRAFT";
    private static PublicationVersion PUBLICATION_VERSION_12_DRAFT;

    public static final String        PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME                                      = "PUBLICATION_VERSION_13_PRODUCTION_VALIDATION";
    private static PublicationVersion PUBLICATION_VERSION_13_PRODUCTION_VALIDATION;

    public static final String        PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME                                       = "PUBLICATION_VERSION_14_DIFFUSION_VALIDATION";
    private static PublicationVersion PUBLICATION_VERSION_14_DIFFUSION_VALIDATION;

    public static final String        PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME                                        = "PUBLICATION_VERSION_15_VALIDATION_REJECTED";
    private static PublicationVersion PUBLICATION_VERSION_15_VALIDATION_REJECTED;

    public static final String        PUBLICATION_VERSION_16_PUBLISHED_NAME                                                  = "PUBLICATION_VERSION_16_PUBLISHED";
    private static PublicationVersion PUBLICATION_VERSION_16_PUBLISHED;

    public static final String        PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME                  = "PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04";
    private static PublicationVersion PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04;

    public static final String        PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME = "PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION";
    private static PublicationVersion PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION;

    public static final String        PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION_NAME                       = "PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION";
    private static PublicationVersion PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION;

    public static final String        PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION_NAME                        = "PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION";
    private static PublicationVersion PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION;

    public static final String        PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED_NAME                         = "PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED";
    private static PublicationVersion PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED;

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
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(2));

            // Relations
            PUBLICATION_VERSION_03_FOR_PUBLICATION_03 = publicationVersion;
            PUBLICATION_VERSION_03_FOR_PUBLICATION_03.setPublication(PublicationMockFactory.getPublication03BasicWith2PublicationVersions());
        }
        return PUBLICATION_VERSION_03_FOR_PUBLICATION_03;
    }

    protected static PublicationVersion getPublicationVersion04ForPublication03AndLastVersion() {
        if (PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION == null) {
            PublicationVersion publicationVersion = createPublicationVersion();

            // Version 002.000
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);

            // Last version
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));

            // Relations
            PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION = publicationVersion;
            PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION.setPublication(PublicationMockFactory.getPublication03BasicWith2PublicationVersions());
        }
        return PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;
    }

    protected static PublicationVersion getPublicationVersion05Operation0001Code000001() {
        if (PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001 == null) {
            PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001 = createPublicationVersionInOperation(OPERATION_01_CODE, 1);
        }
        return PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001;
    }

    protected static PublicationVersion getPublicationVersion06Operation0001Code000002() {
        if (PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002 == null) {
            PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002 = createPublicationVersionInOperation(OPERATION_01_CODE, 2);
        }
        return PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002;
    }

    protected static PublicationVersion getPublicationVersion07Operation0001Code000003() {
        if (PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003 == null) {
            PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003 = createPublicationVersionInOperation(OPERATION_01_CODE, 3);
        }
        return PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003;
    }

    protected static PublicationVersion getPublicationVersion08Operation0002Code000001() {
        if (PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001 == null) {
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001 = createPublicationVersionInOperation(OPERATION_02_CODE, 1);
        }
        return PUBLICATION_VERSION_08_OPERATION_0002_CODE_000001;
    }

    protected static PublicationVersion getPublicationVersion09Operation0002Code000002() {
        if (PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002 == null) {
            PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002 = createPublicationVersionInOperation(OPERATION_02_CODE, 2);
        }
        return PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002;
    }

    protected static PublicationVersion getPublicationVersion10Operation0002Code000003() {
        if (PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003 == null) {
            PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003 = createPublicationVersionInOperation(OPERATION_02_CODE, 3);
        }
        return PUBLICATION_VERSION_10_OPERATION_0002_CODE_000003;
    }

    protected static PublicationVersion getPublicationVersion11Operation0002CodeMax() {
        if (PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX == null) {
            PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX = createPublicationVersionInOperation(OPERATION_01_CODE, 999999);
        }
        return PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX;
    }

    protected static PublicationVersion getPublicationVersion12Draft() {
        if (PUBLICATION_VERSION_12_DRAFT == null) {
            PUBLICATION_VERSION_12_DRAFT = createPublicationVersion();
            PUBLICATION_VERSION_12_DRAFT.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        }
        return PUBLICATION_VERSION_12_DRAFT;
    }

    protected static PublicationVersion getPublicationVersion13ProductionValidation() {
        if (PUBLICATION_VERSION_13_PRODUCTION_VALIDATION == null) {
            PUBLICATION_VERSION_13_PRODUCTION_VALIDATION = createPublicationVersion();
            PUBLICATION_VERSION_13_PRODUCTION_VALIDATION.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        }
        return PUBLICATION_VERSION_13_PRODUCTION_VALIDATION;
    }

    protected static PublicationVersion getPublicationVersion14DiffusionValidation() {
        if (PUBLICATION_VERSION_14_DIFFUSION_VALIDATION == null) {
            PUBLICATION_VERSION_14_DIFFUSION_VALIDATION = createPublicationVersion();
            PUBLICATION_VERSION_14_DIFFUSION_VALIDATION.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        }
        return PUBLICATION_VERSION_14_DIFFUSION_VALIDATION;
    }

    protected static PublicationVersion getPublicationVersion15ValidationRejected() {
        if (PUBLICATION_VERSION_15_VALIDATION_REJECTED == null) {
            PUBLICATION_VERSION_15_VALIDATION_REJECTED = createPublicationVersion();
            PUBLICATION_VERSION_15_VALIDATION_REJECTED.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.VALIDATION_REJECTED);
        }
        return PUBLICATION_VERSION_15_VALIDATION_REJECTED;
    }

    protected static PublicationVersion getPublicationVersion16Published() {
        if (PUBLICATION_VERSION_16_PUBLISHED == null) {
            PUBLICATION_VERSION_16_PUBLISHED = createPublicationVersion();
            PUBLICATION_VERSION_16_PUBLISHED.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        }
        return PUBLICATION_VERSION_16_PUBLISHED;
    }

    protected static PublicationVersion getPublicationVersion17WithStructureForPublicationVersion04() {
        if (PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04 == null) {

            // General metadata
            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(2));

            // Structure
            // Chapter 01
            ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
            // --> Chapter 01.01
            ElementLevel elementLevel01_01 = createChapterElementLevel(publicationVersion, elementLevel01);
            elementLevel01_01.setOrderInLevel(Long.valueOf(1));
            // ----> Cube 01.01.01
            ElementLevel elementLevel01_01_01 = createDatasetCubeElementLevel(publicationVersion, getDataset03With2DatasetVersions(), elementLevel01_01);
            elementLevel01_01_01.setOrderInLevel(Long.valueOf(1));
            // --> Chapter 01.02
            ElementLevel elementLevel01_02 = createChapterElementLevel(publicationVersion, elementLevel01);
            elementLevel01_02.setOrderInLevel(Long.valueOf(2));
            // ----> Cube 01.02.01
            ElementLevel elementLevel01_02_01 = createQueryCubeElementLevel(publicationVersion, getQuery01Simple(), elementLevel01_02);
            elementLevel01_02_01.setOrderInLevel(Long.valueOf(1));
            // --> Cube 01.03
            ElementLevel elementLevel01_03 = createDatasetCubeElementLevel(publicationVersion, getDataset01Basic(), elementLevel01);
            elementLevel01_03.setOrderInLevel(Long.valueOf(3));
            // Chapter 02
            ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
            elementLevel02.setOrderInLevel(Long.valueOf(2));
            // --> Cube 02.01
            ElementLevel elementLevel02_01 = createQueryCubeElementLevel(publicationVersion, getQuery01Simple(), elementLevel02);
            elementLevel02_01.setOrderInLevel(Long.valueOf(1));
            // Cube 03
            ElementLevel elementLevel03 = createQueryCubeElementLevel(publicationVersion, getQuery01Simple());
            elementLevel03.setOrderInLevel(Long.valueOf(3));

            // Relations
            PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04 = publicationVersion;
            PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04.setPublication(PublicationMockFactory.getPublication03BasicWith2PublicationVersions());
        }
        return PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04;
    }

    protected static PublicationVersion getPublicationVersion18WithStructureForPublicationVersion04AndLastVersion() {
        if (PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION == null) {

            // General metadata
            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(SECOND_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);

            // Structure
            // Chapter 01
            ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
            // Chapter 02
            ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
            elementLevel02.setOrderInLevel(Long.valueOf(2));
            // --> Chapter 02.01
            ElementLevel elementLevel02_01 = createChapterElementLevel(publicationVersion, elementLevel02);
            elementLevel02_01.setOrderInLevel(Long.valueOf(1));

            // Relations
            PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION = publicationVersion;
            PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION.setPublication(PublicationMockFactory.getPublication03BasicWith2PublicationVersions());
        }
        return PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION;
    }

    protected static PublicationVersion getPublicationVersion19WithStructureProductionValidation() {
        if (PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION == null) {
            // General metadata
            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);

            // Structure
            // Chapter 01
            ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
            // Chapter 02
            ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
            elementLevel02.setOrderInLevel(Long.valueOf(2));
            // --> Chapter 02.01
            ElementLevel elementLevel02_01 = createChapterElementLevel(publicationVersion, elementLevel02);
            elementLevel02_01.setOrderInLevel(Long.valueOf(1));
            
            PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION = publicationVersion;
        }

        return PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION;
    }

    protected static PublicationVersion getPublicationVersion20WithStructureDiffusionValidation() {
        if (PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION == null) {
            // General metadata
            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);

            // Structure
            // Chapter 01
            ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
            // Chapter 02
            ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
            elementLevel02.setOrderInLevel(Long.valueOf(2));
            // --> Chapter 02.01
            ElementLevel elementLevel02_01 = createChapterElementLevel(publicationVersion, elementLevel02);
            elementLevel02_01.setOrderInLevel(Long.valueOf(1));
            
            PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION = publicationVersion;
        }

        return PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION;
    }

    protected static PublicationVersion getPublicationVersion21WithStructureValidationRejected() {
        if (PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED == null) {
            // General metadata
            PublicationVersion publicationVersion = createPublicationVersion();
            publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic(INIT_VERSION);
            publicationVersion.getSiemacMetadataStatisticalResource().setCreationDate(new DateTime().minusDays(1));
            publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(ProcStatusEnum.VALIDATION_REJECTED);

            // Structure
            // Chapter 01
            ElementLevel elementLevel01 = createChapterElementLevel(publicationVersion);
            elementLevel01.setOrderInLevel(Long.valueOf(1));
            // Chapter 02
            ElementLevel elementLevel02 = createChapterElementLevel(publicationVersion);
            elementLevel02.setOrderInLevel(Long.valueOf(2));
            // --> Chapter 02.01
            ElementLevel elementLevel02_01 = createChapterElementLevel(publicationVersion, elementLevel02);
            elementLevel02_01.setOrderInLevel(Long.valueOf(1));
            
            PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED = publicationVersion;
        }

        return PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED;
    }

    private static PublicationVersion createPublicationVersion() {
        return getStatisticalResourcesPersistedDoMocks().mockPublicationVersion();
    }

    private static ElementLevel createQueryCubeElementLevel(PublicationVersion publicationVersion, Query query) {
        return getStatisticalResourcesPersistedDoMocks().mockQueryCubeElementLevel(publicationVersion, query);
    }

    private static ElementLevel createQueryCubeElementLevel(PublicationVersion publicationVersion, Query query, ElementLevel parentElementLevel) {
        return getStatisticalResourcesPersistedDoMocks().mockQueryCubeElementLevel(publicationVersion, query, parentElementLevel);
    }

    private static ElementLevel createDatasetCubeElementLevel(PublicationVersion publicationVersion, Dataset dataset, ElementLevel parentElementLevel) {
        return getStatisticalResourcesPersistedDoMocks().mockDatasetCubeElementLevel(publicationVersion, dataset, parentElementLevel);
    }

    private static ElementLevel createChapterElementLevel(PublicationVersion publicationVersion, ElementLevel parentElementLevel) {
        return getStatisticalResourcesPersistedDoMocks().mockChapterElementLevel(publicationVersion, parentElementLevel);
    }

    private static ElementLevel createChapterElementLevel(PublicationVersion publicationVersion) {
        return getStatisticalResourcesPersistedDoMocks().mockChapterElementLevel(publicationVersion);
    }

    private static PublicationVersion createPublicationVersionInOperation(String operationCode, int sequentialId) {
        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
        PublicationVersion publicationVersion = getStatisticalResourcesPersistedDoMocks().mockPublicationVersion();
        publicationVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(operation);
        publicationVersion.getSiemacMetadataStatisticalResource().setCode(buildPublicationCode(operationCode, sequentialId));
        return publicationVersion;
    }

    private static String buildPublicationCode(String operationCode, int sequentialId) {
        return operationCode + "_" + String.format("%06d", sequentialId);
    }
}
