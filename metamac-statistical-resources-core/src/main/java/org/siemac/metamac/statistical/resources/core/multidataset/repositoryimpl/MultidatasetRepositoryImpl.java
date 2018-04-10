package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.Multidataset;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Multidataset
 */
@Repository("multidatasetRepository")
public class MultidatasetRepositoryImpl extends MultidatasetRepositoryBase {

    public MultidatasetRepositoryImpl() {
    }

    @Override
    public Multidataset retrieveByUrn(String urn) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> condition = criteriaFor(Multidataset.class).withProperty(MultidatasetProperties.identifiableStatisticalResource().urn()).eq(urn).distinctRoot().build();

        // Find
        List<Multidataset> result = findByCondition(condition);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.MULTIDATASET_VERSION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one multidataset with urn " + urn);
        }

        return result.get(0);

    }
}
