package org.siemac.metamac.statistical.resources.core.base.serviceimpl;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;
import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.base.serviceapi.SiemacLifecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("siemacLifecycleService")
public class SiemacLifecycleServiceImpl implements SiemacLifecycleService {

    @Autowired
    private LifecycleService lifecycleService;
    
    @Override
    public void checkSendToProductionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToProductionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }
    
    @Override
    public void applySendToProductionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToProductionValidationActions(ctx, resource);
    }
    
    @Override
    public void checkSendToDiffusionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToDiffusionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }
    
    @Override
    public void applySendToDiffusionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToDiffusionValidationActions(ctx, resource);
    }
    
    protected void checkSiemacMetadataAllActions(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        checkMetadataRequired(resource.getLanguage(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGE), exceptionItems);
        checkMetadataRequired(resource.getLanguages(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGES), exceptionItems);
        
        checkMetadataRequired(resource.getStatisticalOperation(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_OPERATION), exceptionItems);
        
        checkMetadataRequired(resource.getType(), addParameter(metadataName, ServiceExceptionSingleParameters.TYPE), exceptionItems);
        
        checkMetadataRequired(resource.getMaintainer(), addParameter(metadataName, ServiceExceptionSingleParameters.MAINTAINER), exceptionItems);
        checkMetadataRequired(resource.getCreator(), addParameter(metadataName, ServiceExceptionSingleParameters.CREATOR), exceptionItems);
        checkMetadataRequired(resource.getLastUpdate(), addParameter(metadataName, ServiceExceptionSingleParameters.LAST_UPDATE), exceptionItems);
        
        checkMetadataRequired(resource.getPublisher(), addParameter(metadataName, ServiceExceptionSingleParameters.PUBLISHER), exceptionItems);
        
        checkMetadataRequired(resource.getRightsHolder(), addParameter(metadataName, ServiceExceptionSingleParameters.RIGHTS_HOLDER), exceptionItems);
        checkMetadataRequired(resource.getLicense(), addParameter(metadataName, ServiceExceptionSingleParameters.LICENSE), exceptionItems);
    }
    
}
