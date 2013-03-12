package org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.QueryStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public class QueryConstraintValidator {

    public static void checkQueryForMarkAsDiscontinued(Query query) throws MetamacException {
        QueryStatusEnumUtils.checkPossibleQueryStatus(query, QueryStatusEnum.PENDING_REVIEW);
    }
}
