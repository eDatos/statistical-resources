package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesInvocationValidator;
import org.springframework.stereotype.Service;

/**
 * Implementation of DatasetService.
 */
@Service("datasetService")
public class DatasetServiceImpl extends DatasetServiceImplBase {

    public DatasetServiceImpl() {
    }

    public Datasource createDatasource(ServiceContext ctx, String datasetUrn, Datasource datasource) throws MetamacException {

        // Validations
        StatisticalResourcesInvocationValidator.checkCreateDatasource(datasetUrn, datasource, null);

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
        StatisticalResourcesInvocationValidator.checkUpdateDatasource(datasource, null);

        // TODO: Comprobar que el estado del dataset asociado es el correcto y otras condiciones necesarias

        // Update
        Datasource updatedDataSource = getDatasourceRepository().save(datasource);

        return updatedDataSource;

    }

    public Datasource retrieveDatasourceByUrn(ServiceContext ctx, String urn) throws MetamacException {

        // Validation
        StatisticalResourcesInvocationValidator.checkRetrieveDatasourceByUrn(urn, null);

        // Retrieve
        Datasource datasource = getDatasourceRepository().retrieveByUrn(urn);
        return datasource;

    }

    public void deleteDatasource(ServiceContext ctx, String urn) throws MetamacException {

        // Validation
        StatisticalResourcesInvocationValidator.checkDeleteDatasource(urn, null);

        // Retrieve
        Datasource datasource = getDatasourceRepository().retrieveByUrn(urn);

        // TODO: Comprobar que el dataset está en un estado en el que se le pueden eliminar datasources

        deleteDatasourceToDataset(datasource);
    }

    public List<Datasource> retrieveDatasourcesByDatasetVersion(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {

        // Validation
        StatisticalResourcesInvocationValidator.checkRetrieveDatasourcesByDataset(datasetVersionUrn, null);

        // Retrieve
        DatasetVersion datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);
        List<Datasource> datasources = datasetVersion.getDatasources();

        return datasources;
    }

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
}
