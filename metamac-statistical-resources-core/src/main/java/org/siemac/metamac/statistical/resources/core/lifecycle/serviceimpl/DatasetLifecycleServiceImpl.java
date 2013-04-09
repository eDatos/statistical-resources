package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.base.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.base.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.validators.DatasetLifecycleServiceInvocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of DatasetLifecycleService.
 */
@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends DatasetLifecycleServiceImplBase {
    
    @Autowired
    private SiemacLifecycleChecker siemacLifecycleService;
    
    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    @Autowired
    private DatasetVersionRepository datasetVersionRepository; 
    
    @Autowired
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;

    /*------------------ PRODUCTION VALIDATION ----------------------*/
    
    @Override
    public void sendToProductionValidation(ServiceContext ctx, DatasetVersion datasetVersion) throws MetamacException {
        
        datasetLifecycleServiceInvocationValidator.checkSendToProductionValidation(ctx, datasetVersion);
        
        datasetVersion = datasetVersionRepository.retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        checkMetadataSendToProductionValidation(datasetVersion);
        
        siemacLifecycleService.applySendToProductionValidationActions(ctx, datasetVersion.getSiemacMetadataStatisticalResource());
        
        datasetVersionRepository.save(datasetVersion);
    }
    
    private void checkMetadataSendToProductionValidation(DatasetVersion datasetVersion) throws org.siemac.metamac.core.common.exception.MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
        
        String actualParameter = ServiceExceptionParameters.DATASET_VERSION;
        siemacLifecycleService.checkSendToProductionValidation(datasetVersion.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE, exceptions);
        
        checkDatasetMetadataAllActions(datasetVersion, actualParameter, exceptions);
        
        ExceptionUtils.throwIfException(exceptions);
    }
    
    /*------------------ DIFFUSION VALIDATION ----------------------*/
    
    @Override
    public void sendToDiffusionValidation(ServiceContext ctx, DatasetVersion datasetVersion) throws MetamacException {
        
        datasetLifecycleServiceInvocationValidator.checkSendToDiffusionValidation(ctx, datasetVersion);
        
        datasetVersion = datasetVersionRepository.retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        
        checkMetadataSendToDiffusionValidation(datasetVersion);
        
        siemacLifecycleService.applySendToDiffusionValidationActions(ctx, datasetVersion.getSiemacMetadataStatisticalResource());
        
        datasetVersionRepository.save(datasetVersion);
        
    }
    
    private void checkMetadataSendToDiffusionValidation(DatasetVersion datasetVersion) throws org.siemac.metamac.core.common.exception.MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
        
        String actualParameter = ServiceExceptionParameters.DATASET_VERSION;
        siemacLifecycleService.checkSendToDiffusionValidation(datasetVersion.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE, exceptions);
        
        checkDatasetMetadataAllActions(datasetVersion, actualParameter, exceptions);
        
        ExceptionUtils.throwIfException(exceptions);
    }
    
    /*------------------- COMMON ----------------------*/
    
    private void checkDatasetMetadataAllActions(DatasetVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, metadataName, exceptionItems);
    }
    
}
