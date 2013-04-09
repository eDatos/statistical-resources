package org.siemac.metamac.statistical.resources.core.base.lifecycle;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiemacLifecycleChecker {

    @Autowired
    private LifecycleChecker lifecycleService;
    
    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    public void checkSendToProductionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToProductionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }
    
    public void applySendToProductionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToProductionValidationActions(ctx, resource);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    public void checkSendToDiffusionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToDiffusionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }
    
    public void applySendToDiffusionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToDiffusionValidationActions(ctx, resource);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------
    
    public void checkSendToValidationRejected(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToValidationRejected(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
        
    }

    public void applySendToValidationRejectedActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToValidationRejectedActions(ctx, resource);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> PROTECTED COMMON UTILS
    // ------------------------------------------------------------------------------------------------------
    
    private void checkSiemacMetadataAllActions(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkSiemacCommonMetadata(resource, metadataName, exceptionItems);
    }
}
