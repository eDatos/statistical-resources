package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.validators.DatasetServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of DatasetService.
 */
@Service("datasetService")
public class DatasetServiceImpl extends DatasetServiceImplBase {

    @Autowired
    IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    DatasetServiceInvocationValidator         datasetServiceInvocationValidator;

    public DatasetServiceImpl() {
    }

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
        fillMetadataForDatasource(datasource, datasetVersion);

        // Save
        datasource = getDatasourceRepository().save(datasource);

        // Update dataset version (add datasource)
        addDatasourceForDatasetVersion(datasource, datasetVersion);

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
    // DATASETS
    // ------------------------------------------------------------------------

    @Override
    public DatasetVersion createDatasetVersion(ServiceContext ctx, DatasetVersion datasetVersion) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkCreateDatasetVersion(ctx, datasetVersion);

        // Create dataset
        Dataset dataset = new Dataset();
        dataset = getDatasetRepository().save(dataset);

        // Fill metadata
        fillMetadataForCreateDatasetVersion(datasetVersion, dataset);

        // Checks
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasetVersion.getSiemacMetadataStatisticalResource());

        // Save version
        datasetVersion.setDataset(dataset);

        // Add version to dataset
        dataset.addVersion(datasetVersion);
        getDatasetRepository().save(dataset);
        datasetVersion = getDatasetVersionRepository().save(datasetVersion);

        return datasetVersion;
    }

    @Override
    public DatasetVersion updateDatasetVersion(ServiceContext ctx, DatasetVersion datasetVersion) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkUpdateDatasetVersion(ctx, datasetVersion);

        // Check status
        BaseValidator.checkStatisticalResourceCanBeEdited(datasetVersion.getSiemacMetadataStatisticalResource());

        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasetVersion.getSiemacMetadataStatisticalResource());

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
        // TODO: Determinar cuales son los estados en los que se puede borrar
        BaseValidator.checkStatisticalResourceCanBeEdited(datasetVersion.getSiemacMetadataStatisticalResource());

        // TODO: Determinar si hay algunas comprobaciones que impiden el borrado

        // TODO: Comprobar si hay que eliminar relaciones a otros recursos

        if (VersionUtil.isInitialVersion(datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic())) {
            Dataset dataset = datasetVersion.getDataset();
            getDatasetRepository().delete(dataset);
        } else {
            DatasetVersion previousDatasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getReplacesVersion().getUrn());

            // Delete version
            Dataset dataset = datasetVersion.getDataset();
            dataset.getVersions().remove(datasetVersion);
            getDatasetVersionRepository().delete(datasetVersion);

            // Update previous version
            previousDatasetVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);
            previousDatasetVersion.getSiemacMetadataStatisticalResource().setIsReplacedBy(null);
            getDatasetVersionRepository().save(previousDatasetVersion);
        }
    }

    @Override
    public DatasetVersion versioningDatasetVersion(ServiceContext ctx, String datasetVersionUrnToCopy, VersionTypeEnum versionType) throws MetamacException {
        throw new UnsupportedOperationException("versioningPublicationVersion not implemented");
        // // Validations
        // datasetServiceInvocationValidator.checkVersioningDatasetVersion(ctx, datasetVersionUrnToCopy, versionType);
        //
        // // Initialize new version copying
        // DatasetVersion datasetVersionToCopy = retrieveDatasetVersionByUrn(ctx, datasetVersionUrnToCopy);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void fillMetadataForDatasource(Datasource datasource, DatasetVersion datasetVersion) {
        datasource.setDatasetVersion(datasetVersion);
        datasource.getIdentifiableStatisticalResource().setUri(null);
        datasource.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasourceUrn(datasource.getIdentifiableStatisticalResource().getCode()));
    }

    private void addDatasourceForDatasetVersion(Datasource datasource, DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(datasource);
        getDatasetVersionRepository().save(datasetVersion);
    }

    private void deleteDatasourceToDataset(Datasource datasource) {
        DatasetVersion parent = datasource.getDatasetVersion();
        parent.removeDatasource(datasource);
        getDatasetVersionRepository().save(parent);
    }

    private void fillMetadataForCreateDatasetVersion(DatasetVersion datasetVersion, Dataset dataset) {
        datasetVersion.setDataset(dataset);

        String operationCode = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        String type = datasetVersion.getSiemacMetadataStatisticalResource().getType().name();

        String previousCode = getDatasetVersionRepository().getLastCodeUsedInStatisticalOperation(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        int nextSequantialId = 1;
        if (previousCode != null) {
            String previousSequential = previousCode.split(type + "_")[1];
            nextSequantialId = Integer.parseInt(previousSequential);
            nextSequantialId++;
        }

        String seqCode = String.format("%04d", nextSequantialId);
        String code = operationCode + "_" + type + "_" + seqCode;

        datasetVersion.getSiemacMetadataStatisticalResource().setCode(code);

        datasetVersion.getSiemacMetadataStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(datasetVersion.getSiemacMetadataStatisticalResource().getCode(), datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        datasetVersion.getSiemacMetadataStatisticalResource().setUri(null);
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic("01.000");
    }
}
