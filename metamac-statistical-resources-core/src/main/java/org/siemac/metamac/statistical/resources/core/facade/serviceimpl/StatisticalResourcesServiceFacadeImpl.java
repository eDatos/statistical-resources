package org.siemac.metamac.statistical.resources.core.facade.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
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
    private QueryDo2DtoMapper queryDo2DtoMapper;

    @Autowired
    @Qualifier("queryDto2DoMapper")
    private QueryDto2DoMapper queryDto2DoMapper;

    
    protected QueryDo2DtoMapper getQueryDo2DtoMapper() {
        return queryDo2DtoMapper;
    }

    protected QueryDto2DoMapper getQueryDto2DoMapper() {
        return queryDto2DoMapper;
    }
    
    public StatisticalResourcesServiceFacadeImpl() {
    }

    
    /**************************************************************************
     * QUERIES
     **************************************************************************/
    public QueryDto retrieveQueryByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueryByUrn(ctx);
        
        // Retrieve
        Query query = getQueryService().retrieveQueryByUrn(ctx, urn);
        
        // Transform
        QueryDto queryDto = getQueryDo2DtoMapper().queryDoToDto(query);
        
        return queryDto;
    }

    @Override
    public List<QueryDto> retrieveQueries(ServiceContext ctx) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryDto createQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryDto updateQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PagedResult<QueryDto> findQueriesByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // TODO Auto-generated method stub
        return null;
    }
}
