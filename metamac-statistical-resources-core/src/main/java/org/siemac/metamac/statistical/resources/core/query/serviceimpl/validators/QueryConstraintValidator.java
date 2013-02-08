package org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;


public class QueryConstraintValidator {

    public static void checkQueryForMarkAsDiscontinued(Query query) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
        checkQueryExpectedStatus(QueryStatusEnum.PENDING_REVIEW, query.getStatus(), exceptions);
        ExceptionUtils.throwIfException(exceptions);
    }

    private static void checkQueryExpectedStatus(QueryStatusEnum expected, QueryStatusEnum actual, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (!expected.equals(actual)) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_INVALID_STATUS, expected));
        }
    }
}
