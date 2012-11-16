package org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

import java.util.List;

public class QueryServiceInvocationValidatorImpl extends BaseInvocationValidator {

    // ------------------------------------------------------------------------
    // PUBLIC METHODS FOR SERVICE METHODS
    // ------------------------------------------------------------------------

    public static void checkRetrieveQueryByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
    }

    public static void checkRetrieveQueries(List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    public static void checkFindQueriesByCondition(List<ConditionalCriteria> conditions, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    public static void checkCreateQuery(Query query, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkNewQuery(query, exceptions);
    }

    public static void checkUpdateQuery(Query query, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkExistingQuery(query, exceptions);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void checkNewQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(query, ServiceExceptionParameters.QUERY, exceptions);
        if (query == null) {
            return;
        }

        checkNewNameableStatisticalResource(query.getNameableStatisticalResource(), exceptions);
        checkQuery(query, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getId(), ServiceExceptionParameters.QUERY_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getVersion(), ServiceExceptionParameters.QUERY_VERSION, exceptions);
    }

    private static void checkExistingQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(query, ServiceExceptionParameters.QUERY, exceptions);
        if (query == null) {
            return;
        }

        checkExistingNameableStatisticalResource(query.getNameableStatisticalResource(), exceptions);
        checkQuery(query, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getId(), ServiceExceptionParameters.QUERY_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getVersion(), ServiceExceptionParameters.QUERY_VERSION, exceptions);
    }

    private static void checkQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getUuid(), ServiceExceptionParameters.QUERY_UUID, exceptions);
    }
}
