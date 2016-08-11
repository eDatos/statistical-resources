package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Publication
 */
@Repository("publicationRepository")
public class PublicationRepositoryImpl extends PublicationRepositoryBase {

    public PublicationRepositoryImpl() {
    }

    public Publication retrieveByUrn(String urn) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> condition = criteriaFor(Publication.class).withProperty(PublicationProperties.identifiableStatisticalResource().urn()).eq(urn).distinctRoot().build();

        // Find
        List<Publication> result = findByCondition(condition);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one publication with urn " + urn);
        }

        return result.get(0);
    }
}
