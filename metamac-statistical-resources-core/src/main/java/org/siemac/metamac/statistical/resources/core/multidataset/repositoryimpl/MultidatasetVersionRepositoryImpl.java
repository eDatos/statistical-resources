package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersionProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for MultidatasetVersion
 */
@Repository("multidatasetVersionRepository")
public class MultidatasetVersionRepositoryImpl extends MultidatasetVersionRepositoryBase {

    public MultidatasetVersionRepositoryImpl() {
    }

    @Override
    public MultidatasetVersion retrieveByUrn(String urn) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> condition = criteriaFor(MultidatasetVersion.class).withProperty(MultidatasetVersionProperties.siemacMetadataStatisticalResource().urn()).eq(urn).distinctRoot()
                .build();

        // Find
        List<MultidatasetVersion> result = findByCondition(condition);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.MULTIDATASET_VERSION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one multidataset with urn " + urn);
        }

        return result.get(0);

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
