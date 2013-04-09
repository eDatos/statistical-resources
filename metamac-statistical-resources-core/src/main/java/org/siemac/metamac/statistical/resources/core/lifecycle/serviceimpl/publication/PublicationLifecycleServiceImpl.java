package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//FIXME uncomment @service
//@Service("publicationLifecycleService")
public abstract class PublicationLifecycleServiceImpl extends LifecycleTemplateService<PublicationVersion> implements LifecycleService<PublicationVersion>{

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    @Autowired
    private SiemacLifecycleChecker siemacLifecycleChecker;
    
    @Autowired
    private PublicationLifecycleServiceInvocationValidator publicationLifecycleServiceInvocationValidator;
    
    @Autowired
    private PublicationVersionRepository publicationVersionRepository;
    
   
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException{
       //FIXME: check possible fields to be checked
    }
    
    @Override
    protected void checkSendToProductionValidationLinkedStatisticalResource(PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToProductionValidation(resource.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }
    
    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        //FIXME: check possible fields to be checked
    }
    
    @Override
    protected void applySendToProductionValidationLinkedStatisticalResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToProductionValidationActions(ctx,resource.getSiemacMetadataStatisticalResource());
    }
    
    
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    @Override
    protected void checkSendToDiffusionValidationResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException{
        //FIXME: check possible fields to be checked
    }
    
    @Override
    protected void checkSendToDiffusionValidationLinkedStatisticalResource(PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToDiffusionValidation(resource.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }
    
    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        //FIXME: check possible fields to be checked
    }
    
    @Override
    protected void applySendToDiffusionValidationLinkedStatisticalResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToDiffusionValidationActions(ctx,resource.getSiemacMetadataStatisticalResource());
    }
    
    
    /* GENERAL ABSTRACT METHODS */
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
    protected void checkResourceMetadataAllActions(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException{
       lifecycleCommonMetadataChecker.checkPublicationVersionCommonMetadata(resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }
}
