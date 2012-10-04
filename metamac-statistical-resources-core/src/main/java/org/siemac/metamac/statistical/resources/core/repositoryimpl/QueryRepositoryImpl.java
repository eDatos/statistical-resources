package org.siemac.metamac.statistical.resources.core.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.domain.Query;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Query
 */
@Repository("queryRepository")
public class QueryRepositoryImpl extends QueryRepositoryBase {
    public QueryRepositoryImpl() {
    }

    public Query findByUrn(String urn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByUrn not implemented");

    }
}
