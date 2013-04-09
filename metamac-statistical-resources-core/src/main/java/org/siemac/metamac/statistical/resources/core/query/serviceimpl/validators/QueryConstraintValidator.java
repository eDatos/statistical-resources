package org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.QueryStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public class QueryConstraintValidator {

    public static void checkQueryVersionForMarkAsDiscontinued(QueryVersion queryVersion) throws MetamacException {
        QueryStatusEnumUtils.checkPossibleQueryStatus(queryVersion, QueryStatusEnum.PENDING_REVIEW);
    }
}
