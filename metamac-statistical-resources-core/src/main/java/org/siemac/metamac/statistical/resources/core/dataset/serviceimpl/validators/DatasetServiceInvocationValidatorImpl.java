package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

import java.util.List;

public class DatasetServiceInvocationValidatorImpl extends BaseInvocationValidator {

    // ------------------------------------------------------------------------
    // PUBLIC METHODS FOR SERVICE METHODS
    // ------------------------------------------------------------------------

    // DATASOURCE
    // -------------

    public static void checkCreateDatasource(String datasetVersionUrn, Datasource datasource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
        checkNewDatasource(datasource, exceptions);
    }

    public static void checkUpdateDatasource(Datasource datasource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkExistingDatasource(datasource, exceptions);
    }

    public static void checkRetrieveDatasourceByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
    }

    public static void checkDeleteDatasource(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
    }

    public static void checkRetrieveDatasourcesByDatasetVersion(String datasetVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
    }

    // DATASETS
    // -------------

    public static void checkCreateDatasetVersion(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkNewDatasetVersion(datasetVersion, exceptions);
    }

    public static void checkUpdateDatasetVersion(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkExistingDatasetVersion(datasetVersion, exceptions);
    }

    public static void checkRetrieveDatasetVersionByUrn(String datasetVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
    }

    public static void checkRetrieveDatasetVersions(String datasetUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
    }

    public static void checkFindDatasetVersionsByCondition(List<ConditionalCriteria> conditions, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    public static void checkDeleteDatasetVersion(String datasetVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
    }

    public static void checkVersioningDatasetVersion(String datasetVersionUrnToCopy, VersionTypeEnum versionType, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrnToCopy, ServiceExceptionParameters.DATASET_VERSION_URN_TO_COPY, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(versionType, ServiceExceptionParameters.VERSION_TYPE, exceptions);
        
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    // DATASOURCES
    // --------------

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

    // DATASET VERSION
    // ----------------

    private static void checkNewDatasetVersion(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersion, ServiceExceptionParameters.DATASET_VERSION, exceptions);

        if (datasetVersion == null) {
            return;
        }

        checkNewSiemacMetadataStatisticalResource(datasetVersion.getSiemacMetadataStatisticalResource(), exceptions);
        checkDatasetVersion(datasetVersion, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasetVersion.getDataset(), ServiceExceptionParameters.DATASET_VERSION_DATASET, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasetVersion.getId(), ServiceExceptionParameters.DATASET_VERSION_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasetVersion.getVersion(), ServiceExceptionParameters.DATASET_VERSION_VERSION, exceptions);
    }

    private static void checkExistingDatasetVersion(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersion, ServiceExceptionParameters.DATASOURCE, exceptions);

        if (datasetVersion == null) {
            return;
        }

        checkExistingSiemacMetadataStatisticalResource(datasetVersion.getSiemacMetadataStatisticalResource(), exceptions);
        checkDatasetVersion(datasetVersion, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getDataset(), ServiceExceptionParameters.DATASET_VERSION_DATASET, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getId(), ServiceExceptionParameters.DATASET_VERSION_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getVersion(), ServiceExceptionParameters.DATASET_VERSION_VERSION, exceptions);
    }

    private static void checkDatasetVersion(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getUuid(), ServiceExceptionParameters.DATASET_VERSION_UUID, exceptions);
    }
}
