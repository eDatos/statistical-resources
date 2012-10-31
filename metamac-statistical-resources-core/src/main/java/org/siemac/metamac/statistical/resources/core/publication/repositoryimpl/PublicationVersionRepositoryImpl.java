package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for PublicationVersion
 */
@Repository("publicationVersionRepository")
public class PublicationVersionRepositoryImpl extends PublicationVersionRepositoryBase {

    public PublicationVersionRepositoryImpl() {
    }

    public PublicationVersion retrieveByUrn(String urn) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> condition = criteriaFor(PublicationVersion.class)
                .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().urn()).eq(urn).distinctRoot().build();

        // Find
        List<PublicationVersion> result = findByCondition(condition);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one publication with urn " + urn);
        }

        return result.get(0);
    }

    public PublicationVersion retrieveLastVersion(Long statisticalResourceId) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class).withProperty(PublicationVersionProperties.publication().id()).eq(statisticalResourceId)
                .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().isLastVersion()).eq(Boolean.TRUE).distinctRoot().build();

        // Find
        List<PublicationVersion> result = findByCondition(conditions);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_LAST_VERSION_NOT_FOUND, statisticalResourceId);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one last version found for publication with id " + statisticalResourceId);
        }

        return result.get(0);
    }

    public PublicationVersion retrieveByVersion(Long statisticalResourceId, String versionLogic) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class).withProperty(PublicationVersionProperties.publication().id()).eq(statisticalResourceId)
                .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq(versionLogic).distinctRoot().build();

        // Find
        List<PublicationVersion> result = findByCondition(conditions);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, statisticalResourceId, versionLogic);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one publication with id " + statisticalResourceId + " and versionLogic " + versionLogic + " found");
        }
        return result.get(0);
    }
}
