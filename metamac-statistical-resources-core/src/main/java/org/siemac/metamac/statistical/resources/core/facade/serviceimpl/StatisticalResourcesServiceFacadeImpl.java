package org.siemac.metamac.statistical.resources.core.facade.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.criteria.mapper.MetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.criteria.mapper.SculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.security.QueriesSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalResourcesServiceFacade.
 */
@Service("statisticalResourcesServiceFacade")
public class StatisticalResourcesServiceFacadeImpl extends StatisticalResourcesServiceFacadeImplBase {

    @Autowired
    @Qualifier("queryDo2DtoMapper")
    private QueryDo2DtoMapper                      queryDo2DtoMapper;

    @Autowired
    @Qualifier("queryDto2DoMapper")
    private QueryDto2DoMapper                      queryDto2DoMapper;

    @Autowired
    private MetamacCriteria2SculptorCriteriaMapper metamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private SculptorCriteria2MetamacCriteriaMapper sculptorCriteria2MetamacCriteriaMapper;

    public StatisticalResourcesServiceFacadeImpl() {
    }

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------
    
    public QueryDto retrieveQueryByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueryByUrn(ctx);

        // Retrieve
        Query query = getQueryService().retrieveQueryByUrn(ctx, urn);

        // Transform
        QueryDto queryDto = queryDo2DtoMapper.queryDoToDto(query);

        return queryDto;
    }

    @Override
    public List<QueryDto> retrieveQueries(ServiceContext ctx) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueries(ctx);

        // Retrieve
        List<Query> queries = getQueryService().retrieveQueries(ctx);

        // Transform
        List<QueryDto> queriesDto = queryDo2DtoMapper.queryDoListToDtoList(queries);

        return queriesDto;
    }

    @Override
    public QueryDto createQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canCreateQuery(ctx);

        // Transform
        Query query = queryDto2DoMapper.queryDtoToDo(queryDto);

        // Create
        query = getQueryService().createQuery(ctx, query);

        // Transform to DTO
        queryDto = queryDo2DtoMapper.queryDoToDto(query);

        return queryDto;
    }

    @Override
    public QueryDto updateQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canUpdateQuery(ctx);

        // Transform
        Query query = queryDto2DoMapper.queryDtoToDo(queryDto);

        // Update
        query = getQueryService().updateQuery(ctx, query);

        // Transform to Dto
        queryDto = queryDo2DtoMapper.queryDoToDto(query);

        return queryDto;
    }

    @Override
    public MetamacCriteriaResult<QueryDto> findQueriesByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        QueriesSecurityUtils.canFindQueriesByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getQueryCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<Query> result = getQueryService().findQueriesByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<QueryDto> metamacCriteriaResult = sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultQuery(result, sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }
}
