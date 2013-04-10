package org.siemac.metamac.statistical.resources.core.query.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForUpdateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
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
    private IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;
    
    @Autowired
    private QueryServiceInvocationValidator queryServiceInvocationValidator;
    
    @Autowired
    private DatasetVersionRepository datasetVersionRepository;   

    public QueryServiceImpl() {
    }

    @Override
    public QueryVersion retrieveQueryVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryVersionByUrn(ctx, urn);

        // Retrieve
        QueryVersion query = getQueryVersionRepository().retrieveByUrn(urn);
        return query;
    }

    @Override
    public List<QueryVersion> retrieveQueryVersions(ServiceContext ctx) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryVersions(ctx);

        // Retrieve
        List<QueryVersion> queries = getQueryVersionRepository().findAll();
        return queries;
    }

    @Override
    public PagedResult<QueryVersion> findQueryVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkFindQueryVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, Query.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<QueryVersion> queryPagedResult = getQueryVersionRepository().findByCondition(conditions, pagingParameter);
        return queryPagedResult;

    }

    @Override
    public QueryVersion createQueryVersion(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkCreateQueryVersion(ctx, queryVersion);

        Query query = createQueryResourceFromQueryVersion(queryVersion);
        query = getQueryRepository().save(query);
        
        // Fill metadata
        fillMetadataForCreateQueryVersion(ctx, queryVersion);

        // Checks
        // TODO: Comprobar si hay que hacer alguno más
        // TODO: Comprobar si pueden ser comunes al resto de artefactos
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryVersion.getLifeCycleStatisticalResource());

        // Save version
        queryVersion.setQuery(query);
        queryVersion = getQueryVersionRepository().save(queryVersion);

        return queryVersion;
    }
    
    @Override
    public QueryVersion updateQueryVersion(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkUpdateQueryVersion(ctx, queryVersion);

        // Fill metadata
        fillMetadataForUpdateQuery(queryVersion);

        // Check that could be update
        // TODO: Comprobar si hay que hacer alguno más
        // TODO: Comprobar si pueden ser comunes al resto de artefactos
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryVersion.getLifeCycleStatisticalResource());

        // Repository operation
        return getQueryVersionRepository().save(queryVersion);

    }

    @Override
    public QueryVersion markQueryVersionAsDiscontinued(ServiceContext ctx, QueryVersion query) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkMarkQueryVersionAsDiscontinued(ctx, query);

        // Retrieve entity
        query = retrieveQueryVersionByUrn(ctx, query.getLifeCycleStatisticalResource().getUrn());

        // Check that query is pending_review
        QueryConstraintValidator.checkQueryVersionForMarkAsDiscontinued(query);

        // Change status
        query.setStatus(QueryStatusEnum.DISCONTINUED);

        return getQueryVersionRepository().save(query);
    }

    @Override
    public void deleteQueryVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkDeleteQueryVersion(ctx, urn);

        // Retrieve entity
        QueryVersion queryVersion = retrieveQueryVersionByUrn(ctx, urn);

        // Check that query is pending_review
        BaseValidator.checkStatisticalResourceCanBeDeleted(queryVersion.getLifeCycleStatisticalResource());

        // Delete
        getQueryVersionRepository().delete(queryVersion);
    }

    private Query createQueryResourceFromQueryVersion(QueryVersion queryVersion) throws MetamacException {
        Query query = new Query();
        query.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        query.getIdentifiableStatisticalResource().setCode(queryVersion.getLifeCycleStatisticalResource().getCode());
        query.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(queryVersion.getLifeCycleStatisticalResource().getCode()));
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(query.getIdentifiableStatisticalResource());
        return query;
    }

    private void fillMetadataForCreateQueryVersion(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        FillMetadataForCreateResourceUtils.fillMetadataForCreateLifeCycleResource(queryVersion.getLifeCycleStatisticalResource(), ctx);
        queryVersion.setStatus(determineQueryStatus(queryVersion));
        queryVersion.getLifeCycleStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(queryVersion.getLifeCycleStatisticalResource().getCode(), queryVersion.getLifeCycleStatisticalResource().getVersionLogic()));
    }

    private QueryStatusEnum determineQueryStatus(QueryVersion queryVersion) throws MetamacException {
        if (datasetVersionRepository.isLastVersion(queryVersion.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())) {
            return QueryStatusEnum.ACTIVE;
        } else {
            return QueryStatusEnum.DISCONTINUED;
        }
    }

    private void fillMetadataForUpdateQuery(QueryVersion queryVersion) throws MetamacException {
        queryVersion.setStatus(determineQueryStatus(queryVersion));
        FillMetadataForUpdateResourceUtils.fillMetadataForUpdateLifeCycleResource(queryVersion.getLifeCycleStatisticalResource(), StatisticalResourceTypeEnum.QUERY);
    }
}
