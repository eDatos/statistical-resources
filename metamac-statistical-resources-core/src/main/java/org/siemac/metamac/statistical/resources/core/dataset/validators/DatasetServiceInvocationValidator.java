package org.siemac.metamac.statistical.resources.core.dataset.validators;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class DatasetServiceInvocationValidator extends BaseInvocationValidator {

    // ------------------------------------------------------------------------
    // PUBLIC METHODS FOR SERVICE METHODS
    // ------------------------------------------------------------------------

    public static void checkCreateDatasource(String datasetUrn, Datasource datasource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        StatisticalResourcesValidationUtils.checkParameterRequired(datasetUrn, ServiceExceptionParameters.DATASET_URN, exceptions);
        checkNewDatasource(datasource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkUpdateDatasource(Datasource datasource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        checkExistingDatasource(datasource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkRetrieveDatasourceByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkDeleteDatasource(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    public static void checkRetrieveDatasourcesByDataset(String datasetUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (exceptions == null) {
            exceptions = new ArrayList<MetamacExceptionItem>();
        }

        StatisticalResourcesValidationUtils.checkParameterRequired(datasetUrn, ServiceExceptionParameters.DATASET_URN, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void checkNewDatasource(Datasource datasource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasource, ServiceExceptionParameters.DATASOURCE, exceptions);

        if (datasource == null) {
            return;
        }

        checkNewIdentifiableStatisticalResource(datasource.getIdentifiableStatisticalResource(), exceptions);
        checkDatasource(datasource, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasource.getDatasetVersion(), ServiceExceptionParameters.DATASOURCE_DATASET_VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasource.getId(), ServiceExceptionParameters.DATASOURCE_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasource.getVersion(), ServiceExceptionParameters.DATASOURCE_VERSION, exceptions);
    }

    private static void checkExistingDatasource(Datasource datasource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasource, ServiceExceptionParameters.DATASOURCE, exceptions);

        if (datasource == null) {
            return;
        }

        checkExistingIdentifiableStatisticalResource(datasource.getIdentifiableStatisticalResource(), exceptions);
        checkDatasource(datasource, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getDatasetVersion(), ServiceExceptionParameters.DATASOURCE_DATASET_VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getId(), ServiceExceptionParameters.DATASOURCE_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getVersion(), ServiceExceptionParameters.DATASOURCE_VERSION, exceptions);
    }

    private static void checkDatasource(Datasource datasource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getUuid(), ServiceExceptionParameters.DATASOURCE_UUID, exceptions);
    }
}
