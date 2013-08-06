package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.base.components.SiemacStatisticalResourceGeneratedCode;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForVersioningResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.validators.DatasetServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.dataset.utils.DatasetVersioningCopyUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorResult;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
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
    private DatasetRepositoriesServiceFacade          statisticsDatasetRepositoriesServiceFacade;

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    public Datasource createDatasource(ServiceContext ctx, String datasetVersionUrn, Datasource datasource) throws MetamacException {

        // Validations
        datasetServiceInvocationValidator.checkCreateDatasource(ctx, datasetVersionUrn, datasource);

        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Validate dataset PROC_STATUS
        // TODO: Comprobar que se puede asociar el datasource al dataset

        // Fill metadata
        fillMetadataForCreateDatasource(datasource, datasetVersion);

        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasource.getIdentifiableStatisticalResource());

        // Save
        datasource = getDatasourceRepository().save(datasource);

        // Update dataset version (add datasource)
        addDatasourceForDatasetVersion(datasource, datasetVersion);

        // FIXME: REMOVE
        // mockData(datasetVersion);

        return datasource;
    }

    @Override
    public Datasource updateDatasource(ServiceContext ctx, Datasource datasource) throws MetamacException {

        // Validation of parameters
        datasetServiceInvocationValidator.checkUpdateDatasource(ctx, datasource);

        // TODO: Comprobar que el estado del dataset asociado es el correcto y otras condiciones necesarias

        // Update
        Datasource updatedDataSource = getDatasourceRepository().save(datasource);

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

        // TODO: Comprobar que el dataset está en un estado en el que se le pueden eliminar datasources

        deleteDatasourceToDataset(datasource);
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

        // Check status
        BaseValidator.checkStatisticalResourceCanBeEdited(datasetVersion);

        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasetVersion.getSiemacMetadataStatisticalResource());

        if (datasetVersion.isRelatedDsdChanged()) {
            datasetVersion.getDatasources().clear();
        }

        datasetVersion = getDatasetVersionRepository().save(datasetVersion);
        return datasetVersion;
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
    public DatasetVersion versioningDatasetVersion(ServiceContext ctx, String datasetVersionUrnToCopy, VersionTypeEnum versionType) throws MetamacException {
        datasetServiceInvocationValidator.checkVersioningDatasetVersion(ctx, datasetVersionUrnToCopy, versionType);
        // TODO: check only published datasets can be versioned

        DatasetVersion datasetVersionToCopy = retrieveDatasetVersionByUrn(ctx, datasetVersionUrnToCopy);
        DatasetVersion datasetNewVersion = DatasetVersioningCopyUtils.copyDatasetVersion(datasetVersionToCopy);

        FillMetadataForVersioningResourceUtils.fillMetadataForVersioningSiemacResource(ctx, datasetVersionToCopy.getSiemacMetadataStatisticalResource(),
                datasetNewVersion.getSiemacMetadataStatisticalResource(), versionType);

        // DATASET URN
        String[] creator = new String[]{datasetNewVersion.getSiemacMetadataStatisticalResource().getCreator().getCode()};
        datasetNewVersion.getSiemacMetadataStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(creator, datasetNewVersion.getSiemacMetadataStatisticalResource().getCode(), datasetNewVersion
                        .getSiemacMetadataStatisticalResource().getVersionLogic()));

        // TODO: DATE_NEXT_UPDATE

        datasetNewVersion = getDatasetVersionRepository().save(datasetNewVersion);

        return datasetNewVersion;
    }

    @Override
    public void importDatasourcesInDatasetVersion(ServiceContext ctx, String datasetVersionUrn, List<URL> fileUrls) throws MetamacException {
        datasetServiceInvocationValidator.checkImportDatasourcesInDatasetVersion(ctx, datasetVersionUrn, fileUrls);

        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);

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
        return FilenameUtils.getBaseName(path) + "." + FilenameUtils.getExtension(path);
    }

    @Override
    public void importDatasourcesInStatisticalOperation(ServiceContext ctx, String statisticalOperationUrn, List<URL> fileUrls) throws MetamacException {
        throw new NotImplementedException();
    }

    @Override
    public void proccessDatasetFileImportationResult(ServiceContext ctx, String datasetImportationId, List<FileDescriptorResult> fileDescriptors) throws MetamacException {
        String datasetVersionUrn = datasetImportationId;
        datasetServiceInvocationValidator.checkProccessDatasetFileImportationResult(ctx, datasetImportationId, fileDescriptors);

        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);

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
        datasetVersion.setDatasetRepositoryId(datasetImportationId);
        getDatasetVersionRepository().save(datasetVersion);
    }

    @Override
    public List<String> retrieveDatasetVersionDimensionsIds(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        datasetServiceInvocationValidator.checkRetrieveDatasetVersionDimensionsIds(ctx, datasetVersionUrn);

        DatasetVersion datasetVersion = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

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

        return getCodeDimensionRepository().findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), dimensionId);
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
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void fillMetadataForCreateDatasource(Datasource datasource, DatasetVersion datasetVersion) {
        FillMetadataForCreateResourceUtils.fillMetadataForCreateIdentifiableResource(datasource.getIdentifiableStatisticalResource(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getStatisticalOperation());

        datasource.setDatasetVersion(datasetVersion);
        datasource.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasourceUrn(datasource.getIdentifiableStatisticalResource().getCode()));
    }

    private void addDatasourceForDatasetVersion(Datasource datasource, DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(datasource);
        datasetVersion.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime());
        getDatasetVersionRepository().save(datasetVersion);
    }

    private void deleteDatasourceToDataset(Datasource datasource) {
        DatasetVersion parent = datasource.getDatasetVersion();
        parent.removeDatasource(datasource);
        parent.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime());
        getDatasetVersionRepository().save(parent);
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
        String[] maintainerCodes = new String[]{datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCode()};

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

    /**
     * DATA MOCKING
     * 
     * @throws MetamacException
     */

    // FIXME: DELETE MOCK
    private void mockData(DatasetVersion resource) throws MetamacException {
        if (resource.getRelatedDsd() != null) {
            DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(resource.getRelatedDsd().getUrn());

            DimensionListType dimensions = dataStructure.getDataStructureComponents().getDimensionList();

            if (dimensions != null) {
                List<String> dimOrder = computeDimensionOrder(dimensions);
                Map<String, List<String>> dimensionCodes = computeDimensionsCodes(dimensions);

                List<ObservationExtendedDto> observations = buildObservations(dimensionCodes, dimOrder);

                DatasetRepositoryDto datasetRepositoryDto = new DatasetRepositoryDto();
                datasetRepositoryDto.setDatasetId("dataset_" + UUID.randomUUID().toString());
                datasetRepositoryDto.setMaxAttributesObservation(1);
                datasetRepositoryDto.getLanguages().add("es");
                datasetRepositoryDto.getLanguages().add("en");
                for (String dimensionId : dimOrder) {
                    datasetRepositoryDto.getDimensions().add(dimensionId);
                }

                try {

                    datasetRepositoryDto = statisticsDatasetRepositoriesServiceFacade.createDatasetRepository(datasetRepositoryDto);
                    statisticsDatasetRepositoriesServiceFacade.createObservationsExtended(datasetRepositoryDto.getDatasetId(), observations);
                    String oldDatasetRepositoryId = resource.getDatasetRepositoryId();
                    resource.setDatasetRepositoryId(datasetRepositoryDto.getDatasetId());
                    getDatasetVersionRepository().save(resource);
                    if (!StringUtils.isEmpty(oldDatasetRepositoryId)) {
                        try {
                            statisticsDatasetRepositoriesServiceFacade.deleteDatasetRepository(oldDatasetRepositoryId);
                        } catch (Exception e) {
                            // TODO:
                        }
                    }
                } catch (ApplicationException e) {
                    throw new RuntimeException("Error mocking data, creating dataset repository", e);
                }
            }
        }
    }

    private List<ObservationExtendedDto> buildObservations(Map<String, List<String>> dimensionCodes, List<String> dimOrder) {
        CodeSelectionGenerator codeGen = new CodeSelectionGenerator(dimOrder, dimensionCodes);

        List<ObservationExtendedDto> observations = new ArrayList<ObservationExtendedDto>();
        while (codeGen.hasNext()) {
            int[] indexes = codeGen.getNext();
            ObservationExtendedDto observation = new ObservationExtendedDto();
            for (int i = 0; i < indexes.length; i++) {
                String dimensionId = dimOrder.get(i);
                String code = dimensionCodes.get(dimensionId).get(indexes[i]);
                CodeDimensionDto codeDimensionDto = new CodeDimensionDto(dimensionId, code);
                observation.addCodesDimension(codeDimensionDto);
            }
            observation.setPrimaryMeasure(String.valueOf(Math.random() * 10000));
            observations.add(observation);
        }
        return observations;
    }

    private class CodeSelectionGenerator {

        private int[] indexes = null;
        private int[] max     = null;

        public CodeSelectionGenerator(List<String> dimensionOrder, Map<String, List<String>> dimensionCodes) {
            indexes = new int[dimensionCodes.size()];
            max = new int[dimensionCodes.size()];
            int i = 0;
            for (String dimensionId : dimensionOrder) {
                indexes[i] = 0;
                max[i] = dimensionCodes.get(dimensionId).size();
                i++;
            }
            indexes[indexes.length - 1] = -1;
        }

        public boolean hasNext() {
            for (int i = 0; i < indexes.length; i++) {
                if (indexes[i] < max[i] - 1) {
                    return true;
                }
            }
            return false;
        }

        public int[] getNext() {
            int acum = 1;
            for (int i = indexes.length - 1; i >= 0; i--) {
                int newAcum = (indexes[i] + acum) / max[i];
                indexes[i] = (indexes[i] + acum) % max[i];
                acum = newAcum;
            }
            return Arrays.copyOf(indexes, indexes.length);
        }

    }

    private Map<String, List<String>> computeDimensionsCodes(DimensionListType dimensions) throws MetamacException {
        Map<String, List<String>> dimensionCodes = new HashMap<String, List<String>>();
        for (Object dimensionObj : dimensions.getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
            if (dimensionObj instanceof DimensionType) {
                DimensionType dim = (DimensionType) dimensionObj;
                dimensionCodes.put(dim.getId(), getCodesForDimension(dim));
            } else if (dimensionObj instanceof TimeDimensionType) {
                TimeDimensionType dim = (TimeDimensionType) dimensionObj;
                dimensionCodes.put(dim.getId(), getCodesForTimeDimension(dim));
            } else if (dimensionObj instanceof MeasureDimensionType) {
                MeasureDimensionType dim = (MeasureDimensionType) dimensionObj;
                dimensionCodes.put(dim.getId(), getCodesForMeasureDimension(dim));
            }
        }
        return dimensionCodes;
    }

    private List<String> computeDimensionOrder(DimensionListType dimensions) throws MetamacException {
        List<String> order = new ArrayList<String>();
        for (Object dimensionObj : dimensions.getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
            if (dimensionObj instanceof DimensionType) {
                DimensionType dim = (DimensionType) dimensionObj;
                order.add(dim.getId());
            } else if (dimensionObj instanceof TimeDimensionType) {
                TimeDimensionType dim = (TimeDimensionType) dimensionObj;
                order.add(dim.getId());
            } else if (dimensionObj instanceof MeasureDimensionType) {
                MeasureDimensionType dim = (MeasureDimensionType) dimensionObj;
                order.add(dim.getId());
            }
        }
        return order;
    }

    private List<String> getCodesForDimension(DimensionType dimension) throws MetamacException {
        List<String> codes = null;
        if (dimension.getLocalRepresentation() != null) {
            CodelistReferenceType codeListRef = dimension.getLocalRepresentation().getEnumeration();
            if (codeListRef != null) {
                codes = getCodesFromCodelist(codeListRef.getURN());
            } else {
                codes = mockStringCodes(15);
            }
        } else {
            codes = getCodesFromConceptRepresentation(dimension.getConceptIdentity());
        }
        return codes;
    }

    private List<String> getCodesForTimeDimension(TimeDimensionType dimension) throws MetamacException {
        List<String> codes = null;
        if (dimension.getLocalRepresentation() != null) {
            codes = mockStringCodes(15);
        } else {
            codes = getCodesFromConceptRepresentation(dimension.getConceptIdentity());
        }
        return codes;
    }

    private List<String> getCodesForMeasureDimension(MeasureDimensionType dimension) throws MetamacException {
        List<String> codes = null;
        if (dimension.getLocalRepresentation() != null) {
            ConceptSchemeReferenceType conceptSchemeRef = dimension.getLocalRepresentation().getEnumeration();
            codes = getCodesFromConceptScheme(conceptSchemeRef);
        } else {
            codes = getCodesFromConceptRepresentation(dimension.getConceptIdentity());
        }
        return codes;
    }

    private List<String> getCodesFromConceptScheme(ConceptSchemeReferenceType conceptSchemeRef) throws MetamacException {
        Concepts concepts = srmRestInternalService.retrieveConceptsOfConceptSchemeEfficiently(conceptSchemeRef.getURN());

        List<String> codes = new ArrayList<String>();
        for (ItemResourceInternal concept : concepts.getConcepts()) {
            codes.add(concept.getId());
        }
        return codes;
    }

    private List<String> mockStringCodes(int size) {
        List<String> codes = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            codes.add("no_emun_code_" + i);
        }
        return codes;
    }

    private List<String> getCodesFromConceptRepresentation(ConceptReferenceType conceptRef) throws MetamacException {
        List<String> codes = new ArrayList<String>();

        Concept concept = srmRestInternalService.retrieveConceptByUrn(conceptRef.getURN());
        if (concept.getCoreRepresentation() != null) {
            codes = getCodesFromCodelist(concept.getCoreRepresentation().getEnumerationCodelist());
        } else {
            throw new IllegalStateException("Found a concept with no core representation");
        }
        return codes;
    }

    private List<String> getCodesFromCodelist(String codelistUrn) throws MetamacException {
        Codes codelistCodes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistUrn);

        List<String> codes = new ArrayList<String>();
        for (CodeResourceInternal code : codelistCodes.getCodes()) {
            codes.add(code.getId());
        }
        return codes;
    }
}
