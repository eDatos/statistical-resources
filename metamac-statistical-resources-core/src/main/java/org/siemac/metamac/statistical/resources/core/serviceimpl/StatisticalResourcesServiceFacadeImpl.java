package org.siemac.metamac.statistical.resources.core.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;
import org.siemac.metamac.statistical.resources.core.mapper.Do2DtoMapper;
import org.siemac.metamac.statistical.resources.core.mapper.Dto2DoMapper;
import org.siemac.metamac.statistical.resources.core.security.QueriesSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalResourcesServiceFacade.
 */
@Service("statisticalResourcesServiceFacade")
public class StatisticalResourcesServiceFacadeImpl extends StatisticalResourcesServiceFacadeImplBase {

    public StatisticalResourcesServiceFacadeImpl() {
    }

    @Autowired
    @Qualifier("do2DtoMapper")
    private Do2DtoMapper do2DtoMapper;

    @Autowired
    @Qualifier("dto2DoMapper")
    private Dto2DoMapper dto2DoMapper;

    protected Do2DtoMapper getDo2DtoMapper() {
        return do2DtoMapper;
    }

    protected Dto2DoMapper getDto2DoMapper() {
        return dto2DoMapper;
    }

    /**************************************************************************
     * QUERIES
     **************************************************************************/
    public QueryDto retrieveQueryByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueryByUrn(ctx);
        
        // Retrieve
        Query query = getStatisticalResourcesService().retrieveQueryByUrn(ctx, urn);
        
        // Transform
        QueryDto queryDto = getDo2DtoMapper().queryDoToDto(query);
        
        return queryDto;
    }
}
