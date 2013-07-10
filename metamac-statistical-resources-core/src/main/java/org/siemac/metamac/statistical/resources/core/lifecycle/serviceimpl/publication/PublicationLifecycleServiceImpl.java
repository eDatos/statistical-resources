package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publicationLifecycleService")
public class PublicationLifecycleServiceImpl extends LifecycleTemplateService<PublicationVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker                 lifecycleCommonMetadataChecker;

    @Autowired
    private PublicationLifecycleServiceInvocationValidator publicationLifecycleServiceInvocationValidator;

    @Autowired
    private PublicationVersionRepository                   publicationVersionRepository;

    @Override
    protected String getResourceMetadataName() throws MetamacException {
        return ServiceExceptionParameters.PUBLICATION_VERSION;
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // check format extent resources ?
        // FIXME: check possible fields to be checked
    }


    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // FIXME: check possible fields to be checked
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // FIXME: check possible fields to be checked
    }
    

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // FIXME: check possible fields to be checked
    }


    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO
    }


    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // TODO
    }


    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToPublishedResource(PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void applySendToPublishedResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void checkSendToPublishedLinkedStatisticalResource(PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void applySendToPublishedLinkedStatisticalResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkVersioningResource(PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    protected void applyVersioningResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    protected void checkVersioningLinkedStatisticalResource(PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void applyVersioningLinkedStatisticalResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        
    }
    
    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------
    
    @Override
    protected LifecycleInvocationValidatorBase<PublicationVersion> getInvocationValidator() {
        return publicationLifecycleServiceInvocationValidator;
    }

    @Override
    protected PublicationVersion saveResource(PublicationVersion resource) {
        return publicationVersionRepository.save(resource);
    }

    @Override
    protected PublicationVersion retrieveResourceByResource(PublicationVersion resource) throws MetamacException {
        return publicationVersionRepository.retrieveByUrn(resource.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    protected void checkResourceMetadataAllActions(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkPublicationVersionCommonMetadata(resource, ServiceExceptionParameters.PUBLICATION_VERSION, exceptions);
    }
}
