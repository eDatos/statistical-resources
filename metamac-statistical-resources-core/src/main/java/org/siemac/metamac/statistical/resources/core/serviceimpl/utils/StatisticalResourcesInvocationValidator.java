package org.siemac.metamac.statistical.resources.core.serviceimpl.utils;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public class StatisticalResourcesInvocationValidator {

    // TODO: Hay que chequear todos los campos de las entidades de tipo IS y EI para que llamen a checkMetadataRequired o checkMetadataOptionalIsValid
    
    public static void checkRetrieveQueryByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }
    
    public static void checkRetrieveQueries(List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }
        
        ExceptionUtils.throwIfException(exceptions);
    }
    
    public static void checkFindQueriesByCondition(List<ConditionalCriteria> conditions, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }
        
        ExceptionUtils.throwIfException(exceptions);
        
    }

    public static void checkCreateQuery(Query query, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }
        checkQuery(query, exceptions);
        ExceptionUtils.throwIfException(exceptions);
        
    }

    public static void checkUpdateQuery(Query query, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }
        checkQuery(query, exceptions);
        ExceptionUtils.throwIfException(exceptions);
    }


    private static void checkQuery(Query query, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(query, ServiceExceptionParameters.QUERY, exceptions);
        if (query == null) {
            return;
        }
        // Common metadata of query
        BaseInvocationValidator.checkNameableStatisticalResource(query.getNameableStatisticalResource(), exceptions);
    }
}
