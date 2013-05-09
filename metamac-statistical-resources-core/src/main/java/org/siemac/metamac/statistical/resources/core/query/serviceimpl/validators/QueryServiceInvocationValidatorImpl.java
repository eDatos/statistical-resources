package org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class QueryServiceInvocationValidatorImpl extends BaseInvocationValidator {

    // ------------------------------------------------------------------------
    // PUBLIC METHODS FOR SERVICE METHODS
    // ------------------------------------------------------------------------

    public static void checkRetrieveQueryVersionByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    public static void checkRetrieveQueryVersions(List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    public static void checkFindQueryVersionsByCondition(List<ConditionalCriteria> conditions, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    public static void checkCreateQueryVersion(QueryVersion queryVersion, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkNewQuery(queryVersion, exceptions);
    }

    public static void checkUpdateQueryVersion(QueryVersion queryVersion, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkExistingQuery(queryVersion, exceptions);
    }
    
    public static void checkMarkQueryVersionAsDiscontinued(QueryVersion queryVersion, List<MetamacExceptionItem> exceptions) {
        checkExistingQuery(queryVersion, exceptions);
    }
    
    public static void checkDeleteQueryVersion(String urn, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void checkNewQuery(QueryVersion queryVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(queryVersion, ServiceExceptionParameters.QUERY_VERSION, exceptions);
        if (queryVersion == null) {
            return;
        }

        checkNewLifeCycleStatisticalResource(queryVersion.getLifeCycleStatisticalResource(), ServiceExceptionParameters.QUERY_VERSION__LIFE_CYCLE_STATISTICAL_RESOURCE, exceptions);
        
        if (queryVersion.getLifeCycleStatisticalResource() != null) {
            // Check code
            StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getLifeCycleStatisticalResource().getCode(), ServiceExceptionParameters.QUERY_VERSION__LIFE_CYCLE_STATISTICAL_RESOURCE__CODE, exceptions);
            StatisticalResourcesValidationUtils.checkSemanticIdentifierAsMetamacID(queryVersion.getLifeCycleStatisticalResource().getCode(), ServiceExceptionParameters.QUERY_VERSION__LIFE_CYCLE_STATISTICAL_RESOURCE__CODE, exceptions);
        }
        checkQueryVersion(queryVersion, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(queryVersion.getId(), ServiceExceptionParameters.QUERY_VERSION__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(queryVersion.getVersion(), ServiceExceptionParameters.QUERY_VERSION__VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(queryVersion.getStatus(), ServiceExceptionParameters.QUERY_VERSION__STATUS, exceptions);
    }

    private static void checkExistingQuery(QueryVersion queryVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(queryVersion, ServiceExceptionParameters.QUERY_VERSION, exceptions);
        if (queryVersion == null) {
            return;
        }

        checkExistingLifeCycleStatisticalResource(queryVersion.getLifeCycleStatisticalResource(), ServiceExceptionParameters.QUERY_VERSION, exceptions);
        checkQueryVersion(queryVersion, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getId(), ServiceExceptionParameters.QUERY_VERSION__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getVersion(), ServiceExceptionParameters.QUERY_VERSION__VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getStatus(), ServiceExceptionParameters.QUERY_VERSION__STATUS, exceptions);
    }

    private static void checkQueryVersion(QueryVersion queryVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getUuid(), ServiceExceptionParameters.QUERY_VERSION__UUID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getDatasetVersion(), ServiceExceptionParameters.QUERY_VERSION__DATASET_VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getSelection(), ServiceExceptionParameters.QUERY_VERSION__SELECTION, exceptions);
        
        if (QueryTypeEnum.LATEST_DATA.equals(queryVersion.getType())) {
            StatisticalResourcesValidationUtils.checkMetadataRequired(queryVersion.getLatestDataNumber(), ServiceExceptionParameters.QUERY_VERSION__LATEST_DATA_NUMBER, exceptions);
            if (queryVersion.getLatestDataNumber() != null && queryVersion.getLatestDataNumber() <= Integer.valueOf(0)) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.QUERY_VERSION__LATEST_DATA_NUMBER));
            }
        } else {
            StatisticalResourcesValidationUtils.checkMetadataEmpty(queryVersion.getLatestDataNumber(), ServiceExceptionParameters.QUERY_VERSION__LATEST_DATA_NUMBER, exceptions);
        }
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(queryVersion.getSelection(), ServiceExceptionParameters.QUERY_VERSION__SELECTION, exceptions);
    }
}
