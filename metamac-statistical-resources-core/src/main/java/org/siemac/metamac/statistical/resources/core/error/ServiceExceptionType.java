package org.siemac.metamac.statistical.resources.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    // Agency
    public static final CommonServiceExceptionType AGENCY_NOT_FOUND                                 = create("exception.resources.agency.not_found");

    // Dataset
    public static final CommonServiceExceptionType DATASET_NOT_FOUND                                = create("exception.resources.dataset.not_found");
    public static final CommonServiceExceptionType DATASET_VERSION_NOT_FOUND                        = create("exception.resources.dataset.version_not_found");
    public static final CommonServiceExceptionType DATASET_LAST_VERSION_NOT_FOUND                   = create("exception.resources.dataset.last_version_not_found");
    public static final CommonServiceExceptionType DATASET_MAX_REACHED_IN_OPERATION                 = create("exception.resources.dataset.max_reached_in_operation");

    // Datasource
    public static final CommonServiceExceptionType DATASOURCE_NOT_FOUND                             = create("exception.resources.datasource.not_found");

    // Publication
    public static final CommonServiceExceptionType PUBLICATION_NOT_FOUND                            = create("exception.resources.publication.not_found");
    public static final CommonServiceExceptionType PUBLICATION_VERSION_NOT_FOUND                    = create("exception.resources.publication.version_not_found");
    public static final CommonServiceExceptionType PUBLICATION_LAST_VERSION_NOT_FOUND               = create("exception.resources.publication.last_version_not_found");
    public static final CommonServiceExceptionType PUBLICATION_MAX_REACHED_IN_OPERATION             = create("exception.resources.publication.max_reached_in_operation");

    // Chapter
    public static final CommonServiceExceptionType CHAPTER_NOT_FOUND                                = create("exception.resources.chapter.not_found");
    public static final CommonServiceExceptionType CHAPTER_NOT_FOUND_IN_PUBLICATION_VERSION         = create("exception.resources.chapter.not_found_in_publication_version");

    // Cube
    public static final CommonServiceExceptionType CUBE_NOT_FOUND                                   = create("exception.resources.cube.not_found");

    // Query
    public static final CommonServiceExceptionType QUERY_NOT_FOUND                                  = create("exception.resources.query.not_found");

    // Identifiable Statistical Resource
    public static final CommonServiceExceptionType IDENTIFIABLE_STATISTICAL_RESOURCE_NOT_FOUND      = create("exception.resources.identifiable_statistical_resource.not_found");
    public static final CommonServiceExceptionType IDENTIFIABLE_STATISTICAL_RESOURCE_URN_DUPLICATED = create("exception.resources.identifiable_statistical_resource.urn_duplicated");

    // Life Cycle Statistical Resource
    public static final CommonServiceExceptionType LIFE_CYCLE_WRONG_PROC_STATUS                     = create("exception.resources.life_cycle.wrong_proc_status");

    // LISTS
    public static final CommonServiceExceptionType STATISTIC_OFFICIALITY_NOT_FOUND                  = create("exception.resources.statistic_officiality.not_found");

}
