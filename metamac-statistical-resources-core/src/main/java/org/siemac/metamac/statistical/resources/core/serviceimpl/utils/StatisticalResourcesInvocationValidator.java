package org.siemac.metamac.statistical.resources.core.serviceimpl.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.domain.Query;

public class StatisticalResourcesInvocationValidator {

    public static void checkRetrieveQueryByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }


    private static void checkQuery(Query query, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(query, ServiceExceptionParameters.QUERY, exceptions);
        if (query == null) {
            return;
        }
        // Common metadata of statistical resource
        // TODO
    }
}
