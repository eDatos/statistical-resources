package org.siemac.metamac.statistical.resources.core.base.serviceapi;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;


public interface LifecycleService {

    // Draft >> Production Validation
    void checkSendToProductionValidation(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException;
    void applySendToProductionValidationActions(ServiceContext ctx, LifeCycleStatisticalResource resource);
    
    // Production valiation >> Difussion validation
    void checkSendToDiffusionValidation(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException;
    void applySendToDiffusionValidationActions(ServiceContext ctx, LifeCycleStatisticalResource resource);
    
    // Production Valiation | Diffusion validation >> Validation rejected
    void checkSendToValidationRejected(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException;
    void applySendToValidationRejectedActions(ServiceContext ctx, LifeCycleStatisticalResource resource);
    
}
