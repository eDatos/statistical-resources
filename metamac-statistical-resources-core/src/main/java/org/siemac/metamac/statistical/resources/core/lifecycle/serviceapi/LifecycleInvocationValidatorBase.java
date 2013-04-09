package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;


public abstract class LifecycleInvocationValidatorBase<E> {

    public void checkSendToProductionValidation(ServiceContext ctx, E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToProductionValidationInternal(ctx,resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }
    
    protected abstract void checkSendToProductionValidationInternal(ServiceContext ctx, E resource, List<MetamacExceptionItem> exceptions);
    
    public void checkSendToDiffusionValidation(ServiceContext ctx, E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
        
        checkSendToDiffusionValidationInternal(ctx,resource, exceptions);
        
        ExceptionUtils.throwIfException(exceptions);
    }
    
    protected abstract void checkSendToDiffusionValidationInternal(ServiceContext ctx, E resource, List<MetamacExceptionItem> exceptions);
}
