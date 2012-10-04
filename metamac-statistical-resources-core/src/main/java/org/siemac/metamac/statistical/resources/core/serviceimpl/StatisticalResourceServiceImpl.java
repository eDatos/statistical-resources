package org.siemac.metamac.statistical.resources.core.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.domain.Query;

import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalResourceService.
 */
@Service("statisticalResourceService")
public class StatisticalResourceServiceImpl
    extends StatisticalResourceServiceImplBase {
    public StatisticalResourceServiceImpl() {
    }

    public Query retrieveQueryByUrn(ServiceContext ctx, String urn)
        throws MetamacException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveQueryByUrn not implemented");

    }
}
