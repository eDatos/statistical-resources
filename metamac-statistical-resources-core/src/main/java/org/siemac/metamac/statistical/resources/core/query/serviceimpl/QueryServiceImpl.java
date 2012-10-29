package org.siemac.metamac.statistical.resources.core.query.serviceimpl;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.serviceimpl.utils.StatisticalResourcesInvocationValidator;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of QueryService.
 */
@Service("queryService")
public class QueryServiceImpl extends QueryServiceImplBase {

    public QueryServiceImpl() {
    }

    public Query retrieveQueryByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        StatisticalResourcesInvocationValidator.checkRetrieveQueryByUrn(urn, null);

        // Retrieve
        Query query = getQueryRepository().findByUrn(urn);
        return query;
    }

    @Override
    public List<Query> retrieveQueries(ServiceContext ctx) throws MetamacException {
        // Validations
        StatisticalResourcesInvocationValidator.checkRetrieveQueries(null);
        
        // Retrieve
        List<Query> queries = getQueryRepository().findAll(); 
        return queries;
    }

    @Override
    public PagedResult<Query> findQueriesByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        StatisticalResourcesInvocationValidator.checkFindQueriesByCondition(conditions, pagingParameter, null);
        
        // Find
        if (conditions == null) {
            conditions = ConditionalCriteriaBuilder.criteriaFor(Query.class).distinctRoot().build();
        }
        PagedResult<Query> queryPagedResult = getQueryRepository().findByCondition(conditions, pagingParameter);
        return queryPagedResult;
        
    }

    @Override
    public Query createQuery(ServiceContext ctx, Query query) throws MetamacException {
        // Validations
        StatisticalResourcesInvocationValidator.checkCreateQuery(query, null);
        
        // Fill metadata
        fillMetadataForCreateQuery(query);
        
        // Save
        query = getQueryRepository().save(query);
        
        return query;
    }


    @Override
    public Query updateQuery(ServiceContext ctx, Query query) throws MetamacException {
        // Validations
        StatisticalResourcesInvocationValidator.checkUpdateQuery(query, null);
        
        // Check that could be update
        // TODO
        
        // Repository operation
        return getQueryRepository().save(query);
        
        
    }
    
    private void fillMetadataForCreateQuery(Query query) {
        // TODO: Llamar al método del generator cuando se sepa cómo se construye la URN
        query.getNameableStatisticalResource().setUrn("todo:mock");
        query.getNameableStatisticalResource().setUri(null);
    }
}
