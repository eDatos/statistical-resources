package org.siemac.metamac.statistical.resources.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    // Agency
    public static final CommonServiceExceptionType AGENCY_NOT_FOUND                            = create("exception.resources.agency.not_found");

    // Dataset
    public static final CommonServiceExceptionType DATASET_NOT_FOUND                           = create("exception.resources.dataset.not_found");
    public static final CommonServiceExceptionType DATASET_VERSION_NOT_FOUND                   = create("exception.resources.dataset.version_not_found");
    public static final CommonServiceExceptionType DATASET_LAST_VERSION_NOT_FOUND              = create("exception.resources.dataset.last_version_not_found");
    public static final CommonServiceExceptionType DATASET_ALREADY_EXIST_IDENTIFIER_DUPLICATED = create("exception.resources.dataset.already_exist.identifier_duplicated");

    // Datasource
    public static final CommonServiceExceptionType DATASOURCE_NOT_FOUND                        = create("exception.resources.datasource.not_found");

    // Publication
    public static final CommonServiceExceptionType PUBLICATION_NOT_FOUND                       = create("exception.resources.publication.not_found");
    public static final CommonServiceExceptionType PUBLICATION_VERSION_NOT_FOUND               = create("exception.resources.publication.version_not_found");
    public static final CommonServiceExceptionType PUBLICATION_LAST_VERSION_NOT_FOUND          = create("exception.resources.publication.last_version_not_found");

    // Query
    public static final CommonServiceExceptionType QUERY_NOT_FOUND                             = create("exception.resources.query.not_found");

}
