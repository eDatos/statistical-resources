package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.utils.QueryVersioningCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("queryLifecycleService")
public class QueryLifecycleServiceImpl extends LifecycleTemplateService<QueryVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private QueryVersionRepository         queryVersionRepository;

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
        throw new UnsupportedOperationException("Not implemented in this version");
    }


    @Override
    protected QueryVersion copyResourceForVersioning(QueryVersion previousResource) throws MetamacException {
        return QueryVersioningCopyUtils.copyQueryVersion(previousResource);
    }
    
    @Override
    protected void applyVersioningNewResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        throw new UnsupportedOperationException("Not implemented in this version");
    }
    

    @Override
    protected void applyVersioningPreviousResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        throw new UnsupportedOperationException("Not implemented in this version");
    }
    
    @Override
    protected QueryVersion updateResourceUrn(QueryVersion resource) throws MetamacException {
        String[] creator = new String[]{resource.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        resource.getLifeCycleStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(creator, resource.getLifeCycleStatisticalResource().getCode(), resource
                        .getLifeCycleStatisticalResource().getVersionLogic()));
        return resource;
    }

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected QueryVersion saveResource(QueryVersion resource) {
        return queryVersionRepository.save(resource);
    }

    @Override
    protected QueryVersion retrieveResourceByUrn(String urn) throws MetamacException {
        return queryVersionRepository.retrieveByUrn(urn);
    }


    @Override
    protected QueryVersion retrieveResourceByResource(QueryVersion resource) throws MetamacException {
        return queryVersionRepository.retrieveByUrn(resource.getLifeCycleStatisticalResource().getUrn());
    }
    
    @Override
    protected QueryVersion retrievePreviousPublishedResourceByResource(QueryVersion resource) throws MetamacException {
        return queryVersionRepository.retrieveLastPublishedVersion(resource.getQuery().getIdentifiableStatisticalResource().getUrn());
    }

    @Override
    protected void checkResourceMetadataAllActions(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkQueryVersionCommonMetadata(resource, ServiceExceptionParameters.QUERY_VERSION, exceptions);
    }

    @Override
    protected String getResourceUrn(QueryVersion resource) {
        return resource.getLifeCycleStatisticalResource().getUrn();
    }
}