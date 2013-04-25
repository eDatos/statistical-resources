package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;

public abstract class LifecycleInvocationValidatorBase<E> {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToProductionValidation(ServiceContext ctx, E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToProductionValidationInternal(ctx, resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected abstract void checkSendToProductionValidationInternal(ServiceContext ctx, E resource, List<MetamacExceptionItem> exceptions);

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToDiffusionValidation(ServiceContext ctx, E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToDiffusionValidationInternal(ctx, resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected abstract void checkSendToDiffusionValidationInternal(ServiceContext ctx, E resource, List<MetamacExceptionItem> exceptions);

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToValidationRejected(ServiceContext ctx, E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToValidationRejectedInternal(ctx, resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected abstract void checkSendToValidationRejectedInternal(ServiceContext ctx, E resource, List<MetamacExceptionItem> exceptions);

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToPublished(ServiceContext ctx, E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToPublishedInternal(ctx, resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected abstract void checkSendToPublishedInternal(ServiceContext ctx, E resource, List<MetamacExceptionItem> exceptions);
}
