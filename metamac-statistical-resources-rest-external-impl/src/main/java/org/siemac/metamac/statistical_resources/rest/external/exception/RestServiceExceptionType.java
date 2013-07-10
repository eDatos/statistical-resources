package org.siemac.metamac.statistical_resources.rest.external.exception;

import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;

public class RestServiceExceptionType extends RestCommonServiceExceptionType {

    public static final RestCommonServiceExceptionType DATASET_NOT_FOUND = create("exception.statistical_resources.dataset.not_found");
}
