package org.siemac.metamac.statistical.resources.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    // Agency
    public static final CommonServiceExceptionType AGENCY_NOT_FOUND                                        = create("exception.resources.agency.not_found");

    // Dataset
    public static final CommonServiceExceptionType DATASET_NOT_FOUND                                       = create("exception.resources.dataset.not_found");
    public static final CommonServiceExceptionType DATASET_VERSION_NOT_FOUND                               = create("exception.resources.dataset.version_not_found");
    public static final CommonServiceExceptionType DATASET_LAST_VERSION_NOT_FOUND                          = create("exception.resources.dataset.last_version_not_found");
    public static final CommonServiceExceptionType DATASET_MAX_REACHED_IN_OPERATION                        = create("exception.resources.dataset.max_reached_in_operation");
    public static final CommonServiceExceptionType DATASET_EMPTY_DATASOURCES                               = create("exception.resources.dataset.empty_datasources");
    public static final CommonServiceExceptionType DATASET_NO_DATA                                         = create("exception.resources.dataset.empty_data");

    // Datasource
    public static final CommonServiceExceptionType DATASOURCE_NOT_FOUND                                    = create("exception.resources.datasource.not_found");

    // Publication
    public static final CommonServiceExceptionType PUBLICATION_NOT_FOUND                                   = create("exception.resources.publication.not_found");
    public static final CommonServiceExceptionType PUBLICATION_VERSION_NOT_FOUND                           = create("exception.resources.publication.version_not_found");
    public static final CommonServiceExceptionType PUBLICATION_LAST_VERSION_NOT_FOUND                      = create("exception.resources.publication.last_version_not_found");
    public static final CommonServiceExceptionType PUBLICATION_MAX_REACHED_IN_OPERATION                    = create("exception.resources.publication.max_reached_in_operation");

    // Chapter
    public static final CommonServiceExceptionType CHAPTER_NOT_FOUND                                       = create("exception.resources.chapter.not_found");
    public static final CommonServiceExceptionType CHAPTER_NOT_FOUND_IN_PUBLICATION_VERSION                = create("exception.resources.chapter.not_found_in_publication_version");

    // Cube
    public static final CommonServiceExceptionType CUBE_NOT_FOUND                                          = create("exception.resources.cube.not_found");

    // Query
    public static final CommonServiceExceptionType QUERY_NOT_FOUND                                         = create("exception.resources.query.not_found");

    // Identifiable Statistical Resource
    public static final CommonServiceExceptionType IDENTIFIABLE_STATISTICAL_RESOURCE_NOT_FOUND             = create("exception.resources.identifiable_statistical_resource.not_found");
    public static final CommonServiceExceptionType IDENTIFIABLE_STATISTICAL_RESOURCE_URN_DUPLICATED        = create("exception.resources.identifiable_statistical_resource.urn_duplicated");

    // Life Cycle Statistical Resource
    public static final CommonServiceExceptionType LIFE_CYCLE_WRONG_PROC_STATUS                            = create("exception.resources.life_cycle.wrong_proc_status");

    // Related resources
    public static final CommonServiceExceptionType RELATED_RESOURCE_NOT_PUBLISHED                          = create("exception.resources.related_resource.not_published");
    public static final CommonServiceExceptionType RELATED_RESOURCE_WITHOUT_PUBLISHED_VERSION              = create("exception.resources.related_resource.without_published_version");

    // External items
    public static final CommonServiceExceptionType EXTERNAL_ITEM_NOT_PUBLISHED                             = create("exception.resources.external_item.urn.not_published");

    // Lists
    public static final CommonServiceExceptionType STATISTIC_OFFICIALITY_NOT_FOUND                         = create("exception.resources.statistic_officiality.not_found");

    // Tasks
    public static final CommonServiceExceptionType TASKS_ERROR_MAX_CURRENT_JOBS                            = create("exception.resources.task.error.max_current_jobs");
    public static final CommonServiceExceptionType TASKS_JOB_NOT_FOUND                                     = create("exception.resources.task.error.not_found");
    public static final CommonServiceExceptionType TASKS_ERROR_SERVER_DOWN                                 = create("exception.resources.task.error.server_down");
    public static final CommonServiceExceptionType TASKS_ERROR                                             = create("exception.resources.task.error");
    public static final CommonServiceExceptionType TASKS_SCHEDULER_ERROR                                   = create("exception.resources.task.scheduler.error");

    // Dataset Importation
    public static final CommonServiceExceptionType FILE_NOT_LINKED_TO_ANY_DATASET_IN_STATISTICAL_OPERATION = create("exception.resources.dataset.importation.file_not_linked_to_any_dataset_in_statistical_operation");
    public static final CommonServiceExceptionType INVALID_FILE_FOR_DATASET_VERSION                        = create("exception.resources.dataset.importation.invalid_file_for_dataset");
    public static final CommonServiceExceptionType IMPORTATION_DATASET_VERSION_ERROR                       = create("exception.resources.dataset.importation.dataset_version_error");
    public static final CommonServiceExceptionType IMPORTATION_ATTR_CODE_ENUM_NOT_VALID                    = create("exception.resources.dataset.importation.attribute_code_enumeration_not_valid");
    public static final CommonServiceExceptionType IMPORTATION_ATTR_NOT_MATCH                              = create("exception.resources.dataset.importation.attribute_not_match");

    public static final CommonServiceExceptionType IMPORTATION_DIM_CODE_ENUM_NOT_VALID                     = create("exception.resources.dataset.importation.dimension_code_enumeration_not_valid");
    public static final CommonServiceExceptionType IMPORTATION_DIM_KEY_CARDINALITY_NOT_MATCH               = create("exception.resources.dataset.importation.dimension_keys_cardinality_not_match");
    public static final CommonServiceExceptionType IMPORTATION_DIM_NOT_MATCH                               = create("exception.resources.dataset.importation.dimension_not_match");

    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_ATTR_NOT_MATCH                  = create("exception.resources.dataset.importation.observation_attribute_not_match");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_ATTR_CARDINALITY_EXCEEDED       = create("exception.resources.dataset.importation.observation_attributes_cardinality_exceeded");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_MANDATORY_ATTR_NOT_FOUND        = create("exception.resources.dataset.importation.observation_mandatory_attribute_not_found");

    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_MINLENGTH         = create("exception.resources.dataset.importation.observation.nonenumerated.minlength");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_MAXLENGTH         = create("exception.resources.dataset.importation.observation.nonenumerated.maxlength");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_MINVALUE          = create("exception.resources.dataset.importation.observation.nonenumerated.minvalue");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_MAXVALUE          = create("exception.resources.dataset.importation.observation.nonenumerated.maxvalue");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_PATTERN           = create("exception.resources.dataset.importation.observation.nonenumerated.pattern");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_TEMPORAL_PATTERN  = create("exception.resources.dataset.importation.observation.nonenumerated.temporal_pattern");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_DECIMAL           = create("exception.resources.dataset.importation.observation.nonenumerated.decimal");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_STARTTIMES        = create("exception.resources.dataset.importation.observation.nonenumerated.starttimes");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NONENUMERATED_ENDTIMES          = create("exception.resources.dataset.importation.observation.nonenumerated.endtimes");

    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_CODE_ENUM_NOT_VALID             = create("exception.resources.dataset.importation.observation.code_enumeration_not_valid");
    public static final CommonServiceExceptionType IMPORTATION_OBSERVATION_NOT_NUMERIC                     = create("exception.resources.dataset.importation.observation.not_numeric");

}
