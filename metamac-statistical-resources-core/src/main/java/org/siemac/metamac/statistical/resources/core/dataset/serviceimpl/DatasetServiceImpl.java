package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
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
    DatasetServiceInvocationValidator datasetServiceInvocationValidator;
    

    public DatasetServiceImpl() {
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public Datasource createDatasource(ServiceContext ctx, String datasetVersionUrn, Datasource datasource) throws MetamacException {

        // Validations
        datasetServiceInvocationValidator.checkCreateDatasource(ctx, datasetVersionUrn, datasource);

        // TODO: Obtener el datasetVerison cuando el servicio correspondiente esté hecho. Eliminar el new.
        DatasetVersion datasetVersion = new DatasetVersion();

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

    public Datasource updateDatasource(ServiceContext ctx, Datasource datasource) throws MetamacException {

        // Validation of parameters
        datasetServiceInvocationValidator.checkUpdateDatasource(ctx, datasource);

        // TODO: Comprobar que el estado del dataset asociado es el correcto y otras condiciones necesarias

        // Update
        Datasource updatedDataSource = getDatasourceRepository().save(datasource);

        return updatedDataSource;

    }

    public Datasource retrieveDatasourceByUrn(ServiceContext ctx, String urn) throws MetamacException {

        // Validation
        datasetServiceInvocationValidator.checkRetrieveDatasourceByUrn(ctx, urn);

        // Retrieve
        Datasource datasource = getDatasourceRepository().retrieveByUrn(urn);
        return datasource;

    }

    public void deleteDatasource(ServiceContext ctx, String urn) throws MetamacException {

        // Validation
        datasetServiceInvocationValidator.checkDeleteDatasource(ctx, urn);

        // Retrieve
        Datasource datasource = getDatasourceRepository().retrieveByUrn(urn);

        // TODO: Comprobar que el dataset está en un estado en el que se le pueden eliminar datasources

        deleteDatasourceToDataset(datasource);
    }

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DatasetVersion retrieveDatasetVersionByUrn(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DatasetVersion> retrieveDatasetVersions(ServiceContext ctx, String datasetUrn) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PagedResult<DatasetVersion> findDatasetVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteDatasetVersion(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // TODO Auto-generated method stub

    }

    @Override
    public DatasetVersion versioningDatasetVersion(ServiceContext ctx, String datasetVersionUrnToCopy, VersionTypeEnum versionType) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private void fillMetadataForDatasource(Datasource datasource, DatasetVersion datasetVersion) {
        datasource.setDatasetVersion(datasetVersion);
        datasource.getIdentifiableStatisticalResource().setUri(null);
        // TODO: Cuando se sepa como se construye la URN llamar al método correspondiente en el GeneratorUrnUtils
        datasource.getIdentifiableStatisticalResource().setUrn("TODO:mock");
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
        // TODO: Cuando se sepa como se construye la URN llamar al método correspondiente en el GeneratorUrnUtils
        datasetVersion.getSiemacMetadataStatisticalResource().setUrn("TODO:mock");
        datasetVersion.getSiemacMetadataStatisticalResource().setUri(null);
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic("01.000");
    }

}
