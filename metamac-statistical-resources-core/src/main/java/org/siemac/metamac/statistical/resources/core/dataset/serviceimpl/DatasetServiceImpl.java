package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.validators.DatasetServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.dataset.utils.DatasetVersioningCopyUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of DatasetService.
 */
@Service("datasetService")
public class DatasetServiceImpl extends DatasetServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(DatasetServiceImpl.class);
    
    @Autowired
    private IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    private DatasetServiceInvocationValidator         datasetServiceInvocationValidator;
    

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
    public DatasetVersion createDatasetVersion(ServiceContext ctx, DatasetVersion datasetVersion, ExternalItem statisticalOperation) throws MetamacException {
        // Validations
        datasetServiceInvocationValidator.checkCreateDatasetVersion(ctx, datasetVersion, statisticalOperation);

        // Create dataset
        Dataset dataset = new Dataset();
        dataset = getDatasetRepository().save(dataset);

        // Fill metadata
        fillMetadataForCreateDatasetVersion(ctx, datasetVersion, dataset, statisticalOperation);


        // Save version
        datasetVersion.setDataset(dataset);
        
        assignCodeAndSaveDataset(dataset,datasetVersion);
        datasetVersion = getDatasetVersionRepository().retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        return datasetVersion;
    }
    
    private synchronized Dataset assignCodeAndSaveDataset(Dataset dataset, DatasetVersion datasetVersion) throws MetamacException {
        ExternalItem statisticalOperation = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation();
        String seqCodeStr = getDatasetRepository().findLastDatasetCode(statisticalOperation.getUrn());
        int seqCode = 1;
        if (!StringUtils.isEmpty(seqCodeStr)) {
            try { 
                seqCode = Integer.parseInt(seqCodeStr); 
                seqCode++;
            } catch (NumberFormatException e) {
                log.error("Error parsing last sequential code in statistical operation " + statisticalOperation.getCode() + " (" + seqCodeStr + ")");
                throw new MetamacException(CommonServiceExceptionType.UNKNOWN, e.getMessage());
            }
        }
        if (seqCode >= 9999) {
            throw new MetamacException(ServiceExceptionType.DATASET_MAX_REACHED_IN_OPERATION,statisticalOperation.getUrn());
        }
        String code = statisticalOperation.getCode() + "_" + StatisticalResourceTypeEnum.DATASET.name() + "_" + String.format("%04d", seqCode);
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(code);
        datasetVersion.getSiemacMetadataStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(datasetVersion.getSiemacMetadataStatisticalResource().getCode(), datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        
        // Checks
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(datasetVersion.getSiemacMetadataStatisticalResource());

        // Add version to dataset
        dataset.addVersion(datasetVersion);
        return getDatasetRepository().save(datasetVersion.getDataset());
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
        datasetServiceInvocationValidator.checkVersioningDatasetVersion(ctx, datasetVersionUrnToCopy, versionType);
        //TODO: check only published datasets can be versioned
        
        DatasetVersion datasetVersionToCopy = retrieveDatasetVersionByUrn(ctx, datasetVersionUrnToCopy);
        DatasetVersion datasetNewVersion = DatasetVersioningCopyUtils.copyDatasetVersion(datasetVersionToCopy);
        
        fillVersioningMetadataSiemacMetadataSR(ctx, datasetVersionToCopy.getSiemacMetadataStatisticalResource(), datasetNewVersion.getSiemacMetadataStatisticalResource(), versionType);

        //DATASET URN
        datasetNewVersion.getSiemacMetadataStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(datasetNewVersion.getSiemacMetadataStatisticalResource().getCode(), datasetNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        
        //TODO: DATE_NEXT_UPDATE
        
        datasetNewVersion = getDatasetVersionRepository().save(datasetNewVersion);
        
        return datasetNewVersion;
    }


    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------


    private void fillVersioningMetadataSiemacMetadataSR(ServiceContext ctx, SiemacMetadataStatisticalResource previous, SiemacMetadataStatisticalResource next, VersionTypeEnum versionType) {
        fillVersioningMetadataLifecycleSR(ctx, previous, next, versionType);
        
    }
    
    private void fillVersioningMetadataLifecycleSR(ServiceContext ctx, LifeCycleStatisticalResource previous, LifeCycleStatisticalResource next, VersionTypeEnum versionType) {
        fillVersioningMetadataVersionableSR(ctx, previous, next, versionType);
        
        next.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        next.setCreationDate(new DateTime());
        next.setCreationUser(ctx.getUserId());
    }
    

    private void fillVersioningMetadataVersionableSR(ServiceContext ctx,VersionableStatisticalResource previous, VersionableStatisticalResource next, VersionTypeEnum versionType) {
        String newVersion = VersionUtil.createNextVersion(previous.getVersionLogic(), VersionPatternEnum.XX_YYY, versionType);
        next.setVersionLogic(newVersion);
        
    }
    
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

    private void fillMetadataForCreateDatasetVersion(ServiceContext ctx, DatasetVersion datasetVersion, Dataset dataset, ExternalItem statisticalOperation) {
        datasetVersion.setDataset(dataset);

        //TODO: set LANGUAGE to DEFAULT language got form DATA property, add the language also to LANGUAGES, but different objects!!
        //datasetVersion.getSiemacMetadataStatisticalResource().setLanguage(language);
        //datasetVersion.getSiemacMetadataStatisticalResource().getLanguages().add();
        
        //TODO: maintainer, got from data property
        
        datasetVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(statisticalOperation);
        datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic("01.000");
        datasetVersion.getSiemacMetadataStatisticalResource().setUri(null);
        datasetVersion.getSiemacMetadataStatisticalResource().setType(StatisticalResourceTypeEnum.DATASET);
        datasetVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        datasetVersion.getSiemacMetadataStatisticalResource().setCreatedDate(new DateTime());
        datasetVersion.getSiemacMetadataStatisticalResource().setCreatedBy(ctx.getUserId());
        datasetVersion.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().clear();
        datasetVersion.getSiemacMetadataStatisticalResource().addVersionRationaleType(new VersionRationaleType(StatisticalResourceVersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
        //CODE and URN are set just before saving, because the computation for code must be synchronized and this way, we minimize the synchronized block 
    }
}
