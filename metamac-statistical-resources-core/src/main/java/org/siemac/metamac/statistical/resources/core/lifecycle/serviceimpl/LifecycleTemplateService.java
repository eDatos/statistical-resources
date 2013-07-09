package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleFiller;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleFiller;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class LifecycleTemplateService<E extends Object> implements LifecycleService<E> {

    @Autowired
    private SiemacLifecycleChecker siemacLifecycleChecker;
    
    @Autowired
    private SiemacLifecycleFiller siemacLifecycleFiller;
    
    @Autowired
    private LifecycleChecker lifecycleChecker;
    
    @Autowired
    private LifecycleFiller lifecycleFiller;
    
    
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final E sendToProductionValidation(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToProductionValidation(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToProductionValidation(resource);

        applySendToProductionValidation(ctx, resource);

        return saveResource(resource);
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

    protected void checkSendToProductionValidationLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        if (resource instanceof HasSiemacMetadata) {
            siemacLifecycleChecker.checkSendToProductionValidation((HasSiemacMetadata)resource, getResourceMetadataName(), exceptionItems);
        } else if (resource instanceof HasLifecycle) {
            lifecycleChecker.checkSendToProductionValidation((HasLifecycle)resource, getResourceMetadataName(), exceptionItems);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Found an unknown resource type sending to production validation");
        }
    }

    protected void applySendToProductionValidationLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException {
        if (resource instanceof HasSiemacMetadata) {
            siemacLifecycleFiller.applySendToProductionValidationActions(ctx, (HasSiemacMetadata)resource);
        } else if (resource instanceof HasLifecycle) {
            lifecycleFiller.applySendToProductionValidationActions(ctx, (HasLifecycle)resource);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Found an unknown resource type sending to production validation");
        }
    }

    protected abstract void checkSendToProductionValidationResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void applySendToProductionValidationResource(ServiceContext ctx, E resource) throws MetamacException;

    protected abstract String getResourceMetadataName() throws MetamacException;
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final E sendToDiffusionValidation(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToDiffusionValidation(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToDiffusionValidation(resource);

        applySendToDiffusionValidation(ctx, resource);

        return saveResource(resource);
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

    protected void checkSendToDiffusionValidationLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        if (resource instanceof HasSiemacMetadata) {
            siemacLifecycleChecker.checkSendToDiffusionValidation((HasSiemacMetadata)resource, getResourceMetadataName(), exceptionItems);
        } else if (resource instanceof HasLifecycle) {
            lifecycleChecker.checkSendToDiffusionValidation((HasLifecycle)resource, getResourceMetadataName(), exceptionItems);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Found an unknown resource type sending to diffusion validation");
        }
    }

    protected void applySendToDiffusionValidationLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException {
        if (resource instanceof HasSiemacMetadata) {
            siemacLifecycleFiller.applySendToDiffusionValidationActions(ctx, (HasSiemacMetadata)resource);
        } else if (resource instanceof HasLifecycle) {
            lifecycleFiller.applySendToDiffusionValidationActions(ctx, (HasLifecycle)resource);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Found an unknown resource type sending to production validation");
        }
    }

    protected abstract void checkSendToDiffusionValidationResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void applySendToDiffusionValidationResource(ServiceContext ctx, E resource) throws MetamacException;

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final E sendToValidationRejected(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToValidationRejected(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToValidationRejected(resource);

        applySendToValidationRejected(ctx, resource);

        return saveResource(resource);
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

    protected void checkSendToValidationRejectedLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        if (resource instanceof HasSiemacMetadata) {
            siemacLifecycleChecker.checkSendToValidationRejected((HasSiemacMetadata)resource, getResourceMetadataName(), exceptionItems);
        } else if (resource instanceof HasLifecycle) {
            lifecycleChecker.checkSendToValidationRejected((HasLifecycle)resource, getResourceMetadataName(), exceptionItems);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Found an unknown resource type sending to validation rejected");
        }
    }
    
    protected void applySendToValidationRejectedLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException {
        if (resource instanceof HasSiemacMetadata) {
            siemacLifecycleFiller.applySendToValidationRejectedActions(ctx, (HasSiemacMetadata)resource);
        } else if (resource instanceof HasLifecycle) {
            lifecycleFiller.applySendToValidationRejectedActions(ctx, (HasLifecycle)resource);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Found an unknown resource type sending to production validation");
        }
    }

    protected abstract void checkSendToValidationRejectedResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    protected abstract void applySendToValidationRejectedResource(ServiceContext ctx, E resource) throws MetamacException;


    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    public final E sendToPublished(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkSendToPublished(ctx, resource);

        resource = retrieveResourceByResource(resource);

        checkSendToPublished(resource);

        applySendToPublished(ctx, resource);

        return saveResource(resource);
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
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------
    
    @Override
    public final E versioning(ServiceContext ctx, E resource) throws MetamacException {
        getInvocationValidator().checkVersioning(ctx, resource);
        
        resource = retrieveResourceByResource(resource);
        
        checkSendToPublished(resource);
        
        applySendToPublished(ctx, resource);
        
        return saveResource(resource);
    }
    
    protected final void checkVersioning(E resource) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
        
        checkVersioningLinkedStatisticalResource(resource, exceptions);
        
        checkResourceMetadataAllActions(resource, exceptions);
        checkVersioningResource(resource, exceptions);
        
        ExceptionUtils.throwIfException(exceptions);
    }
    
    protected final void applyVersioning(ServiceContext ctx, E resource) throws MetamacException {
        applyVersioningLinkedStatisticalResource(ctx, resource);
        
        applyVersioningResource(ctx, resource);
    }
    
    protected abstract void checkVersioningLinkedStatisticalResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;
    
    protected abstract void checkVersioningResource(E resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException;
    
    protected abstract void applyVersioningLinkedStatisticalResource(ServiceContext ctx, E resource) throws MetamacException;
    
    protected abstract void applyVersioningResource(ServiceContext ctx, E resource) throws MetamacException;

    
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
