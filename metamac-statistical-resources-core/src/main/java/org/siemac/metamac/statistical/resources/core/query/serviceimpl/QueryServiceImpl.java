package org.siemac.metamac.statistical.resources.core.query.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForUpdateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.validators.QueryServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators.QueryConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of QueryService.
 */
@Service("queryService")
public class QueryServiceImpl extends QueryServiceImplBase {

    @Autowired
    IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;
    
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
        fillMetadataForCreateQuery(ctx, query);

        // Checks
        // TODO: Comprobar si hay que hacer alguno más
        // TODO: Comprobar si pueden ser comunes al resto de artefactos
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(query.getLifeCycleStatisticalResource());

        // Save
        query = getQueryRepository().save(query);

        return query;
    }

    @Override
    public Query updateQuery(ServiceContext ctx, Query query) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkUpdateQuery(ctx, query);

        // Fill metadata
        fillMetadataForUpdateQuery(query);

        // Check that could be update
        // TODO: Comprobar si hay que hacer alguno más
        // TODO: Comprobar si pueden ser comunes al resto de artefactos
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(query.getLifeCycleStatisticalResource());

        // Repository operation
        return getQueryRepository().save(query);

    }

    @Override
    public Query markQueryAsDiscontinued(ServiceContext ctx, Query query) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkMarkQueryAsDiscontinued(ctx, query);

        // Retrieve entity
        query = retrieveQueryByUrn(ctx, query.getLifeCycleStatisticalResource().getUrn());

        // Check that query is pending_review
        QueryConstraintValidator.checkQueryForMarkAsDiscontinued(query);

        // Change status
        query.setStatus(QueryStatusEnum.DISCONTINUED);

        return getQueryRepository().save(query);
    }

    @Override
    public void deleteQuery(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkDeleteQuery(ctx, urn);

        // Retrieve entity
        Query query = retrieveQueryByUrn(ctx, urn);

        // Check that query is pending_review
        BaseValidator.checkStatisticalResourceCanBeDeleted(query.getLifeCycleStatisticalResource());

        // Delete
        getQueryRepository().delete(query);
    }

    private void fillMetadataForCreateQuery(ServiceContext ctx, Query query) {
        FillMetadataForCreateResourceUtils.fillMetadataForCreateLifeCycleResource(query.getLifeCycleStatisticalResource(), StatisticalResourceTypeEnum.QUERY, ctx);
        query.setStatus(determineQueryStatus(query));
    }

    private QueryStatusEnum determineQueryStatus(Query query) {
        if (query.getDatasetVersion().getSiemacMetadataStatisticalResource().getIsLastVersion()) {
            return QueryStatusEnum.ACTIVE;
        } else {
            return QueryStatusEnum.DISCONTINUED;
        }
    }

    private void fillMetadataForUpdateQuery(Query query) {
        query.setStatus(determineQueryStatus(query));
        FillMetadataForUpdateResourceUtils.fillMetadataForUpdateLifeCycleResource(query.getLifeCycleStatisticalResource(), StatisticalResourceTypeEnum.QUERY);
    }
}
