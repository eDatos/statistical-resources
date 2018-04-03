package org.siemac.metamac.statistical.resources.core.multidataset.serviceimpl;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

import org.springframework.stereotype.Service;

import java.net.URL;

import java.util.List;

/**
 * Implementation of MultidatasetService.
 */
@Service("multidatasetService")
public class MultidatasetServiceImpl extends MultidatasetServiceImplBase {
    public MultidatasetServiceImpl() {
    }

    public MultidatasetVersion createMultidatasetVersion(ServiceContext ctx,
        MultidatasetVersion multidatasetVersion,
        ExternalItem statisticalOperation) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "createMultidatasetVersion not implemented");

    }

    public MultidatasetVersion updateMultidatasetVersion(ServiceContext ctx,
        MultidatasetVersion multidatasetVersion) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "updateMultidatasetVersion not implemented");

    }

    public MultidatasetVersion retrieveMultidatasetVersionByUrn(
        ServiceContext ctx, String multidatasetVersionUrn)
        throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveMultidatasetVersionByUrn not implemented");

    }

    public MultidatasetVersion retrieveLatestMultidatasetVersionByMultidatasetUrn(
        ServiceContext ctx, String multidatasetUrn) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveLatestMultidatasetVersionByMultidatasetUrn not implemented");

    }

    public MultidatasetVersion retrieveLatestPublishedMultidatasetVersionByMultidatasetUrn(
        ServiceContext ctx, String multidatasetUrn) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveLatestPublishedMultidatasetVersionByMultidatasetUrn not implemented");

    }

    public List<MultidatasetVersion> retrieveMultidatasetVersions(
        ServiceContext ctx, String multidatasetVersionUrn)
        throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveMultidatasetVersions not implemented");

    }

    public PagedResult<MultidatasetVersion> findMultidatasetVersionsByCondition(
        ServiceContext ctx, List<ConditionalCriteria> conditions,
        PagingParameter pagingParameter) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "findMultidatasetVersionsByCondition not implemented");

    }

    public void deleteMultidatasetVersion(ServiceContext ctx,
        String multidatasetVersionUrn) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "deleteMultidatasetVersion not implemented");

    }

    public MultidatasetVersion importMultidatasetVersionStructure(
        ServiceContext ctx, String multidatasetVersionUrn, URL fileURL,
        String language) throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "importMultidatasetVersionStructure not implemented");

    }
}
