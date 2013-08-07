package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("queryLifecycleService")
public class QueryLifecycleServiceImpl extends LifecycleTemplateService<QueryVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker           lifecycleCommonMetadataChecker;

    @Autowired
    private QueryLifecycleServiceInvocationValidator queryLifecycleServiceInvocationValidator;

    @Autowired
    private QueryVersionRepository                   queryVersionRepository;

    @Override
    protected String getResourceMetadataName() throws MetamacException {
        return ServiceExceptionParameters.QUERY_VERSION;
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void applySendToPublishedResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void checkSendToPublishedResource(QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkVersioningResource(QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void applyVersioningResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected LifecycleInvocationValidatorBase<QueryVersion> getInvocationValidator() {
        return queryLifecycleServiceInvocationValidator;
    }

    @Override
    protected QueryVersion saveResource(QueryVersion resource) {
        return queryVersionRepository.save(resource);
    }

    @Override
    protected QueryVersion retrieveResourceByResource(QueryVersion resource) throws MetamacException {
        return queryVersionRepository.retrieveByUrn(resource.getLifeCycleStatisticalResource().getUrn());
    }

    @Override
    protected QueryVersion retrievePreviousResourceByResource(QueryVersion resource) throws MetamacException {
        // FIXME
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void checkResourceMetadataAllActions(QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkQueryVersionCommonMetadata(resource, ServiceExceptionParameters.QUERY_VERSION, exceptions);
    }
}