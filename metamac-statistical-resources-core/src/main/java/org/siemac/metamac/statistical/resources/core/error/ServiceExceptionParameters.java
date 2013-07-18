package org.siemac.metamac.statistical.resources.core.error;

public class ServiceExceptionParameters extends ServiceExceptionBaseParameters {

    // Version
    public static final String VERSION_TYPE                    = "parameter.resources.versionType";
    public static final String PREVIOUS_VERSION                = "parameter.resources.previousVersion";

    // DSD
    public static final String DSD_DIMENSION_ID                = "parameter.resources.dsdDimensionId";

    // Statistical operation
    public static final String STATISTICAL_OPERATION_URN       = "parameter.resources.statisticalOperationUrn";
    public static final String STATISTICAL_OPERATION           = "parameter.resources.statisticalOperation";

    // Dataset
    public static final String DATASET_URN                     = "parameter.resources.datasetUrn";
    public static final String DATASET_VERSION_URN             = "parameter.resources.datasetVersionUrn";
    public static final String DATASET_VERSION_URN_TO_COPY     = "parameter.resources.datasetVersionUrnToCopy";

    // Publication
    public static final String PUBLICATION_URN                 = "parameter.resources.publicationUrn";
    public static final String PUBLICATION_VERSION_URN         = "parameter.resources.publicationVersionUrn";
    public static final String PUBLICATION_VERSION_URN_TO_COPY = "parameter.resources.publicationVersionUrnToCopy";
    public static final String CHAPTER_URN                     = "parameter.resources.chapterUrn";
    public static final String PARENT_CHAPTER_URN              = "parameter.resources.parentChapterUrn";
    public static final String ORDER_IN_LEVEL                  = "parameter.resources.orderInLevel";
    public static final String CUBE_URN                        = "parameter.resources.cubeUrn";

    // Query
    public static final String QUERY_URN                       = "parameter.resources.queryUrn";

    // Tasks
    public static final String TASK_INFO_DATASET               = "parameter.resources.taskInfoDataset";
    public static final String TASK_INFO_DATASET_DSD_URN       = "parameter.resources.dataStructureUrn";
    public static final String TASK_INFO_DATASET_DATASET_ID    = "parameter.resources.repoDatasetId";
    public static final String TASK_INFO_DATASET_FILES         = "parameter.resources.files";
    public static final String TASK_INFO_DATASET_JOB_KEY       = "parameter.resources.jobKey";
    public static final String TASK_DATASOURCE_ID              = "parameter.resources.datasourceId";
    public static final String TASK                            = "parameter.resources.task";
    public static final String TASK_DATASET_FORMAT             = "parameter.resources.datasetFormat";

    // Files
    public static final String FILE_DESCRIPTOR_INPUT_MESSAGE   = "parameter.resources.inputMessage";
    public static final String FILE_DESCRIPTOR_FILE_NAME       = "parameter.resources.fileName";

}
