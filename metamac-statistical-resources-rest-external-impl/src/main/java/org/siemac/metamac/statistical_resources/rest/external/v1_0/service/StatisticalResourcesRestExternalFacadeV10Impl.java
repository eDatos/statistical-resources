package org.siemac.metamac.statistical_resources.rest.external.v1_0.service;

import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("statisticalResourcesRestExternalFacadeV10")
public class StatisticalResourcesRestExternalFacadeV10Impl implements StatisticalResourcesV1_0 {

    private final ServiceContext serviceContextRestExternal = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger         logger                     = LoggerFactory.getLogger(StatisticalResourcesRestExternalFacadeV10Impl.class);

    @Override
    public Dataset retrieveDataset(String agencyID, String resourceID, String version) {
        try {
            // Retrieve

            // Transform
            return null;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    /**
     * Throws response error, logging exception
     */
    private RestException manageException(Exception e) {
        logger.error("Error", e);
        if (e instanceof RestException) {
            return (RestException) e;
        } else {
            // do not show information details about exception to user
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestCommonServiceExceptionType.UNKNOWN);
            return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }
}
