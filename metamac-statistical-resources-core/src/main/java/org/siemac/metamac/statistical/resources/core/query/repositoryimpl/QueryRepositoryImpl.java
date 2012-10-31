package org.siemac.metamac.statistical.resources.core.query.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Query
 */
@Repository("queryRepository")
public class QueryRepositoryImpl extends QueryRepositoryBase {

    public QueryRepositoryImpl() {
    }

    public Query retrieveByUrn(String urn) throws MetamacException {
        
        List<ConditionalCriteria> condition = criteriaFor(Query.class).withProperty(QueryProperties.nameableStatisticalResource().urn()).eq(urn).distinctRoot().build();
        
        List<Query> result = findByCondition(condition);
        
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one query with urn " + urn);
        }
        
        return result.get(0);
    }
}
