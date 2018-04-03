package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for MultidatasetVersion
 */
@Repository("multidatasetVersionRepository")
public class MultidatasetVersionRepositoryImpl
    extends MultidatasetVersionRepositoryBase {
    public MultidatasetVersionRepositoryImpl() {
    }

    public MultidatasetVersion retrieveByUrn(String urn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("retrieveByUrn not implemented");

    }

    public MultidatasetVersion retrieveByUrnPublished(String urn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveByUrnPublished not implemented");

    }

    public MultidatasetVersion retrieveLastVersion(String multidatasetUrn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveLastVersion not implemented");

    }

    public MultidatasetVersion retrieveLastPublishedVersion(
        String multidatasetUrn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveLastPublishedVersion not implemented");

    }

    public MultidatasetVersion retrieveByVersion(Long statisticalResourceId,
        String versionLogic) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveByVersion not implemented");

    }

    public RelatedResourceResult retrieveIsReplacedByOnlyLastPublished(
        MultidatasetVersion multidatasetVersion) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveIsReplacedByOnlyLastPublished not implemented");

    }

    public RelatedResourceResult retrieveIsReplacedByOnlyIfPublished(
        MultidatasetVersion multidatasetVersion) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveIsReplacedByOnlyIfPublished not implemented");

    }

    public RelatedResourceResult retrieveIsReplacedBy(
        MultidatasetVersion multidatasetVersion) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveIsReplacedBy not implemented");

    }
}
