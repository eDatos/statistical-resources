package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.serviceimpl.utils.StatisticalResourcesInvocationValidator;

import org.springframework.stereotype.Service;

import java.util.List;

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

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("updateDatasource not implemented");

    }

    public Datasource retrieveDatasourceByUrn(ServiceContext ctx, String urn) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("retrieveDatasourceByUrn not implemented");

    }

    public void deleteDatasource(ServiceContext ctx, String urn) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("deleteDatasource not implemented");

    }

    public List<Datasource> retrieveDatasourcesByDataset(ServiceContext ctx, String datasetUrn, String datasetVersionNumber) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("retrieveDatasourcesByDataset not implemented");

    }
    
    private void fillMetadataForDatasource(Datasource datasource, DatasetVersion datasetVersion) {
        datasource.setDatasetVersion(datasetVersion);
        datasource.getIdentifiableStatisticalResource().setUri(null);
        // TODO: Cuando se sepa como se construye la URN llamar al método correspondiente en el GeneratorUrnUtils
        datasource.getIdentifiableStatisticalResource().setUrn("TODO-mock");
    }
    
    private void addDatasourceForDatasetVersion(Datasource datasource, DatasetVersion datasetVersion) {
        datasetVersion.addDatasource(datasource);
        getDatasetVersionRepository().save(datasetVersion);
    }
}
