package org.siemac.metamac.statistical.resources.core.query.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.validators.QueryServiceInvocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of QueryService.
 */
@Service("queryService")
public class QueryServiceImpl extends QueryServiceImplBase {

    @Autowired
    QueryServiceInvocationValidator queryServiceInvocationValidator;
    
    public QueryServiceImpl() {
    }

    public Query retrieveQueryByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryByUrn(ctx, urn);

        // Retrieve
        Query query = getQueryRepository().retrieveByUrn(urn);
        return query;
    }

    @Override
    public List<Query> retrieveQueries(ServiceContext ctx) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueries(ctx);
        
        // Retrieve
        List<Query> queries = getQueryRepository().findAll(); 
        return queries;
    }

    @Override
    public PagedResult<Query> findQueriesByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkFindQueriesByCondition(ctx, conditions, pagingParameter);
        
        // Find
        conditions = CriteriaUtils.initConditions(conditions, Query.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<Query> queryPagedResult = getQueryRepository().findByCondition(conditions, pagingParameter);
        return queryPagedResult;
        
    }

    @Override
    public Query createQuery(ServiceContext ctx, Query query) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkCreateQuery(ctx, query);
        
        // Fill metadata
        fillMetadataForCreateQuery(query);
        
        // Save
        query = getQueryRepository().save(query);
        
        return query;
    }


    @Override
    public Query updateQuery(ServiceContext ctx, Query query) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkUpdateQuery(ctx, query);
        
        // Check that could be update
        // TODO
        
        // Repository operation
        return getQueryRepository().save(query);
        
        
    }
    
    private void fillMetadataForCreateQuery(Query query) {
        query.getLifeCycleStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacQueryUrn(query.getLifeCycleStatisticalResource().getCode()));
        query.getLifeCycleStatisticalResource().setUri(null);
    }
}
