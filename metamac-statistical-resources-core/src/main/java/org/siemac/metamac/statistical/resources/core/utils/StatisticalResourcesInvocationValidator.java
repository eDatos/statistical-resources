package org.siemac.metamac.statistical.resources.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
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

    public static void checkCreateDatasource(String datasetUrn, Datasource datasource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(datasetUrn, ServiceExceptionParameters.DATASET_URN, exceptions);
        checkDatasource(datasource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkUpdateDatasource(Datasource datasource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        checkDatasource(datasource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkRetrieveDatasourceByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkDeleteDatasource(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkRetrieveDatasourcesByDataset(String datasetUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        ValidationUtils.checkParameterRequired(datasetUrn, ServiceExceptionParameters.DATASET_URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    private static void checkDatasource(Datasource datasource, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(datasource, ServiceExceptionParameters.DATASOURCE, exceptions);

        if (datasource == null) {
            return;
        }

        ValidationUtils.checkMetadataRequired(datasource.getDatasetVersion(), ServiceExceptionParameters.DATASOURCE_DATASET_VERSION, exceptions);

        BaseInvocationValidator.checkIdentifiableStatisticalResource(datasource.getIdentifiableStatisticalResource(), exceptions);
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
