package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.time.TimeSdmx;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.SdmxTimeUtils;
import org.siemac.metamac.core.common.util.TimeSdmxComparator;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.base.components.SiemacStatisticalResourceGeneratedCode;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponent;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.checks.DatasetMetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.validators.DatasetServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestMapper;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorResult;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;
import com.arte.statistic.dataset.repository.dto.AttributeInstanceObservationDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

/**
 * Implementation of DatasetService.
 */
@Service("datasetService")
public class DatasetServiceImpl extends DatasetServiceImplBase {

    @Autowired
    private IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    private DatasetServiceInvocationValidator         datasetServiceInvocationValidator;

    @Autowired
    private SiemacStatisticalResourceGeneratedCode    siemacStatisticalResourceGeneratedCode;

    @Autowired
    private SrmRestInternalService                    srmRestInternalService;

    @Autowired
    private QueryVersionRepository                    queryVersionRepository;

    @Autowired
    private DatasetRepositoriesServiceFacade          statisticsDatasetRepositoriesServiceFacade;

    @Autowired
    private RestMapper                                restMapper;

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    public Datasource createDatasource(ServiceContext ctx, String datasetVersionUrn, Datasource datasource) throws MetamacException {

        // Validations
        datasetServiceInvocationValidator.checkCreateDatasource(ctx, datasetVersionUrn, datasource);

        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        checkCanAlterDatasourcesInDatasetVersion(datasetVersion);

        // Fill metadata
        fillMetadataForCreateDatasource(datasource, datasetVersion);

        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasource.getIdentifiableStatisticalResource());

        // Save
        datasource = getDatasourceRepository().save(datasource);

        // Update dataset version (add datasource)
        datasetVersion = addDatasourceForDatasetVersion(datasource, datasetVersion);

        computeDataRelatedMetadata(datasetVersion);

        getDatasetVersionRepository().save(datasetVersion);

