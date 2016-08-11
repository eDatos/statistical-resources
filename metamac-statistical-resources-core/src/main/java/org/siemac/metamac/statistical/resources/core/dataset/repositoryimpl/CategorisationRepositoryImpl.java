package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CategorisationProperties;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Categorisation
 */
@Repository("categorisationRepository")
public class CategorisationRepositoryImpl extends CategorisationRepositoryBase {

    public CategorisationRepositoryImpl() {
    }

    @Override
    public Categorisation retrieveByUrn(String urn) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(Categorisation.class).withProperty(CategorisationProperties.versionableStatisticalResource().urn()).eq(urn).distinctRoot().build();

        List<Categorisation> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.CATEGORISATION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one categorisation with urn " + urn);
        }

        return result.get(0);
    }

    @Override
    public List<Categorisation> retrieveCategorisationsByDatasetVersionUrn(String urn) throws MetamacException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("urn", urn);
        List<Categorisation> result = findByQuery("from Categorisation where datasetVersion.siemacMetadataStatisticalResource.urn = :urn order by id", parameters);
        return result;
    }

}
