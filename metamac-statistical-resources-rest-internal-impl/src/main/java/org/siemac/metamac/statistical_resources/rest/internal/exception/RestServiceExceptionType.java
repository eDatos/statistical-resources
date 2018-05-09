package org.siemac.metamac.statistical_resources.rest.internal.exception;

import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;

public class RestServiceExceptionType extends RestCommonServiceExceptionType {

    public static final RestCommonServiceExceptionType DATASET_NOT_FOUND    = create("exception.statistical_resources.dataset.not_found");
    public static final RestCommonServiceExceptionType COLLECTION_NOT_FOUND = create("exception.statistical_resources.collection.not_found");
    public static final RestCommonServiceExceptionType QUERY_NOT_FOUND      = create("exception.statistical_resources.query.not_found");
    public static final RestCommonServiceExceptionType MULTIDATASET_NOT_FOUND = create("exception.statistical_resources.multidataset.not_found");
}
