package org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class QueryServiceInvocationValidatorImpl extends BaseInvocationValidator {

    // ------------------------------------------------------------------------
    // PUBLIC METHODS FOR SERVICE METHODS
    // ------------------------------------------------------------------------

    public static void checkRetrieveQueryByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionSingleParameters.URN, exceptions);
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

        checkNewNameableStatisticalResource(query.getNameableStatisticalResource(), ServiceExceptionParameters.QUERY__NAMEABLE_STATISTICAL_RESOURCE, exceptions);
        checkQuery(query, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getId(), ServiceExceptionParameters.QUERY__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getVersion(), ServiceExceptionParameters.QUERY__VERSION, exceptions);
    }

    private static void checkExistingQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(query, ServiceExceptionParameters.QUERY, exceptions);
        if (query == null) {
            return;
        }

        checkExistingNameableStatisticalResource(query.getNameableStatisticalResource(), ServiceExceptionParameters.QUERY, exceptions);
        checkQuery(query, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getId(), ServiceExceptionParameters.QUERY__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getVersion(), ServiceExceptionParameters.QUERY__VERSION, exceptions);
    }

    private static void checkQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getUuid(), ServiceExceptionParameters.QUERY__UUID, exceptions);
    }
}
