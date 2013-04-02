package org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
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
    
    public static void checkMarkQueryAsDiscontinued(Query query, List<MetamacExceptionItem> exceptions) {
        checkExistingQuery(query, exceptions);
    }
    
    public static void checkDeleteQuery(String urn, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void checkNewQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(query, ServiceExceptionParameters.QUERY, exceptions);
        if (query == null) {
            return;
        }

        checkNewLifeCycleStatisticalResource(query.getLifeCycleStatisticalResource(), ServiceExceptionParameters.QUERY__LIFE_CYCLE_STATISTICAL_RESOURCE, exceptions);
        if (query.getLifeCycleStatisticalResource() != null) {
            // Check code
            StatisticalResourcesValidationUtils.checkMetadataRequired(query.getLifeCycleStatisticalResource().getCode(), ServiceExceptionParametersUtils.addParameter(ServiceExceptionParameters.QUERY__LIFE_CYCLE_STATISTICAL_RESOURCE, ServiceExceptionSingleParameters.CODE), exceptions);
            StatisticalResourcesValidationUtils.checkSemanticIdentifierAsMetamacID(query.getLifeCycleStatisticalResource().getCode(), ServiceExceptionParametersUtils.addParameter(ServiceExceptionParameters.QUERY__LIFE_CYCLE_STATISTICAL_RESOURCE, ServiceExceptionSingleParameters.CODE), exceptions);
        }
        checkQuery(query, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getId(), ServiceExceptionParameters.QUERY__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getVersion(), ServiceExceptionParameters.QUERY__VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getStatus(), ServiceExceptionParameters.QUERY__STATUS, exceptions);
    }

    private static void checkExistingQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(query, ServiceExceptionParameters.QUERY, exceptions);
        if (query == null) {
            return;
        }

        checkExistingLifeCycleStatisticalResource(query.getLifeCycleStatisticalResource(), ServiceExceptionParameters.QUERY, exceptions);
        checkQuery(query, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getId(), ServiceExceptionParameters.QUERY__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getVersion(), ServiceExceptionParameters.QUERY__VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getStatus(), ServiceExceptionParameters.QUERY__STATUS, exceptions);
    }

    private static void checkQuery(Query query, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getUuid(), ServiceExceptionParameters.QUERY__UUID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(query.getDatasetVersion(), ServiceExceptionParameters.QUERY__DATASET_VERSION, exceptions);
        if (QueryTypeEnum.LATEST_DATA.equals(query.getType())) {
            StatisticalResourcesValidationUtils.checkMetadataRequired(query.getLatestDataNumber(), ServiceExceptionParameters.QUERY__LATEST_DATA_NUMBER, exceptions);
            if (query.getLatestDataNumber() != null && query.getLatestDataNumber() <= Integer.valueOf(0)) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY__LATEST_DATA_NUMBER));
            }
        } else {
            StatisticalResourcesValidationUtils.checkMetadataEmpty(query.getLatestDataNumber(), ServiceExceptionParameters.QUERY__LATEST_DATA_NUMBER, exceptions);
        }
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(query.getSelection(), ServiceExceptionParameters.QUERY__SELECTION, exceptions);
    }
}
