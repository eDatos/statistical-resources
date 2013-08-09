package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publicationLifecycleService")
public class PublicationLifecycleServiceImpl extends LifecycleTemplateService<PublicationVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private PublicationVersionRepository   publicationVersionRepository;

    @Override
    protected String getResourceMetadataName() throws MetamacException {
        return ServiceExceptionParameters.PUBLICATION_VERSION;
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void applySendToPublishedResource(ServiceContext ctx, PublicationVersion resource) throws MetamacException {
        // TODO:
        // - cumplimentar format_extent_resources

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void checkSendToPublishedResource(PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // FIXME
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
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

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected PublicationVersion saveResource(PublicationVersion resource) {
        return publicationVersionRepository.save(resource);
    }

    @Override
    protected PublicationVersion retrieveResourceByUrn(String urn) throws MetamacException {
        return publicationVersionRepository.retrieveByUrn(urn);
    }

    @Override
    protected PublicationVersion retrievePreviousResourceByResource(PublicationVersion resource) throws MetamacException {
        // FIXME
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void checkResourceMetadataAllActions(ServiceContext ctx, PublicationVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkPublicationVersionCommonMetadata(resource, ServiceExceptionParameters.PUBLICATION_VERSION, exceptions);
    }
}