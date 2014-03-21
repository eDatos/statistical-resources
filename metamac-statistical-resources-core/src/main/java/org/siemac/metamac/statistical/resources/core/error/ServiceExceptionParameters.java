package org.siemac.metamac.statistical.resources.core.error;

import java.io.Serializable;

public class ServiceExceptionParameters extends ServiceExceptionBaseParameters {

    // Version
    public static final String       VERSION_TYPE                             = "parameter.resources.versionType";
    public static final String       PREVIOUS_VERSION                         = "parameter.resources.previousVersion";

    // DSD
    public static final String       DSD_DIMENSION_ID                         = "parameter.resources.dsdDimensionId";

    // Statistical operation
    public static final String       STATISTICAL_OPERATION_URN                = "parameter.resources.statisticalOperationUrn";
    public static final String       STATISTICAL_OPERATION                    = "parameter.resources.statisticalOperation";

    // Dataset
    public static final String       DATASET_URN                              = "parameter.resources.datasetUrn";
    public static final String       DATASET_VERSION_URN                      = "parameter.resources.datasetVersionUrn";
    public static final String       DATASET_VERSION_URN_TO_COPY              = "parameter.resources.datasetVersionUrnToCopy";
    public static final String       DATASET_ATTRIBUTE                        = "parameter.resources.dataset_attribute";
    public static final String       DATASET_ATTRIBUTE__VALUE                 = "parameter.resources.dataset_attribute_value";
    public static final String       DATASET_ATTRIBUTE__UUID                  = "parameter.resources.dataset_attribute_uuid";
    public static final String       DATASET_DIMENSION_REPRESENTATION_MAPPING = "parameter.resources.dataset_dimension_representation_mapping";

    // Publication
    public static final String       PUBLICATION_URN                          = "parameter.resources.publicationUrn";
    public static final String       PUBLICATION_VERSION_URN                  = "parameter.resources.publicationVersionUrn";
    public static final String       PUBLICATION_VERSION_URN_TO_COPY          = "parameter.resources.publicationVersionUrnToCopy";
    public static final String       CHAPTER_URN                              = "parameter.resources.chapterUrn";
    public static final String       PARENT_CHAPTER_URN                       = "parameter.resources.parentChapterUrn";
    public static final String       ORDER_IN_LEVEL                           = "parameter.resources.orderInLevel";
    public static final String       CUBE_URN                                 = "parameter.resources.cubeUrn";

    // Query
    public static final String       QUERY_URN                                = "parameter.resources.queryUrn";
    public static final String       QUERY_VERSION__SELECTION__TIME_PERIOD    = "parameter.resources.queryVersion.selection.time_period";

    // Tasks
    public static final String       TASK_INFO_RESOURCE_ID                    = "parameter.resources.resourceId";
    public static final String       TASK_INFO_DATASET                        = "parameter.resources.taskInfoDataset";
    public static final String       TASK_INFO_DATASET_DSD_URN                = "parameter.resources.dataStructureUrn";
    public static final String       TASK_INFO_DATASET_DATASET_VERSION_ID     = "parameter.resources.datasetVersionId";
    public static final String       TASK_INFO_DATASET_NEW_DATASET_VERSION_ID = "parameter.resources.newDatasetVersionId";
    public static final String       TASK_INFO_DATASET_FILES                  = "parameter.resources.files";
    public static final String       TASK_DATASOURCE_ID                       = "parameter.resources.datasourceId";
    public static final String       TASK                                     = "parameter.resources.task";
    public static final String       TASK_DATASET_JOB_KEY                     = "parameter.resources.jobKey";
    public static final String       NOTIFY_TO_USER                           = "parameter.resources.notifyToUser";

    // Files
    public static final String       FILE_URLS                                = "parameter.resources.fileUrls";
    public static final String       FILE_DESCRIPTORS                         = "parameter.resources.fileDescriptors";
    public static final String       FILE_DESCRIPTOR_INPUT_MESSAGE            = "parameter.resources.inputMessage";
    public static final String       FILE_DESCRIPTOR_FILE_NAME                = "parameter.resources.fileName";
    public static final String       FILE_DESCRIPTOR_FORMAT                   = "parameter.resources.datasetFormat";

    // Rest parameters
    public static final Serializable CONCEPT_SCHEME_URN                       = "parameter.resources.rest.conceptSchemeUrn";
    public static final Serializable CODELIST_URN                             = "parameter.resources.rest.codelistUrn";
    public static final Serializable CATEGORY_SCHEME_URN                      = "parameter.resources.rest.categorySchemeUrn";
    public static final Serializable ORGANISATION_SCHEME_URN                  = "parameter.resources.rest.organisationSchemeUrn";

    // Misc
    public static final String       TIME                                     = "parameter.resources.time";

}
