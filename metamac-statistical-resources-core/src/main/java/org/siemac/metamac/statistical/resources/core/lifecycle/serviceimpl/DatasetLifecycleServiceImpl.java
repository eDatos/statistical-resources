package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;
import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkParameterRequired;
import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.serviceapi.SiemacLifecycleService;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.validators.DatasetLifecycleServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.validators.DatasetLifecycleServiceInvocationValidatorBaseImpl;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.validators.DatasetLifecycleServiceInvocationValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of DatasetLifecycleService.
 */
@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends DatasetLifecycleServiceImplBase {
    
    @Autowired
    private SiemacLifecycleService siemacLifecycleService;
    
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
    
    private static void checkDatasetMetadataAllActions(DatasetVersion datasetVersion, String actualParameter, List<MetamacExceptionItem> exceptionItems) {
        checkMetadataRequired(datasetVersion.getRelatedDsd(), addParameter(actualParameter, ServiceExceptionSingleParameters.RELATED_DSD), exceptionItems);
        checkMetadataRequired(datasetVersion.getDateNextUpdate(), addParameter(actualParameter, ServiceExceptionSingleParameters.DATE_NEXT_UPDATE), exceptionItems);
        checkMetadataRequired(datasetVersion.getUpdateFrequency(), addParameter(actualParameter, ServiceExceptionSingleParameters.UPDATE_FREQUENCY), exceptionItems);
        checkMetadataRequired(datasetVersion.getStatisticOfficiality(), addParameter(actualParameter, ServiceExceptionSingleParameters.STATISTIC_OFFICIALITY), exceptionItems);
    }
    
}
