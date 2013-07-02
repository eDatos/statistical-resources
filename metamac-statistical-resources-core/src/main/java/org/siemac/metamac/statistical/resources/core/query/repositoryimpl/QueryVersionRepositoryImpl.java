package org.siemac.metamac.statistical.resources.core.query.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for QueryVersion
 */
@Repository("queryVersionRepository")
public class QueryVersionRepositoryImpl extends QueryVersionRepositoryBase {

    public QueryVersionRepositoryImpl() {
    }

    @Override
    public QueryVersion retrieveByUrn(String urn) throws MetamacException {

        List<ConditionalCriteria> condition = criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.lifeCycleStatisticalResource().urn()).eq(urn).distinctRoot().build();

        List<QueryVersion> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one query version with urn " + urn);
        }

        return result.get(0);
    }

}