        return datasource;
    }

    private void checkCanAlterDatasourcesInDatasetVersion(DatasetVersion datasetVersion) throws MetamacException {
        if (!DatasetMetadataEditionChecks.canAlterDatasources(datasetVersion.getSiemacMetadataStatisticalResource().getProcStatus())) {
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_CANT_ALTER_DATASOURCES, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    @Override
    public Datasource updateDatasource(ServiceContext ctx, Datasource datasource) throws MetamacException {

        // Validation of parameters
        datasetServiceInvocationValidator.checkUpdateDatasource(ctx, datasource);

        checkCanAlterDatasourcesInDatasetVersion(datasource.getDatasetVersion());

        checkNotTasksInProgress(ctx, datasource.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn());

        // Update
        Datasource updatedDataSource = getDatasourceRepository().save(datasource);

        // TODO: IF CODE can be changed, attribute in dataset repository must be changed to ensure consistency

        return updatedDataSource;
    }

    @Override
    public Datasource retrieveDatasourceByUrn(ServiceContext ctx, String urn) throws MetamacException {

        // Validation
        datasetServiceInvocationValidator.checkRetrieveDatasourceByUrn(ctx, urn);

        // Retrieve
        Datasource datasource = getDatasourceRepository().retrieveByUrn(urn);
        return datasource;
    }

    @Override
    public void deleteDatasource(ServiceContext ctx, String urn) throws MetamacException {

        // Validation
        datasetServiceInvocationValidator.checkDeleteDatasource(ctx, urn);

        // Retrieve
        Datasource datasource = getDatasourceRepository().retrieveByUrn(urn);

        DatasetVersion datasetVersion = datasource.getDatasetVersion();

        checkCanAlterDatasourcesInDatasetVersion(datasource.getDatasetVersion());

        checkNotTasksInProgress(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        checkDatasetVersionForDatasourceHasNoQueries(datasource);

        datasetVersion = deleteDatasourceToDataset(datasource);

        deleteDatasourceData(datasource);

        computeDataRelatedMetadata(datasetVersion);

        getDatasetVersionRepository().save(datasetVersion);
    }

    private void checkDatasetVersionForDatasourceHasNoQueries(Datasource datasource) throws MetamacException {
        List<QueryVersion> queries = queryVersionRepository.findLinkedToDatasetVersion(datasource.getDatasetVersion().getId());
        if (!queries.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.DATASOURCE_IN_DATASET_VERSION_WITH_QUERIES_DELETE_ERROR, datasource.getIdentifiableStatisticalResource().getUrn());
        }
    }

    private void deleteDatasourceData(Datasource datasource) throws MetamacException {
        try {
            InternationalStringDto internationalStringDto = new InternationalStringDto();
            LocalisedStringDto localisedStringDto = new LocalisedStringDto();
            localisedStringDto.setLabel(datasource.getIdentifiableStatisticalResource().getCode());
            localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
            internationalStringDto.addText(localisedStringDto);

            AttributeInstanceObservationDto attributeInstanceObservationDto = new AttributeInstanceObservationDto(ManipulateDataUtils.DATA_SOURCE_ID, internationalStringDto);

            statisticsDatasetRepositoriesServiceFacade.deleteObservationsByAttributeInstanceValue(ManipulateDataUtils.DATA_SOURCE_ID, 0, attributeInstanceObservationDto);
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.DATASOURCE_DATA_DELETE_ERROR, datasource.getIdentifiableStatisticalResource().getCode());
        }
    }

    @Override
    public List<Datasource> retrieveDatasourcesByDatasetVersion(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {

        // Validation
        datasetServiceInvocationValidator.checkRetrieveDatasourcesByDatasetVersion(ctx, datasetVersionUrn);

        // Retrieve
        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);
        List<Datasource> datasources = datasetVersion.getDatasources();

        return datasources;
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    @Override
    public DatasetVersion createDatasetVersion(ServiceContext ctx, DatasetVersion datasetVersion, ExternalItem statisticalOperation) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkCreateDatasetVersion(ctx, datasetVersion, statisticalOperation);

        // Create dataset
        Dataset dataset = new Dataset();
        fillMetadataForCreateDataset(ctx, dataset, statisticalOperation);

        // Fill metadata
        fillMetadataForCreateDatasetVersion(ctx, datasetVersion, statisticalOperation);

        // Save version
        datasetVersion.setDataset(dataset);

        assignCodeAndSaveDataset(dataset, datasetVersion);
        datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        return datasetVersion;
    }

    @Override
    public DatasetVersion updateDatasetVersion(ServiceContext ctx, DatasetVersion datasetVersion) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkUpdateDatasetVersion(ctx, datasetVersion);

        checkNotTasksInProgress(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        checkDsdChanges(datasetVersion);

        // Check status
        BaseValidator.checkStatisticalResourceCanBeEdited(datasetVersion);

        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasetVersion.getSiemacMetadataStatisticalResource());

        if (datasetVersion.isRelatedDsdChanged()) {
            clearDataRelatedMetadata(datasetVersion);
        }

        datasetVersion = getDatasetVersionRepository().save(datasetVersion);
        return datasetVersion;
    }

    private void checkDsdChanges(DatasetVersion datasetVersion) throws MetamacException {
        if (datasetVersion.isRelatedDsdChanged()) {
            List<QueryVersion> queriesLinkedToDataset = queryVersionRepository.findLinkedToDatasetVersion(datasetVersion.getId());
            if (!queriesLinkedToDataset.isEmpty()) {
                throw new MetamacException(ServiceExceptionType.DATASET_VERSION_CANT_CHANGE_DSD_SOME_QUERIES_EXIST, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            }
        }
    }

    private void clearDataRelatedMetadata(DatasetVersion resource) throws MetamacException {
        // Clear datasources
        for (Datasource datasource : resource.getDatasources()) {
            getDatasourceRepository().delete(datasource);
        }
        resource.getDatasources().clear();
        resource.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime());

        // Clear coverages
        resource.getDimensionsCoverage().clear();
        resource.getGeographicCoverage().clear();
        resource.getTemporalCoverage().clear();
        resource.getMeasureCoverage().clear();

        // Date start and end
        resource.setDateStart(null);
        resource.setDateEnd(null);

        // Format extent
        resource.setFormatExtentDimensions(null);
        resource.setFormatExtentObservations(null);

        // Date next update
        if (BooleanUtils.isNotTrue(resource.getUserModifiedDateNextUpdate())) {
            resource.setDateNextUpdate(null);
        }

        // Dataset repository
        try {
            statisticsDatasetRepositoriesServiceFacade.deleteDatasetRepository(resource.getDatasetRepositoryId());
            resource.setDatasetRepositoryId(null);
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "Error removing datasetRepository " + resource.getDatasetRepositoryId());
        }
    }

    @Override
    public DatasetVersion retrieveDatasetVersionByUrn(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkRetrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Retrieve
        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);
        return datasetVersion;
    }

    @Override
    public DatasetVersion retrieveLatestDatasetVersionByDatasetUrn(ServiceContext ctx, String datasetUrn) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkRetrieveLatestDatasetVersionByDatasetUrn(ctx, datasetUrn);

        // Retrieve
        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveLastVersion(datasetUrn);
        return datasetVersion;
    }

    @Override
    public DatasetVersion retrieveLatestPublishedDatasetVersionByDatasetUrn(ServiceContext ctx, String datasetUrn) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkRetrieveLatestPublishedDatasetVersionByDatasetUrn(ctx, datasetUrn);

        // Retrieve
        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveLastPublishedVersion(datasetUrn);
        return datasetVersion;
    }

    @Override
    public List<DatasetVersion> retrieveDatasetVersions(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkRetrieveDatasetVersions(ctx, datasetVersionUrn);

        // Retrieve
        List<DatasetVersion> datasetVersions = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn).getDataset().getVersions();

        return datasetVersions;
    }

    @Override
    public PagedResult<DatasetVersion> findDatasetVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkFindDatasetVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, DatasetVersion.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<DatasetVersion> datasetVersionPagedResult = getDatasetVersionRepository().findByCondition(conditions, pagingParameter);
        return datasetVersionPagedResult;
    }

    @Override
    public void deleteDatasetVersion(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkDeleteDatasetVersion(ctx, datasetVersionUrn);

        // Retrieve version to delete
        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        checkNotTasksInProgress(ctx, datasetVersionUrn);

        // Check can be deleted
        BaseValidator.checkStatisticalResourceCanBeDeleted(datasetVersion);

        // TODO: Determinar si hay algunas comprobaciones que impiden el borrado

        // TODO: Comprobar si hay que eliminar relaciones a otros recursos

        if (VersionUtil.isInitialVersion(datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic())) {
            Dataset dataset = datasetVersion.getDataset();
            getDatasetRepository().delete(dataset);
        } else {
            // Previous version
            RelatedResource previousResource = datasetVersion.getSiemacMetadataStatisticalResource().getReplacesVersion();
            if (previousResource.getDatasetVersion() != null) {
                DatasetVersion previousVersion = previousResource.getDatasetVersion();
                previousVersion.getSiemacMetadataStatisticalResource().setLastVersion(true);
            }
            // Delete version
            Dataset dataset = datasetVersion.getDataset();
            dataset.getVersions().remove(datasetVersion);
            getDatasetVersionRepository().delete(datasetVersion);
        }
    }

    @Override
    public void importDatasourcesInDatasetVersion(ServiceContext ctx, String datasetVersionUrn, List<URL> fileUrls) throws MetamacException {
        datasetServiceInvocationValidator.checkImportDatasourcesInDatasetVersion(ctx, datasetVersionUrn, fileUrls);

        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);

        checkNotTasksInProgress(ctx, datasetVersionUrn);

        ProcStatusEnumUtils.checkPossibleProcStatus(datasetVersion, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED);

        String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();

        checkFilesCanBeAssociatedWithDataset(datasetUrn, datasetVersionUrn, fileUrls);

        TaskInfoDataset taskInfo = buildImportationTaskInfo(datasetVersion, fileUrls);

        getTaskService().planifyImportationDataset(ctx, taskInfo);
    }

    private TaskInfoDataset buildImportationTaskInfo(DatasetVersion datasetVersion, List<URL> fileUrls) {
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        TaskInfoDataset taskInfo = new TaskInfoDataset();
        taskInfo.setDatasetVersionId(datasetVersionUrn);
        taskInfo.setDataStructureUrn(datasetVersion.getRelatedDsd().getUrn());

        for (URL url : fileUrls) {
            String filename = getFilenameFromPath(url.getPath());
            DatasetFileFormatEnum format = calculateFileFormat(filename);
            FileDescriptor fileDescriptor = new FileDescriptor(new File(url.getPath()), filename, format);
            taskInfo.addFile(fileDescriptor);
        }

        return taskInfo;
    }

    private void checkFilesCanBeAssociatedWithDataset(String datasetUrn, String datasetVersionUrn, List<URL> fileUrls) throws MetamacException {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        for (URL url : fileUrls) {
            String filename = getFilenameFromPath(url.getPath());
            String linkedDatasetVersionUrn = getDatasetRepository().findDatasetUrnLinkedToDatasourceFile(filename);
            if (linkedDatasetVersionUrn != null && !StringUtils.equals(datasetUrn, linkedDatasetVersionUrn)) {
                exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.INVALID_FILE_FOR_DATASET_VERSION, filename, datasetVersionUrn));
            }
        }
        if (exceptionItems.size() > 0) {
            throw new MetamacException(exceptionItems);
        }
    }

    private DatasetFileFormatEnum calculateFileFormat(String filename) {
        if (filename.endsWith(StatisticalResourcesConstants.PX_EXTENSION)) {
            return DatasetFileFormatEnum.PX;
        } else if (filename.endsWith(StatisticalResourcesConstants.SDMX_EXTENSION)) {
            return DatasetFileFormatEnum.SDMX_2_1;
        } else {
            return DatasetFileFormatEnum.CSV;
        }
    }

    private String getFilenameFromPath(String path) {
        String base = FilenameUtils.getBaseName(path);
        String extension = FilenameUtils.getExtension(path);
        if (StringUtils.isEmpty(extension)) {
            return base;
        }
        return base + "." + extension;
    }

    @Override
    public void importDatasourcesInStatisticalOperation(ServiceContext ctx, String statisticalOperationUrn, List<URL> fileUrls) throws MetamacException {
        datasetServiceInvocationValidator.checkImportDatasourcesInStatisticalOperation(ctx, statisticalOperationUrn, fileUrls);

        Map<String, List<URL>> datasetVersionsForFiles = organizeFilesByDatasetVersionUrn(statisticalOperationUrn, fileUrls);

        List<MetamacExceptionItem> items = new ArrayList<MetamacExceptionItem>();
        for (String datasetVersionUrn : datasetVersionsForFiles.keySet()) {
            try {
                List<URL> urls = datasetVersionsForFiles.get(datasetVersionUrn);
                importDatasourcesInDatasetVersion(ctx, datasetVersionUrn, urls);
            } catch (MetamacException e) {
                MetamacExceptionItem item = new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DATASET_VERSION_ERROR, datasetVersionUrn);
                item.setExceptionItems(e.getExceptionItems());
                items.add(item);
            }
        }
        if (items.size() > 0) {
            throw new MetamacException(items);
        }
    }

    protected Map<String, List<URL>> organizeFilesByDatasetVersionUrn(String statisticalOperationUrn, List<URL> fileUrls) throws MetamacException {
        Map<String, List<URL>> datasetVersionsForFiles = new HashMap<String, List<URL>>();
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        for (URL url : fileUrls) {
            String filename = getFilenameFromPath(url.getPath());
            String datasetUrn = getDatasetRepository().findDatasetUrnLinkedToDatasourceFile(filename);
            DatasetVersion datasetVersion = null;
            if (datasetUrn != null) {
                datasetVersion = getDatasetVersionRepository().retrieveLastVersion(datasetUrn);
            }

            if (datasetVersion != null && StringUtils.equals(statisticalOperationUrn, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn())) {
                StatisticalResourcesCollectionUtils.addValueToMapValueList(datasetVersionsForFiles, datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), url);
            } else {
                exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.FILE_NOT_LINKED_TO_ANY_DATASET_IN_STATISTICAL_OPERATION, filename, statisticalOperationUrn));
            }
        }
        if (exceptionItems.size() > 0) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build();
        }
        return datasetVersionsForFiles;
    }

    @Override
    public void proccessDatasetFileImportationResult(ServiceContext ctx, String datasetImportationId, List<FileDescriptorResult> fileDescriptors) throws MetamacException {
        String datasetVersionUrn = datasetImportationId;
        datasetServiceInvocationValidator.checkProccessDatasetFileImportationResult(ctx, datasetImportationId, fileDescriptors);

        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);
        datasetVersion.setDatasetRepositoryId(datasetImportationId);
        getDatasetVersionRepository().save(datasetVersion);

        for (FileDescriptorResult fileDescriptor : fileDescriptors) {
            Datasource datasource = new Datasource();
            datasource.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
            datasource.getIdentifiableStatisticalResource().setCode(fileDescriptor.getDatasourceId());
            datasource.setFilename(fileDescriptor.getFileName());
            if (DatasetFileFormatEnum.PX.equals(fileDescriptor.getDatasetFileFormatEnum())) {
                datasource.setDateNextUpdate(new DateTime(fileDescriptor.getNextUpdate()));
            }
            createDatasource(ctx, datasetImportationId, datasource);
        }
    }

    @Override
    public List<String> retrieveDatasetVersionDimensionsIds(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        datasetServiceInvocationValidator.checkRetrieveDatasetVersionDimensionsIds(ctx, datasetVersionUrn);

        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        checkNotTasksInProgress(ctx, datasetVersionUrn);

        List<String> dimensionsIds = getDatasetVersionRepository().retrieveDimensionsIds(datasetVersion);
        if (dimensionsIds.size() > 0) {
            return dimensionsIds;
        } else {
            throw new MetamacException(ServiceExceptionType.DATASET_NO_DATA, datasetVersionUrn);
        }
    }

    @Override
    public List<CodeDimension> retrieveCoverageForDatasetVersionDimension(ServiceContext ctx, String datasetVersionUrn, String dimensionId) throws MetamacException {
        datasetServiceInvocationValidator.checkRetrieveCoverageForDatasetVersionDimension(ctx, datasetVersionUrn, dimensionId);

        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        checkNotTasksInProgress(ctx, datasetVersionUrn);

        return getCodeDimensionRepository().findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), dimensionId, null);
    }

    @Override
    public List<CodeDimension> filterCoverageForDatasetVersionDimension(ServiceContext ctx, String datasetVersionUrn, String dimensionId, String filter) throws MetamacException {
        datasetServiceInvocationValidator.checkFilterCoverageForDatasetVersionDimension(ctx, datasetVersionUrn, dimensionId, filter);

        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        checkNotTasksInProgress(ctx, datasetVersionUrn);

        return getCodeDimensionRepository().findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), dimensionId, filter);
    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    @Override
    public PagedResult<Dataset> findDatasetsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkFindDatasetsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, DatasetVersion.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<Dataset> datasetsPagedResult = getDatasetRepository().findByCondition(conditions, pagingParameter);
        return datasetsPagedResult;
    }

    @Override
    public List<StatisticOfficiality> findStatisticOfficialities(ServiceContext ctx) throws MetamacException {
        datasetServiceInvocationValidator.checkFindStatisticOfficialities(ctx);
        return getStatisticOfficialityRepository().findAll();
    }

    // ------------------------------------------------------------------------
    // DATASET ATTRIBUTES
    // ------------------------------------------------------------------------

    @Override
    public AttributeInstanceDto createAttributeInstance(ServiceContext ctx, String datasetVersionUrn, AttributeInstanceDto attributeInstanceDto) throws MetamacException {

        // Validations
        datasetServiceInvocationValidator.checkCreateAttributeInstance(ctx, datasetVersionUrn, attributeInstanceDto);

        // Retrieve the datasetVersion to get the datasetRepositoryId
        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Create attribute
        try {
            return statisticsDatasetRepositoriesServiceFacade.createAttributeInstance(datasetVersion.getDatasetRepositoryId(), attributeInstanceDto);
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "Error creating attribute instance in datasetRepository " + datasetVersionUrn + ". Details: " + e.getMessage());
        }
    }

    @Override
    public List<AttributeInstanceDto> retrieveAttributeInstances(ServiceContext ctx, String datasetVersionUrn, String attributeId) throws MetamacException {

        // Validations
        datasetServiceInvocationValidator.checkRetrieveAttributeInstances(ctx, datasetVersionUrn, attributeId);

        // Retrieve the datasetVersion to get the datasetRepositoryId
        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Retrieve the attribute instances
        try {
            return statisticsDatasetRepositoriesServiceFacade.findAttributesInstances(datasetVersion.getDatasetRepositoryId(), attributeId);
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "Error retrieve attribute instances in datasetRepository " + datasetVersionUrn + ". Details: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private void checkNotTasksInProgress(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        if (getTaskService().existsTaskForResource(ctx, datasetVersionUrn)) {
            throw new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn);
        }
    }

    protected void computeDataRelatedMetadata(DatasetVersion resource) throws MetamacException {
        ExternalItem externalDsd = resource.getRelatedDsd();
        DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(externalDsd.getUrn());

        processCoverages(resource, dataStructure);

        processDataRelatedMetadata(resource);

        processStartEndDates(resource);

        processDateNextUpdate(resource);
    }

    private void processStartEndDates(DatasetVersion resource) {
        List<TemporalCode> temporalCoverage = resource.getTemporalCoverage();
        TemporalCode start = temporalCoverage.get(temporalCoverage.size() - 1);
        TemporalCode end = temporalCoverage.get(0);

        resource.setDateStart(temporalCodeToDateTimeStart(start));
        resource.setDateEnd(temporalCodeToDateTimeEnd(end));
    }

    private DateTime temporalCodeToDateTimeStart(TemporalCode temporalCode) {
        String timeCode = temporalCode.getIdentifier();
        DateTime[] times = SdmxTimeUtils.calculateDateTimes(timeCode);
        return times[0]; // start
    }

    private DateTime temporalCodeToDateTimeEnd(TemporalCode temporalCode) {
        String timeCode = temporalCode.getIdentifier();
        DateTime[] times = SdmxTimeUtils.calculateDateTimes(timeCode);
        return times[1]; // start
    }

    private void processDateNextUpdate(DatasetVersion resource) {
        if (resource.getDateNextUpdate() == null || BooleanUtils.isNotTrue(resource.getUserModifiedDateNextUpdate())) {
            DateTime mostRecentDate = null;
            for (Datasource datasource : resource.getDatasources()) {
                if (datasource.getDateNextUpdate() != null) {
                    if (isNewDateBestOptionForDateNextUpdate(mostRecentDate, datasource.getDateNextUpdate())) {
                        mostRecentDate = datasource.getDateNextUpdate();
                    }
                }
            }
            resource.setDateNextUpdate(mostRecentDate);
            resource.setUserModifiedDateNextUpdate(false);
        }
    }

    private boolean isNewDateBestOptionForDateNextUpdate(DateTime current, DateTime newCandidate) {
        if (current == null) {
            return true;
        }
        if (newCandidate.isAfterNow() && newCandidate.isBefore(current)) {
            return true;
        }
        return false;
    }

    private void processDataRelatedMetadata(DatasetVersion resource) throws MetamacException {
        try {
            DatasetRepositoryDto datasetRepository = statisticsDatasetRepositoriesServiceFacade.retrieveDatasetRepository(resource.getDatasetRepositoryId());
            resource.setFormatExtentDimensions(datasetRepository.getDimensions().size());
            long num = statisticsDatasetRepositoriesServiceFacade.countObservations(resource.getDatasetRepositoryId());
            resource.setFormatExtentObservations(num);
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "Error retrieving datasetRepository " + resource.getDatasetRepositoryId() + ". Details: " + e.getMessage());
        }
    }

    // COVERAGE UTILS
    private void processCoverages(DatasetVersion resource, DataStructure dataStructure) throws MetamacException {
        List<DsdDimension> dimensions = DsdProcessor.getDimensions(dataStructure);

        resource.getDimensionsCoverage().clear();
        resource.getGeographicCoverage().clear();
        resource.getTemporalCoverage().clear();
        resource.getMeasureCoverage().clear();

        for (DsdDimension dimension : dimensions) {
            List<CodeDimension> codes = getCodesFromDsdComponent(resource, dimension);
            List<ExternalItem> items = buildExternalItemsBasedOnCodeDimensions(codes, dimension);
            if (items != null) {
                addTranslationsToCodesFromExternalItems(codes, items);
            }

            if (DsdComponentType.TEMPORAL.equals(dimension.getType())) {
                sortTemporalCodeDimensions(codes);
            }

            resource.getDimensionsCoverage().addAll(codes);
            switch (dimension.getType()) {
                case SPATIAL:
                    for (ExternalItem item : items) {
                        if (!StatisticalResourcesCollectionUtils.isExternalItemInCollection(resource.getGeographicCoverage(), item)) {
                            resource.getGeographicCoverage().add(item);
                        }
                    }
                    break;
                case MEASURE:
                    resource.getMeasureCoverage().addAll(items);
                    break;
                case TEMPORAL:
                    List<TemporalCode> temporalCodes = buildTemporalCodeFromCodeDimensions(codes);
                    resource.getTemporalCoverage().addAll(temporalCodes);
                    break;
            }
        }

        // Try to fill specific coverages from attributes
        if (resource.getGeographicCoverage().isEmpty()) {
            List<ExternalItem> codeItems = processExternalItemsCodeFromAttributeByType(resource, dataStructure, DsdComponentType.SPATIAL);
            resource.getGeographicCoverage().addAll(codeItems);
        }
        if (resource.getTemporalCoverage().isEmpty()) {
            List<CodeDimension> codeItems = processCodeFromAttributeByType(resource, dataStructure, DsdComponentType.TEMPORAL);
            resource.getTemporalCoverage().addAll(buildTemporalCodeFromCodeDimensions(codeItems));
        }
        if (resource.getMeasureCoverage().isEmpty()) {
            List<ExternalItem> codeItems = processExternalItemsCodeFromAttributeByType(resource, dataStructure, DsdComponentType.MEASURE);
            resource.getMeasureCoverage().addAll(codeItems);
        }
    }

    private void sortTemporalCodeDimensions(List<CodeDimension> codes) {
        Collections.sort(codes, new Comparator<CodeDimension>() {

            private final TimeSdmxComparator sdmxComparator = new TimeSdmxComparator();

            @Override
            public int compare(CodeDimension o1, CodeDimension o2) {
                return sdmxComparator.compare(new TimeSdmx(o2.getIdentifier()), new TimeSdmx(o1.getIdentifier()));
            }
        });
    }

    private void addTranslationsToCodesFromExternalItems(List<CodeDimension> codeDimensions, List<ExternalItem> externalItems) {
        for (ExternalItem externalItem : externalItems) {
            for (CodeDimension codeDimension : codeDimensions) {
                if (codeDimension.getIdentifier().equals(externalItem.getCode())) {
                    String title = externalItem.getTitle().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
                    if (title != null) {
                        codeDimension.setTitle(title);
                    } else {
                        codeDimension.setTitle(codeDimension.getIdentifier());
                    }
                }
            }
        }
    }

    private List<ExternalItem> processExternalItemsCodeFromAttributeByType(DatasetVersion resource, DataStructure dataStructure, DsdComponentType type) throws MetamacException {
        DsdAttribute attribute = getDsdAttributeByType(dataStructure, type);
        List<ExternalItem> items = new ArrayList<ExternalItem>();
        if (attribute != null) {
            List<CodeDimension> codes = filterCodesFromAttribute(resource, resource.getDatasetRepositoryId(), attribute.getComponentId());
            items = buildExternalItemsBasedOnCodeDimensions(codes, attribute);
        }
        return items;
    }

    private List<CodeDimension> processCodeFromAttributeByType(DatasetVersion resource, DataStructure dataStructure, DsdComponentType type) throws MetamacException {
        DsdAttribute attribute = getDsdAttributeByType(dataStructure, type);
        List<CodeDimension> codes = new ArrayList<CodeDimension>();
        if (attribute != null) {
            codes = filterCodesFromAttribute(resource, resource.getDatasetRepositoryId(), attribute.getComponentId());
            List<ExternalItem> items = buildExternalItemsBasedOnCodeDimensions(codes, attribute);
            addTranslationsToCodesFromExternalItems(codes, items);
        }
        return codes;
    }

    private DsdAttribute getDsdAttributeByType(DataStructure dataStructure, DsdComponentType type) throws MetamacException {
        List<DsdAttribute> attributes = DsdProcessor.getAttributes(dataStructure);
        DsdAttribute foundAttribute = filterDsdAttributeWithType(attributes, type);
        return foundAttribute;
    }

    private DsdAttribute filterDsdAttributeWithType(List<DsdAttribute> attributes, DsdComponentType type) {
        for (DsdAttribute attr : attributes) {
            if (type.equals(attr.getType())) {
                return attr;
            }
        }
        return null;
    }

    private List<TemporalCode> buildTemporalCodeFromCodeDimensions(List<CodeDimension> codes) {
        List<TemporalCode> temporalCodes = new ArrayList<TemporalCode>();
        for (CodeDimension codeDim : codes) {
            TemporalCode tempCode = new TemporalCode();
            tempCode.setIdentifier(codeDim.getIdentifier());
            tempCode.setTitle(codeDim.getTitle());
            temporalCodes.add(tempCode);
        }
        return temporalCodes;
    }

    private List<ExternalItem> buildExternalItemsBasedOnCodeDimensions(List<CodeDimension> codes, DsdComponent component) throws MetamacException {
        if (component.getCodelistRepresentationUrn() != null) {
            return buildExternalItemsBasedOnCodeDimensionsInCodelist(codes, component.getCodelistRepresentationUrn());
        } else if (component.getConceptSchemeRepresentationUrn() != null) {
            return buildExternalItemsBasedOnCodeDimensionsInConceptScheme(codes, component.getConceptSchemeRepresentationUrn());
        } else {
            return null;
        }
    }

    private List<ExternalItem> buildExternalItemsBasedOnCodeDimensionsInCodelist(List<CodeDimension> codeDimensions, String codelistRepresentationUrn) throws MetamacException {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();

        Codes codes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistRepresentationUrn);

        for (CodeResourceInternal code : codes.getCodes()) {
            for (CodeDimension codeDim : codeDimensions) {
                if (codeDim.getIdentifier().equals(code.getId())) {
                    externalItems.add(restMapper.buildExternalItemFromCode(code));
                }
            }
        }

        if (externalItems.size() < codeDimensions.size()) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Some codes in dimension were not found in codelist " + codelistRepresentationUrn);
        }

        return externalItems;
    }

    private List<ExternalItem> buildExternalItemsBasedOnCodeDimensionsInConceptScheme(List<CodeDimension> codeDimensions, String conceptSchemeRepresentationUrn) throws MetamacException {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();

        Concepts concepts = srmRestInternalService.retrieveConceptsOfConceptSchemeEfficiently(conceptSchemeRepresentationUrn);

        for (ItemResourceInternal concept : concepts.getConcepts()) {
            for (CodeDimension codeDim : codeDimensions) {
                if (codeDim.getIdentifier().equals(concept.getId())) {
                    externalItems.add(restMapper.buildExternalItemFromSrmItemResourceInternal(concept));
                }
            }
        }

        if (externalItems.size() < codeDimensions.size()) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Some codes in dimension were not found in conceptScheme " + conceptSchemeRepresentationUrn);
        }

        return externalItems;
    }

    private List<CodeDimension> getCodesFromDsdComponent(DatasetVersion resource, DsdComponent dsdComponent) throws MetamacException {
        List<CodeDimension> codes = new ArrayList<CodeDimension>();
        if (dsdComponent != null) {
            if (dsdComponent instanceof DsdDimension) {
                codes = filterCodesFromDimension(resource, resource.getDatasetRepositoryId(), dsdComponent.getComponentId());
            } else if (dsdComponent instanceof DsdAttribute) {
                codes = filterCodesFromAttribute(resource, resource.getDatasetRepositoryId(), dsdComponent.getComponentId());
            }
        }
        return codes;
    }

    private List<CodeDimension> filterCodesFromDimension(DatasetVersion resource, String datasetRepositoryId, String dimensionId) throws MetamacException {
        try {
            List<ConditionObservationDto> conditions = statisticsDatasetRepositoriesServiceFacade.findCodeDimensions(datasetRepositoryId);

            List<CodeDimensionDto> dimCodes = filterCodeDimensionsForDimension(dimensionId, conditions);

            List<CodeDimension> codes = new ArrayList<CodeDimension>();
            for (CodeDimensionDto code : dimCodes) {
                CodeDimension codeDimension = new CodeDimension();
                codeDimension.setIdentifier(code.getCodeDimensionId());
                codeDimension.setTitle(code.getCodeDimensionId());
                codeDimension.setDsdComponentId(dimensionId);
                codeDimension.setDatasetVersion(resource);
                codes.add(codeDimension);
            }
            return codes;
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "An error has ocurred retrieving codes from dataset repository " + datasetRepositoryId + " for dimension " + dimensionId);
        }
    }

    private List<CodeDimension> filterCodesFromAttribute(DatasetVersion resource, String datasetRepositoryId, String attributeId) throws MetamacException {
        try {
            List<AttributeInstanceDto> attributes = statisticsDatasetRepositoriesServiceFacade.findAttributesInstances(datasetRepositoryId, attributeId);

            List<CodeDimension> codes = new ArrayList<CodeDimension>();
            if (attributes.size() > 0) {
                String value = attributes.get(0).getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
                CodeDimension codeDimension = new CodeDimension();
                codeDimension.setIdentifier(value);
                codeDimension.setTitle(value);
                codeDimension.setDsdComponentId(attributeId);
                codeDimension.setDatasetVersion(resource);
                codes.add(codeDimension);
            }
            return codes;
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "An error has ocurred retrieving values from dataset repository " + datasetRepositoryId + " for attribute " + attributeId);
        }
    }

    private List<CodeDimensionDto> filterCodeDimensionsForDimension(String dimensionId, List<ConditionObservationDto> conditions) {
        for (ConditionObservationDto condition : conditions) {
            if (condition.getCodesDimension().size() > 0 && condition.getCodesDimension().get(0).getDimensionId().equals(dimensionId)) {
                return condition.getCodesDimension();
            }
        }
        return null;
    }

    private static void fillMetadataForCreateDatasource(Datasource datasource, DatasetVersion datasetVersion) {
        FillMetadataForCreateResourceUtils.fillMetadataForCreateIdentifiableResource(datasource.getIdentifiableStatisticalResource(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getStatisticalOperation());

        datasource.setDatasetVersion(datasetVersion);
        datasource.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasourceUrn(datasource.getIdentifiableStatisticalResource().getCode()));
    }

    private DatasetVersion addDatasourceForDatasetVersion(Datasource datasource, DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(datasource);
        datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime());
        return getDatasetVersionRepository().save(datasetVersion);
    }

    private DatasetVersion deleteDatasourceToDataset(Datasource datasource) {
        DatasetVersion parent = datasource.getDatasetVersion();
        parent.removeDatasource(datasource);
        parent.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime());
        return getDatasetVersionRepository().save(parent);
    }

    private void fillMetadataForCreateDataset(ServiceContext ctx, Dataset dataset, ExternalItem statisticalOperation) {
        dataset.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        FillMetadataForCreateResourceUtils.fillMetadataForCreateIdentifiableResource(dataset.getIdentifiableStatisticalResource(), statisticalOperation);
    }

    private void fillMetadataForCreateDatasetVersion(ServiceContext ctx, DatasetVersion datasetVersion, ExternalItem statisticalOperation) {
        FillMetadataForCreateResourceUtils.fillMetadataForCretateSiemacResource(datasetVersion.getSiemacMetadataStatisticalResource(), statisticalOperation, StatisticalResourceTypeEnum.DATASET, ctx);
    }

    private synchronized Dataset assignCodeAndSaveDataset(Dataset dataset, DatasetVersion datasetVersion) throws MetamacException {
        String code = siemacStatisticalResourceGeneratedCode.fillGeneratedCodeForCreateSiemacMetadataResource(datasetVersion.getSiemacMetadataStatisticalResource());
        String[] maintainerCodes = new String[]{datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};

        dataset.getIdentifiableStatisticalResource().setCode(code);
        dataset.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(maintainerCodes, dataset.getIdentifiableStatisticalResource().getCode()));

        datasetVersion.getSiemacMetadataStatisticalResource().setCode(code);
        datasetVersion.getSiemacMetadataStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(maintainerCodes, datasetVersion.getSiemacMetadataStatisticalResource().getCode(), datasetVersion
                        .getSiemacMetadataStatisticalResource().getVersionLogic()));

        // Checks
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasetVersion.getSiemacMetadataStatisticalResource());

        // Add version to dataset
        dataset.addVersion(datasetVersion);
        return getDatasetRepository().save(datasetVersion.getDataset());
    }
}
