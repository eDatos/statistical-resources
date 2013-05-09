package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;

public abstract class LifecycleTemplateService<E extends Object> implements LifecycleService<E> {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final void sendToProductionValidation(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToProductionValidation(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToProductionValidation(resource);

        applySendToProductionValidation(ctx, resource);

        saveResource(resource);
    }

    protected final void checkSendToProductionValidation(E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToProductionValidationLinkedStatisticalResource(resource, exceptions);

        checkResourceMetadataAllActions(resource, exceptions);
        checkSendToProductionValidationResource(resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected final void applySendToProductionValidation(ServiceContext ctx, E resource) throws MetamacException {
        applySendToProductionValidationLinkedStatisticalResource(ctx, resource);

        applySendToProductionValidationResource(ctx, resource);
    }

    protected abstract void checkSendToProductionValidationLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void checkSendToProductionValidationResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void applySendToProductionValidationLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException;

    protected abstract void applySendToProductionValidationResource(ServiceContext ctx, E resource) throws MetamacException;

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final void sendToDiffusionValidation(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToDiffusionValidation(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToDiffusionValidation(resource);

        applySendToDiffusionValidation(ctx, resource);

        saveResource(resource);
    }

    protected final void checkSendToDiffusionValidation(E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToDiffusionValidationLinkedStatisticalResource(resource, exceptions);

        checkResourceMetadataAllActions(resource, exceptions);
        checkSendToDiffusionValidationResource(resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected final void applySendToDiffusionValidation(ServiceContext ctx, E resource) throws MetamacException {
        applySendToDiffusionValidationLinkedStatisticalResource(ctx, resource);

        applySendToDiffusionValidationResource(ctx, resource);
    }

    protected abstract void checkSendToDiffusionValidationLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void checkSendToDiffusionValidationResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void applySendToDiffusionValidationLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException;

    protected abstract void applySendToDiffusionValidationResource(ServiceContext ctx, E resource) throws MetamacException;

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final void sendToValidationRejected(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToValidationRejected(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToValidationRejected(resource);

        applySendToValidationRejected(ctx, resource);

        saveResource(resource);
    }

    protected final void checkSendToValidationRejected(E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToValidationRejectedLinkedStatisticalResource(resource, exceptions);

        checkResourceMetadataAllActions(resource, exceptions);
        checkSendToValidationRejectedResource(resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected final void applySendToValidationRejected(ServiceContext ctx, E resource) throws MetamacException {
        applySendToValidationRejectedLinkedStatisticalResource(ctx, resource);

        applySendToValidationRejectedResource(ctx, resource);
    }

    protected abstract void checkSendToValidationRejectedLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void checkSendToValidationRejectedResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void applySendToValidationRejectedLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException;

    protected abstract void applySendToValidationRejectedResource(ServiceContext ctx, E resource) throws MetamacException;

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final void sendToPublished(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToPublished(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToPublished(resource);

        applySendToPublished(ctx, resource);

        saveResource(resource);
    }

    protected final void checkSendToPublished(E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();

        checkSendToPublishedLinkedStatisticalResource(resource, exceptions);

        checkResourceMetadataAllActions(resource, exceptions);
        checkSendToPublishedResource(resource, exceptions);

        ExceptionUtils.throwIfException(exceptions);
    }

    protected final void applySendToPublished(ServiceContext ctx, E resource) throws MetamacException {
        applySendToPublishedLinkedStatisticalResource(ctx, resource);

        applySendToPublishedResource(ctx, resource);
    }

    protected abstract void checkSendToPublishedLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void checkSendToPublishedResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void applySendToPublishedLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException;

    protected abstract void applySendToPublishedResource(ServiceContext ctx, E resource) throws MetamacException;

    // ------------------------------------------------------------------------------------------------------
    // GLOBAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------

    // This method provides a valid implementation of InvocationValidator based on resource Type
    protected abstract LifecycleInvocationValidatorBase<E> getInvocationValidator();

    // This method knows how to retrieve a resource given the resource
    protected abstract E retrieveResourceByResource(E resource) throws MetamacException;

    protected abstract void checkResourceMetadataAllActions(E resource, List<MetamacExceptionItem> exceptions) throws MetamacException;

    protected abstract E saveResource(E resource);

}
