package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.net.URL;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorResult;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

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
        checkExistingDatasource(datasource, ServiceExceptionParameters.DATASOURCE, exceptions);
    }

    public static void checkRetrieveDatasourceByUrn(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    public static void checkDeleteDatasource(String urn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    public static void checkRetrieveDatasourcesByDatasetVersion(String datasetVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
    }

    // DATASETS
    // --------------------

    public static void checkFindDatasetsByCondition(List<ConditionalCriteria> conditions, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) {
        // NOTHING
    }

    // DATASETS VERSIONS
    // --------------------

    public static void checkCreateDatasetVersion(DatasetVersion datasetVersion, ExternalItem statisticalOperation, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkNewDatasetVersion(datasetVersion, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(statisticalOperation, ServiceExceptionParameters.STATISTICAL_OPERATION, exceptions);
    }

    public static void checkUpdateDatasetVersion(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkExistingDatasetVersion(datasetVersion, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }

    public static void checkRetrieveDatasetVersionByUrn(String datasetVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
    }

    public static void checkRetrieveLatestDatasetVersionByDatasetUrn(String datasetUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetUrn, ServiceExceptionParameters.DATASET_URN, exceptions);
    }

    public static void checkRetrieveLatestPublishedDatasetVersionByDatasetUrn(String datasetUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetUrn, ServiceExceptionParameters.DATASET_URN, exceptions);
    }

    public static void checkRetrieveDatasetVersions(String datasetVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
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

    public static void checkRetrieveDatasetVersionDimensionsIds(String datasetVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    public static void checkRetrieveCoverageForDatasetVersionDimension(String datasetVersionUrn, String dsdDimensionId, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersionUrn, ServiceExceptionSingleParameters.URN, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(dsdDimensionId, ServiceExceptionParameters.DSD_DIMENSION_ID, exceptions);
    }
    

    public static void checkFilterCoverageForDatasetVersionDimension(String datasetVersionUrn, String dsdDimensionId, String filter, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersionUrn, ServiceExceptionSingleParameters.URN, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(dsdDimensionId, ServiceExceptionParameters.DSD_DIMENSION_ID, exceptions);
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

        checkNewIdentifiableStatisticalResource(datasource.getIdentifiableStatisticalResource(), ServiceExceptionParameters.DATASOURCE__IDENTIFIABLE_STATISTICAL_RESOURCE, exceptions);

        // CODE
        if (datasource.getIdentifiableStatisticalResource() != null) {
            StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getIdentifiableStatisticalResource().getCode(),
                    ServiceExceptionParameters.DATASOURCE__IDENTIFIABLE_STATISTICAL_RESOURCE__CODE, exceptions);
        }

        checkDatasource(datasource, ServiceExceptionParameters.DATASOURCE, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasource.getDatasetVersion(), ServiceExceptionParameters.DATASOURCE__DATASET_VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasource.getId(), ServiceExceptionParameters.DATASOURCE__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasource.getVersion(), ServiceExceptionParameters.DATASOURCE__VERSION, exceptions);
    }

    private static void checkExistingDatasource(Datasource datasource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasource, metadataName, exceptions);

        if (datasource == null) {
            return;
        }

        checkExistingIdentifiableStatisticalResource(datasource.getIdentifiableStatisticalResource(), TypeRelatedResourceEnum.DATASOURCE, metadataName, exceptions);
        checkDatasource(datasource, metadataName, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getDatasetVersion(), addParameter(metadataName, ServiceExceptionSingleParameters.DATASET_VERSION), exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getId(), addParameter(metadataName, ServiceExceptionSingleParameters.ID), exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getVersion(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION), exceptions);
    }

    private static void checkDatasource(Datasource datasource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasource.getUuid(), addParameter(metadataName, ServiceExceptionSingleParameters.UUID), exceptions);
    }

    // DATASET VERSION
    // ----------------

    private static void checkNewDatasetVersion(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersion, ServiceExceptionParameters.DATASET_VERSION, exceptions);

        if (datasetVersion == null) {
            return;
        }

        checkNewSiemacMetadataStatisticalResource(datasetVersion.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE, exceptions);
        checkDatasetVersion(datasetVersion, ServiceExceptionParameters.DATASET_VERSION, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasetVersion.getDataset(), ServiceExceptionParameters.DATASET_VERSION__DATASET, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasetVersion.getId(), ServiceExceptionParameters.DATASET_VERSION__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(datasetVersion.getVersion(), ServiceExceptionParameters.DATASET_VERSION__VERSION, exceptions);
    }

    public static void checkExistingDatasetVersion(DatasetVersion datasetVersion, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersion, metadataName, exceptions);

        if (datasetVersion == null) {
            return;
        }

        checkExistingSiemacMetadataStatisticalResource(datasetVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION, metadataName, exceptions);
        checkDatasetVersion(datasetVersion, metadataName, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getDataset(), addParameter(metadataName, ServiceExceptionSingleParameters.DATASET), exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getId(), ServiceExceptionSingleParameters.ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getVersion(), ServiceExceptionSingleParameters.VERSION, exceptions);
    }

    private static void checkDatasetVersion(DatasetVersion datasetVersion, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(datasetVersion.getUuid(), ServiceExceptionSingleParameters.UUID, exceptions);
    }
    
    public static void checkFindStatisticOfficialities(List<MetamacExceptionItem> exceptions) {
        //NOTHING
    }

    public static void checkProccessDatasetFileImportationResult(String datasetImportationId, List<FileDescriptorResult> fileDescriptors, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetImportationId, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(fileDescriptors, ServiceExceptionParameters.FILE_DESCRIPTORS, exceptions);
    }

    public static void checkImportDatasourcesInDatasetVersion(String datasetVersionUrn, List<URL> fileUrls, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(datasetVersionUrn, ServiceExceptionParameters.DATASET_VERSION_URN, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(fileUrls, ServiceExceptionParameters.FILE_URLS, exceptions);
    }

    public static void checkImportDatasourcesInStatisticalOperation(String statisticalOperationUrn, List<URL> fileUrls, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(statisticalOperationUrn, ServiceExceptionParameters.STATISTICAL_OPERATION_URN, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(fileUrls, ServiceExceptionParameters.FILE_URLS, exceptions);
    }

    
    

}
