package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends LifecycleTemplateService<DatasetVersion> implements LifecycleService<DatasetVersion>{

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    @Autowired
    private SiemacLifecycleChecker siemacLifecycleChecker;
    
    @Autowired
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;
    
    @Autowired
    private DatasetVersionRepository datasetVersionRepository;
    
   
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException{
        //NOTHING
    }
    
    @Override
    protected void checkSendToProductionValidationLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToProductionValidation(resource.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }
    
    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
       // NOTHING
    }
    
    @Override
    protected void applySendToProductionValidationLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToProductionValidationActions(ctx,resource.getSiemacMetadataStatisticalResource());
    }
    
    
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    @Override
    protected void checkSendToDiffusionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException{
        //NOTHING
    }
    
    @Override
    protected void checkSendToDiffusionValidationLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToDiffusionValidation(resource.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }
    
    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
       // NOTHING
    }
    
    @Override
    protected void applySendToDiffusionValidationLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToDiffusionValidationActions(ctx,resource.getSiemacMetadataStatisticalResource());
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------
    
    @Override
    protected void checkSendToValidationRejectedResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException{
        //NOTHING
    }
    
    @Override
    protected void checkSendToValidationRejectedLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToValidationRejected(resource.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }
    
    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
       // NOTHING
    }
    
    @Override
    protected void applySendToValidationRejectedLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToValidationRejectedActions(ctx,resource.getSiemacMetadataStatisticalResource());
    }
    
    
    
    
    /* GENERAL ABSTRACT METHODS */
    @Override
    protected LifecycleInvocationValidatorBase<DatasetVersion> getInvocationValidator() {
        return datasetLifecycleServiceInvocationValidator;
    }
    
    @Override
    protected DatasetVersion saveResource(DatasetVersion resource) {
        return datasetVersionRepository.save(resource);
    }
    
    @Override
    protected DatasetVersion retrieveResourceByResource(DatasetVersion resource) throws MetamacException {
        return datasetVersionRepository.retrieveByUrn(resource.getSiemacMetadataStatisticalResource().getUrn());
    }
    
    @Override
    protected void checkResourceMetadataAllActions(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException{
       lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }
}
