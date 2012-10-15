package org.siemac.metamac.statistical.resources.core.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.serviceimpl.utils.StatisticalResourcesInvocationValidator;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalResourceService.
 */
@Service("statisticalResourceService")
public class StatisticalResourcesServiceImpl extends StatisticalResourcesServiceImplBase {
    
    public StatisticalResourcesServiceImpl() {
    }

    public Query retrieveQueryByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        StatisticalResourcesInvocationValidator.checkRetrieveQueryByUrn(urn, null);

        // Retrieve
        Query query = getQueryRepository().findByUrn(urn);
        return query;
    }
}
