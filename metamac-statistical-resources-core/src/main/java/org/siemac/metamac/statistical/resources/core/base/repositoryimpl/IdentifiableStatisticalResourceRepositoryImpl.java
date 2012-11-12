package org.siemac.metamac.statistical.resources.core.base.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceProperties;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for IdentifiableStatisticalResource
 */
@Repository("identifiableStatisticalResourceRepository")
public class IdentifiableStatisticalResourceRepositoryImpl extends IdentifiableStatisticalResourceRepositoryBase {

    public IdentifiableStatisticalResourceRepositoryImpl() {
    }

    public IdentifiableStatisticalResource retrieveByUrn(String urn) throws MetamacException {

        List<ConditionalCriteria> condition = criteriaFor(IdentifiableStatisticalResource.class).withProperty(IdentifiableStatisticalResourceProperties.urn()).eq(urn).distinctRoot().build();

        List<IdentifiableStatisticalResource> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.IDENTIFIABLE_STATISTICAL_RESOURCE_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one query with urn " + urn);
        }

        return result.get(0);

    }
}
