package org.siemac.metamac.statistical.resources.core.common.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType SECURITY_ACTION_NOT_ALLOWED                 = create("exception.resources.security.action_not_allowed");

    public static final CommonServiceExceptionType DATASET_NOT_FOUND                           = create("exception.resources.dataset.not_found");
    public static final CommonServiceExceptionType DATASET_ALREADY_EXIST_IDENTIFIER_DUPLICATED = create("exception.resources.dataset.already_exist.identifier_duplicated");


}
