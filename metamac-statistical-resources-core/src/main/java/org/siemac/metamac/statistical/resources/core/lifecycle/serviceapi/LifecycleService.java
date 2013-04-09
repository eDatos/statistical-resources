package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;


public interface LifecycleService<E extends Object> {

    public void sendToProductionValidation(ServiceContext ctx, E resource) throws MetamacException;
    
    public void sendToDiffusionValidation(ServiceContext ctx, E resource) throws MetamacException;
    
    public void sendToValidationRejected(ServiceContext ctx, E resource) throws MetamacException;
    
}
